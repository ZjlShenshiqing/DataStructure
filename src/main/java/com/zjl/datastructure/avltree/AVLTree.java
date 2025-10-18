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

    /**
     * 右旋
     *
     * @param node 失衡节点
     * @return 新的根节点
     */
    private AVLNode rightRotate(AVLNode node) {
        // 第一步就是拿到TA的左孩子,这个肯定是有的，不用考虑没有的情况
        AVLNode leftChild = node.left;

        // 左子树的右孩子，现在要给TA换个爹，这个为null也没关系
        AVLNode leftChildRight = leftChild.right;

        // 左孩子去失衡节点，然后失衡节点变成右孩子（上位）
        leftChild.right = node;

        // 换爹，右孩子变成之前失衡节点的左孩子
        node.left = leftChildRight;

        // (中间步骤) 更新可能变化了的高度
        updateHeight(node);
        updateHeight(leftChild); // 注意：leftChild 节点在左旋后可能变成了新子树的子节点，它的高度也可能变化
        // 其他节点的高度不会变化

        return leftChild;
    }

    /**
     * 左旋
     *
     * @param node 失衡节点
     * @return 新的根节点
     */
    private AVLNode leftRotate(AVLNode node) {
        // 第一步就是拿到TA的右孩子,这个肯定是有的，不用考虑没有的情况
        AVLNode rightChild = node.right;

        // 右子树的左孩子，现在要给TA换个爹，这个为null也没关系
        AVLNode rightChildLeft = rightChild.left;

        // 右孩子去失衡节点，然后失衡节点变成左孩子（上位）
        rightChild.left = node;

        // 换爹，右子树的右孩子变成之前失衡节点的右孩子
        node.right = rightChildLeft;


        updateHeight(node); // 先更新较低的
        updateHeight(rightChild); // 再更新较高的

        return rightChild;
    }

    /**
     * 先左旋左子树，再右旋根节点 - 解决LR失衡情况
     *
     * @param node 失衡节点
     * @return 新的根节点
     */
    private AVLNode leftRightRotate(AVLNode node) {
        // 先拿到失衡节点的左子树
        AVLNode leftChild = node.left;
        // 直接左旋，并更新失衡节点的左子树，这样变成了LL的情况
        node.left = leftRotate(leftChild);

        // 右旋根节点
        return rightRotate(node);
    }

    /**
     * 先右旋右子树，再左旋根节点 - 解决RL失衡情况
     *
     * @param node 失衡节点
     * @return 新的根节点
     */
    private AVLNode rightLeftRotate(AVLNode node) {
        // 先拿到失衡节点的右子树
        AVLNode rightChild = node.right;

        // 直接右旋，并更新失衡节点的右子树，这样变成了RR的情况
        node.right = rightRotate(rightChild);

        // 左旋根节点
        return leftRotate(node);
    }

    /**
     * 检查节点是否失衡，重新平衡代码
     * @param node 失衡
     * @return     新的根节点
     */
    private AVLNode balance(AVLNode node) {
        if (node == null) {
            return null;
        }

        int balanceFactor = balanceFactor(node);

        // 不平衡的情况
        // LL情况 注意需要考虑等于时的条件，也就是子树的两个左右孩子高度是一样的
        if (balanceFactor > 1 && balanceFactor(node.left) >= 0) {
            // 做一次右旋
            return rightRotate(node);
        }
        // LR情况
        else if (balanceFactor > 1 && balanceFactor(node.left) < 0) {
            // 左右旋
            return leftRightRotate(node);
        }
        // RL情况
        else if (balanceFactor < -1 && balanceFactor(node.right) > 0) {
            // 右左旋
            return rightLeftRotate(node);
        }
        // RR情况
        else if (balanceFactor < -1 && balanceFactor(node.right) <= 0) {
            // 做一次左旋
            return leftRotate(node);
        }

        // 平衡的情况
        return node;
    }
}
