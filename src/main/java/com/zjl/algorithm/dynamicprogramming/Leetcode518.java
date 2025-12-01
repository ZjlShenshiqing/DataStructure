package com.zjl.algorithm.dynamicprogramming;

/**
 * 零钱兑换II
 * @author zhangjlk
 * @date 2025/11/29 18:51
 */
public class Leetcode518 {
    public int change(int amount, int[] coins) {
        // 1. 定义 DP 表
        int[][] dp = new int[coins.length][amount + 1];

        // 2. 初始化 Base Case (针对金额 0)
        // 无论用什么硬币，凑出金额 0 的方法永远只有 1 种（那就是什么都不拿）。
        // 这是后续累加的基础，否则结果全是 0。
        for (int i = 0; i < coins.length; i++) {
            dp[i][0] = 1;
        }

        // 3. 单独处理第一枚硬币 (coins[0]) 的情况
        // 因为没有“上一行”可以继承，所以必须手动初始化第一行。
        for (int j = 1; j < amount + 1; j++) {
            // 这里必须是 >=，表示只要当前金额 j 足够买一枚 coins[0]
            if (j >= coins[0]) {
                // 方案数 = 减去这枚硬币后剩余金额的方案数
                // 比如硬币是2，凑6元的方法数 = 凑4元的方法数 = ... = 凑0元的方法数 = 1
                dp[0][j] = dp[0][j - coins[0]];
            }
            // 如果 j < coins[0]，dp[0][j] 默认为 0，表示凑不出来，无需操作
        }

        // 4. 处理剩余的硬币 (从第 2 枚硬币开始遍历)
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j < amount + 1; j++) {

                // 情况 A: 当前金额 j 足够大，能够容纳当前的硬币 coins[i]
                if (j >= coins[i]) {
                    // 核心状态转移方程 (完全背包的核心)：
                    // dp[i][j] = (不选这枚硬币) + (选这枚硬币)
                    // 1. dp[i - 1][j]: 不选当前硬币。方案数直接继承“上一行”的结果（只用旧硬币凑 j）。
                    // 2. dp[i][j - coins[i]]: 选当前硬币。既然硬币无限，选了还能再选，
                    //    所以查的是“当前行” (i) 在 j-coin 位置的数据。
                    dp[i][j] = dp[i][j - coins[i]] + dp[i - 1][j];
                }
                // 情况 B: 当前金额 j 太小，买不起 coins[i]
                else {
                    // 只能完全继承上一行的方案数（相当于这枚硬币不存在）
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 5. 返回结果
        // 表格最右下角的值：使用所有硬币 (coins.length-1)，凑出目标金额 (amount) 的方案总数
        return dp[coins.length - 1][amount];
    }
}
