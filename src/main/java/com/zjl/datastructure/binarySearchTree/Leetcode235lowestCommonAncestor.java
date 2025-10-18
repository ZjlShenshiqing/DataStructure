package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * @author zhangjlk
 * @date 2025/10/17 下午12:02
 * @description Leetcode235lowestCommonAncestor
 */
public class Leetcode235lowestCommonAncestor {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        // pq同时在左右子树的情况（同侧）
        while (p.val < node.val  && q.val < node.val || p.val > node.val && q.val > node.val) {

            // 左子树
            if (p.val < node.val) {
                node = node.left;
            } else {
                node = node.right;
            }

            // 现在的情况是，要不然node等于pq其中之一，要不然node是pq最近的爹
        }
        return node;
    }
}
