package com.zjl.datastructure.linkList;

/**
 * 删除倒数节点
 * @author zhangjlk
 * @date 2025/9/21 11:49
 */
public class LeetcodeRemoveNthFromEnd {

    public ListNode removeNthFromEnd1(ListNode head, int n) {
        recursion(head, n);
        return head;
    }

    public int recursion(ListNode p, int n) {
        if (p == null) {
            return 0;
        }

        int nth = recursion(p.next, n); // 下一个节点的倒数位置

        // 找到了要删除的节点
        if (nth == n) {
            p.next = p.next.next;
        }

        return nth + 1;
    }


    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode sentinel = new ListNode(-1, head);
        ListNode p1 = sentinel;
        ListNode p2 = sentinel;

        // p2移动 n+1 位
        for (int i = 0; i < n + 1; i++) {
            p2 = p2.next;
        }

        while (p2 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }

        p1.next = p1.next.next;
        return sentinel.next;
    }


    public static void main(String[] args) {
        ListNode o5 = new ListNode(6, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(6, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        System.out.println(new LeetcodeRemoveNthFromEnd().removeNthFromEnd2(o1, 2));
    }
}
