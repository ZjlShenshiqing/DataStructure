package com.zjl.datastructure.linkList;

/**
 *
 * @author zhangjlk
 * @date 2025/9/9 11:00
 */
public class DoubleLinkedListSentinel {

    private Node head; // 头哨兵
    private Node tail; // 尾哨兵

    static class Node {
        Node prev; // 上一个节点
        int value; // 节点值
        Node next; // 下一个节点

        public Node(Node prev, int value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public DoubleLinkedListSentinel() {
        head = new Node(null, 666, null);
        tail = new Node(null, 888, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 根据索引位置找到节点
     * @param index 索引位置
     * @return 节点
     */
    private Node findNode(int index) {
        int i = -1;
        for (Node node = head; node != tail; node = node.next) {
            if (i == index){
                return node;
            }
        }

        return null;
    }

    /**
     * 插入数据
     * @param index 索引
     * @param value 值
     */
    public void insert(int index, int value) {
        Node prev = findNode(index - 1); // 找到上一个节点

        if (prev == null) {
            throw illegalIndex(index);
        }

        Node next = prev.next;

        // 创建新节点
        Node inserted = new Node(prev, value, next);

        // 指针也换一下
        prev.next = inserted;
        next.prev = inserted;
    }

    /**
     * 删除节点
     * @param index 节点索引
     */
    public void remove(int index) {
        Node prev = findNode(index - 1); // 找到上一个节点
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node removed = prev.next;

        if (removed == tail) {
            throw illegalIndex(index);
        }

        Node next = removed.next;

        prev.next = next; // 上一个节点的指针指向下一个节点
        next.prev = prev;
    }

    /**
     * 将值加入到链表的尾部
     * @param value 值
     */
    public void addLast(int value) {
        Node last = tail.prev; // 拿到最后一个节点
        Node added = new Node(last, value, tail);
        last.next = added;
        tail.prev = added;
    }

    /**
     * 删除最后一个节点
     */
    public void removeLast() {
        Node removed = tail.prev;
        // 链表已经删空了
        if (removed == head) {
            throw illegalIndex(0);
        }
        Node prev = removed.prev;
        prev.next = tail;
        tail.prev = prev;
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException();
    }
}
