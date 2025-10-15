package com.zjl.datastructure.binarySearchTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        BinarySearchTreeNode node = root;
        BinarySearchTreeNode parent = null;
        while (node != null) {
            if (key < node.key) {
                parent = node; // 删除节点之前要找到TA的parent
                node = node.left;
            } else if (key > node.key) {
                parent = node; // 删除节点之前要找到TA的parent
                node = node.right;
            } else {
                break;
            }
        }

        if (node == null) {
            // 节点没有找到，直接返回空
            return null;
        }

        // 进行删除操作
        if (node.left == null) {
            // 情况1：删除节点没有左孩子，将右孩子托孤给 Parent
            shift(parent, node, node.right);
        } else if (node.right == null) {
            // 情况2：删除节点没有右孩子，将左孩子托孤给 Parent
            shift(parent, node, node.left);
        } else { // 情况3：删除节点左右孩子都有
            // 在右子树中查找 node 的“中序后继”（即右子树中的最小节点）
            BinarySearchTreeNode s = node.right;      // 后继节点候选：从右孩子开始
            BinarySearchTreeNode sParent = node;      // s 的父节点，初始为 node
            while (s.left != null) {
                sParent = s; // 更新父亲的值
                s = s.left;
            }
            // 循环结束后，s就是后继节点

            // 如果后继节点 s 不是 node 的直接右孩子（即 sParent != node）
            if (sParent != node) {
                // 那么 s 一定没有左孩子（由 BST 性质决定），但可能有右孩子
                // 将 s 的右孩子“托孤”给 sParent（即用 s.right 替代 s 的位置）
                shift(sParent, s, s.right);

                // 将原 node 的右子树挂到 s 的右边（因为 s 要取代 node）
                s.right = node.right;
            }

            // 注意：如果 s 是 node 的直接右孩子（sParent == node），
            // 那么 s 本来就在正确位置，无需调整其右子树
            // 用后继节点 s 完全取代被删除节点 node 的位置
            shift(parent, node, s);

            // 将原 node 的左子树挂到 s 的左边
            s.left = node.left;
        }

        // 删除的节点值
        return node.value;
    }

    /**
     * 托孤操作：在删除节点后，将被删除节点的子节点“托付”给其父节点，
     * 从而维持二叉搜索树的结构连续性。
     *
     * 该方法的作用是：将 deleted 节点从树中移除，并用 child 节点替代它在父节点中的位置。
     * 这常用于 BST 删除操作中，当被删除节点最多只有一个子节点时（即 child 可能为 null 或一个子树）。
     *
     * @param parent  被删除节点（deleted）的父节点；若 deleted 是根节点，则 parent 为 null
     * @param deleted 要被删除的节点
     * @param child   被顶上去的节点
     */
    private void shift(BinarySearchTreeNode parent, BinarySearchTreeNode deleted, BinarySearchTreeNode child) {
        // 情况1：被删除的节点是整棵树的根节点（即 parent == null）
        if (parent == null) {
            // 直接将 child 作为新的根节点（child 可能为 null，此时树变为空）
            root = child;
        }
        // 情况2：被删除的节点是其父节点的左孩子
        else if (deleted == parent.left) {
            // 将父节点的 left 指针指向 child，完成“托孤”
            parent.left = child;
        }
        // 情况3：被删除的节点是其父节点的右孩子
        else {
            // 将父节点的 right 指针指向 child
            parent.right = child;
        }
    }

    // 对于二叉搜索树来讲，中序遍历，就能得到TA的一个升序的结果


    /**
     * 找到比 key 小的所有节点
     * 思路就是先进行中序遍历把二叉搜索树的节点进行排序
     *
     */
    public List<Object> less(int key) {
        ArrayList<Object> result = new ArrayList<>();
        BinarySearchTreeNode node = root;

        // 需要记录每一个经过的节点
        LinkedList<BinarySearchTreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left; // 向左走
            } else {
                BinarySearchTreeNode pop = stack.pop();// 弹栈
                // 值小于key，记录下来
                if (pop.key < key) {
                    result.add(pop.value);
                } else {
                    break; // 直接出去，因为这个是顺序的，如果比key大了，后面都比key大了
                }
                // 处理右边的节点
                node = pop.right;
            }
        }
        return result;
    }

    /**
     * 找到比 key 大的所有节点
     */
    public List<Object> greater(int key) {
        ArrayList<Object> result = new ArrayList<>();
        BinarySearchTreeNode node = root;

        // 需要记录每一个经过的节点
        LinkedList<BinarySearchTreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left; // 向左走
            } else {
                BinarySearchTreeNode pop = stack.pop();// 弹栈
                // 处理值
                if (pop.key > key) {
                    result.add(pop.value);
                } // 要一直遍历到最后
                // 处理右边的节点
                node = pop.right;
            }
        }
        return result;
    }

    /**
     * 找到大于k1，但是小于k2的所有值
     */
    public List<Object> between(int key1, int key2) {
        ArrayList<Object> result = new ArrayList<>();
        BinarySearchTreeNode node = root;

        // 需要记录每一个经过的节点
        LinkedList<BinarySearchTreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left; // 向左走
            } else {
                BinarySearchTreeNode pop = stack.pop();// 弹栈
                // 处理值
                if (pop.key >= key1 && pop.key <= key2) {
                    result.add(pop.value);
                } else if (pop.key > key2) {
                    // 退出
                    break;
                }
                // 处理右边的节点
                node = pop.right;
            }
        }
        return result;
    }
}
