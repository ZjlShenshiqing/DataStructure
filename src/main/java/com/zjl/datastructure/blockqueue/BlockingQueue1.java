package com.zjl.datastructure.blockqueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjlk
 * @date 2025/10/4 上午10:01
 * @description BlockingQueue1
 */
public class BlockingQueue1<E> implements BlockingQueue<E> {

    private final E[] array;
    private int head;
    private int tail;
    private int size;

    public BlockingQueue1(int capacity) {
        array = (E[]) new Object[capacity];
    }

    private ReentrantLock lock = new ReentrantLock();

    // 该条件通常用于：当队列为空时，从队头取元素的线程（消费者）在此等待
    // 直到有其他线程向队列中添加了元素（使队列非空），才会被唤醒。
    private Condition headWaits = lock.newCondition();

    // 该条件通常用于：当队列已满时，向队尾添加元素的线程（生产者）在此等待，
    // 直到有其他线程从队列中移除了元素（腾出空间），才会被唤醒。
    private Condition tailWaits = lock.newCondition();

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == array.length;
    }

    @Override
    public void offer(E e) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 要用循环而不是if，防止线程抢占
            while (isFull()) {
                // 如果满了，让线程进行等待，但是等待时间无上限，直到有线程唤醒TA
                tailWaits.await();
            }

            array[tail] = e;
            // 防止数组边界影响
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal(); // 唤醒消费者等待的线程
        } finally {
            lock.unlock();
        }
    }

    // 在有限的时间内进行等待
    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 将给定的毫秒（milliseconds）时间值转换为纳秒（nanoseconds），并存储在 long 类型的变量 time 中
            long time = TimeUnit.MILLISECONDS.toNanos(timeout);

            // 要用循环而不是if，防止线程抢占
            while (isFull()) {
                // 如果剩余等待时间已耗尽（time <= 0），说明超时，无法继续等待
                if (time <= 0) {
                    return false;
                }

                // 如果满了，让线程进行等待，这个等待是时间有上限的等待
                // 返回值重新赋给 'time'，用于下一次循环判断是否还有时间继续等待
                time = tailWaits.awaitNanos(time); // 最多等待多少纳秒，到上限直接false，注意这个方法有返回值，返回值代表的是剩余多久到达上限
            }

            array[tail] = e;
            // 防止数组边界影响
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal(); // 唤醒消费者等待的线程
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (isEmpty()) {
                headWaits.await();
            }
            E e = array[head];
            // 垃圾回收
            array[head] = null;
            if (++head == array.length) {
                head = 0;
            }
            // 唤醒生产者线程
            tailWaits.signal();
            size--;
            return e;
        } finally {
            lock.unlock();
        }
    }
}
