package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

import java.util.LinkedList;

/**
 * @author zhangjlk
 * @date 2025/10/16 下午3:53
 * @description Leetcode98ValidateBinarySearchTree
 */
public class Leetcode98ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        TreeNode node = root;
        long parent = Long.MIN_VALUE;

        // 使用一个栈来储存结果
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 进行中序遍历
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode pop = stack.pop();
                // 非法情况
                if (parent >= pop.val) {
                    // 如果前一个节点的值 大于或等于 当前节点的值，
                    // 就违反了 BST 的「严格递增」特性，所以返回 false
                    return false;
                } else {
                    // 更新 parent，让它指向刚刚处理完的节点
                    parent = pop.val;
                }
                node = pop.right;
            }
        }
        return true;
    }
}
