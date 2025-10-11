package com.zjl.datastructure.stack;

/**
 * 使用队列去模拟栈
 *
 * @author zhangjlk
 * @date 2025/9/28 下午6:49
 * @description UseQueueToMakeStack
 */
public class UseQueueToMakeStack {
    /**
     *      栈顶       栈底
     *     队列头      队列尾
     * 关于添加元素:
     * 因为之前设计的队列都是从队尾添加元素，队首去除元素，所以当添加元素的时候，元素全都是到栈底而不是栈顶，所以要调换一下
     * 要把队列头的元素移除，再加入到队尾去，这样才能实现栈的数据结构
     *
     * 移除元素是跟队列是一致的
     */
    class MyStack {

        ArrayQueue3<Integer> queue = new ArrayQueue3<>(100);
        private int size = 0;

        public MyStack() {

        }

        public void push(int x) {
            queue.offer(x);
            for (int i = 0; i < size; i++) {
                queue.offer(queue.poll());
            }
            size++;
        }

        public int pop() {
            size--;
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    class ArrayQueue3<E> {

        private final E[] array;
        int head = 0;
        int tail = 0;

        @SuppressWarnings("all")
        public ArrayQueue3(int c) {
            c -= 1;
            c |= c >> 1;
            c |= c >> 2;
            c |= c >> 4;
            c |= c >> 8;
            c |= c >> 16;
            c += 1;
            array = (E[]) new Object[c];
        }

        public boolean offer(E value) {
            if (isFull()) {
                return false;
            }
            array[tail & (array.length - 1)] = value;
            tail++;
            return true;
        }

        public E poll() {
            if (isEmpty()) {
                return null;
            }
            E value = array[head & (array.length - 1)];
            head++;
            return value;
        }

        public E peek() {
            if (isEmpty()) {
                return null;
            }
            return array[head & (array.length - 1)];
        }

        public boolean isEmpty() {
            return head == tail;
        }

        public boolean isFull() {
            return tail - head == array.length;
        }
    }
}
