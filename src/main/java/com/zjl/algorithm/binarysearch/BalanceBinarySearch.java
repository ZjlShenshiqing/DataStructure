package com.zjl.algorithm.binarysearch;

import java.util.Arrays;

/**
 *
 * @author zhangjlk
 * @date 2025/8/28 16:33
 */
public class BalanceBinarySearch {

    public static int balanceBinarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (1 < right - left) {
            int mid = (left + right) >>> 1;

            if (target < nums[mid]) {
                right = mid;
            } else if (target > nums[mid]) {
                left = mid;
            }
        }

        if (target == nums[left]) {
            return left;
        } else {
            return -1;
        }
    }


    public static int binarySearchLeftMost1(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int candidate = -1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (target < nums[mid]) {
                right = mid - 1;
            }  else if (target > nums[mid]) {
                left = mid + 1;
            } else  {
                candidate = mid;
                // 继续向左查找
                right = mid - 1;
                return mid;
            }
        }

        return candidate;
    }
}
