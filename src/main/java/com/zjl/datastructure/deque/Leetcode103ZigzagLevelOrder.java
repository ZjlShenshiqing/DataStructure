package com.zjl.datastructure.deque;

import com.zjl.datastructure.queue.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangjlk
 * @date 2025/9/30 下午3:50
 * @description ZigzagLevelOrder
 */
public class Leetcode103ZigzagLevelOrder {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> res = new ArrayList<>(); // 返回结果集

        if (root == null) { // 边界：空树直接返回空列表
            return res;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();

        queue.offer(root);  // 把根节点放进队列（第0层开始）
        int lay1 = 1;       // 当前层（即将处理的层）有 1 个节点（就是 root）
        boolean odd = true; // 当前位置是奇数层

        while (!queue.isEmpty()) { // 循环遍历元素，并就将元素加入到结果集中
            LinkedList<Integer> level = new LinkedList<>(); // 存当前层的值，注意奇数层向尾部添加元素，偶数层向头部添加元素就ok了 注意数据结构必须得是linkedlist这种双端队列
            int lay2 = 0; // 用来统计下一层有多少个节点

            for (int i = 0; i < lay1; i++) {
                TreeNode node = queue.poll();
                if (odd) {
                    level.offerLast(node.value); // 奇数层，往尾部添加
                } else {
                    level.offerFirst(node.value); // 偶数层，往头部添加
                }

                if (node.left != null) {
                    queue.offer(node.left);
                    lay2++;
                }

                if (node.right != null) {
                    queue.offer(node.right);
                    lay2++;
                }
            }
            odd = !odd;
            res.add(level); // 把当前层的结果加入最终答案
            lay1 = lay2;    // 下一层变成新的“当前层”
        }

        return res; // 返回结果
    }

}
