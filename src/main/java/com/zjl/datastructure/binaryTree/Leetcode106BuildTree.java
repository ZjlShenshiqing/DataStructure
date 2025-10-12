package com.zjl.datastructure.binaryTree;

import java.util.Arrays;

/**
 * Leetcode 106 根据中序与后序遍历结果构造二叉树
 *
 * @author zhangjlk
 * @date 2025/10/12 下午4:10
 * @description Leetcode106BuildTree
 */
public class Leetcode106BuildTree {

    public TreeNode buildTree(int[] inorder, int[] postorder) {

        // 递归的终止条件：如果传入的数组是空的，表示这是一个空子树，返回 null。
        if (postorder.length == 0 || inorder.length == 0) {
            return null;
        }

        int rootVal = postorder[postorder.length - 1];

        // 根节点
        TreeNode root = new TreeNode(rootVal);

        // 如果数组只剩下一个元素，表示它是一个叶子节点，直接返回。
        if (postorder.length == 1) {
            return root;
        }

        // 切分左右子树
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                int[] inLeft = Arrays.copyOfRange(inorder, 0, i);
                int[] inRight = Arrays.copyOfRange(inorder, i + 1, inorder.length);

                int[] postLeft = Arrays.copyOfRange(postorder, 0, i);
                int[] postRight = Arrays.copyOfRange(postorder, i, postorder.length - 1);

                root.left = buildTree(inLeft, postLeft);
                root.right = buildTree(inRight, postRight);
                break;
            }
        }
        return root;
    }
}
