package com.zjl.datastructure.binaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树最小深度计算 - 层序遍历求解
 *
 * @author zhangjlk
 * @date 2025/10/12 上午11:04
 * @description Leetcode111MinDepth_1
 */
public class Leetcode111MinDepth_2 {

    /**
     * 计算二叉树的最小深度。
     * * 思路:
     * 使用广度优先搜索 (BFS) 按层遍历树。BFS 的特性保证了我们会先访问离根节点近的节点。
     * 因此，我们在遍历过程中遇到的第一个叶子节点（即没有左右子节点的节点），
     * 它所在的层级就是这棵树的最小深度。
     *
     * @param root 树的根节点
     * @return 树的最小深度
     */
    public int minDepth(TreeNode root) {

        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);

        int depth = 0;

        while (!queue.isEmpty()) {
            // 获取当前层的节点数量
            int size = queue.size();

            // 每次进入 while 循环，都意味着我们开始遍历新的一层，所以深度加 1
            depth++;

            // 遍历当前层的所有节点
            for (int i = 0; i < size; i++) {
                // 从队列中取出一个节点
                TreeNode node = queue.poll();

                // --- 核心判断 ---
                // 检查当前节点是否是叶子节点 (左右子节点都为 null)
                if (node.left == null && node.right == null) {
                    // 因为是层序遍历，第一个遇到的叶子节点一定在最浅的层。
                    // 因此，直接返回当前深度即可。
                    return depth;
                }

                // 如果当前节点不是叶子节点，则将其非空的子节点加入队列，
                // 以便在下一轮循环中处理。
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return depth;
    }
}
