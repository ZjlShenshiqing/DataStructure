package com.zjl.algorithm.binarysearch;

import java.util.Arrays;

/**
 * 插入排序实现
 * @author zhangjlk
 * @date 2025/9/13 10:53
 */
public class InsertionSort {

//    public static void sort(int[] a) {
//
//    }
//
//    /**
//     * 插入排序
//     * @param a 数组
//     * @param low 未排序区域的左边界
//     */
//    private static void insertion(int[] a, int low) {
//        if (low == a.length) {
//            return;
//        }
//
//        int temp = a[low];
//        int i = low - 1; // 指针 i 指向已排序区域的最后一个元素（初始为 low-1）
//
//        while (i >= 0 && a[i] > temp) {
//            // 向左查找，把所有比 temp 大的元素往后挪一位，留给将来要插入的值
//            a[i + 1] = a[i];  // 把大的数往后移
//            i--;              // 继续往左找
//        }
//
//        if (i + 1 != low) { // 减少一次赋值动作
//            a[i + 1] = temp; // 找到了合适的位置，插入 temp
//        }
//
//        insertion(a, low + 1);
//    }

    public static void sort(int[] a) {

        // 外层循环从 1 开始，因为我们默认第 0 个元素 a[0] 自身是“已排序”的
        // low 是未排序区域的第一个元素
        for (int low = 1; low < a.length; low++) {

            // 将 low 位置的元素插入至 [0..low-1] 的已排序区域
            int t = a[low];
            int i = low - 1; // 已排序区域指针

            // 开始从右向左比较（在“已排序区域”中寻找插入位置）
            //    循环条件:
            //    - i >= 0: 保证指针 i 还在数组范围内
            //    - t < a[i]: 保证我们“手里的牌”t 小于“正在比较的牌”a[i]
            while (i >= 0 && t < a[i]) {

                // 如果 t 更小，说明 a[i] 不在正确的位置，
                // 必须将 a[i] 向右移动一位（即 "空出插入位置"）
                a[i + 1] = a[i];

                // 指针 i 向左移动，继续比较前一个元素
                i--;
            }

            // 找到插入位置
            if (i != low - 1) {
                a[i + 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 5, 8, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
