package com.zjl.datastructure.deque;

import java.util.Iterator;

/**
 * 基于双向环形链表实现
 *
 * @author zhangjlk
 * @date 2025/9/29 上午10:45
 * @description LinkedListQueue
 */
public class LinkedListDeque<E> implements Deque<E>, Iterable<E> {

    /**
     * 双向环形链表
     * @param <E>
     */
    static class Node<E> {
        Node<E> prev;
        E value;
        Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    int capacity;
    int size;
    Node<E> sentinel = new Node<>(null, null, null);

    public LinkedListDeque(int capacity) {
        this.capacity = capacity;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    // sentinel <-> 新加的节点 <-> sentinel.next
    @Override
    public boolean offerFirst(E e) {
        // 判断队列是否已经满了
        if (isFull()) {
            return false;
        }

        // 加入节点，并改变指向的指针，实现插入操作
        Node<E> a = sentinel;
        Node<E> b = sentinel.next;
        Node<E> added = new Node<>(a, e, b); // 新加入节点
        a.next = added;
        b.prev = added;
        size++;
        return true;
    }

    // sentinel.prev <-> 新加入节点 <-> sentinel
    @Override
    public boolean offerLast(E e) {
        if (isFull()) {
            return false;
        }

        Node<E> a = sentinel.prev;
        Node<E> b = sentinel;
        Node<E> added = new Node<>(a, e, b); // 新加入节点
        a.next = added;
        b.prev = added;
        size++;
        return true;
    }

    // sentinel <-> 移除的节点 <-> sentinel.next
    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<E> a = sentinel;
        Node<E> removed = sentinel.next;
        Node<E> b = sentinel.next.next;
        a.next = b;
        b.prev = a;
        size--;
        return removed.value;
    }

    // sentinel.prev <-> 移除的节点 <-> sentinel
    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }

        Node<E> a = sentinel.prev.prev;
        Node<E> removed = sentinel.prev;
        Node<E> b = sentinel;
        a.next = b;
        b.prev = a;
        size--;
        return removed.value;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.next.value;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.prev.value;
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

            Node<E> p = sentinel.next;

            @Override
            public boolean hasNext() {
                return p.next != sentinel;
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
