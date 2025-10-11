package com.zjl.datastructure.linkList;

import java.util.Iterator;

/**
 * 环形链表
 * @author zhangjlk
 * @date 2025/9/9 12:18
 */
public class DoublyLinkedListSentinel implements Iterable<Integer> {

    /**
     * 使用迭代器遍历环形链表
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            Node p = sentinel.next;

            @Override
            public boolean hasNext() {
                return p != sentinel;
            }

            @Override
            public Integer next() {
                int val = p.val;
                p = p.next;
                return val;
            }
        };
    }

    /**
     * 节点类
     */
    private static class Node {
        int val;
        Node next;
        Node prev;

        public Node(int val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node sentinel = new Node(-1, null, null);

    /**
     * 初始化哨兵节点
     */
    public DoublyLinkedListSentinel() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * 添加到节点的第一个地方去
     * @param val 待添加值
     */
    public void addFirst(int val) {
        Node a = sentinel; // 前序节点
        Node b = sentinel.next; // 后面的节点

        Node added = new Node(val, a, b);

        a.next = added;
        b.prev = added;
    }

    /**
     * 添加到节点的最后一个地方去
     * @param val 待添加值
     */
    public void addLast(int val) {
        Node a = sentinel.prev;
        Node b = sentinel;

        Node added = new Node(val, a, b);

        a.next = added;
        b.prev = added;
    }

    /**
     * 删除哨兵后的第一个节点
     */
    public void removeFirst() {
        Node removed = sentinel.next;

        if (removed == sentinel) {
            throw new IllegalArgumentException("删除的是非法的");
        }

        Node a = sentinel;
        Node b = removed.next;

        a.next = b;
        b.prev = a;
    }

    /**
     * 删除最后一个节点：哨兵前面的
     */
    public void removeLast() {
        Node removed = sentinel.prev;

        if (removed == sentinel) {
            throw new IllegalArgumentException("删除的是非法的");
        }

        Node a = removed.prev;
        Node b = sentinel;

        a.next = b;
        b.prev = a;
    }

    /**
     * 通过值找到节点
     * @param val 值
     * @return 节点
     */
    private Node findByValue(int val) {
        Node p = sentinel.next;

        while (p != sentinel) {
            if (p.val == val) {
                return p;
            }

            p = p.next;
        }
        return null;
    }

    /**
     * 通过值删除节点
     * @param val 值
     */
    private void removeByValue(int val) {
        Node removed = findByValue(val);

        if (removed == null) {
            return; // 不用删
        }

        Node a = removed.prev;
        Node b = removed.next;

        a.next = b;
        b.prev = a;
    }
}
