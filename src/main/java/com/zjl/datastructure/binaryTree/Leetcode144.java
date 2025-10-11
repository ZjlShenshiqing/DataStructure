package com.zjl.datastructure.binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangjlk
 * @date 2025/10/10 下午12:28
 * @description LeetCode144
 */
public class Leetcode144 {
    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            LinkedList<TreeNode> stack = new LinkedList<>(); // 创建一个栈
            TreeNode curr = root; // 代表当前节点
            TreeNode pop = null; // 最近一次弹栈的元素

            List<Integer> result = new ArrayList<>();

            while (!stack.isEmpty() || curr != null) { // 栈不为空，不能退出循环
                if (curr != null) { // 当前节点不为空
                    stack.push(curr); // 压入栈，记住回来的路
                    // 往左遍历,处理左子树
                    curr = curr.left;
                } else {
                    TreeNode peek = stack.peek(); // 拿出栈顶节点
                    if (peek.right == null) { // 右子树是null

                        result.add(peek.val);

                        // 弹栈的元素记录一下
                        pop = stack.pop();

                    } else if (peek.right == pop) { // 右子树已经处理完成
                        // 弹栈的元素记录一下
                        pop = stack.pop();

                    } else {
                        result.add(peek.val);
                        // 往右遍历,处理右子树
                        curr = peek.right;
                    }
                }
            }
            return result;
        }
    }
}
