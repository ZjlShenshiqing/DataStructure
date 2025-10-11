package com.zjl.algorithm.binarysearch;

/**
 * 在排序数组中查找元素的第一个和最后一个位置
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * @author zhangjlk
 * @date 2025/8/31 15:21
 */
public class leetcode35 {

    public int[] searchRange(int[] nums, int target) {
        int left = leftMost(nums, target);
        int right = rightMost(nums, target);

        if (left == nums.length || nums[left] != target) {
            return new int[]{-1, -1};
        }

        return new int[]{left, right};
    }

    public int leftMost(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;

            if (target <= nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public int rightMost(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;

            if (target < nums[mid]) {
                right = mid - 1;
            } else  {
                left = mid + 1;
            }
        }

        return right;
    }
}
