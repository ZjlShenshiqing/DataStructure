package com.zjl.datastructure.linkList;

/**
 * 有序链表去重 - leetcode82
 *
 * @author zhangjlk
 * @date 2025/9/21 13:22
 */
public class LeetcodeDeleteDuplicates2 {

    /**
     * 删除已排序链表中的所有重复元素（完全去重）
     *
     * 与常规去重不同：这个方法会将“只要出现过重复”的节点全部删除，
     * 即使是第一个也删，最终只保留“从未重复过的节点”。
     *
     * 示例：
     *     输入：1 -> 2 -> 2 -> 3 -> 3 -> 4 -> null
     *     输出：1 -> 4 -> null
     *
     * 使用递归实现：从当前节点开始，判断是否该保留自己
     *
     * @param p 当前链表的头节点
     * @return 去重后的链表头节点（不含任何重复出现过的值）
     */
    public ListNode deleteDuplicates(ListNode p) {

        // 边界条件
        if (p == null || p.next == null) {
            return p;
        }

        // 当前节点的值 和 下一个节点的值相同 → 说明 p 是重复值的一部分
        if (p.val == p.next.val) {
            // 我们不能只跳一个，因为可能有多个连续相同的值（如 2->2->2）
            // 所以需要找到第一个“不等于 p.val”的节点

            // temp 指向当前重复段之后的第一个候选节点
            ListNode temp = p.next.next;

            // 跳过所有值仍然等于 p.val 的节点
            while (temp != null && temp.val == p.val) {
                temp = temp.next;
            }

            // 循环结束后，temp 指向的是第一个“值不等于 p.val”的节点
            // 或者是 null（说明后面全是重复的）

            // 因为 p 是重复值，所以我自己也不能留！
            // 直接递归处理 temp 开始的链表，相当于把我这一整段“重复值”全扔了
            return deleteDuplicates(temp);
        }
        // 【情况2】当前节点和下一个节点值不同 → p 可能是唯一的（但还不确定）
        else {
            // 我虽然目前看起来不重复，但我后面的链表可能还有问题
            // 所以我要让我的 next 接上“从 p.next 开始彻底去重后的结果”

            p.next = deleteDuplicates(p.next);

            // 如果后续处理完后，我仍然是那个“唯一”的节点
            // 那我就留下来作为结果的一部分
            return p;
        }
    }

    /**
     * 三指针法删除已排序链表中的所有重复元素（完全去重）
     *
     * 使用迭代方式 + 三个指针完成：
     *   - p1: 指向当前“安全节点”（可能唯一，也可能后续发现是重复的）
     *   - p2: 指向 p1 的下一个节点（待检查是否重复）
     *   - p3: 指向 p2 的下一个节点，用于探测是否有重复
     *
     * @param head 头节点
     * @return     去重之后的链表
     */
    public ListNode deleteDuplicates2(ListNode head) {
        // 边界条件
        if (head == null || head.next == null) {
            return head;
        }

        // 哨兵节点
        // 创建哨兵节点（虚拟头节点），指向原头节点
        // 目的：统一处理逻辑，避免 head 被删除时需要特殊判断
        ListNode sentinel = new ListNode(-1, head);

        ListNode p1 = sentinel;

        // p2 和 p3 是工作指针，分别表示：
        //   p2 = p1.next      → 第一个候选节点
        //   p3 = p2.next      → 下一个节点，用来判断是否重复
        ListNode p2;
        ListNode p3;

        // 循环条件：p2 和 p3 都不能为 null
        // 因为我们需要至少两个连续节点才能判断“是否重复”
        while ((p2 = p1.next) != null && (p3 = p2.next) != null) {
            // 有重复元素
            if (p2.val == p3.val) {
                while((p3 = p3.next) != null && p3.val == p2.val){
                    // 空循环体：只是让 p3 不断前进
                    // 直到跳出为止
                }
                // p3找到了不重复的值
                p1.next = p3;
            } else {
                // 移动 p1 p2 p3 位置，继续向后检查
                p1 = p1.next;
            }
        }
        // 返回去重之后的链表
        return sentinel.next;
    }
}
