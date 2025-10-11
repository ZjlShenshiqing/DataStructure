package com.zjl.datastructure.queue;

import java.util.Iterator;

/**
 * 环形数组实现队列
 *
 * @author zhangjlk
 * @date 2025/9/24 15:39
 */
public class ArrayQueue2<E>  implements Queue<E>, Iterable<E> {

    private E[] array; // 环形数组
    private int head = 0; // 头指针
    private int tail = 0; // 尾指针，注意尾指针必须为空
    private int size = 0; // 队列中元素的个数

    @SuppressWarnings("all")
    public ArrayQueue2(int capacity) {
        array = (E[]) new Object[capacity];
    }

    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }

        array[tail] = value;

        /**
         * 如果tail + 1 < array.length的话，取模的结果就是它本身
         * 如果等于array.length的话，取模的结果就是0，这样就会绕过最后一位，去第一位
         */
        tail = (tail + 1) % array.length;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        E value = array[head];

        // 移动 head 指针，让它指向下一个元素，这样就相当于head指针的元素从队列出队了
        head = (head + 1) % array.length;
        size--;
        return value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return array[head];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int p = head;
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public E next() {
                E value = array[p];
                p = (p + 1) % array.length;
                count++;
                return value;
            }
        };
    }
}
