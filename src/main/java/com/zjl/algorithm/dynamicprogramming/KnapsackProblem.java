package com.zjl.algorithm.dynamicprogramming;

import com.zjl.algorithm.greedyAlgorithm.FractionalKnapsackProblem;

/**
 * 0-1 背包问题
 *
 * @author zhangjlk
 * @date 2025/11/26 21:56
 */
public class KnapsackProblem {

    public static class Item {
        int index;
        String name;
        int weight;
        int value;

        public Item(int index, String name, int weight, int value) {
            this.index = index;
            this.name = name;
            this.weight = weight;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Item(" + name + ")";
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[]{
                new Item(1, "黄金", 4, 1600),
                new Item(2, "宝石", 8, 2400),
                new Item(3, "白银", 5, 30),
                new Item(4, "钻石", 1, 10_000),
        };
        System.out.println(select(items, 10));
    }

    /**
     * 使用二维动态规划（DP）解决 0/1 背包问题。
     *
     * @param items 物品数组，每个物品有重量（weight）和价值（value）。
     * @param total 背包的最大容量（总重量限制）。
     * @return 在不超过总容量的前提下，能获得的最大总价值。
     */
    static int select(Item[] items, int total) {
        // 定义 DP 数组：
        // dp[i][j] 表示：考虑前 i 个物品（索引从 0 到 i-1），
        // 且背包总容量不超过 j 时，能获得的最大价值。
        // 数组大小：items.length 行（对应物品数量），total + 1 列（对应容量 0 到 total）。
        int[][] dp = new int[items.length][total + 1];

        // 获取第一个物品（索引 0），用于初始化第一行。
        Item item0 = items[0];

        // 1. 初始化第一行 DP 状态：处理第一个物品 (i=0)
        // 遍历所有可能的容量 j (从 0 到 total)。
        for (int j = 0; j < total + 1; j++ ) { // 修正循环边界
            if (j >= item0.weight) {
                // 容量 j 装得下 item0
                // 此时的最大价值就是 item0 的价值
                dp[0][j] = item0.value;
            } else {
                // 容量 j 装不下 item0
                // 此时的最大价值为 0
                dp[0][j] = 0;
            }
        }

        // 2. 状态转移：从第二个物品开始（i=1）
        // 遍历每一个物品 i (代表当前正在处理第 i+1 个物品，即前 i+1 个物品)
        for (int i = 1; i < dp.length; i++) {
            Item item = items[i]; // 当前考虑的物品

            // 遍历所有可能的容量 j (从 0 到 total)
            for (int j = 0; j < total + 1; j++) {

                // 核心判断：当前容量 j 是否足以装下 item
                if (j >= item.weight) {
                    // 容量充足：可以选择装或不装 item

                    // 决策 1 (不装 item)：最大价值继承自前一个物品（i-1）在相同容量 j 下的最大价值。
                    int value_not_take = dp[i - 1][j];

                    // 决策 2 (装 item)：最大价值是 item 的价值 加上
                    // 前一个物品（i-1）在剩余容量 (j - item.weight) 下的最大价值。
                    int value_take = dp[i - 1][j - item.weight] + item.value;

                    // 取两种决策中的最大值
                    dp[i][j] = Integer.max(value_not_take, value_take);

                } else {
                    // 容量不足：只能选择不装 item
                    // 最大价值直接继承自前一个物品（i-1）在相同容量 j 下的最大价值。
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 3. 返回最终结果
        // 结果位于：考虑了所有物品 (items.length - 1)，且容量为总容量 (total) 的位置。
        return dp[items.length - 1][total];
    }
}
