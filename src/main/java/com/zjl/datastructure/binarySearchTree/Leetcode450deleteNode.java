package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

import java.util.ArrayList;

/**
 * @author zhangjlk
 * @date 2025/10/15 下午8:14
 * @description Leetcode450deleteNode
 */
public class Leetcode450deleteNode {

    /**
     * 使用递归实现删除二叉搜索树的节点
     * @param root 树的根
     * @param key  树的值
     * @return 删除后的树的root
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode node = root;

        if (node == null) return null;

        if (key < node.val) {
            node.left = deleteNode(node.left, key);
            return node;
        } else if (key > node.val) {
            node.right = deleteNode(node.right, key);
            return node;
        }

        // 找到了删除节点

        // 被删除节点只有左&右子树
        if (node.left == null) {
            return node.right;
        }

        if (node.right == null) {
            return node.left;
        }

        // 如果两个子树都有值 (相邻)
        TreeNode nextNode = node.right;
        while (nextNode.left != null) {
            nextNode = nextNode.left;
        }
        // nextNode此时就是后继节点
        // 那我们还得处理一下nextNode的后事
        nextNode.right = deleteNode(node.right, nextNode.val);
        nextNode.left = node.left;

        return nextNode;
    }
}
