package com.zjl.datastructure.linkList;

/**
 * 合并有序链表
 *
 *  输入：两个已按非降序排列的单链表 p1 和 p2
 *  输出：合并后的新链表，仍然保持升序
 *
 * @author zhangjlk
 * @date 2025/9/22 12:41
 */
public class Leetcode21MergeTwoLists {

    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {

        // 创建一个虚拟头节点（哨兵），简化边界处理
        // 最终返回 sentinel.next 即可得到真实头节点
        ListNode sentinel = new ListNode(-1 , null);
        ListNode p = sentinel;

        // 当两个链表都还有节点时，持续比较并选择较小者接入结果链表
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                // p1 的值更小 → 把 p1 节点接到结果链表末尾
                p.next = p1;
                p1 = p1.next;  // p1 向后移动一步
                p = p.next;    // p 指向刚接入的节点
            } else if (p1.val > p2.val) {
                p.next = p2;
                p2 = p2.next;
                p = p.next;
            } else {
                // 相等时，可以任选一个先放进去，比如先把 p1 接上
                p.next = p1;
                p1 = p1.next;
                // 或者也可以两个都接（但题目一般要求有序合并，不重复扩展）
            }
            p = p.next;
        }

        // 走到这里说明至少有一个链表已经遍历完了
        // 把另一个非空链表剩余部分直接拼接到结果链表后面
        if (p1 != null) {
            p.next = p1;
        } else if (p2 != null) {
            p.next = p2;
        }

        // 返回合并后的链表头
        return sentinel.next;
    }

    public ListNode mergeTwoLists2(ListNode p1, ListNode p2) {
        // 递归终止条件
        if (p1 == null) {
            return p2;
        } else if (p2 == null) {
            return p1;
        }

        if (p1.val < p2.val) {
            p1.next = mergeTwoLists(p1.next, p2);
            return p1;
        } else if (p1.val > p2.val) {
            p2.next = mergeTwoLists(p1, p2.next);
            return p2;
        } else {
            p2.next = mergeTwoLists(p1, p2.next);
            return p2;
        }
    }

    /**
     * 合并多个链表数组
     * @param lists 链表数组
     * @return      合并后的数组
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 数组内都没有元素，返回空
        if (lists.length == 0) {
            return null;
        }

        // 调用分治函数，对整个数组 [0, lists.length - 1] 进行递归合并
        return split(lists, 0, lists.length - 1);
    }

    /**
     * 分治函数：递归地将链表数组 [i, j] 区间内的链表合并成一个有序链表
     * @param lists 链表数组
     * @param i     当前处理区间的左边界（包含）
     * @param j     当前处理区间的右边界（包含）
     * @return      合并后的单个有序链表头节点
     */
    private ListNode split(ListNode[] lists, int i, int j) {
        // 基础情况（递归终止条件）：区间内只有一个链表（i == j）
        // 直接返回该链表，无需合并
        if (i == j) {
            return lists[i]; // 返回第 i 个链表的头节点
        }

        // 计算中点，将区间 [i, j] 分成两半
        // 使用无符号右移 >>> 1 相当于 (i + j) / 2，但避免整数溢出，且向下取整
        int m = (i + j) >>> 1;

        // 递归处理左半部分 [i, m]
        ListNode left = split(lists, i, m);

        // 递归处理右半部分 [m+1, j]
        ListNode right = split(lists, m + 1, j);

        // 将左右两部分合并后的链表，再合并成一个有序链表
        return mergeTwoLists(left, right);
    }
}
