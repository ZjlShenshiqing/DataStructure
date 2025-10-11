package com.zjl.datastructure.stack;

import java.util.Iterator;

/**
 * 基于链表的栈实现
 *
 * @author zhangjlk
 * @date 2025/9/26 上午10:36
 * @description LinkedListStack
 */
public class LinkedListStack<E> implements Stack<E>, Iterable<E> {

    private int capacity; // 栈的最大容量
    private int size;     // 栈中元素的个数

    // 头节点,其实这是一个为了方便操作的哨兵节点，真正的栈顶是head.next
    private Node<E> head = new Node<>(null, null); // 只是再一边添加移除，那么其实是不需要两个指针，一个head指针就够了

    public LinkedListStack(int capacity) {
        this.capacity = capacity;
    }

    static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean push(E value) {
        if (isFull()) {
            return false;
        }

        // 新节点插入到哨兵 head 之后，成为新的栈顶, 同时哨兵指向这个节点
        head.next = new Node<>(value, head.next);

        size ++;
        return true;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        // 获取第一个节点
        Node<E> first = head.next;
        // 移除第一个节点
        head.next = first.next;
        size--;
        return first.value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        // 获取第一个节点
        Node<E> first = head.next;
        return first.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != null;
            }

            @Override
            public E next() {
                E value = p.value;
                p = p.next;
                return value;
            }
        };
    }
}
