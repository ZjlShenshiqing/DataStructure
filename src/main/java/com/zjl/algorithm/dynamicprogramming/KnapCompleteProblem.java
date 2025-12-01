package com.zjl.algorithm.dynamicprogramming;

/**
 * 完全背包问题
 *
 * @author zhangjlk
 * @date 2025/11/27 17:56
 */
public class KnapCompleteProblem {

    /**
     * 物品类，定义了物品的基本属性
     */
    static class Item {
        int index;      // 编号
        String name;    // 名称
        int weight;     // 重量
        int value;      // 价值

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
        // 初始化测试数据：青铜、白银、黄金
        // 完全背包特点：每种物品都有无限个
        Item[] items = new Item[]{
                new Item(1, "青铜", 2, 3),    // 重量2，价值3
                new Item(2, "白银", 3, 4),    // 重量3，价值4
                new Item(3, "黄金", 4, 7),    // 重量4，价值7
        };
        // 计算背包容量为 6 时的最大价值
        System.out.println(select(items, 6));
    }

    /*
     * 推导过程参考表：
     * 0   1   2   3   4   5   6  (背包容量)
     * 1青铜  0   0   3   3   6   6   9  (只放青铜)
     * 2白银  0   0   3   4   6   7   9  (考虑青铜+白银)
     * 3黄金  0   0   3   4   7   7   10 (考虑青铜+白银+黄金)
     */

    /**
     * 完全背包核心算法
     * @param items 物品列表
     * @param total 背包总容量
     * @return 最大价值
     */
    private static int select(Item[] items, int total) {
        // 行：表示当前考虑到第几个物品 (0 ~ items.length-1)
        // 列：表示当前的背包容量 (0 ~ total)
        // dp[i][j] 的含义：在前 i 个物品中选择，且背包容量为 j 时的最大价值
        int[][] dp = new int[items.length][total + 1];

        // --- 步骤 1: 初始化第一行 (处理第 0 个物品：青铜) ---
        Item item0 = items[0];
        for (int j = 0; j < total + 1; j++) {
            // 如果背包容量 >= 物品重量，说明能放进去
            if (j >= item0.weight) {
                // 核心逻辑：
                // dp[0][j - item0.weight]：是指【当前行】更小容量时的最大价值
                // 因为是完全背包，物品无限，所以是基于“在这个容量下已经放了青铜的基础上，再加一个青铜”
                dp[0][j] = dp[0][j - item0.weight] + item0.value;
            }
            // 否则默认为 0 (Java int数组初始化即为0，所以省略了 else dp[0][j]=0)
        }

        // --- 步骤 2: 遍历剩余物品 (从第 1 个物品开始：白银、黄金...) ---
        for (int i = 1; i < items.length; i++) {
            Item item = items[i]; // 当前要考虑的新物品

            // 遍历所有可能的背包容量
            for (int j = 0; j < total + 1; j++) {

                // 情况 A: 背包能装下当前物品
                if (j >= item.weight) {
                    // 决策：在“不选这个物品”和“选这个物品”之间取最大值

                    // 1. dp[i - 1][j]: 直接继承上一行(不包含当前物品)同容量的最大价值。
                    // 2. dp[i][j - item.weight] + item.value: 。
                    //    注意这里查的是 dp[i] (当前行)，而不是 dp[i-1]。
                    //    意思是：扣除当前物品重量后，在【已经考虑了当前物品】的状态下，剩余空间能装的最大价值 + 当前物品价值。
                    //    这体现了“一个物品可以被多次选择”的特性。

                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - item.weight] + item.value);
                }
                // 情况 B: 背包太小，装不下当前物品
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 返回表格右下角的值，即考虑了所有物品、在最大容量下的最优解
        return dp[items.length - 1][total];
    }
}
