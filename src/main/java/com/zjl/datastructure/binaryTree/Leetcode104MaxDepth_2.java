package com.zjl.datastructure.binaryTree;

import com.sun.source.tree.Tree;
import com.zjl.datastructure.queue.LinkedListQueue;

import java.util.LinkedList;

/**
 * 后序遍历求解-非递归
 *
 * @author zhangjlk
 * @date 2025/10/11 下午12:39
 * @description Leetcode104MaxDepth_2
 */
public class Leetcode104MaxDepth_2 {

    public int maxDepth(TreeNode root) {
        TreeNode curr = root;

        // 最近一次弹栈的元素
        TreeNode pop = null;

        // 使用栈来记录元素值
        LinkedList<TreeNode> stack = new LinkedList<>();

        // 栈的最大高度
        int max = 0;

        while (curr != null || !stack.isEmpty()) {
            // 向左子树进行遍历
            if (curr != null) {
                stack.push(curr);
                int size = stack.size();
                if (size > max) { // 核心地方
                    max = size;
                }
                curr = curr.left;
            }
            // 左边走到头了
            else {
                TreeNode peek = stack.peek();
                // 如果右子树已经处理完成，就说明已经完成后续遍历，可以释放栈顶元素了
                if (peek.right == null || peek.right == pop) {
                    pop = stack.pop();
                } else {
                    curr = peek.right;
                }
            }
        }
        return max;
    }
}
