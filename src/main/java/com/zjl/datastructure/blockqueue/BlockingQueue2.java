package com.zjl.datastructure.blockqueue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列 - 双锁实现
 *
 * @author zhangjlk
 * @date 2025/10/5 上午11:01
 * @description BlockingQueue2
 */
public class BlockingQueue2<E> implements BlockingQueue<E> {

    private final E[] array;
    private int head;
    private int tail;
    private AtomicInteger size = new AtomicInteger(0);

    ReentrantLock headLock = new ReentrantLock();  // 保护 head 的锁
    Condition headWaits = headLock.newCondition(); // 队列空时，需要等待的线程集合

    ReentrantLock tailLock = new ReentrantLock();  // 保护 tail 的锁
    Condition tailWaits = tailLock.newCondition(); // 队列满时，需要等待的线程集合

    public BlockingQueue2(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    @Override
    public void offer(E e) throws InterruptedException {
        int c;
        tailLock.lockInterruptibly();
        try {
            while (isFull()) {
                tailWaits.await();
            }

            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            c = size.getAndIncrement(); // 原子地 +1 并返回新值 , 他这个方法是先获取旧的值，然后再进行自增

            if (c + 1 < array.length) {
                tailWaits.signal();
            }
        } finally {
            tailLock.unlock();
        }
        if (c == 0) {
            headLock.lock();
            try {
                headWaits.signal();
            } finally {
                headLock.unlock();
            }
        }
    }

    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        return false;
    }

    @Override
    public E poll() throws InterruptedException {
        int c; // 取走前的元素个数
        E e;
        headLock.lockInterruptibly();
        try {
            while (isEmpty()) {
                headWaits.await();
            }

            e = array[head];
            array[head] = null;
            if (++head == array.length) {
                head = 0;
            }

            c = size.getAndDecrement(); // 原子的 -1 并返回新值

            if (c > 1) {
                headWaits.signal();
            }
        } finally {
            headLock.unlock();
        }

        // 队列由满 -> 不满时， poll队列唤醒offer的线程
        if (c == array.length) {
            tailLock.lock();
            try {
                tailWaits.signal();
            } finally {

            }
        }
        return e;
    }

    private boolean isEmpty() {
        return size.get() == 0;
    }

    private boolean isFull() {
        return size.get() == array.length;
    }
}
