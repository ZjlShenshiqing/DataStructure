package com.zjl.algorithm.greedyAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 零钱兑换 - 暴力递归
 *
 * @author zhangjlk
 * @date 2025/11/16 11:44
 */
public class Leetcode518 {

    /**
     * 处理步骤：假设金额是5，硬币组合是[1,2,5]
     * rec 1，5 硬币1 金额5  --> 走入else
     *
     */
    public int change(int amount, int[] coins) {
        return rec(0, coins, amount, new LinkedList<>(), true);
    }

    /**
     * 回溯求解：在给定硬币数组 coins 的前提下，统计
     * 「使用 coins[index..] 这些面值，可以凑成指定剩余金额 remainder 的组合个数」。
     *
     * @param index     当前选择的硬币下标（从 coins[index] 开始往后尝试）
     * @param coins     硬币面值数组（例如 [1, 2, 5]）
     * @param remainder 当前还需要凑出来的金额；每选一枚硬币就会减去相应面值
     * @param stack     用来记录“当前递归路径”下已经选择的硬币组合，仅用于打印调试
     * @param first     是否为第一次调用（根节点）。第一次调用不向栈中 push，后续递归才 push
     * @return          在当前状态下，能够凑成 remainder 的所有组合数量
     */
    private int rec(int index, int[] coins, int remainder, LinkedList<Integer> stack, boolean first) {
        // 如果不是第一次调用，说明本层递归刚刚选择了 coins[index] 这枚硬币，
        // 把它压入栈中，表示“当前路径上多用了这枚硬币”。
        if (!first) {
            stack.push(coins[index]);
        }

        // 情况 1：剩余金额 < 0，说明当前路径已经“凑超了”，后面再怎么加都不可能凑回正数
        if (remainder < 0) {
            // 打印一下当前路径，方便调试：这是一条走不通的路径
            print("无解", stack);

            // 退出本层递归之前，需要把本层压入的那一枚硬币弹出
            if (!stack.isEmpty()) {
                stack.pop();
            }

            // 没有解，返回 0
            return 0;

            // 情况 2：剩余金额 = 0，说明当前路径刚好凑齐，这是一个完整合法的解
        } else if (remainder == 0) {
            // 打印当前栈中的硬币组合，这是一个有效 solution
            print("有解", stack);

            // 同样需要撤销本层压栈操作，回到上一层状态
            if (!stack.isEmpty()) {
                stack.pop();
            }

            // 找到 1 个解，因此返回 1
            return 1;

            // 情况 3：剩余金额 > 0，还没凑够，需要继续向下搜索其它硬币
        } else {
            int count = 0; // 用来统计从当前状态出发，能找到多少种合法组合

            // 从当前 index 开始，依次尝试 coins[i] 作为“下一枚硬币”
            for (int i = index; i < coins.length; i++) {
                // 递归调用：
                //  - 选择 coins[i] 作为当前新增的一枚硬币
                //  - 剩余金额变成 remainder - coins[i]
                //  - 仍然从 i 开始往后尝试（允许重复使用同一面值的硬币）
                //  - 这里传 false，表示后续递归层都不是“第一次调用”，需要正常 push 进栈
                count += rec(i, coins, remainder - coins[i], stack, false);
            }

            // 当前层的所有分支都尝试完毕了，要退出当前递归。
            // 若本层曾经向栈中 push 过一枚硬币（即 first 为 false），此时栈顶就是本层那枚硬币
            if (!stack.isEmpty()) {
                stack.pop();
            }

            // 返回所有分支的解的总数
            return count;
        }
    }

    /**
     * 原地反转一个 int 数组
     * 例如 [1,2,5] 变成 [5,2,1]
     */
    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;
        }
    }


    private static void print(String prompt, LinkedList<Integer> stack) {
        ArrayList<Integer> print = new ArrayList<>();
        ListIterator<Integer> iterator = stack.listIterator();
        while (iterator.hasPrevious()) {
            print.add(iterator.previous());
        }
        System.out.println(prompt + print);
    }
}
