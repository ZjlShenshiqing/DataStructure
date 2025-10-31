package com.zjl.datastructure.HashTable;

import java.util.*;

/**
 * @author zhangjlk
 * @date 2025/10/31 下午3:22
 * @description Leetcode49
 */
public class Leetcode49 {

    // 自定义键类型：用于表示一个字符串中各字母的出现频次
    // 由于 Java 的 int[] 不能直接作为 HashMap 的可靠 key（未重写 equals/hashCode），
    // 所以我们封装成一个类，并正确实现 equals 和 hashCode。
    static class ArrayKey {
        int[] key = new int[26];

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ArrayKey arrayKey = (ArrayKey) o;
            return Objects.deepEquals(key, arrayKey.key);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(key);
        }

        // 构造函数：根据输入字符串统计每个字母的频次
        public ArrayKey(String str) {
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);        // 获取第 i 个字符
                key[charAt - 'a']++;                // 转为 0~25 索引，并计数 +1
            }
        }
    }

    // 主方法：将字符串数组按“字母异位词”分组
    // 字母异位词：字母种类和数量完全相同，但顺序不同（如 "eat", "tea", "ate"）
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<ArrayKey, List<String>> map = new HashMap<>();
        for (String str : strs) {

            // 为当前字符串生成其“字母频次特征”作为 key
            ArrayKey key = new ArrayKey(str);

            // 如果 map 中还没有这个 key，就创建一个新的 ArrayList 作为 value；
            // 如果已有，则直接返回对应的 list。
            List<String> list = map.computeIfAbsent(key, k -> new ArrayList<>());

            // 将当前字符串加入对应分组
            list.add(str);
        }

        // 返回所有分组（map 的 values 集合），包装为 ArrayList
        return new ArrayList<>(map.values());
    }
}
