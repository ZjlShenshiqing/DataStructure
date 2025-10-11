package com.zjl.datastructure.linkList;

/**
 * 判断环形链表
 * @author zhangjlk
 * @date 2025/9/23 18:32
 */
public class Leetcode142hasCycle {

    public ListNode detectCycle(ListNode head) {
        ListNode slow = head; // 龟
        ListNode fast = head; // 兔子

        // 边界条件：兔子走完了整个链表
        while (fast != null && fast.next != null) {
            slow = slow.next; // 龟走一步
            fast = fast.next.next; // 兔子走两步

            if (slow == fast) { // 兔子碰到龟了
                // 进入第二阶段，去找环的入口
                slow = head; // 龟回到起点，兔子原地不动
                // 龟和兔子都走一步
                while (true) {
                    if (slow == fast) {
                        return slow;
                    }

                    slow = slow.next;
                    fast = fast.next;
                }
            }
        }

        return null;
    }

}
