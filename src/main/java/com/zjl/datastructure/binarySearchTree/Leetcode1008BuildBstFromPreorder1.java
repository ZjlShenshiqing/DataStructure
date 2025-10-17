package com.zjl.datastructure.binarySearchTree;

import com.zjl.datastructure.binaryTree.TreeNode;

/**
 * @author zhangjlk
 * @date 2025/10/17 上午9:50
 * @description Leetcode1008BuildbstFromPreorder
 */
public class Leetcode1008BuildBstFromPreorder1 {

    /**
     * 根据前序遍历结果构造二叉树
     *
     * 题目说明
     * 1 <= preorder.length <= 100   ->  肯定有个根节点
     * 1 <= preorder[i] <= 10^8
     * preorder 中的值 互不相同
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        // 第一个元素一定是根节点（前序遍历的第一个节点就是根）
        TreeNode root = new TreeNode(preorder[0]);

        // 从第二个元素开始（索引为1），依次将每个值插入到 BST 中
        for (int i = 1; i < preorder.length; i++) {
            int val = preorder[i];
            insert(root, val); // 将当前值插入到以 root 为根的 BST 中
        }

        return root; // 返回构建好的 BST 的根节点
    }

    /**
     * 递归地将一个值插入到以 node 为根的二叉搜索树中。
     *
     * @param node 当前子树的根节点（可能为 null）
     * @param val  要插入的值
     * @return 插入完成后当前子树的根节点（用于连接父子节点）
     */
    private TreeNode insert(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }

        // 如果插入值小于当前节点值，应插入到左子树
        if (val < node.val) {
            node.left = insert(node.left, val); // 递归插入，并更新左子节点引用
        }
        // 如果插入值大于当前节点值，应插入到右子树
        else if (val > node.val) {
            node.right = insert(node.right, val); // 递归插入，并更新右子节点引用
        }

        // 返回当前节点，保持树结构的连接
        return node;
    }
}
