package com.zjl.datastructure.binaryTree;

/**
 * 二叉树的最大深度 - 解法1 - 递归
 *
 * @author zhangjlk
 * @date 2025/10/11 下午12:11
 * @description Leetcode104MaxDepth
 */
public class Leetcode104MaxDepth_1 {


    public int maxDepth(TreeNode root) {
        // 终止条件: 空节点
        // 如果节点是 null，说明这里没有节点，它对深度没有贡献，深度为 0。
        if (root == null) {
            return 0;
        }


        // 递归分解:
        // 这里完全对应了“后序遍历”的思想。
        // 在对当前节点做任何计算之前，我们先去把左、右子树的结果算出来。

        // 1. 先去计算左子树的最大深度
        int d1 = maxDepth(root.left);

        // 2. 再去计算右子树的最大深度
        int d2 = maxDepth(root.right);

        // 3. 最后处理当前节点：合并结果
        // 这就是我们讨论的核心公式：“取左右子树深度的最大值，然后加上自己这一层（+1）”
        return Math.max(d1, d2) + 1;
    }
}
