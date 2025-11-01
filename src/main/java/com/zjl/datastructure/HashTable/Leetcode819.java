package com.zjl.datastructure.HashTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 出现次数最多的单词
 *
 * @author zhangjlk
 * @date 2025/11/1 上午11:49
 * @description Leetcode819
 */
public class Leetcode819 {

    public String mostCommonWord(String paragraph, String[] banned) {
//       String[] splitResult = paragraph.toLowerCase().split("[^A-Za-z]+");
        Set<String> set = Set.of(banned);
        HashMap<String, Integer> map = new HashMap<>();
        char[] chars = paragraph.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else {
                String key = sb.toString();
                if (key.length() > 0 && !set.contains(key)) {
                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            }
            sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            String key = sb.toString();
            if (!set.contains(key)) {
                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            }
        }
//        for (String key : splitResult) {
////            Integer value = map.get(key);
////            if (value == null) {
////                value = 0;
////            }
////            map.put(key, value + 1);
//            if (!set.contains(key)) {
//                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
//            }
//        }
        Integer max = 0;
        String maxKey = null;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            Integer value = e.getValue();
            if (value > max) {
                max = value;
                maxKey = e.getKey();
            }
        }

        return maxKey;
    }
}
