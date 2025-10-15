package com.zjl.datastructure.linkList;

/**
 * 判断回文链表
 * @author zhangjlk
 * @date 2025/9/23 16:09
 */
public class Leetcode234IsPalindrome {

    public boolean isPalindrome(ListNode head) {
        // Step 1: 找到链表的中点
        ListNode slow = head;
        ListNode fast = head;
        ListNode new1 = null;
        ListNode old1 = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // 快慢指针走完一个过程后，直接进行反转链表
            ListNode old2 = old1.next; // old2直接可以是慢指针的位置
            old1.next = new1;
            new1 = old1;
            old1 = old2;
        }

        // 上面这种是偶数个节点的情况，如果是奇数个节点就无法判断是不是回文链表
        // 比如现在这个 1 2 3 2 1
        // 慢指针在3，也就是 3 2 1 而反转链表是 2 1 这样是会直接报错的，那肯定不行
        // 所以针对奇数个节点，还得具体情况具体分析一下
        if (fast != null) {
            slow = slow.next; // 向后走一路，这样才能进行对比
        }

        while (new1 != null) {
            if (new1.val != slow.val) {
                return false;
            }

            new1 = new1.next;
            slow = slow.next;
        }

        return true;
    }

}
