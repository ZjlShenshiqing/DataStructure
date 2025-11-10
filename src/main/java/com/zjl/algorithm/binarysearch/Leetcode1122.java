package com.zjl.algorithm.binarysearch;

/**
 * 数组的相对排序
 *
 * @author zhangjlk
 * @date 2025/11/9 12:07
 */
public class Leetcode1122 {

    /**
     * 对 arr1 进行相对排序。
     * 1. arr1 中在 arr2 中出现的元素，保持它们在 arr2 中的相对顺序。
     * 2. arr1 中未在 arr2 中出现的元素，按升序排在数组末尾。
     *
     * @param arr1 待排序数组
     * @param arr2 相对顺序参考数组
     * @return 排序后的新数组
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {

        // 1. 创建一个“桶”或“计数器”数组。
        // count[i] 存储的是数字 i 在 arr1 中出现的次数。
        int[] count = new int[1001];

        // 2. 遍历 arr1，统计每个数字的频率。
        for (int num : arr1) {
            count[num]++;
        }

        // 3. 准备结果数组，长度与 arr1 相同。
        int[] result = new int[arr1.length];
        // 'key' 是一个指针，用于跟踪 result 数组中下一个要插入元素的位置。
        int key = 0;

        // 4. 第一遍处理：按 arr2 的顺序处理
        // 遍历 arr2，将 arr2 中的元素按顺序（以及其在 arr1 中的全部数量）放入 result 数组。
        for (int num : arr2) {
            // 检查这个数字 (num) 在 arr1 中是否出现过 (count[num] > 0)
            while (count[num] > 0) {
                // 放入 result 数组
                result[key++] = num;
                // 每放入一个，计数器减 1，表示这个数字“用掉”了一次。
                count[num]--;
            }
        }

        // 5. 第二遍处理：处理 arr2 中未出现的剩余元素
        // 此时，count 数组中计数值 > 0 的索引 (i)，都是在 arr1 中出现、但在 arr2 中未出现的数字。
        // 遍历整个计数器数组，这天然保证了升序。
        for (int i = 0; i < count.length; i++) {
            // 检查这个数字 (i) 是否还有剩余
            while (count[i] > 0) {
                // 放入 result 数组的末尾
                result[key++] = i;
                count[i]--;
            }
        }

        // 返回排序后的结果数组
        return result;
    }
}
