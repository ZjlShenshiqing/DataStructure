package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

import java.util.LinkedList;

/**
 * @author zhangjlk
 * @date 2025/10/16 下午10:42
 * @description Leetcode938RangeSumBST1
 */
public class Leetcode938RangeSumBST2 {

    /**
     * 直接使用递归来实现
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
        TreeNode node = root;

        if (node == null) {
            return 0;
        }

        if (node.val < low) {
            // 左子树已经不用累加了，返回右子树的累加结果就好了
            return rangeSumBST(node.right, low, high);
        }
        if (node.val > high) {
            // 右子树不用考虑，只需要考虑左子树的累加结果
            return rangeSumBST(node.left, low, high);
        }

        // 如果已经在这个范围内了，我们就要进行累加了
        return node.val + rangeSumBST(node.left, low, high) + rangeSumBST(node.right, low, high);
    }
}
