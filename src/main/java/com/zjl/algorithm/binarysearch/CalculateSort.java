package com.zjl.algorithm.binarysearch;

/**
 * 计数排序
 *
 * @author zhangjlk
 * @date 2025/11/6 下午6:16
 * @description CalculateSort
 */
public class CalculateSort {

    /**
     * 优化版计数排序实现
     *
     * 这个版本通过计算最大值(max)和最小值(min)的差，
     * 来处理任意整数范围（包括负数）的数组，从而更节省空间。
     *
     * @param arr 待排序的整数数组（可以包含负数）
     */
    public static void sort(int[] arr) {

        // --- 1. 找出数组中的最大值和最小值 ---
        // 这是为了确定需要统计的数据范围
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i]; // 更新最大值
            }
            if (min > arr[i]) {
                min = arr[i]; // 更新最小值
            }
        }

        // --- 2. 创建计数数组（"桶"） ---
        // 数组的大小为 (max - min + 1)，这是需要统计的 "数字" 的个数。
        // 例如：arr = [5, 2, 3], max=5, min=2. 大小 = 5-2+1 = 4.
        // 索引 0 对应数字 2 (min)
        // 索引 1 对应数字 3
        // 索引 2 对应数字 4
        // 索引 3 对应数字 5 (max)
        int[] count = new int[max - min + 1];

        // --- 3. 统计 arr 中每个元素的出现次数 ---
        // 遍历原始数组 arr
        for (int v : arr) {
            // 'v - min' 是一个关键的偏移量计算。
            // 它将原始值 v 映射到 count 数组的索引上。
            // 例如：如果 min = -10, v = -10, 那么索引是 -10 - (-10) = 0.
            //      如果 min = -10, v = -9,  那么索引是 -9 - (-10) = 1.
            count[v - min]++;
        }

        // --- 4. 重建原始数组 arr，使其有序 ---
        int key = 0; // key 是 arr 数组的新索引，用于按顺序填充

        // 遍历计数数组 count
        // 这里的变量 i 代表的是 count 数组的索引 (从 0 到 max-min)
        for (int i = 0; i < count.length; i++) {

            // 检查 count 数组索引 i 处的值是否大于 0
            // (即检查原始数字 (i + min) 是否存在)
            while (count[i] > 0) {

                // 将索引 i "翻译" 回原始的数值 (i + min)
                arr[key] = i + min;

                // key 向后移动一位
                key++;

                // 计数减 1
                count[i]--;
            }
        }
        // 循环结束后，arr 数组就已经被原地排序了
    }
}
