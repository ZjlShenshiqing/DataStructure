package com.zjl.datastructure.binaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 后序遍历求解-使用层序遍历求解
 *
 * @author zhangjlk
 * @date 2025/10/11 下午12:39
 * @description Leetcode104MaxDepth_2
 */
public class Leetcode104MaxDepth_3 {

    /**
     * 计算二叉树的最大深度。
     *
     * 思路:
     * 1. 使用一个队列来进行层序遍历 (BFS)。层序遍历的特点是一层一层地访问节点。
     * 2. 我们每完整地遍历完一层，就让深度计数器加一。
     * 3. 当所有层都遍历完毕（即队列为空）时，深度计数器的值就是树的最大深度。
     *
     * @param root 树的根节点
     * @return 树的最大深度
     */
    public int maxDepth(TreeNode root) {

        // 如果根节点本身就是 null，那么树不存在，深度为 0。
        if (root == null) {
            return 0;
        }

        // 存放节点的队列
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);

       //  int layer = 1; // 记录每一层有几个元素，可以用queue.size()替代

        // 初始化深度为 0
        int depth = 0;

        // 队列不为空，就一直进行遍历
        while (!queue.isEmpty()) {

            // 关键步骤：在开始遍历当前层之前，记录下当前队列的大小。
            // 这个 size 就是当前层所包含的节点数量。
            int size = queue.size();

//            int childCount = 0; // 元素下一层孩子的个数
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            System.out.println();
            depth++;
        }
        return depth;
    }
}
