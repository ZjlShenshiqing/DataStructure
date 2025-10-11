package com.zjl.datastructure.priorqueue;

import com.zjl.datastructure.linkList.ListNode;

/**
 * @author zhangjlk
 * @date 2025/10/3 上午10:51
 * @description leetcode23UsePriorQueueToMergeList
 */
public class leetcode23UsePriorQueueToMergeList {

    /**
     * 大概的思路是这样的
     *
     * 首先有这样三个链表（举例）
     *
     * p
     * 1 -> 4 -> 3 -> 2 -> null
     * p
     * 2 -> 6 -> 2 -> 7 -> null   这三个链表就组成了一个lists
     * p
     * 3 -> 6 -> 9 -> null
     *
     * 现在将链表的头节点加入到最小堆中，并排好序
     * 1 2 3 也就是这样
     * 然后将里面最小的去掉，并移到新的链表中
     * new ListNode -> 1
     * 然后移掉的那个节点的 p 指针，往下一个节点去走，然后再加入到最小堆中，再移除，重复这个过程
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 创建一个容量为 lists.length 的小顶堆（最小堆）
        // 堆中存储的是链表节点，堆顶始终是当前所有候选节点中值最小的节点
        MinHeap minHeap = new MinHeap(lists.length);

        // 遍历所有链表的头节点
        for (ListNode node : lists) {
            // 只有非空节点才加入堆中
            if (node != null) {
                minHeap.offer(node); // 将当前链表的头节点加入小顶堆
            }
        }

        // 创建一个哨兵节点，用于简化链表构建过程，它的 next 指向最终结果链表的真正头节点
        ListNode sentinel = new ListNode(-1, null);
        ListNode t = sentinel; // t 用于追踪新链表的当前尾部，初始指向哨兵
        // 当堆不为空时，说明还有未处理的节点，循环处理节点
        while (!minHeap.isEmpty()) {
            // 从堆中取出当前值最小的节点（堆顶）
            ListNode min = minHeap.poll();

            // 将该最小节点连接到结果链表的尾部
            t.next = min;
            t = min; // 更新 t 为当前新加入的节点，保持 t 指向结果链表的尾部

            // 如果该节点还有下一个节点（即它所在的原始链表还没遍历完）
            if (min.next != null) {
                // 将下一个节点加入堆中，作为后续的候选最小值
                minHeap.offer(min.next);
            }
        }

        // 返回哨兵节点的 next，即合并后链表的真实头节点
        return sentinel.next;
    }
}
