package com.zjl.algorithm.binarysearch;

/**
 * 归并排序 - 自顶向下的递归实现
 *
 * @author zhangjlk
 * @date 2025/11/3 下午5:32
 * @description MergeSort
 */
public class MergeSortTopDown {

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
        // 1. 创建一个与原数组等大小的临时数组 (a2)
        // 这个临时数组在“合并”(merge)阶段是必需的，用来存放排序好的结果
        int[] a2 = new int[a1.length];

        // 2. 开始递归地分割数组
        // a1 是源数组，a2 是临时数组
        split(a1, 0, a1.length - 1, a2);
    }

    /**
     * 递归地分割和合并数组
     * @param a1    源数组（从中读取数据）
     * @param left  当前要处理的范围的左边界索引
     * @param right 当前要处理的范围的右边界索引
     * @param a2    临时数组（用于存放合并后的数据）
     */
    public static void split(int[] a1, int left, int right, int[] a2) {

        // 1. “治” (Conquer) - 递归的终止条件
        // 如果 left == right，说明子数组只剩一个元素，天然有序，停止分割
        if (left == right) {
            return;
        }

        // 2. “分” (Divide) - 找到中间点
        int mid = (left + right) >>> 1;

        // 3. 递归分割左半部分
        split(a1, left, mid, a2);

        // 4. 递归分割右半部分
        split(a1, mid + 1, right, a2);

        // 5. “合” (Combine) - 合并两个已排序的子数组
        // 此时，[left ... mid] 和 [mid + 1 ... right] 这两部分在 a1 中
        // 都已经（通过递归）被排好序了。
        // 我们调用 merge 方法，将 a1 的这两个范围合并，结果存入 a2 的 [left ... right] 范围
        merge(a1, left, mid, mid + 1, right, a2);

        // 6. 将合并后的结果复制回原数组
        // 因为 merge 是将结果存放在 a2 (临时数组) 中的，我们必须将 a2 范围内的、已排好序的数据，复制回原数组 a1 的位置。
        // 这样，当递归返回到上一层时，上一层的 merge 才能在 a1 中读到正确的已排序子数组。
        System.arraycopy(a2, left, a1, left, right - left + 1);
    }
}
