package com.zjl.datastructure.HashTable;

import java.util.HashMap;

/**
 * 判断有没有重复元素-Leetcode 217
 *
 * @author zhangjlk
 * @date 2025/10/31 下午3:52
 * @description Leetcode217
 */
public class Leetcode217 {

    public boolean containsDuplicate(int[] nums) {
        // 创建一个哈希表
        HashMap<Integer, Integer> map = new HashMap<>();
        // 遍历数组
        for (int key : nums) {
            if (map.containsKey(key)) {
                return true;
            }
            map.put(key, key);
        }
        return false;
    }
}
