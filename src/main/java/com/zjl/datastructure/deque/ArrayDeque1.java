package com.zjl.datastructure.deque;

import java.util.Iterator;

/**
 * 基于循环数组实现
 *
 * @author zhangjlk
 * @date 2025/9/29 上午11:51
 * @description ArrayDeque1
 */
public class ArrayDeque1<E> implements Deque<E>, Iterable<E> {

    /**
     * head - h
     * tail - t
     *
     * tail 指向的是尾部后面一个空位（没数据！）
     * 最后一个有效元素在 tail - 1 的位置
     *
     * h
     * t
     * 0  1  2  3
     *
     * offerLast:往后添加元素进入队列，先添加到tail指针指向的位置，然后tail再往后+1
     * offerFirst:往前添加元素进入队列，先head - 1，再添加到head指针指向的位置
     *
     * head == tail 空
     * head ~ tail = tail - 1 队列满，存放元素的位置是 tail - 1，得浪费一个位置区分空还是满
     *
     * 删除元素
     * pollFirst 先获取要移除的值，再让head++
     * pollLast  先tail - 1, 再获取要移除的值
     */

    @Override
    public boolean offerFirst(E e) {
        if (isFull()) {
            return false;
        }
        head = dec(head, array.length); // 要将head - 1
        array[head] = e; // 赋值到head索引上
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull()) {
            return false;
        }
        array[tail] = e; // 赋值
        tail = inc(tail, array.length); // 将tail：末指针位置+1
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }

        E e = array[head];
        array[head] = null; // 如果是引用对象，需要解除引用，并进行垃圾回收
        head = inc(head, array.length);
        return e;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        E e = array[tail];
        tail = dec(tail, array.length);
        array[tail] = null; // 如果是引用对象，需要解除引用，并进行垃圾回收
        return e;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        E e = array[head];
        return e;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        E e = array[dec(tail, array.length)]; // 这样做是防止数组越界
        return e;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean isFull() {
        // 要分两种情况进行判断，一种是tail > head, 一种是tail < head的情况
        if (tail > head) {
            return tail - head == array.length - 1;
        } else if (tail < head) {
            /**
             * tail head
             * 0    1   2   3
             */
            return head - tail == 1;
        } else {
            return false;
        }
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
                E e = array[p];
                p = inc(p, array.length);
                return e;
            }
        };
    }

    /**
     * 增加数组下标时判断是否越界
     */
    static int inc(int i, int length) {
        if (i + 1 >= length){
            return 0; // 让越界的指针返回到头节点
        }
        return i + 1;
    }

    /**
     * 减少数组下标时判断是否越界
     */
    static int dec(int i, int length) {
        if (i - 1 < 0){
            // 如果减完小于 0，就跳到数组最后一个位置（length - 1）
            return length - 1; // 到开头了，绕回末尾
        }

        return i - 1;
    }

    E[] array;
    int head;
    int tail;

    public ArrayDeque1(int capacity) {
        array = (E[]) new Object[capacity + 1]; // 因为tail指针指向的地方没有元素，所以容量得+1
    }
}
