package com.zjl.algorithm.greedyAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 零钱兑换 - 计算并返回可以凑成总金额所需的最少的硬币个数
 *
 * @author zhangjlk
 * @date 2025/11/16 11:44
 */
public class Leetcode322 {

    /**
     * 算法思路：先从大面值的硬币开始凑，这样最经济
     *
     * 贪心的原则：
     *    1. 每一次都选取当前的最优解
     *    2. 向前看，别回头 - 也就是说已经选择了就选了，不能再倒回去了
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        // 剩余的硬币
        int remainder = amount;
        // 最后使用硬币的个数
        int count = 0;
        // 假定传过来的coins数组已经是由大到小排好序了
        for (int coin : coins) {
            while (remainder > coin) {
                remainder -= coin;
                count++;
            }
            if (remainder == coin) {
                remainder = 0;
                count++;
                // 在这里写是为了跳出外层循环
                break;
            }
        }
        if (remainder > 0) {
            return -1;
        } else  {
            return count;
        }
    }

}
