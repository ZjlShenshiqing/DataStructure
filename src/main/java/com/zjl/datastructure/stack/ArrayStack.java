package com.zjl.datastructure.stack;

import java.util.Iterator;

/**
 * 基于数组实现栈
 *
 * @author zhangjlk
 * @date 2025/9/26 上午11:12
 * @description ArrayStack
 */
public class ArrayStack<E> implements Stack<E>, Iterable<E> {

    private E[] array;
    private int top; // 栈顶指针

    /**
     * 基于数组实现栈的思路如下所示
     *
     * 底          顶
     * 0   1   2   3  因为数组是右边好进行元素的增删操作，所以如果使用数组实现的话，一般栈顶都会设置在右边
     */

    @SuppressWarnings("all")
    public ArrayStack(int capacity) {
        this.array = (E[]) new Object[capacity];
    }


    @Override
    public boolean push(E value) {
        if (isFull()) {
            return false;
        }
        array[top] = value;
        top++;
        return true;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }

        E value = array[top - 1];
        top--; // 不用真正的清理掉，只需要读不到就可以了
        return value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }

        return array[top - 1];
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public boolean isFull() {
        return top == array.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int p = top;

            @Override
            public boolean hasNext() {
                return p > 0;
            }

            @Override
            public E next() {
                E value = array[p - 1];
                return value;
            }
        };
    }
}
