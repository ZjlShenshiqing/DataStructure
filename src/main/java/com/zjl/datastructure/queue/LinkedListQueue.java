package com.zjl.datastructure.queue;

import java.util.Iterator;

/**
 * 使用单向环形带哨兵链表的方式实现队列
 *
 * @author zhangjlk
 * @date 2025/9/24 11:27
 */
public class LinkedListQueue<E> implements Queue<E>, Iterable<E> {

    // 节点类
    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    // 头节点，是不存数据的哨兵节点
    private Node<E> head = new Node<>(null, null);

    // 尾节点
    private Node<E> tail = head;

    // 当前队列的节点数
    int size;

    // 整个队列的容量
    int capacity = Integer.MAX_VALUE;

    // 让尾节点指向头节点，完成环形
    public LinkedListQueue() {
        tail.next = head;
    }

    // 指定容量
    public LinkedListQueue(int capacity) {
        this.capacity = capacity;
        // 让尾节点指向头节点，完成环形
        tail.next = head;
    }


    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false; // 队列满了，不能再添加新节点了
        }
        Node<E> added = new Node<>(value, head); // 初始化节点，注意这并没有加入到队列中
        tail.next = added; // 加入到队列到尾部
        tail = added; // 更新尾节点
        size++;
        return true; // 返回插入成功的结果
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        Node<E> removed = head.next;
        head.next = removed.next;
        if (removed == tail) {
            tail = head;
        }
        size--;
        return removed.value;
    }

    /**
     * 从队列头获取值
     * @return
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }

        return head.next.value;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
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
                return p != head;
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
