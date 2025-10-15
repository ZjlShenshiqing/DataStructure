package com.zjl.algorithm.binarysearch;

/**
 * 插入排序实现
 * @author zhangjlk
 * @date 2025/9/13 10:53
 */
public class InsertionSort {

    public static void sort(int[] a) {

    }

    /**
     * 插入排序
     * @param a 数组
     * @param low 未排序区域的左边界
     */
    private static void insertion(int[] a, int low) {
        if (low == a.length) {
            return;
        }

        int temp = a[low];
        int i = low - 1; // 指针 i 指向已排序区域的最后一个元素（初始为 low-1）

        while (i >= 0 && a[i] > temp) {
            // 向左查找，把所有比 temp 大的元素往后挪一位，留给将来要插入的值
            a[i + 1] = a[i];  // 把大的数往后移
            i--;              // 继续往左找
        }

        if (i + 1 != low) { // 减少一次赋值动作
            a[i + 1] = temp; // 找到了合适的位置，插入 temp
        }

        insertion(a, low + 1);
    }
}
