package com.zjl.algorithm.binarysearch;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 单边快排
 *
 * @author zhangjlk
 * @date 2025/11/4 下午4:33
 * @description QuickSortLomuto
 */
public class QuickSortLomuto {

    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    /**
     * 快速排序的递归主函数
     * 对数组 a 的 [left, right] 区间进行排序（闭区间）
     *
     * @param a     待排序的整数数组
     * @param left  当前处理区间的左边界
     * @param right 当前处理区间的右边界
     */
    private static void quick(int[] a, int left, int right) {
        // 基线条件：如果左边界 >= 右边界，说明区间无效或只有一个元素，无需排序
        if (left >= right) {
            return;
        }

        // 调用分区函数，将数组划分为两部分，并返回基准点最终的位置索引 p
        int p = partition1(a, left, right);

        // 递归地对基准点左侧的子数组进行快速排序
        quick(a, left, p - 1);

        // 递归地对基准点右侧的子数组进行快速排序
        quick(a, p + 1, right);
    }

    /**
     * 分区函数（Lomuto 分区方案）
     * 选择 a[right] 作为基准值（pivot），将数组划分为：
     * - 小于 pivot 的元素放在左边
     * - 大于等于 pivot 的元素放在右边
     * 最终将 pivot 放到正确的位置，并返回该位置的索引
     *
     * @param a     待分区的数组
     * @param left  分区的左边界（包含）
     * @param right 分区的右边界（包含），同时 a[right] 作为 pivot
     * @return pivot 最终所在的位置索引
     */
    private static int partition1(int[] a, int left, int right) {
        // 选择最后一个元素作为基准值（pivot）
        int pivot = a[right];

        // i 指向“小于 pivot 的区域”的下一个插入位置（即该区域的右边界 + 1）
        // 初始时，小于 pivot 的区域为空，所以 i 从 left 开始
        int i = left;

        // j 用于遍历 [left, right - 1] 区间的所有元素
        int j = left;

        // 遍历除 pivot 外的所有元素
        while (j < right) {
            // 如果当前元素 a[j] 小于 pivot，说明它应该被放到“小于 pivot 的区域”
            if (a[j] < pivot) {
                // 如果 i 和 j 不同，说明 a[j] 不在正确位置，需要与 a[i] 交换
                if (i != j) {
                    swap(a, i, j);
                }
                // 将 i 向右移动，扩展“小于 pivot 的区域”
                i++;
            }
            // 无论是否交换，j 都继续向右移动
            j++;
        }

        // 循环结束后，[left, i-1] 区间内都是小于 pivot 的元素，
        // [i, right-1] 区间内都是 >= pivot 的元素。
        // 此时应将 pivot（原在 a[right]）放到 i 的位置，使其左边都小于它，右边都大于等于它
        swap(a, i, right);

        // 返回 pivot 最终的位置 i
        return i;
    }

    /**
     * 双边指针分区函数（以最左侧元素为基准）
     * 使用两个指针 i 和 j 从数组两端向中间扫描，将小于基准的元素移到左边，
     * 大于基准的移到右边，最终将基准放到正确位置。
     *
     * @param a     待分区的数组
     * @param left  分区左边界（包含）
     * @param right 分区右边界（包含）
     * @return 基准元素最终所在的位置索引
     */
    private static int partition2(int[] a, int left, int right) {
        // 选择最左边的元素作为基准值（pivot）
        int pivot = a[left];

        // 初始化双指针：
        // i 从左向右扫描，用于找“大于等于 pivot”的元素
        // j 从右向左扫描，用于找“小于等于 pivot”的元素
        int i = left;
        int j = right;

        // 当两个指针尚未相遇时，持续扫描
        while (i < j) {
            // 1. j 从右往左移动，跳过所有“大于 pivot”的元素
            //    目标：找到第一个 ≤ pivot 的元素（注意：这里用 >，所以等于 pivot 会停止）
            while (i < j && a[j] > pivot) {
                j--;
            }

            // 2. i 从左往右移动，跳过所有“小于 pivot”的元素
            //    目标：找到第一个 ≥ pivot 的元素（注意：这里用 <，所以等于 pivot 会停止）
            while (i < j && a[i] < pivot) {
                i++;
            }

            // 3. 此时：
            //    - a[i] ≥ pivot
            //    - a[j] ≤ pivot
            //    - 且 i < j（尚未交错）
            //    说明 a[i] 和 a[j] 位置颠倒，需要交换
            swap(a, i, j);
        }

        // 循环结束时，i == j，指针相遇
        // 此时 a[i]（即 a[j]）是 ≤ pivot 的（因为 j 最后停在 ≤ pivot 的位置）
        // 所以把原始基准值（a[left]）和 a[i] 交换，使基准归位
        swap(a, i, left);

        // 返回基准值最终的位置索引
        return i;
    }

    /*
    循环内
        i 从 left + 1 开始，从左向右找大的或相等的
        j 从 right 开始，从右向左找小的或相等的
        交换，i++ j--

    循环外 j 和 基准点交换，j 即为分区位置
    */
    private static int partition3(int[] a, int left, int right) {
        // --- 随机化基准 ---
        // 随机选择一个 [left, right] 范围内的索引
        int idx = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        // 将随机选中的元素与最左侧元素交换位置
        swap(a, left, idx);

        // --- 初始化 ---
        // 使用最左侧的元素（现在是随机选来的）作为基准值 (pivot value)
        int pv = a[left];
        int i = left + 1;
        int j = right;

        // 当 i 和 j 尚未交错时（允许 i == j）
        // 因为 i 是 left + 1，会发生交错
        while (i <= j) {
            while (i <= j && a[i] < pv) {
                i++;
            }
            while (i <= j && a[j] > pv) {
                j--;
            }

            // 此时，i 和 j 要么都停下了，要么已经交错 (i > j)
            // 如果 i 还在 j 的左边（或重合），说明找到了需要交换的对
            // a[i] >= pv (在左侧，但值偏大)
            // a[j] <= pv (在右侧，但值偏小)
            if (i <= j) {
                // 交换 a[i] 和 a[j]
                swap(a, i, j);

                // 交换后，两个指针必须各自推进，
                // 否则如果 a[i] == pv 且 a[j] == pv，两个内部 while 都会停止，
                // 导致外部 while 循环卡死（无限循环）。
                i++;
                j--;
            }
        }
        swap(a, j, left);
        return j;
    }

    /**
     * 交换数组中两个位置的元素
     */
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
