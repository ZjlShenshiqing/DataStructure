package com.zjl.datastructure.blockqueue;

/**
 * 阻塞队列
 *
 * 会用锁来保证线程安全
 * 使用条件变量让 poll 和 offer 线程进入等待状态，而不是循环尝试，这样会导致空转
 *
 * @author zhangjlk
 * @date 2025/10/4 上午9:53
 * @description BlockingQueue
 */
public interface BlockingQueue<E> {

    void offer(E e) throws InterruptedException;

    boolean offer(E e, long timeout) throws InterruptedException;

    E poll() throws InterruptedException;
}
