package com.zjl.datastructure.binarySearchTree;

/**
 * 二叉搜索树实现方式1
 *
 * @author zhangjlk
 * @date 2025/10/13 上午10:10
 * @description BinarySearchTree1
 */
public class BinarySearchTree1 {

    BinarySearchTreeNode root; // 根节点

    static class BinarySearchTreeNode {
        /**
         * 键，注意这个是唯一值
         */
        int key;

        /**
         * 值
         */
        Object value;

        /**
         * 左右孩子
         */
        BinarySearchTreeNode left;
        BinarySearchTreeNode right;

        public BinarySearchTreeNode (int key) {
            this.key = key;
        }

        public BinarySearchTreeNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public BinarySearchTreeNode(int key, Object value, BinarySearchTreeNode left, BinarySearchTreeNode right) {
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
    public Object get(int key) {
        BinarySearchTreeNode node = root;
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (node.key < key) {
                node = node.right;
            } else {
                return node.value;
            }
        }
        return null;
    }


    /**
     * 查找最小的 key 的对应值
     *
     * @return 查找最小的 key 的对应值
     */
    public Object min() {
        return min(root);
    }

    /**
     * 在以指定节点为根的二叉搜索树（BST）中，查找具有最小 key 的节点，
     * 并返回该节点所关联的值（value）。
     *
     * @param node 二叉搜索树的根节点（可以是整棵树的根，也可以是某个子树的根）
     * @return 最小 key 对应的 value；如果树为空（node 为 null），则返回 null
     */
    public Object min(BinarySearchTreeNode node) {
        if (node == null) {
            return null;
        }
        BinarySearchTreeNode p = node;
        while (p.left != null) {
            p = p.left;
        }
        return p.value;
    }

    /**
     * 查找最大的 key 的对应值
     *
     * @return 查找最大的 key 的对应值
     */
    public Object max() {
        return max(root);
    }

    /**
     * 在以指定节点为根的二叉搜索树（BST）中，查找具有最大 key 的节点，
     * 并返回该节点所关联的值（value）。
     *
     * @param node 二叉搜索树的根节点（可以是整棵树的根，也可以是某个子树的根）
     * @return 最大 key 对应的 value；如果树为空（node 为 null），则返回 null
     */
    public Object max(BinarySearchTreeNode node) {
        if (node == null) {
            return null;
        }
        BinarySearchTreeNode p = node;
        while (p.right != null) {
            p = p.right;
        }
        return p.value;
    }

    /**
     * 存储关键字和对应值
     *
     * @param key   关键字
     * @param value 对应的值
     */
    public void put(int key, Object value) {
        BinarySearchTreeNode node = root;
        BinarySearchTreeNode parent = null;
        while (node != null) {
            parent = node; // parent就是更新前的值，就比如最后走出去就是null了，parent就是上一个节点
            if (key < node.key) {
                node = node.left;
            } else if (node.key < key) {
                node = node.right;
            } else {
                // key 有，更新值
                node.value = value;
                return;
            }
        }
        if (parent == null) { // 树整个为空
            root = new BinarySearchTreeNode(key, value);
        }
        if (key < parent.key) {
            // key 没有，新增，是parent的左孩子
            parent.left = new BinarySearchTreeNode(key, value);
        } else if (key > parent.key) {
            parent.right = new BinarySearchTreeNode(key, value);
        }
    }

    /**
     * 查找关键字的前任值
     * 通过这个 key 查找上一个 key
     *
     * @param key 关键字
     * @return    这个key的上一个key
     */
    public Object successor(int key) {
        BinarySearchTreeNode node = root;

        // 自左而来的祖先
        BinarySearchTreeNode ancestorFromLeft = null;

        // 没有找到节点的情况
        if (node == null) {
            return null;
        }

        // 通过key找到节点的代码
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                // 向右走，代表祖先是自左而来的
                ancestorFromLeft = node;
                node = node.right;
            } else {
                break;
            }
        }

        /**
         * 情况1：如果节点有左子树，此时前任就是左子树的最大值
         * 情况2：如果节点没有左子树，此时离他最近的，从左来的就是他的前任
         */
        // 情况1
        if (node.left != null) {
            return max(node.left);
        }

        // 情况2: 找从左而来的祖先
        return ancestorFromLeft != null ? ancestorFromLeft.value : null;
    }

    /**
     * 通过这个 key 查找下一个 key
     *
     * @param key 关键字
     * @return    这个key的下一个key
     */
    public Object predecessor(int key) {
        BinarySearchTreeNode node = root;
        BinarySearchTreeNode ancestorFromRight = null;

        // 没有找到节点的情况
        if (node == null) {
            return null;
        }

        // 通过key找到节点的代码
        while (node != null) {
            if (key < node.key) {
                // 向左走，代表祖先是自右而来的
                ancestorFromRight = node;
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                break;
            }
        }

        /**
         * 情况1：如果节点有右子树，此时前任就是右子树的最大值
         * 情况2：如果节点没有右子树，此时离他最近的，从右来的就是他的前任
         */
        // 情况1
        if (node.right != null) {
            return min(node.right);
        }

        // 情况2: 找从左而来的祖先
        return ancestorFromRight != null ? ancestorFromRight.value : null;
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
