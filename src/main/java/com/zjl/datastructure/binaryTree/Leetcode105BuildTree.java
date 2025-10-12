package com.zjl.datastructure.binaryTree;

import java.util.Arrays;

/**
 * 根据前序中序的遍历结果构建二叉树
 *
 * @author zhangjlk
 * @date 2025/10/12 下午12:52
 * @description Leetcode105BuildTree
 */
public class Leetcode105BuildTree {

    /**
     * 根据前序遍历和中序遍历的结果来构建一棵二叉树。
     *
     * 核心原理:
     * 1. 前序遍历的第一个元素一定是当前树（或子树）的根节点。
     * 2. 在中序遍历中找到这个根节点，它左边的所有元素都属于左子树，右边的所有元素都属于右子树。
     * 3. 根据左子树的节点数量，可以确定前序遍历中哪些部分对应左子树，哪些对应右子树。
     * 4. 递归地对左右子树重复以上步骤。
     *
     * @param preorder 前序遍历的结果数组
     * @param inorder  中序遍历的结果数组
     * @return 构建好的树的根节点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {

        // 递归的终止条件：如果传入的数组是空的，表示这是一个空子树，返回 null。
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        // 根节点
        int rootValue = preorder[0];

        // 创建树
        TreeNode root = new TreeNode(rootValue);

        // 如果数组只剩下一个元素，表示它是一个叶子节点，直接返回。
        if (preorder.length == 1) {
            return root;
        }

        // 根据中序遍历，区分左右子树
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootValue) {

                /**
                 * 拆分中序遍历结果 (inorder)
                 * 根据根节点索引 i，将 inorder 数组分割成两部分。
                 */
                // 0 ~ i-1 就是左子树
                // i+1 ~ inOrder.length - 1 就是右子树
                int[] inLeft = Arrays.copyOfRange(inorder, 0, i);          // 左子树
                int[] inRight = Arrays.copyOfRange(inorder, i + 1, inorder.length); // 右子树

                /**
                 * 拆分前序遍历结果 (preorder)
                 * 根据左子树的节点数量 (也就是 inLeft.length，即 i)，来分割 preorder 数组。
                 */
                // preorder 中，紧跟在根节点后面的 i 个元素，是左子树的前序遍历。
                // 范围是从索引 1 到 i。所以 copyOfRange 的结束索引是 i+1。
                int[] preLeft = Arrays.copyOfRange(preorder, 1, i + 1);
                int[] preRight = Arrays.copyOfRange(preorder, i + 1, inorder.length);

                root.left = buildTree(preLeft, inLeft); // 左子树
                root.right = buildTree(preRight, inRight); // 右子树
                break;
            }
        }
        return root;
    }
}
