package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * 使用递归实现验证是否为合法的二叉树
 *
 * @author zhangjlk
 * @date 2025/10/16 下午3:53
 * @description Leetcode98ValidateBinarySearchTree
 */
public class Leetcode98ValidateBinarySearchTree3 {

    // 使用中序遍历递归实现
    public boolean isValidBST(TreeNode root) {
        return doValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean doValid(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        // 超过这个界限，不能相等是因为二叉搜索树里面不能有相等的元素
        if (node.val <= min || node.val >= max) {
            return false;
        }

        // 对于左孩子进行递归校验
        boolean leftValid = doValid(node.left, min, node.val);

        // 对于右孩子进行递归校验
        boolean rightValid = doValid(node.right, node.val, max);

        return leftValid && rightValid;
    }
}
