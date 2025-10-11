package com.zjl.datastructure.queue;

/**
 * 简单的二叉树
 * @author zhangjlk
 * @date 2025/9/25 下午3:54
 * @description TreeNode
 */
public class TreeNode {

    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode(TreeNode left, int value, TreeNode right) {
        this.left = left;
        this.value = value;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
