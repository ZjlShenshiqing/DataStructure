package com.zjl.algorithm.dynamicprogramming;

/**
 * 最长公共子串
 *
 * @author zhangjlk
 * @date 2025/11/29 21:43
 */
public class LCSubString {

    static int lcs(String a, String b) {
        // dp[i][j] 表示以 a[i] 和 b[j] 结尾的最长公共子串长度
        int[][] dp = new int[a.length()][b.length()];
        int max = 0;

        // i 遍历字符串 a (对应 dp 的行)
        for (int i = 0; i < a.length(); i++) {
            // j 遍历字符串 b (对应 dp 的列)
            for (int j = 0; j < b.length(); j++) {

                // 比较 a 的第 i 个字符 和 b 的第 j 个字符
                if (a.charAt(i) == b.charAt(j)) {
                    // 边界情况：如果是第一行或第一列，没有左上角，直接设为 1
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        // 核心转移方程：继承左上角的值 + 1
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    }
                    // 只有当字符相等时，才有可能更新最大值
                    max = Math.max(max, dp[i][j]);
                } else {
                    // 不相等则断开，长度为 0
                    dp[i][j] = 0;
                }
            }
        }
        return max;
    }
}
