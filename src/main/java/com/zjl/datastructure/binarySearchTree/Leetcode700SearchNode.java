package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * 查询节点
 *
 * @author zhangjlk
 * @date 2025/10/16 下午3:47
 * @description Leetcode700SearchNode
 */
public class Leetcode700SearchNode {

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        } else if (val > root.val) {
            return searchBST(root.right, val);
        } else {
            return root;
        }
    }
}
