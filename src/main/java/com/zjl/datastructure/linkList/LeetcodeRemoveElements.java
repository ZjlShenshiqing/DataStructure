package com.zjl.datastructure.linkList;

/**
 *
 * @author zhangjlk
 * @date 2025/9/20 22:41
 */
public class LeetcodeRemoveElements {

    public ListNode removeElements1(ListNode head, int val) {
        // 哨兵节点
        ListNode sentinel = new ListNode(-1, head);
        ListNode p1 = sentinel;
        ListNode p2 = sentinel.next;

        while (p2 != null) {
            if (p2.val == val) {
                p1.next = p2.next;
            } else {
                p1 = p1.next;
            }
            p2 = p2.next;
        }

        return sentinel.next;
    }

    public ListNode removeElements2(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        // 如果这个节点要删除
        if (head.val == val) {
            return removeElements2(head.next, val);
        } else {
            head.next = removeElements2(head.next, val);
            return head;
        }
    }

    public static void main(String[] args) {
        ListNode o5 = new ListNode(6, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(6, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        System.out.println(new LeetcodeRemoveElements().removeElements2(o1, 6));
    }
}
