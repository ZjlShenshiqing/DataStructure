package com.zjl.datastructure.linkList;

/**
 * 有序链表去重 - leetcode83
 * @author zhangjlk
 * @date 2025/9/21 12:33
 */
public class LeetcodeDeleteDuplicates1 {

    public ListNode deleteDuplicates1(ListNode head) {
        // 节点数小于2，不用比较
        if (head == null || head.next == null) {
            return head;
        }

        // 节点数大于2
        ListNode p1 = head;
        ListNode p2 = head;

        /**
         * 先把 p1.next 赋值给变量 p2，然后判断 p2 是否不等于 null，如果不为 null 就进入循环
         *
         * 等价于：
         * p2 = p1.next;        // 先赋值
         * while (p2 != null) {  // 再判断
         *     // 循环体
         *     ...
         *     p2 = p1.next;     // 如果需要继续，可能在循环里重新赋值（视逻辑而定）
         * }
         */
        while ((p2 = p1.next) != null) {
            if (p1.val == p2.val) {
                p1.next = p2.next; // 删除p2节点
            } else {
                p1 = p1.next; // 向后平移
            }
        }
        return head;
    }

    /**
     * 删除已排序链表中的重复元素（保留每个值只出现一次）
     *
     * 输入链表必须是“升序排列”的，这样相同的值才会相邻。
     * 例如：1 -> 1 -> 2 -> 3 -> 3 -> null
     * 输出：1 -> 2 -> 3 -> null
     *
     * 使用递归方式实现：从后往前处理，每层只决定“自己要不要留”
     *
     * @param p 当前链表的头节点
     * @return 去重后的链表头节点
     */
    public ListNode deleteDuplicates2(ListNode p) {
        // 链表为空&就剩一个节点就不进行递归了，不用进行去重
        if (p == null || p.next == null) {
            return p;
        }

        // 进行去重
        if (p.val == p.next.val) {
            // 我（当前节点）不应该留在结果中
            // 直接跳过我，返回“从下一个节点开始去重”的结果
            // 相当于把我删了，让后面的链表接上来
            return deleteDuplicates2(p.next);
        } else {
            // 我虽然能留下，但我的 next 必须连接“后面已经去重完成的链表”
            // 所以先让我后面的链表进行去重，并把结果接到我身上
            p.next = deleteDuplicates2(p.next);
            return p;
        }
    }

    public static void main(String[] args) {
        ListNode o5 = new ListNode(6, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(2, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        System.out.println(new LeetcodeDeleteDuplicates1().deleteDuplicates2(o1));
    }
}
