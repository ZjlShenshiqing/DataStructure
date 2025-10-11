package com.zjl.datastructure.priorqueue;

import com.zjl.datastructure.queue.Queue;

/**
 * 优先级队列
 *
 * @author zhangjlk
 * @date 2025/9/30 下午4:27
 * @description PriorityQueue
 */
public class PriorityQueue2<E extends Priority> implements Queue<E> {

    /**
     * 优先级数组
     */
    Priority[] array;

    /**
     * 有效元素长度
     */
    int size;

    public PriorityQueue2(int capacity) {
        array = new Priority[capacity];
    }

    /**
     * 插入排序操作
     */
    private void insert(E e) {
        int i = size - 1;
        while (i >= 0 && array[i].priority() > e.priority()) {
            array[i + 1] = array[i];
            i--;
        }
        array[i + 1] = e; // 因为i--了，所以这个时候i + 1的地方才是空的，之前在i + 1这里的元素跑到 i + 1 + 1去了
    }

    // O(n)
    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        }
        insert(e);
        size++;
        return true;
    }

    // O(1)
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        E e = (E) array[size - 1];
        size --;
        array[size - 1] = null;
        return e;
    }

    // O(1)
    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }

        return (E) array[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }
}
