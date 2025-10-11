package com.zjl.datastructure.priorqueue;

import com.zjl.datastructure.queue.Queue;

/**
 * 优先级队列
 *
 * @author zhangjlk
 * @date 2025/9/30 下午4:27
 * @description PriorityQueue
 */
public class PriorityQueue3<E extends Priority> implements Queue<E> {

    /**
     * 优先级数组
     */
    Priority[] array;

    /**
     * 有效元素长度
     */
    int size;

    public PriorityQueue3(int capacity) {
        array = new Priority[capacity];
    }

    /**
     * 入堆新元素，需要加入到数组的末尾
     * 先插入到数组末尾，然后与其父元素进行比较优先级
     *  - 如果父节点优先级低，那么父节点向下移动，新元素向上，然后新元素找到parent
     *  - 继续比较，直到父节点优先级更高，或者child = 0 （堆顶）为止
     * @param e 待插入值
     */
    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        }
        int child = size;
        size++;
        int parent = (child - 1) / 2;
        // 堆顺序调整，保证还是大顶堆
        while (child > 0 && e.priority() > array[parent].priority()) {
            // 父赋值到子
            array[child] = array[parent];
            child = parent;
            parent = (child - 1) / 2; // parent指针向上移动
        }
        array[child] = e;
        return true;
    }

    /**
     * 数组元素进行交换
     */
    private void swap(int i, int j) {
        Priority temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 元素下潜的过程
     * @param parent 爸爸的位置
     */
    private void down(int parent) {
        int left = 2 * parent + 1;
        int right = left + 1;
        int max = parent; // 先假设父元素的优先级是最大的

        if (left < size && array[left].priority() > array[max].priority()) {
            max = left;
        }
        if (right < size && array[right].priority() > array[max].priority()) {
            max = right;
        }
        if (max != parent) { // 有孩子比父亲大
            swap(max, parent);
            down(parent); // 重复上面的过程，继续比较孩子和父亲的大小
        }
    }

    /**
     * 先交换堆顶元素，交换完让尾部元素出队
     * （头部元素进行下潜）
     * - 从堆顶元素开始，将父元素和两个孩子的较大着进行交换
     * - 直到父元素大于俩孩子，或者没有孩子为止
     */
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        swap(0, size - 1);
        size--;
        Priority e = array[size];
        array[size] = null;

        // 调整元素位置保证大顶堆
        down(0);

        return (E) e;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }
}
