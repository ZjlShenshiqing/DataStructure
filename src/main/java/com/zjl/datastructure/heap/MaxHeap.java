package com.zjl.datastructure.heap;

/**
 * 大顶堆实现
 *
 * @author zhangjlk
 * @date 2025/10/6 下午5:14
 * @description MaxHeap
 */
public class MaxHeap {

    int[] array;
    int size;

    public MaxHeap(int capacity) {
        this.array = array;
        this.size = array.length;
        heapify(); // 建堆
    }

    /**
     * 建堆步骤：
     * 1、找到最后一个非叶子节点
     * 2、从后向前，对每个节点执行下潜
     *
     * 起点 i = size / 2 - 1 是最后一个非叶子节点。
     * 终点 i = 0 是根节点（除非 size=0，否则根一定不是叶子）。
     * 中间的 i = size/2 - 2, size/2 - 3, ..., 0 都是非叶子节点（在完全二叉树中，非叶子节点是连续集中在数组前半部分的）。
     */
    private void heapify() {
        // 如何找到非叶子节点，公式是size / 2 - 1
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(i); // 下潜
        }
    }

    /**
     * 获取堆顶元素
     * @return 堆顶元素
     */
    public int peek() {
        return array[0];
    }

    /**
     * 删除堆顶元素
     * @return 堆顶元素
     */
    public int poll() {
        int top = array[0];
        swap(0, size - 1); // 首元素和尾元素进行交换
        size--; // 尾元素删除
        // 下潜刚交换完的元素
        down(0);
        return top;
    }

    /**
     * 删除指定索引处元素
     *
     * 步骤：
     * 先把堆顶元素和最后的元素进行交换，
     * 交换之后移除最后的元素，并将堆顶元素进行下潜
     *
     * @param index 索引
     * @return      被删除元素
     */
    public int poll(int index) {
        int deleted = array[index];
        swap(index, size - 1);
        size--; // 删除元素
        down(index);
        return deleted;
    }

    /**
     * 堆的尾部添加元素
     * @param offered 新元素
     * @return        是否添加成功
     */
    public boolean offer(int offered) {
        if (size == array.length) { // 已满，不得添加
            return false;
        }
        up(offered);
        size++;
        return true;
    }

    /**
     * 保持整个堆的大小不变，替换堆顶元素
     * @param replaced 替换的元素值
     */
    public void replace(int replaced) {
        array[0] = replaced; // 替换元素
        down(0); // 下潜
    }

    /**
     * 将 inserted 元素上浮，直到小于父元素或者到堆顶
     */
    private void up(int offered) {
        int child = size; // 为什么是size，因为调用之前还没有进行自增
        while (child > 0) {
            int parent = (child - 1) / 2; // 父元素指针
            if (offered > array[parent]) {
                array[child] = array[parent]; // 交换值
            } else {
                break;
            }
            child = parent; // 索引位置也交换一下
        }
        array[child] = offered;
    }

    /**
     * 将 parent 索引处元素进行下潜
     * 与两个孩子较大者进行交换，直至没有孩子或者孩子都没TA大
     */
    private void down(int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        // 先假设父亲的最大
        int max = parent;
        if (left < size && array[left] > array[max]) {
            max = left;
        }
        if (right < size && array[right] > array[max]) {
            max = right;
        }
        if (max != parent) { // 有孩子比父亲大
            swap(max, parent); // 交换孩子和父亲的位置
            down(max); // 继续比较孩子和父亲，直到没有元素为止
        }
    }

    /**
     * 交换两个索引处元素
     */
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
