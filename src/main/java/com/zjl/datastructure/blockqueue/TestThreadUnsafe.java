package com.zjl.datastructure.blockqueue;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjlk
 * @date 2025/10/3 上午11:53
 * @description ThreadUnSafe
 */
public class TestThreadUnsafe {
    private final String[] array = new String[10];
    private int tail = 0;
    private int size = 0;
    ReentrantLock lock = new ReentrantLock();  // 锁对象
    Condition tailWaits = lock.newCondition(); // 条件变量对象

    /**
     * 生产者放入队列
     * @param e 数据
     * @throws InterruptedException
     */
    public void offer(String e) throws InterruptedException {
        if (isFull()) {
            // 如果队列满了，应该做的事情, 先让offer线程阻塞，等到队列不满了，将线程唤醒并添加元素进去
            tailWaits.await();	// 当队列满时, 当前线程进入 tailWaits 等待，线程进入阻塞状态
        }
        lock.lockInterruptibly();
        try {
            array[tail] = e;
            // 将 tail 指针向前移动一位（即 tail = tail + 1），但如果已经到达数组末尾，就“绕回”到数组开头（即 tail = 0）
            if (++tail == array.length) {
                tail = 0;
            }
            tail++; // 尾指针 ++
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    private boolean isFull() {
        return size == array.length;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        TestThreadUnsafe queue = new TestThreadUnsafe();
        new Thread(()-> queue.offer("e1"), "t1").start(); // 线程1执行入队操作
        new Thread(()-> queue.offer("e2"), "t2").start(); // 线程2执行入队操作
    }
}
