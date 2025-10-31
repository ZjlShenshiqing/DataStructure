package com.zjl.datastructure.HashTable;

import java.util.HashMap;

/**
 * 两数之和
 *
 * @author zhangjlk
 * @date 2025/10/31 下午12:27
 * @description Leetcode01
 */
public class Leetcode03 {

    public int lengthOfLongestSubstring(String s) {
        // 用哈希表记录每个字符最近一次出现的索引位置
        HashMap<Character, Integer> map = new HashMap();

        // 滑动窗口的左边界（包含），初始为 0
        int begin = 0;

        // 记录最长无重复子串的长度
        int maxLength = 0;

        // 右指针 end 从 0 遍历到字符串末尾
        for (int end = 0; end < s.length(); end++) {
            char charAt = s.charAt(end); // 当前字符

            // 如果当前字符之前出现过
            if (map.containsKey(charAt)) {
                Integer index = map.get(charAt); // 拿到重复字符的索引位置

                // 更新滑动窗口的左边界 (begin)
                begin = Math.max(begin, index + 1);

                // 更新当前字符的最新位置
                map.put(charAt, end);
            } else {
                // 首次出现，直接记录位置
                map.put(charAt, end);
            }
            // 计算当前窗口 [begin, end] 的长度，并更新最大值
            maxLength = Math.max(maxLength, end - begin + 1);
        }
        return maxLength;
    }
}
