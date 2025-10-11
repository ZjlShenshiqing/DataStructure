package com.zjl.algorithm.binarysearch;

/**
 *
 * @author zhangjlk
 * @date 2025/9/13 10:11
 */
public class BubbleSort {

    public static void main(String[] args) {

    }

    /**
     * 冒泡排序
     * @param a 排序的数组
     * @param j 未排序区域的右边界
     */
    private static void bubble(int[] a, int j) {
        if (j == 0) {
            return;
        }

        for (int i = 0; i < j; i++) {
            if (a[i] > a[i + 1]) {
                int tmp = a[i];
                a[i] = a[i + 1];
                a[i + 1] = tmp;
            }
        }

        bubble(a, j - 1); // 进行第二轮比较
    }
}
