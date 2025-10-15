package com.zjl.datastructure.linkList;

/**
 * 反转链表实现
 *
 * @author zhangjlk
 * @date 2025/9/18 15:29
 */
public class LeetcodeReverseList {

    /**
     * 链表节点容器
     */
    static class List {
        ListNode head;

        public List(ListNode head) {
            this.head = head;
        }

        /**
         * 添加节点到头部
         *
         * @param first 添加的节点
         */
        public void addFirst(ListNode first) {
            first.next = head;
            head = first;
        }

        /**
         * 移除头部节点
         *
         * @return 被移除的头部节点
         */
        public ListNode removeFirst() {
            ListNode first = head;
            if (first != null) {
                head = first.next;
            }
            return first;
        }
    }

    /**
     * 反转链表
     * @param o1 旧链表的第一个节点
     * @return
     */
    public ListNode reverse1(ListNode o1) {
        ListNode n1 = null; // 新链表的头节点（初始为空）
        ListNode p = o1; // 当前遍历旧链表的指针
        while (p != null) {
            n1 = new ListNode(p.val, n1);
            p = p.next;
        }
        return n1;
    }

    public ListNode reverse2(ListNode head) {
        List list1 = new List(head); // 原链表
        List list2 = new List(null); // 需要进行反转的链表
        while (true) {
            ListNode first = list1.removeFirst();
            if (first == null) {
                break;
            }
            list2.addFirst(first);
        }
        return list2.head;
    }

    /**
     * 使用递归进行反转链表
     *
     * @param p 指针
     * @return
     */
    public ListNode reverse3(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }

        ListNode last = reverse3(p.next);
        p.next.next = p;
        p.next = null;

        return last;
    }

    public ListNode reverse4(ListNode o1) {
        ListNode o2 = o1.next; // 旧链表老二
        // 新链表指针，指向旧链表的头节点
        ListNode n1 = o1;
        while (o2 != null) {
            o1.next = o2.next;
            o2.next = n1;
            n1 = o2;
            o2 = o1.next;
        }

        return n1;
    }

    public ListNode reverse5(ListNode o1) {
        ListNode n1 = null;

        while (o1 != null) {
            ListNode o2 = o1.next;
            o1.next = n1;
            n1 = o1;
            o1 = o2;
        }

        return n1;
    }

    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new LeetcodeReverseList().reverse5(o1);
        System.out.println(n1);
    }
}
