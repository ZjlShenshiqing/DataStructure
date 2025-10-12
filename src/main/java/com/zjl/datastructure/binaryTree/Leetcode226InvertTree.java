package com.zjl.datastructure.binaryTree;

/**
 * 翻转二叉树
 *
 * @author zhangjlk
 * @date 2025/10/12 下午12:01
 * @description Leetcode226InvertTree
 */
public class Leetcode226InvertTree {

    public TreeNode invertTree(TreeNode root) {
        swapChildren(root);
        return root;
    }

    /**
     * 将节点的左右孩子进行交换
     */
    private static void swapChildren(TreeNode root) {
        if (root == null) return;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        swapChildren(root.left);
        swapChildren(root.right);
    }
}
