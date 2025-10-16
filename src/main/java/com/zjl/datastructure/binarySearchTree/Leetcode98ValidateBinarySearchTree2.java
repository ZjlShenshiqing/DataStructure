package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

import java.util.LinkedList;

/**
 * 使用递归实现验证是否为合法的二叉树
 *
 * @author zhangjlk
 * @date 2025/10/16 下午3:53
 * @description Leetcode98ValidateBinarySearchTree
 */
public class Leetcode98ValidateBinarySearchTree2 {

    long parent = Long.MIN_VALUE;

    // 使用中序遍历递归实现
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        TreeNode node = root;
        // 处理左子树, 这里有个效率问题，这里并没有检查左子树返回来的结果
        boolean leftBST = isValidBST(node.left);
        if (!leftBST) {
            return false;
        }

        // 前序值比现在的要大，那直接返回错误
        if (parent >= node.val) {
            return false;
        }
        parent = node.val;
        // 处理右子树
        boolean rightBST = isValidBST(node.right);
        return leftBST && rightBST;
    }
}
