package com.zjl.algorithm.greedyAlgorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 分数背包问题
 *
 * @author zhangjlk
 * @date 2025/11/20 12:42
 */
public class FractionalKnapsackProblem {

    // 背包中的物品（这里是液体），包含编号、重量、价值等信息
    public static class Item {
        // 物品的编号（方便输出或调试用，比如 0、1、2、3）
        int index;
        // 物品的重量（这里单位是“升”）
        int weight;
        // 物品的总价值
        int value;

        /**
         * 构造方法：创建一件物品
         *
         * @param index  物品编号
         * @param weight 物品重量
         * @param value  物品总价值
         */
        public Item(int index, int weight, int value) {
            this.index = index;
            this.weight = weight;
            this.value = value;
        }

        /**
         * 计算单位价值（性价比）：每 1 单位重量对应多少价值
         * 用于分数背包中的贪心排序：谁的 unitPrice 高，就优先选谁
         *
         * 这里用 int，是因为你题目里数据都设计成能整除，避免小数。
         */
        int unitPrice() {
            return value / weight;
        }

        /**
         * 方便打印调试，输出形如：Item(2)
         */
        @Override
        public String toString() {
            return "Item(" + index + ")";
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[]{
                new Item(0, 4, 24),
                new Item(1, 8, 160),
                new Item(2, 2, 4000),
                new Item(3, 6, 108),
                new Item(4, 1, 4000),
        };
        select(items, 10);
    }

    /**
     * 分数背包问题的贪心选择：
     * 给定若干物品（液体），每个有 weight 和 value，
     * 背包最多能装 totalWeight（例如 10 升），
     * 可以拿整件，也可以拿一部分，目标是让总价值 max 尽可能大。
     *
     * @param items       物品数组
     * @param totalWeight 背包能装的最大重量（容量）
     */
    private static void select(Item[] items, int totalWeight) {
        // 1. 先按单位价值从高到低排序
        //    unitPrice = value / weight，越高说明越“值钱”
        Arrays.sort(items, Comparator.comparingInt(Item::unitPrice).reversed());

        // 用来累加当前能得到的最大总价值
        int max = 0;

        // 2. 从单位价值最高的物品开始，依次尝试装进背包
        for (Item item : items) {
            // 如果当前剩余容量还能完全装下这件物品
            if (totalWeight >= item.weight) { // 可以拿完
                // 减少剩余容量
                totalWeight = totalWeight - item.weight;
                // 把这件物品的全部价值加进去
                max += item.value;
            } else {
                // 否则，剩余容量不够装完整件，只能拿一部分（分数背包的关键）
                // 拿“剩余容量”这么多重量，对应的价值 = 剩余容量 * 单位价值
                max += totalWeight * item.unitPrice();
                // 容量已经用完了，直接结束循环
                break;
            }
        }
    }
}
