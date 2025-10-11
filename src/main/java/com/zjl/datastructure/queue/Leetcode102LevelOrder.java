package com.zjl.datastructure.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树层序遍历
 * @author zhangjlk
 * @date 2025/9/25 下午3:56
 * @description Leetcode102LevelOrder
 */
public class Leetcode102LevelOrder {

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> res = new ArrayList<>();

        if (root == null) { // 边界：空树直接返回空列表
            return res;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();

        queue.offer(root);  // 把根节点放进队列（第0层开始）
        int lay1 = 1;       // 当前层（即将处理的层）有 1 个节点（就是 root）

        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>(); // 存当前层的值
            int lay2 = 0; // 用来统计下一层有多少个节点

            for (int i = 0; i < lay1; i++) {
                TreeNode node = queue.poll();
                level.add(node.value);

                if (node.left != null) {
                    queue.offer(node.left);
                    lay2++;
                }

                if (node.right != null) {
                    queue.offer(node.right);
                    lay2++;
                }
            }

            res.add(level); // 把当前层的结果加入最终答案
            lay1 = lay2;    // 下一层变成新的“当前层”
        }

        return res; // 返回结果
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(
                new TreeNode(
                        new TreeNode(4),
                        2,
                        new TreeNode(5)
                ),
                1,
                new TreeNode(
                        new TreeNode(6),
                        3,
                        new TreeNode(7)
                ));

        LinkedListQueue<TreeNode> queue = new LinkedListQueue<>();

        queue.offer(root);
        int lay1 = 1; // 当前层节点数
        while (!queue.isEmpty()) {
            int lay2 = 0; // 下一层节点数
            for (int i = 0; i < lay1; i++) {
                TreeNode node = queue.poll();
                System.out.println(node + "");
                if (node.left != null) {
                    queue.offer(node.left);
                    lay2++;
                }

                if (node.right != null) {
                    queue.offer(node.right);
                    lay2++;
                }
            }
            System.out.println();
            lay1 = lay2;
        }
    }
}
