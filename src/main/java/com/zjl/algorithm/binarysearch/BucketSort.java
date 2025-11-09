package com.zjl.algorithm.binarysearch;

import com.zjl.datastructure.array.DynamicArray;

/**
 * 桶排序
 *
 * @author zhangjlk
 * @date 2025/11/7 下午3:15
 * @description BucketSort
 */
public class BucketSort {

    /**
     * 桶排序的实现。
     *
     * 桶排序的核心思想是：
     * 1. 将数据（通常是均匀分布的）分散到多个"桶"中。
     * 2. 对每个桶内部的元素进行单独排序。
     * 3. 将所有桶的元素按顺序合并回原数组。
     *
     * @param arrs  待排序的数组
     * @param range 每个桶负责的数据范围（即 "桶的大小" 或 "步长"）
     */
    public static void sort(int[] arrs, int range) {

        // --- 1. 找出最大值和最小值 ---
        // 这是为了确定总的数据范围，以便计算需要多少个桶
        int max = arrs[0];
        int min = arrs[0];
        for (int i = 1; i < arrs.length; i++) {
            if (arrs[i] > max) {
                max = arrs[i];
            }
            if (arrs[i] < min) {
                min = arrs[i];
            }
        }

        // --- 2. 准备桶 ---
        // (max - min) / range 计算出了数据范围需要 "跨越" 多少个桶
        // +1 是为了确保最后一个桶（包含 max）也能被分配
        // 例如：max=99, min=0, range=10. (99-0)/10 = 9 (取整).
        // 但我们需要 10 个桶 (0-9, ..., 90-99)，所以 +1 得到 10.
        DynamicArray[] buckets = new DynamicArray[(max - min) / range + 1];

        // 初始化每个桶（因为此时 buckets 数组里存的还是 null）
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new DynamicArray();
        }

        // --- 3. 放入数据（分配） ---
        // 遍历原始数组中的每一个元素
        for (int arr : arrs) {
            // 计算当前元素 arr 应该被放入哪个桶
            // (arr - min) 是元素相对于最小值的偏移量
            // / range 将这个偏移量转换为桶的索引
            // 例如：min=0, range=10.
            //   arr=5,  (5-0)/10 = 0 -> 放入 buckets[0]
            //   arr=23, (23-0)/10 = 2 -> 放入 buckets[2]
            buckets[(arr - min) / range].addLast(arr);
        }

        int index = 0; // 用于跟踪原始数组 arrs 的填充位置

        // --- 4. 排序桶内元素重新进行排序 ---
        // 遍历每一个桶
        for (DynamicArray bucket : buckets) {

            // a. 从桶中获取其内部的 int[] 副本
            int[] array = bucket.array();

            // b. 对这个小数组进行排序
            // 这里使用了插入排序
            InsertionSort.sort(array);

            // c. 将排好序的桶内元素，合并（写回）到原始数组 arrs
            for (int value : array) {
                arrs[index] = value;
                index++;
            }
        }
        // 当所有桶都合并完毕后，arrs 数组就整体有序了
    }
}
