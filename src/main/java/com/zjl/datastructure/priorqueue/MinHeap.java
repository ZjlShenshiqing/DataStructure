package com.zjl.datastructure.priorqueue;

import com.zjl.datastructure.linkList.ListNode;

/**
 * @author zhangjlk
 * @date 2025/10/3 上午10:47
 * @description MinHeap
 */
public class MinHeap {

    ListNode[] array;
    int size;

    public MinHeap(int capacity) {
        array = new ListNode[capacity];
    }

    /**
     * 1. 入堆新元素，加入到数组末尾 (索引位置为child)
     * 2. 不断的比较新加入的元素和它父节点的优先级，如果新加入的元素的优先级小于父节点的优先级，那就进行上浮，也就是父子节点进行交换
     *    - 直到父节点优先级更高或者 child == 0 为止
     */
    public void offer(ListNode offered) {
        int child = size++;
        int parent = (child - 1) / 2;
        while (child > 0 && offered.val < array[parent].val) {
            array[child] = array[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        array[child] = offered;
    }

    /**
     * 1. 交换堆顶元素和尾部元素，让堆顶元素出队
     * 2. (下潜)
     *     - 从堆顶开始，将父元素与两个孩子较小者进行交换
     *     - 直到父元素小于两个孩子。或者没有孩子为止
     */
    public ListNode poll() {
        if (isEmpty()) {
            return null;
        }
        swap(0, size - 1);
        size--;
        ListNode e = array[size];
        array[size] = null; // help GC

        down(0);

        return e;
    }

    private void down(int parent) {
        int left = 2 * parent + 1;
        int right = left + 1;
        int min = parent;
        if (left < size && array[left].val < array[min].val) {
            min = left;
        }
        if (right < size && array[right].val < array[min].val) {
            min = right;
        }
        if (min != parent) {
            swap(min, parent);
            down(min);
        }
    }

    private void swap(int i, int j) {
        ListNode t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
