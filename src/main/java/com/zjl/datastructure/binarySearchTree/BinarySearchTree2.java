package com.zjl.datastructure.binarySearchTree;

/**
 * 二叉搜索树实现方式1
 *
 * @author zhangjlk
 * @date 2025/10/13 上午10:10
 * @description BinarySearchTree1
 */
public class BinarySearchTree2<T extends Comparable<T>> {

    BinarySearchTreeNode<T> root; // 根节点

    static class BinarySearchTreeNode<T> {
        /**
         * 键，注意这个是唯一值
         */
        T key;

        /**
         * 值
         */
        Object value;

        /**
         * 左右孩子
         */
        BinarySearchTreeNode<T> left;
        BinarySearchTreeNode<T> right;

        public BinarySearchTreeNode (T key) {
            this.key = key;
        }

        public BinarySearchTreeNode(Object value, T key) {
            this.value = value;
            this.key = key;
        }

        public BinarySearchTreeNode(T key, Object value, BinarySearchTreeNode<T> left, BinarySearchTreeNode<T> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

//    /**
//     * 查找关键字对应的值
//     *
//     * @param key 关键字
//     * @return    关键字对应的值
//     */
//    public Object get(int key) {
//        return doGet(root, key);
//    }
//
//    /**
//     * 每个节点的查找逻辑都是一样的，这个时候就可以用递归来求解
//     *
//     * @param node 待比较的节点
//     * @param key  关键字
//     */
//    private Object doGet(BinarySearchTreeNode node, int key) {
//        if (node == null) {
//            return null;
//        }
//
//        if (key < node.key) {
//            // key比目前的节点值小，向左找
//            return doGet(node.left, key);
//        } else if (key > node.key) {
//            // key比目前的节点值要大，那么就向右找
//            return doGet(node.right, key);
//        } else {
//            // 相等就返回目前的节点值
//            return node.value;
//        }
//    }

    /**
     * 查找关键字对应的值
     *
     * @param key 关键字
     * @return    关键字对应的值
     */
    public Object get(T key) {
        BinarySearchTreeNode<T> node = root;
        while (node != null) {
            /**
             * 如果返回的是-1，表示key < node.key
             * 返回的是0，表示 key = node.key
             * 返回的是1，表示 key > node.key
             */
            int result = key.compareTo(node.key);
            // 向左找
            if (result < 0) {
                node = node.left;
            } else if (result > 0) {
                // 向右边去找
                node = node.right;
            } else {
                // 已经找到了
                return node.value;
            }
        }
        // 循环结束还没有查找到，就返回null
        return null;
    }



    /**
     * 查找最小的 key 的对应值
     *
     * @return 查找最小的 key 的对应值
     */
    public Object min() {
        return null;
    }

    /**
     * 查找最大的 key 的对应值
     *
     * @return 查找最大的 key 的对应值
     */
    public Object max() {
        return null;
    }

    /**
     * 存储关键字和对应值
     *
     * @param key   关键字
     * @param value 对应的值
     */
    public void put(int key, Object value) {

    }

    /**
     * 通过这个 key 查找上一个 key
     *
     * @param key 关键字
     * @return    这个key的上一个key
     */
    public Object successor(int key) {
        return null;
    }

    /**
     * 通过这个 key 查找下一个 key
     *
     * @param key 关键字
     * @return    这个key的下一个key
     */
    public Object predecessor(int key) {
        return null;
    }

    /**
     * 根据关键字删除
     * 就是把整个节点都删除了，下一个节点挂到key的上一个节点上去
     *
     * @param key 关键字
     * @return    被删除关键字对应的值
     */
    public Object delete(int key) {
        return null;
    }
}
