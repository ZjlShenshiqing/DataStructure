package com.zjl.datastructure.HashTable;

import java.util.Arrays;

/**
 * 判断字母异位词
 *
 * @author zhangjlk
 * @date 2025/11/1 上午10:39
 * @description Leetcode242
 */
// 解题思路：字符打散后放入int[26]数组中，并比较数组
public class Leetcode242 {

    public boolean isAnagram(String s, String t) {
       return Arrays.equals(getKey(s), getKey(t));
    }

    private int[] getKey(String s) {
        int[] array = new int[26];
        char[] chars = s.toCharArray();

        for (char ch : chars) {
            // 将字符转换为数组下标：
            // 'a' - 'a' = 0，'b' - 'a' = 1，...，'z' - 'a' = 25
            // 然后将对应位置的计数加 1 , 这个的意思就是在那个长度为26的数组里面，在对应的位置加个1，代表有东西了
            array[ch - 'a']++;
        }
        return array;
    }
}
