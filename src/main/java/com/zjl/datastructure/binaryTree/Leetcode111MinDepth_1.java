package com.zjl.datastructure.binaryTree;

/**
 * 二叉树最小深度计算 - 后续遍历求解
 *
 * @author zhangjlk
 * @date 2025/10/12 上午11:04
 * @description Leetcode111MinDepth_1
 */
public class Leetcode111MinDepth_1 {

    /**
     * 计算二叉树的最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * 注意：这个题目和“最大深度”不同，关键在于处理只有一个子节点的节点。
     * 例如，对于节点 A，如果它只有右子树，那么它的最小深度必须是 1 + 右子树的最小深度，
     * 而不能认为左边为 null 路径长度就是 1。
     *
     * @param root 树的根节点
     * @return 树的最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 递归地计算左子树的最小深度
        int leftDepth = minDepth(root.left);
        // 递归地计算右子树的最小深度
        int rightDepth = minDepth(root.right);

        // 情况 1: 左子树为空
        // 根据定义，我们必须沿着右子树走下去才能找到叶子节点。
        // 所以，当前节点的最小深度就是 右子树的最小深度 + 1 (当前节点本身)。
        if (leftDepth == 0) {
            return rightDepth + 1;
        }

        // 情况 2: 右子树为空
        // 当前节点的最小深度就是 左子树的最小深度 + 1。
        if (rightDepth == 0) {
            return leftDepth + 1;
        }

        // 情况 3: 左右子树都不为空
        // 该节点的最小深度等于其左右子树最小深度中的较小者，再加 1。
        return Integer.min(leftDepth, rightDepth) + 1;
    }
}
