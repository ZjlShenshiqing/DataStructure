package com.zjl.datastructure.binaryTree;

import java.util.LinkedList;

/**
 * 树遍历
 */
public class TreeTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(4), 2, null),
                1,
                new TreeNode(new TreeNode(5), 3, new TreeNode(6))
        );

        LinkedList<TreeNode> stack = new LinkedList<>(); // 创建一个栈
        TreeNode curr = root; // 代表当前节点
        TreeNode pop = null; // 最近一次弹栈的元素

        while (curr != null || !stack.isEmpty()) { // 栈不为空，不能退出循环
            if (curr != null) { // 当前节点不为空
                stack.push(curr); // 压入栈，记住回来的路

                // 前序遍历的打印时机：处理左子树之前打印
                System.out.println("前" + curr.val);

                // 往左遍历,处理左子树
                curr = curr.left;
            } else {
                TreeNode peek = stack.peek(); // 拿出栈顶节点
                if (peek.right == null) { // 右子树是null

                    // 中序遍历打印时机：处理右子树之前打印
                    System.out.println("中" + peek.val);

                    // 弹栈的元素记录一下
                    pop = stack.pop();

                    // 后序遍历的打印时机：没有右子树的情况打印一下
                    System.out.println("后" + pop.val);
                } else if (peek.right == pop) { // 右子树已经处理完成
                    // 弹栈的元素记录一下
                    pop = stack.pop();

                    // 后序遍历的打印时机：处理完右子树之后打印
                    System.out.println("后" + pop.val);
                } else {

                    // 中序遍历打印时机：处理右子树之前打印
                    System.out.println("中" + peek.val);

                    // 往右遍历,处理右子树
                    pop = peek.right;
                }
            }
        }
    }

    /**
     * 前序遍历
     *
     * 规则：
     * 先访问节点的值
     * 然后左子树遍历到叶子节点
     * 然后右子树也遍历到叶子节点
     *
     * @param node 节点
     */
    static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val + "\t"); // 先节点的值
        preOrder(node.left); // 遍历左子树到叶子节点
        preOrder(node.right); // 遍历右子树到叶子节点
    }

    /**
     * 中序遍历
     *
     * @param node 节点
     */
    static void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left); // 遍历左子树到叶子节点
        System.out.println(node.val + "\t");
        inOrder(node.right); // 遍历右子树到叶子节点
    }

    /**
     * 后序遍历
     *
     * @param node 节点
     */
    static void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.val + "\t");
    }
}
