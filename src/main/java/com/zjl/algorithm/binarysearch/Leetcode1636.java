package com.zjl.algorithm.binarysearch;

import java.util.Arrays;

/**
 * 按出现频率排序
 *
 * @author zhangjlk
 * @date 2025/11/9 12:34
 */
public class Leetcode1636 {

    public int[] frequencySort(int[] nums) {

        // 1. 创建一个桶
        int[] count = new int[201];

        // 2. 遍历原始数组，统计每个数字的出现频率。
        for (int num : nums) {
            // 使用 +100 作为偏移量：
            // - 索引 0 存储 -100 的计数
            // - 索引 100 存储 0 的计数
            // - 索引 200 存储 100 的计数
            count[num + 100]++;
        }

        // 对原数组 nums 进行流式处理和排序
        return Arrays.stream(nums)

                // 将 IntStream (原始 int 流) 转换为 Stream<Integer> (对象 Integer 流)。
                // 这是必需的，因为 .sorted() 方法如果接受自定义比较器(Comparator)，
                // 那么它必须操作的是对象。
                .boxed()

                // 5. 自定义排序。这是核心逻辑。
                // 比较器 (a, b) -> ...
                // - 返回负数：a 排在 b 前面
                // - 返回正数：b 排在 a 前面
                // - 返回 0：保持相对顺序 (这里不会是0)
                .sorted((a, b) -> {

                    // 6. 获取 a 和 b 各自的频率。
                    // 必须使用相同的 +100 偏移量去 count 数组里查找。
                    int aFrequent = count[a + 100];
                    int bFrequent = count[b + 100];

                    // 7. 比较：
                    // 规则1：按频率升序排序。
                    if (aFrequent < bFrequent) {
                        return -1; // a 的频率更低，a 排在 b 前面。
                    } else if (bFrequent < aFrequent) {
                        return 1; // b 的频率更低，b 排在 a 前面。
                    } else {
                        // 规则2：如果频率相同 (aFrequent == bFrequent)
                        // 按数值本身降序排序。
                        // (b - a) 的结果：
                        // - 如果 b > a，返回正数，b 排在 a 前面。
                        // - 如果 a > b，返回负数，a 排在 b 前面。
                        // 这实现了“数值大的排在前面”的效果。
                        return b - a;
                    }
                })

                // 8. 将排序好的 Stream<Integer> 转换回 IntStream
                .mapToInt(Integer::intValue)

                // 9. 将 IntStream 转换回 int[] 数组并返回。
                .toArray();
    }
}
