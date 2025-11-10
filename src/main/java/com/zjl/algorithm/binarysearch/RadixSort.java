package com.zjl.algorithm.binarysearch;

import java.util.ArrayList;

/**
 * 基数排序 最低有效数字LSD
 *
 * @author zhangjlk
 * @date 2025/11/9 11:05
 */
public class RadixSort {

    /**
     * 使用 LSD (Least Significant Digit) 基数排序对一个字符串数组进行排序。
     * * <p><b>重要前提：</b>此方法要求数组 {@code arr} 中的<b>所有字符串</b>
     * 都必须具有<b>相同的长度</b>，这个长度由参数 {@code length} 指定。</p>
     *
     * <p>如果字符串长度不一致，或者 {@code length} 参数与实际字符串长度不符，
     * 将会导致 {@code StringIndexOutOfBoundsException} 异常。</p>
     *
     * @param arr    要进行“就地排序”(in-place) 的字符串数组。
     * 排序完成后，此数组的内容将被修改。
     * * @param length 数组中<b>每一个</b>字符串的精确长度。
     * 此参数决定了排序需要进行的“轮数”（即从哪个字符索引开始比较）。
     */
    public static void sort(String[] arr, int length) {

        // 准备桶数组。
        // 我们使用 128 个桶，因为这是 ASCII 字符集的大小。
        // 'a' 对应 97, 'A' 对应 65, '0' 对应 48 等。
        // buckets[97] 将存放所有在当前位置为 'a' 的字符串。
        ArrayList<String>[] buckets = new ArrayList[128];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }


        // 外层循环：迭代字符串的每一“位”（即每个字符索引）。
        // 必须从 length - 1 (最低位，即最右边的字符) 开始，
        // 一直处理到 0
        for (int i = length - 1; i >= 0; i--) {

            // --- 2a. 分配 ---
            // 内层循环：遍历数组中的每一个字符串。
            for (String str : arr) {
                // 1. 获取当前字符串在第 i 位置的字符： str.charAt(i)
                // 2. 该字符的 ASCII 值就是它应该去的桶的索引。
                // 3. 将该字符串放入对应的桶中。
                //    例如：i=2, str="CAT", str.charAt(2) 是 'T'。
                //    'T' 的 ASCII 值是 84。
                //    所以 "CAT" 被添加到 buckets[84] 中。
                buckets[str.charAt(i)].add(str);
            }

            // --- 2b. 收集 ---
            // 准备一个索引，用于将桶中的元素“倒”回原数组 arr。
            int key = 0;

            // 遍历整个桶数组
            // 这个遍历必须是按顺序的
            // 这样才能保证 ASCII 值小的字符（如 'A'）先被放回 arr。
            for (ArrayList<String> bucket : buckets) {

                // 遍历当前这一个桶里的所有字符串。
                // 重要的是：ArrayList 保证了字符串被取出的顺序
                //          和它们被放入的顺序一致（先进先出 FIFO）。
                // 这保留了上一轮排序的结果，是算法“稳定性”的核心。
                for (String str : bucket) {
                    // 将字符串按顺序放回原数组。
                    arr[key++] = str;
                }

                // 将当前桶清空，为下一轮外层循环（处理 i-1 位置）做准备。
                bucket.clear();
            }
        }
    }
}
