package com.zjl.algorithm.dynamicprogramming;

/**
 * 零钱兑换问题
 *
 * @author zhangjlk
 * @date 2025/11/28 17:26
 */
public class Leetcode322 {

    public int coinChange(int[] coins, int amount) {
        // 1. 定义 DP 表
        // dp[i][j] 的含义：
        // 使用前 i+1 个硬币（coins[0]...coins[i]），凑出金额 j 所需的最少硬币数量
        // 用 amount + 1 来表示“无法凑出”也就是“无穷大”（因为硬币数不可能超过 amount）
        int[][] dp = new int[coins.length][amount + 1];

        // Java 数组默认初始化为 0。
        // dp[...][0] 默认为 0，表示凑出金额 0 不需要任何硬币，这是正确的 Base Case。

        // 2. 初始化第一行（只使用第一个硬币 coins[0] 的情况）
        for (int j = 1; j < amount + 1; j++) {
            // 如果当前金额 j 大于等于第一个硬币的面值
            if (j >= coins[0]) {
                // 尝试放入一个 coins[0]，硬币数 = (凑出剩余金额 j-coins[0] 的硬币数) + 1
                // 注意：这里依赖 dp[0][0]=0 的初始值
                dp[0][j] = dp[0][j - coins[0]] + 1;
            } else {
                // 如果金额 j 小于第一个硬币的面值，根本放不进去，设为“无穷大”
                dp[0][j] = amount + 1;
            }
        }

        // 3. 填充剩余的表格（从第二个硬币开始遍历）
        for (int i = 1; i < coins.length; i++) { // i 代表当前可选的硬币索引
            for (int j = 1; j < amount + 1; j++) { // j 代表当前要凑的目标金额

                // 情况 A: 当前金额 j 足够容纳当前硬币 coins[i]
                if (j >= coins[i]) {
                    // 状态转移方程（核心逻辑）：
                    // min(如果不选这枚硬币, 如果选这枚硬币)
                    // 1. dp[i - 1][j]: 完全不使用当前硬币，只用之前的硬币凑出 j
                    // 2. dp[i][j - coins[i]] + 1: 使用当前硬币。既然是“完全背包”，选了还能再选，所以查的是当前行 dp[i]
                    dp[i][j] = Integer.min(dp[i][j - coins[i]] + 1, dp[i - 1][j]);
                }
                // 情况 B: 当前金额 j 太小，放不下 coins[i]
                else {
                    // 只能继承“不选这枚硬币”的结果
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 4. 返回结果
        // 查看表格右下角的值（用所有硬币凑出最终 amount）
        // 如果值仍大于 amount，说明无法凑出（之前的 amount + 1 被传递下来了），返回 -1
        // 否则返回具体硬币数
        return dp[coins.length - 1][amount] < amount + 1 ? dp[coins.length - 1][amount] : -1;
    }
}
