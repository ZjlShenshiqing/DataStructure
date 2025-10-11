package com.zjl.datastructure.priorqueue;

import com.zjl.datastructure.queue.Queue;

/**
 * 优先级队列
 *
 * @author zhangjlk
 * @date 2025/9/30 下午4:27
 * @description PriorityQueue
 */
public class PriorityQueue1<E extends Priority> implements Queue<E> {

    /**
     * 优先级数组
     */
    Priority[] array;

    /**
     * 有效元素长度
     */
    int size;

    public PriorityQueue1(int capacity) {
        array = new Priority[capacity];
    }

    // 入队时间复杂度是O(1)
    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }
        array[size] = value;
        size++;
        return true;
    }

    /**
     * 找到优先级最高的索引值
     * @return 索引值
     */
    private int selectMax() {
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (array[i].priority() > array[max].priority()) {
                max = i;
            }
        }

        return max;
    }

    /**
     * 删除元素
     * @param index 删除元素的索引
     */
    private void remove(int index) {
        // 在这个范围外不需要移动
        if (index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
    }

    // 因为要遍历整个数组，出队的时间复杂度是O(n)
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        int max = selectMax();
        E e = (E) array[max];
        size--;
        array[size - 1] = null;
        return e;
    }

    // 因为要遍历整个数组，出队的时间复杂度是O(n)
    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }

        int max = selectMax();
        return (E) array[max];
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
