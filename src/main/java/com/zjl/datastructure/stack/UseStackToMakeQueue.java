package com.zjl.datastructure.stack;

import java.util.Iterator;

/**
 * 使用栈来模拟队列
 *
 * @author zhangjlk
 * @date 2025/9/28 下午6:01
 * @description UseStackToMakeQueue
 */
public class UseStackToMakeQueue {

    /**
     * 实现思路：
     *     队列头          队列尾
     * 栈1 顶  底      栈2 顶  底
     */
    class MyQueue {

        // 使用栈来模拟队列
        ArrayStack<Integer> left = new ArrayStack<>(100); // 队列左
        ArrayStack<Integer> right = new ArrayStack<>(100); // 队列右

        public MyQueue() {

        }

        // 将元素 x 推到队列的末尾,也就是向队列尾部添加元素
        public void push(int x) {
            // 往队列尾部添加，也就是往链表右边添加
            right.push(x);
        }

        // 从队列的开头移除并返回元素
        public int pop() {
            /**
             * 尾部添加是都添加在右边这边
             * 但是我们现在要从左边栈去去除元素
             *
             * 所以要把right里面的元素移动到left这边来
             * right.pop(a) // 右栈弹出a
             * left.push(a) // 左栈压入a
             *
             * b也是按照上面的来
             * 移除就是调用left.pop就行
             */
            if (left.isEmpty()) {
                while (!right.isEmpty()) {
                    left.push(right.pop());
                }
            }
            return left.pop();
        }

        // 返回队列开头的元素
        public int peek() {
            if (left.isEmpty()) {
                while (!right.isEmpty()) {
                    left.push(right.pop());
                }
            }
            return left.peek();
        }

        // 如果队列为空，返回 true ；否则，返回 false
        public boolean empty() {
            return left.isEmpty() && right.isEmpty();
        }
    }

    class ArrayStack<E> implements Iterable<E> {

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


        public boolean push(E value) {
            if (isFull()) {
                return false;
            }
            array[top] = value;
            top++;
            return true;
        }

        public E pop() {
            if (isEmpty()) {
                return null;
            }

            E value = array[top - 1];
            top--; // 不用真正的清理掉，只需要读不到就可以了
            return value;
        }

        public E peek() {
            if (isEmpty()) {
                return null;
            }

            return array[top - 1];
        }

        public boolean isEmpty() {
            return top == 0;
        }

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
}
