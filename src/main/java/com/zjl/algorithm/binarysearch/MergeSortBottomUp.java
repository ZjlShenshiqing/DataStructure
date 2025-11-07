package com.zjl.algorithm.binarysearch;

import java.util.Map;

/**
 * 归并排序 - 自下而上
 *
 * @author zhangjlk
 * @date 2025/11/3 下午6:48
 * @description MergeSortBottomUp
 */
public class MergeSortBottomUp {

    /**
     * 归并排序的“合并”阶段 (Merge)
     * <p>
     * 此方法从一个数组 (a1) 中取出两个相邻的、已排序的范围，
     * 将它们合并成一个有序序列，并存入另一个数组 (a2) 中。
     *
     * @param a1   源数组，包含两个已排序的子数组
     * @param i    第一个有序范围的起始索引 (在 a1 中)
     * @param iEnd 第一个有序范围的结束索引 (在 a1 中)
     * @param j    第二个有序范围的起始索引 (在 a1 中)
     * @param jEnd 第二个有序范围的结束索引 (在 a1 中)
     * @param a2   目标数组 (临时数组)，用于存放合并后的有序结果。
     * 注意：a2 的 k 索引必须和 a1 的 i 索引保持一致。
     */
    public static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2) {
        // k 是目标数组 a2 的写入指针，它必须从第一个范围的起始位置 i 开始
        int k = i;

        // 1. 主合并循环
        // 当两个范围都还有元素时，进行比较
        while (i <= iEnd && j <= jEnd) {
            // 比较两个范围的当前元素
            if (a1[i] < a1[j]) {
                // 将较小的元素 (a1[i]) 放入目标数组 a2
                a2[k] = a1[i];
                i++; // 第一个范围的指针后移
            } else {
                // 将较小的元素 (a1[j]) 放入目标数组 a2
                a2[k] = a1[j];
                j++; // 第二个范围的指针后移
            }
            k++; // 目标数组的写入指针后移
        }

        // 2. 清理剩余元素 (处理一个范围已耗尽的情况)
        // 循环结束后，i 和 j 中必有一个已经超出了范围 (i > iEnd 或 j > jEnd)

        // 如果是第一个范围 (i) 耗尽了 (i > iEnd)
        if (i > iEnd) {
            // 说明第二个范围 (j) 还有剩余元素，
            // 将 j 范围中所有剩余元素（它们必然是最大的）批量复制到 a2 的末尾
            // System.arraycopy(源数组, 源起始索引, 目标数组, 目标起始索引, 复制长度)
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        }

        // 如果是第二个范围 (j) 耗尽了 (j > jEnd)
        if (j > jEnd) {
            // 说明第一个范围 (i) 还有剩余元素，
            // 将 i 范围中所有剩余元素（它们必然是最大的）批量复制到 a2 的末尾
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }
    }

    /**
     * 归并排序（Merge Sort）的入口方法
     * @param a1 待排序的原始数组
     */
    public static void sort(int[] a1) {
        int n = a1.length;
        // a2 是用于合并的临时数组
        int[] a2 = new int[n];

        // 1. 外层循环：控制“合并宽度” (width)
        // width 是指“已排序子数组”的长度
        // width 从 1 开始，每次循环翻倍 (1, 2, 4, 8, ...)
        // 当 width >= n 时，整个数组已经合并完毕
        for (int width = 1; width < n; width *= 2) {
            // 2. 内层循环：遍历所有需要合并的组
            // left 是每一组（包含两个子数组）的起始索引
            // 每次循环，left 递增 2 * width（因为我们要合并两个宽度为 width 的子数组）
            for (int left = 0; left < n; left += 2 * width) {

                // 3. 计算两个子数组的边界
                // [left ... mid] 是第一个已排序子数组
                // [mid + 1 ... right] 是第二个已排序子数组

                // 计算第一个子数组的右边界
                // Math.min 用于处理边界情况：如果 (left + width - 1) 越界了，就取数组末尾 (n - 1)
                int mid = Math.min(left + width - 1, n - 1);

                // 计算第二个子数组的右边界
                int right = Math.min(left + 2 * width - 1, n - 1);
                // 4. 检查第二个子数组是否存在
                // 如果 mid >= right，意味着第二个子数组的起始位置 (mid + 1) 已经超出了 right
                // 这通常发生在内层循环的最后一次，只剩下一个（已排序的）子数组，没有可合并的对了
                // 此时直接跳过 merge，进入下一轮外层循环
                if (mid >= right) {
                    break;
                }

                // 5. "合" - 合并两个已排序的子数组
                merge(a1, left, mid, mid + 1, right, a2);
            }

            // 6. 将合并后的结果复制回原数组
            // 在内层循环（所有 "width" 宽度的合并）完成后，
            // a2 中存放的是本轮（例如 width=2）合并后的所有结果（即若干个长度为 4 的有序数组）
            // 必须将 a2 的全部内容复制回 a1，
            // 以便下一轮外层循环（例如 width=4）能从 a1 中读到正确的数据。
            System.arraycopy(a2, 0, a1, 0, n);
        }
    }

}
