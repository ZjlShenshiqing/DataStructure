package com.zjl.datastructure.heap;

import com.zjl.datastructure.linkList.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author zhangjlk
 * @date 2025/10/20 下午5:10
 * @description Leetcode148sortLinkedList
 */
public class Leetcode148sortLinkedList {

    public ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }
        // 创建小顶堆
        PriorityQueue<ListNode> minHeap = new PriorityQueue<ListNode>(
                Comparator.comparingInt(node -> node.val)
        );
        ListNode current = head;

        // 链表入小顶堆
        while (current != null) {
            minHeap.offer(current);
            current = current.next;
        }

        // 弹出最小值，构成升序链表
        ListNode result = new ListNode(0);
        // 指针,指向链表
        current = result;
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            node.next = null;
            current.next = node;
            current = current.next;
        }
        return result.next;
    }
}
