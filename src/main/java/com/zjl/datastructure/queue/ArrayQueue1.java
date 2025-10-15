package com.zjl.datastructure.queue;

import java.util.Iterator;

/**
 * 环形数组实现队列
 *
 * @author zhangjlk
 * @date 2025/9/24 12:45
 */
public class ArrayQueue1<E> implements Queue<E>, Iterable<E> {

    private E[] array; // 环形数组
    private int head = 0; // 头指针
    private int tail = 0; // 尾指针，注意尾指针必须为空

    @SuppressWarnings("all")
    public ArrayQueue1(int capacity) {
        // “浪费一个空间”，后续可以通过这个区分空/满状态
        array = (E[]) new Object[capacity + 1];
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
        // 当头尾指针相同时，队列为空
        return head == tail;
    }

    @Override
    public boolean isFull() {
        // 当 tail 再前进一位就等于 head 时，队列为满
        return (tail + 1) % array.length == head;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int p = head;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E value = array[p];
                p = (p + 1) % array.length;
                return value;
            }
        };
    }
}
