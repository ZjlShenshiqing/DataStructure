package com.zjl.algorithm.binarysearch;

/**
 * 选择排序
 *
 * @author zhangjlk
 * @date 2025/11/1 下午3:15
 * @description SelectionSort
 */
public class SelectionSort {

    // 选择排序：每次从未排序部分中选出最大值，放到当前未排序区的末尾
    // 最终数组按升序排列（从小到大）
    public static void sort(int[] arr) {

        // 外层循环：控制“已排序区”的右边界
        // right 表示当前轮次要放置最大值的目标位置
        // 选择轮数：数组长度 - 1轮
        // 交换的索引位置：初始 a.length - 1 ， 每次进行递减
        for (int right = arr.length - 1; right > 0; right--) {
            // 假设当前未排序区 [0, right] 中的最大值在 right 位置
            int max = right;
            for (int i = 0; i < right; i++) {
                if (arr[i] > arr[max]) {
                    max = i; // 更新最大值
                }
            }

            // 如果最大值不在 right 位置，则交换，将其放到正确位置
            if (max != right) {
                swap(arr, max, right);
            }
            // 此时，arr[right] 已是未排序区中的最大值，
            // 下一轮 right--，处理更小的未排序区
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
