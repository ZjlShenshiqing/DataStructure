package com.zjl.datastructure.binaryTree;

import java.util.LinkedList;

/**
 * 根据后缀表达式构造表达式树
 *
 * @author zhangjlk
 * @date 2025/10/12 下午12:19
 * @description ExpressionTree
 */
public class ExpressionTree {

    /**
     * 根据给定的后缀表达式（逆波兰表示法）字符串数组，构建一个表达式树。
     *
     * 算法思路:
     * 1. 使用一个栈来辅助构建过程。
     * 2. 从左到右遍历表达式数组中的每一个元素。
     * 3. 如果当前元素是数字（操作数），则为它创建一个新的树节点并将其压入栈中。
     * 4. 如果当前元素是运算符，则从栈中弹出两个节点。第一个弹出的是右孩子，第二个弹出的是左孩子。
     * 然后，为运算符创建一个新的父节点，将弹出的两个节点作为其左右孩子，再将这个新构建的子树（父节点）压回栈中。
     * 5. 遍历结束后，栈中剩下的唯一一个节点就是整个表达式树的根节点。
     *
     * @param expression 一个字符串数组，代表一个后缀表达式。例如: ["3", "4", "+", "2", "*"]
     * @return 构建好的表达式树的根节点。
     */
    public TreeNode constructExpressionTree(String[] expression) {

        // 栈
        LinkedList<TreeNode> stack = new LinkedList<>();

        for (String s : expression) {
            switch (s) {
                // 当元素是运算符时
                case "+", "-", "*", "/" -> { // 运算符
                    TreeNode right = stack.pop(); // 右孩子
                    TreeNode left = stack.pop(); // 左孩子
                    TreeNode parent = new TreeNode(s);
                    parent.left = left;
                    parent.right = right;
                    stack.push(parent);
                }
                // 当元素是数字时
                default -> { // 数字
                    stack.push(new TreeNode(s)); // 数字直接压栈
                }
            }
        }
        return stack.peek();
    }

    /**
     * 树节点类
     */
    public class TreeNode {

        public String val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(String val) {
            this.val = val;
        }

        public TreeNode(TreeNode left, String val, TreeNode right) {
            this.left = left;
            this.val = val;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.valueOf(this.val);
        }
    }
}
