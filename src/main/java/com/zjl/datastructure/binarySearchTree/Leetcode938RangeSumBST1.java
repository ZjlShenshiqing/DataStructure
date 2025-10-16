package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

import java.util.LinkedList;

/**
 * @author zhangjlk
 * @date 2025/10/16 下午10:42
 * @description Leetcode938RangeSumBST1
 */
public class Leetcode938RangeSumBST1 {

    /**
     * 计算二叉搜索树（BST）中，值在 [low, high] 范围内的所有节点的总和。
     * @param root 树的根节点
     * @param low 范围的下限（包含）
     * @param high 范围的上限（包含）
     * @return 范围内节点的总和
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
        TreeNode node = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        int sum = 0;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode pop = stack.pop();
                if (pop.val > high) {
                    break; // 到上限了直接退出，少走步
                }
                // 处理值
                if (pop.val >= low) {
                    sum += pop.val;
                }
                node = pop.right; // 处理右子树
            }
        }

        return sum;
    }
}
