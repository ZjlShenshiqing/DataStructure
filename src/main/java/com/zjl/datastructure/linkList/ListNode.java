package com.zjl.datastructure.linkList;

/**
 * ListNode 只是一个节点类，不是链表整体的类
 *
 * @author zhangjlk
 * @date 2025/9/18 15:31
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(int val) {
        this.val = val;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        builder.append("[");
        ListNode p = this;
        while (p != null) {
            builder.append(p.val);
            if (p.next != null) {
                builder.append(", ");
            }
            p = p.next;
        }

        builder.append("]");
        return builder.toString();
    }
}
