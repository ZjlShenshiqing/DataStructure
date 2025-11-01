package com.zjl.datastructure.HashTable;

/**
 * 找出第一个不重复字符
 *
 * @author zhangjlk
 * @date 2025/11/1 上午11:06
 * @description Leetcode387
 */
public class Leetcode387 {

    public int firstUniqChar(String s) {
        int[] array = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            array[c - 'a']++;
        }
        for (int i = 0; i < chars.length; i++) {
            char charAt = chars[i];
            if (array[charAt - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
