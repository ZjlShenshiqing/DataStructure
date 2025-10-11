package com.zjl.datastructure.linkList;

import java.util.Iterator;

/**
 * 单向链表实现
 * @author zhangjlk
 * @date 2025/9/2 14:24
 */
public class SingleLinkedList implements Iterable<Integer>{ // 链表整体

    private Node head;

    @Override
    public Iterator<Integer> iterator() {
        return new NodeIterator();
    }

    private static class Node {
        int value; // 值
        Node next; // 指向下一个节点的指针

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(64);
            builder.append("[");
            Node p = this;
            while (p != null) {
                builder.append(p.value);
                if (p.next != null) {
                    builder.append(", ");
                }
                p = p.next;
            }

            builder.append("]");
            return builder.toString();
        }
    }

    /**
     * 在链表的“龙头”位置，插入一个新的节点，让这个新节点成为新的“龙头”。
     * @param value 值
     */
    public void addFirst(int value) {
        // 1.链表为空
        // head = new Node(value, null);
        // 2.链表不为空
        head = new Node(value, head);
    }

    /**
     * 使用迭代器遍历链表
     */
    private class NodeIterator implements Iterator<Integer> {
        Node p = head;

        @Override
        public boolean hasNext() { // 是否有下一个元素
            return p != null;
        }

        @Override
        public Integer next() { // 返回当前值，并指向下一个元素
            int value = p.value;
            p = p.next;
            return value;
        }
    }

    /**
     * 找出最后一个元素
     * @return 最后一个元素节点
     */
    private Node findLastNode() {
        // 判断一下是否为空链表
        if (head == null) {
            return null;
        }

        Node p;

        for (p = head; p.next != null; ) {
            p = p.next;
        }

        return p;
    }

    /**
     * 添加最后的节点
     * @param value
     */
    private void addLast(int value) {
        Node lastNode = findLastNode();

        if (lastNode == null) {
            addFirst(value);
            return;
        }

        lastNode.next = new Node(value, null);
    }

    public void loop() {
        recursion(head);
    }

    public void recursion(Node current) { //

        if (current == null) {
            return; // 结束递归
        }

        // 针对某个节点需要执行的操作
        System.out.println(current.value);
        recursion(current.next);
    }
}
