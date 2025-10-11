package com.zjl.algorithm.binarysearch;

/**
 * 递归版本的二分查找
 * @author zhangjlk
 * @date 2025/9/12 15:39
 */
public class BinarySearchByRecursion {

    public static int search(int[] a, int target) {
        return recursion(a, 0, a.length - 1, target);
    }

    private static int recursion(int[] a, int target, int i, int j) {

        if (i > j) {
            return -1;
        }

        int m = (i + j) >>> 1;

        // 二分查找递归调用
        if (target < a[m]) {
            return recursion(a, target, i, m - 1);
        } else if (target > a[m]) {
            return recursion(a, target, m + 1, j);
        } else  {
            return m;
        }
    }
}
