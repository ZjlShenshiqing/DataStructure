package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * @author zhangjlk
 * @date 2025/10/17 上午9:50
 * @description Leetcode1008BuildbstFromPreorder
 */
public class Leetcode1008BuildBstFromPreorder2 {

    /**
     * 主函数，根据前序遍历结果构造二叉搜索树。
     *
     * @param preorder 一个整数数组，表示二叉搜索树的前序遍历结果。
     * @return 构建好的二叉搜索树的根节点。
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        // 对于根节点来说，它没有任何值的上限限制
        return insert(preorder, Integer.MAX_VALUE);
    }

    // 定义一个全局索引或成员变量 `i`。
    // 它的作用是在所有的递归调用之间共享对 `preorder` 数组的遍历进度，
    int i = 0;

    /**
     * 核心递归函数，尝试将 preorder[i] 插入到树的当前位置。
     *
     * @param preorder 前序遍历数组。
     * @param max      一个“上限”值，当前递归分支中所有节点的值都不能超过它。
     * 这个约束确保了 BST 的性质得以维持。
     * @return 构建好的子树根节点；如果不满足上限约束，则返回 null。
     */
    private TreeNode insert(int[] preorder, int max) {
        // 基本情况 1：如果索引 `i` 已经越过数组末尾，
        // 说明所有数字都已经被安放到树中了，无法再构建新节点。
        if (i == preorder.length) {
            return null;
        }

        // 取出当前需要处理的数字。
        int val = preorder[i];

        // 核心判断 / 基本情况 2：如果当前值 `val` 超过了 `max` 的限制，
        // 这意味着 `val` 不能被放置在当前这个子树中（无论是作为左孩子还是右孩子）。
        // 它应该属于更高层级节点的右子树。
        // 因此，我们直接返回 null，并且关键是：索引 `i` 此时不增加！
        // 因为这个 `val` 还没有被成功安放，需要留给上一层的递归调用去处理。
        if (val > max) {
            return null;
        }

        // 如果 `val` 符合上限要求，就为它创建一个新节点。
        TreeNode node = new TreeNode(val);

        // 成功地为 `val` 找到了位置，现在可以将索引 `i` 向后移动一位，
        // 准备处理 `preorder` 数组中的下一个数字。
        i++;

        // 步骤 1: 递归构建左子树。
        // 根据 BST 的性质，当前节点 `node` 的所有左子节点都必须小于 `node.val`。
        // 因此，我们将 `node.val` 作为新的“上限”传入，去构建左子树。
        node.left = insert(preorder, val);

        // 步骤 2: 递归构建右子树。
        // 只有当上面的左子树构建过程完全结束后（即所有小于 `val` 的连续数字都被消耗掉），
        // 程序才会执行到这里。
        // 右子树的节点必须大于当前节点 `val`（这一点由算法流程天然保证），
        // 但同时它也必须遵守从更上层祖先节点继承来的 `max` 限制。
        // 所以我们把原始的 `max` 继续传递下去。
        node.right = insert(preorder, max);

        // 当一个节点的左右子树都构建完成后，将这个节点返回给它的父节点。
        return node;
    }
}
