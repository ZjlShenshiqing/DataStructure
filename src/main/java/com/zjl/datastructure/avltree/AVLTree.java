package com.zjl.datastructure.avltree;

/**
 * @author zhangjlk
 * @date 2025/10/17 下午12:30
 * @description AVLTree
 */
public class AVLTree {

    static class AVLNode {
        /**
         * key,用来比较大小
         */
        int key;

        /**
         * 值
         */
        Object value;

        /**
         * 左孩子
         */
        AVLNode left;

        /**
         * 右孩子
         */
        AVLNode right;

        /**
         * 节点的高度，初始为1
         */
        int height = 1;

        public AVLNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(int key) {
            this.key = key;
        }

        public AVLNode(int key, Object value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 得到节点的高度,避免出现null的问题
     * @param node 节点
     * @return     节点的高度
     */
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    /**
     * 更新节点高度，适用于新增，删除，旋转的情况
     *
     * 取左右子树的最大值然后加1，就是当前节点的高度
     * @param node 节点
     */
    private void updateHeight(AVLNode node) {
        node.height = Integer.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 平衡因子(balance factor) 用于判断现在二叉树是平衡的还是失衡的
     * 等于左子树高度 - 右子树高度
     *
     * @return 返回 0 ，1， -1 都表示现在二叉树的节点是平衡的
     *         返回 > 1 或者 < -1 那么说明二叉树现在是不平衡的
     *         > 1表示左边太高
     *         < 1表示右边太高
     */
    private int balanceFactor(AVLNode node) {
        return height(node.left) - height(node.right);
    }
}
