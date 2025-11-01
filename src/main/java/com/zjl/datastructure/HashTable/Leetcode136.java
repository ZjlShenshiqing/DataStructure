package com.zjl.datastructure.HashTable;

import java.util.HashSet;

/**
 *
 * @author zhangjlk
 * @date 2025/10/31 下午4:08
 * @description Leetcode136
 */
public class Leetcode136 {

    public int singleNumber(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        return set.toArray(new Integer[0])[0];
    }
}
