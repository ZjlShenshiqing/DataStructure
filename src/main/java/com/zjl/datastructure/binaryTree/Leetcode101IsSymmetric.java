package com.zjl.datastructure.binaryTree;

/**
 * 对称二叉树
 *
 * @author zhangjlk
 * @date 2025/10/11 上午11:38
 * @description Leetcode101IsSymmetric
 */
public class Leetcode101IsSymmetric {

    public boolean isSymmetric(TreeNode root) {
        // 从根节点的左子树和右子树开始，递归地进行比较。
        return check(root.left, root.right);
    }

    /**
     * 辅助函数（Helper Function），用于递归地比较两个子树是否是镜像对称的。
     *
     * @param left  左子树的节点。
     * @param right 右子树的节点。
     * @return 如果以 left 和 right 为根的两个子树是镜像对称的，则返回 true；否则返回 false。
     */
    private boolean check(TreeNode left, TreeNode right) {
        // 递归的终止条件 1：
        // 如果左子节点和右子节点都为 null，说明这条路径的比较已经到底，并且是对应的，因此是对称的。
        if (left == null && right == null) {
            return true;
        }

        // 递归的终止条件 2：
        // 如果一个节点为 null，而另一个不为 null，那么它们的结构就不同，肯定不是对称的。
        // 注意：执行到这里时，它们不可能同时为 null（上一个 if 已经处理了）。
        if (left == null || right == null) {
            return false;
        }

        // 递归的终止条件 3：
        // 如果两个节点的值不相等，那么它们也不是对称的。
        if (left.val != right.val) {
            return false;
        }

        // 递归调用：
        // 如果要满足对称性，必须同时满足以下两个条件：
        // 1. 左子树的左孩子（left.left）必须和右子树的右孩子（right.right）镜像对称。
        // 2. 左子树的右孩子（left.right）必须和右子树的左孩子（right.left）镜像对称。
        // 使用 "&&" 连接，表示两个条件必须同时成立。
        return check(left.left, right.right) && check(left.right, right.left);
    }
}
