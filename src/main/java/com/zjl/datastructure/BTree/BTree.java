package com.zjl.datastructure.BTree;

import java.util.Arrays;

/**
 * B树实现
 *
 * @author zhangjlk
 * @date 2025/10/24 下午12:29
 * @description BTree
 */
public class BTree {

    /**
     * 最小度数：一个节点最少有几个孩子
     *
     * 一般由我们自己去指定，最小度数越大，树的高度越低
     * 最小度数必须 >= 2，否则B树的性质无法保证
     */
    final int t;
    
    /**
     * 节点最少关键字数量
     * 
     * B树性质：除了根节点外，每个节点至少有 t-1 个关键字
     * 当节点关键字数量 < t-1 时，需要进行合并操作
     */
    final int MIN_KEY_NUMBER;
    
    /**
     * 节点最多关键字数量
     * 
     * B树性质：每个节点最多有 2t-1 个关键字
     * 当节点关键字数量 = 2t-1 时，需要进行分裂操作
     */
    final int MAX_KEY_NUMBER;
    
    /**
     * B树的根节点
     * 
     * 根节点是B树的入口点，所有操作都从根节点开始
     * 根节点可以违反最少关键字数量的限制（可以有1个关键字）
     */
    Node root;

    /**
     * 默认构造函数
     * 
     * 创建最小度数为2的B树
     * 最小度数必须是2，否则B树的基本性质无法保证
     */
    public BTree() {
        this(2); // 最小度数必须是2，如果没有默认的话
    }

    /**
     * 带参数的构造函数
     * 
     * @param t 最小度数，必须 >= 2
     * 
     * 初始化B树的基本参数：
     * - MIN_KEY_NUMBER = t - 1：节点最少关键字数量
     * - MAX_KEY_NUMBER = 2t - 1：节点最多关键字数量
     * - 创建根节点
     */
    public BTree(int t) {
        this.t = t;
        MIN_KEY_NUMBER = t - 1;
        MAX_KEY_NUMBER = 2 * t - 1;
        root = new Node(t); // root初始化
    }

    /**
     * 查找关键字是否存在于B树中
     * 
     * @param key 要查找的关键字
     * @return 如果关键字存在返回true，否则返回false
     * 
     * 算法：从根节点开始，递归查找关键字
     * 时间复杂度：O(log n)，其中n是B树中的关键字总数
     */
    public boolean contains(int key) {
        return root.get(key) != null;
    }

    static class Node {
        /**
         * 关键字
         */
        int[] keys;

        /**
         * 孩子
         */
        Node[] children;

        /**
         * 有效关键字数目
         */
        int keyNumber;

        /**
         * 是否是叶子节点
         */
        boolean isLeaf = true;

        public Node(int t) {
            this.keys = new int[2 * t - 1];  // 可能拥有的最多的节点数
            this.children = new Node[2 * t]; // 可能拥有的最多的孩子数
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * B树多路查找方法
         * 
         * @param key 要查找的关键字
         * @return 如果找到包含该关键字的节点则返回该节点，否则返回null
         */
        Node get(int key) {
            // 在当前节点中顺序查找关键字
            int i = 0;

            // 遍历当前节点的所有关键字
            while (i < keyNumber) {
                // 如果找到目标关键字，直接返回当前节点
                if (keys[i] == key) {
                    return this;
                }
                // 如果当前关键字大于目标关键字，说明目标关键字应该在左子树中
                if (keys[i] > key) {
                    break;
                }
                i++;
            }
            
            // 执行到这里有两种情况：
            // 1. keys[i] > key：目标关键字应该在children[i]子树中
            // 2. i == keyNumber：目标关键字比当前节点所有关键字都大，应该在children[keyNumber]子树中

            // 如果是叶子节点，说明查找失败
            if (isLeaf) {
                return null;
            }
            
            // 不是叶子节点，递归到合适的子节点继续查找
            // 因为B树节点有n个关键字时，有n+1个子节点
            return children[i].get(key);
        }

        /**
         * 在指定位置插入关键字
         * 
         * @param key 要插入的关键字
         * @param index 插入位置（0到keyNumber之间）
         * 
         * 算法步骤：
         * 1. 将index位置及之后的关键字向右移动一位
         * 2. 在index位置插入新关键字
         * 3. 关键字数量加1
         */
        public void insertKey(int key, int index) {
            // 将index位置及之后的关键字向右移动一位，为新关键字腾出空间
            // keyNumber - index 表示需要移动的元素个数
            System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
            // 在index位置插入新关键字
            keys[index] = key;
            // 关键字数量加1
            keyNumber++;
        }

        /**
         * 在指定位置插入子节点
         * 
         * @param child 要插入的子节点
         * @param index 插入位置（0到keyNumber+1之间）
         * 
         * 算法步骤：
         * 1. 将index位置及之后的子节点向右移动一位
         * 2. 在index位置插入新子节点
         * 
         * 注意：插入子节点后，子节点数量会增加，但keyNumber不变
         * 因为B树节点有n个关键字时，有n+1个子节点
         */
        public void insertChild(Node child, int index) {
            // 将index位置及之后的子节点向右移动一位，为新子节点腾出空间
            System.arraycopy(children, index, children, index + 1, keyNumber - index);
            // 在index位置插入新子节点
            children[index] = child;
        }

        /**
         * 移除指定index处的key
         * @param index 指定的索引
         */
        int removeKey(int index) {
            int temp = keys[index];
            // 删除指定位置的元素，后面的元素自动向前移动填补空位
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
            return temp;
        }

        /**
         * 移除最左边的key
         */
        int removeLeftMostKey() {
            return removeKey(0);
        }

        /**
         * 移除最右边的key
         */
        int removeRightMostKey() {
            return removeKey(keyNumber - 1);
        }

        /**
         * 移除指定索引位置的child
         * @param index 索引
         */
        Node removeChild(int index) {
            Node t = children[index];
            System.arraycopy(children, index + 1, children, index, keyNumber - index);
            children[keyNumber] = null;
            return t;
        }

        /**
         * 移除最左边的child
         */
        Node removeLeftMostChild() {
            return removeChild(0);
        }

        /**
         * 移除最右边的child
         */
        Node removeRightMostChild() {
            return removeChild(keyNumber);
        }

        /**
         * index孩子处左边的兄弟
         * @param index 索引
         */
        Node childLeftSibling(int index) {
            return index > 0 ? children[index - 1] : null;
        }

        /**
         * index孩子处右边的兄弟
         * @param index 索引
         */
        Node childRightSibling(int index) {
            return index == keyNumber ? null : children[index + 1];
        }

        /**
         * 复制当前节点所有的key和child到target中，这个相当于是合并当前节点到目标节点上
         * @param target
         */
        void moveToTarget(Node target) {
            // 记录目标节点(target)在合并前已有的 key 的数量
            // 这个 start 值将作为 "追加" 数据的起始索引位置。
            int start = target.keyNumber;
            // 不是叶子节点的情况
            if (!isLeaf) {
                for (int i = 0; i <= keyNumber; i++) {
                    // 将子节点指针复制到目标节点的 children 数组中
                    // 存放的位置从 'start' 索引开始偏移
                    target.children[start + i] = children[i];
                }
            }

            for (int i = 0; i < keyNumber; i++) {
                // 将 key 复制到目标节点的 keys 数组末尾
                target.keys[target.keyNumber++] = keys[i];
            }
        }
    }

    /**
     * 向B树中插入关键字
     * 
     * @param key 要插入的关键字
     * 
     * 这是B树插入操作的公共接口，从根节点开始插入
     */
    public void put(int key) {
        doPut(root, key, null, 0);
    }

    /**
     * 递归插入关键字的内部方法
     * 
     * @param node 当前处理的节点
     * @param key 要插入的关键字
     * @param parent 父节点
     * @param index  待分裂的节点是父节点的第几个孩子
     */
    private void doPut(Node node, int key, Node parent, int index) {
        // 在当前节点中查找插入位置
        int i = 0;
        
        // 遍历当前节点的所有关键字，找到合适的插入位置
        while (i < node.keyNumber) {
            if (node.keys[i] == key) {
                return; // 走更新的逻辑
            }
            // 如果当前关键字大于要插入的关键字，说明插入位置就在当前位置
            if (node.keys[i] > key) {
                break; // 退出循环，此时i就是插入的位置
            }
            i++; // 继续向后找
        }

        if (node.isLeaf) {
            // 叶子节点：直接在当前节点插入关键字
            node.insertKey(key, i);
        } else {
            // 内部节点：递归到合适的子节点继续插入，注意为什么孩子是i，因为他这个查找是找往左的
            doPut(node.children[i], key, node, i);
        }
        // 分裂检查: 如果满了就直接分裂，不用等满了之后再添加元素的时候再分裂
        if (isFull(node)) {
            split(parent, node, index);
        }
    }

    boolean isFull(Node node) {
        return node.keyNumber == MAX_KEY_NUMBER;
    }

    /**
     * 分裂节点
     * 
     * 当子节点关键字数量达到最大值 2t-1 时，需要进行分裂操作
     * 分裂过程：
     * 1. 创建新的右节点
     * 2. 将原节点的后半部分关键字移动到右节点
     * 3. 将中间关键字提升到父节点
     * 4. 将右节点作为父节点的新子节点
     *
     * @param left   待分裂的节点（原节点，分裂后变成左节点）
     * @param parent 父节点
     * @param index  待分裂的节点是父节点的第几个孩子
     * 
     * 分裂前后结构变化：
     * 分裂前：parent.keys[index] -> left(2t-1个关键字)
     * 分裂后：parent.keys[index] -> left(t-1个关键字), parent.keys[index+1] -> right(t-1个关键字)
     */
    private void split(Node left, Node parent, int index) {
        if (parent == null) {
            // 分裂的是根节点,创建新的根节点，然后用新的根节点取代掉原来的根
            Node newRoot = new Node(t);
            newRoot.isLeaf = false;
            newRoot.insertChild(left, 0);
            root = newRoot;
            parent = newRoot;
        }

        // 创建新的右节点，用于存储分裂后的后半部分关键字
        Node right = new Node(t);

        // 右节点的叶子属性与原节点相同
        right.isLeaf = left.isLeaf;

        // 将原节点的后半部分关键字（从位置t开始）复制到右节点
        // 原节点有2t-1个关键字，后半部分有t-1个关键字
        System.arraycopy(left.keys, t, right.keys, 0, t - 1);

        // 如果待分裂数组是非叶子节点
        if (!left.isLeaf) {
            // 后半部分有t-1个关键字，那么就有t个孩子，那就考t个孩子过去x
            System.arraycopy(left.children, t, right.children, 0, t);
        }

        // 设置右节点的关键字数量
        right.keyNumber = t - 1;
        // 设置左节点的关键字数量（去掉后半部分和中间关键字）
        left.keyNumber = t - 1;

        // 将中间关键字提升到父节点
        // 这个关键字将作为左右子节点的分隔关键字
        int middle = left.keys[t - 1];
        parent.insertKey(middle, index);

        // 将新创建的右节点插入到父节点的子节点数组中
        // 插入位置是index+1，因为中间关键字已经插入到index位置
        parent.insertChild(right, index + 1);
    }
}
