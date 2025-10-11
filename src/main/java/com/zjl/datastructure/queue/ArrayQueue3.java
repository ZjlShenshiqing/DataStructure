package com.zjl.datastructure.queue;

import java.util.Iterator;

/**
 * @author zhangjlk
 * @date 2025/9/25 下午3:03
 * @description ArrayQueue3
 */
public class ArrayQueue3<E> implements Queue<E>, Iterable<E> {

    private int head = 0;
    private int tail = 0;
    private final E[] array;
    private final int capacity;

    // 如果除数是2的n次方，被除数的后n位就是我们的余数 注意这里说的是被除数的2进制表示，然后被除数右移n位就是商了
    // 余数就是被除数保留的n位
    // 求被除数后n位的方法， 就是与2^n - 1 进行按位与，就能得到被除数的后n位，也就是商
    @SuppressWarnings("all")
    public ArrayQueue3(int capacity) {
        if ((capacity & capacity - 1) != 0) {
            throw new IllegalArgumentException("capacity 必须为 2 的幂");
        }
        this.capacity = capacity;
        array = (E[]) new Object[this.capacity];
    }

    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }
        array[tail & capacity - 1] = value; // 余数
        tail++; // tail 本身不是索引，只是用来记录的，要计算才能得到索引，计算公式如上所示
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E value = array[head & capacity - 1];
        head++;
        return value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return array[head & capacity - 1];
    }

    @Override
    public boolean isEmpty() {
        return tail - head == 0;
    }

    @Override
    public boolean isFull() {
        return tail - head == capacity;
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
                E value = array[p & capacity - 1];
                p++;
                return value;
            }
        };
    }
}
