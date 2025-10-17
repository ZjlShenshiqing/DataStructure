package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * @author zhangjlk
 * @date 2025/10/17 上午9:50
 * @description Leetcode1008BuildbstFromPreorder
 */
public class Leetcode1008BuildBstFromPreorder3 {


    public TreeNode bstFromPreorder(int[] preorder) {
        return partition(preorder, 0, preorder.length - 1);
    }



    private TreeNode partition(int[] preorder, int start, int end) {
        // 范围内没有元素了，返回null
        if (start > end) {
            return null;
        }

        // 本次递归根节点的root
        TreeNode root = new TreeNode(preorder[start]);
        // 确定左右子树的一个分界线：第一个比root大的元素
        int index = start + 1;
        while (index <= end) {
            if (preorder[index] > preorder[start]) {
                break;
            }
            index++;
        }
        // 此时退出循环的index就是右子树的起点
        // 左子树
        root.left = partition(preorder, start + 1, index - 1);
        root.right = partition(preorder, index, end);
        return root;
    }
}
