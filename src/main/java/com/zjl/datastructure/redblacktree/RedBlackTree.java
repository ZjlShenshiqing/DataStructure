package com.zjl.datastructure.redblacktree;


/**
 * 红黑树实现
 *
 * @author zhangjlk
 * @date 2025/10/20 下午3:22
 * @description RedBlackTree
 */
public class RedBlackTree {

    enum Color {
        RED, BLACK
    }

    /**
     * 根节点
     */
    private Node root;

    private static class Node {
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
        Node left;

        /**
         * 右孩子
         */
        Node right;

        /**
         * 节点的父节点
         */
        Node parent;

        /**
         * 颜色
         */
        Color color = Color.RED;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 判断是否为父亲节点的左孩子
         *
         * @return 判断结果
         */
        boolean isLeftChild() {
            // 1. parent != null：确保当前节点不是根（根没有父节点）
            // 2. parent.left == this：检查父节点的左孩子是否就是当前节点（引用比较）
            return parent != null && parent.left == this;
        }

        /**
         * 叔叔节点，也就是根父亲平辈的节点
         *
         * 示例：
         *           5        ← 祖父 (grandparent)
         *         /   \
         *        3     6      ← 3 是父节点，6 就是叔叔（当当前节点是 2 或 4 时）
         *       / \     \
         *      2   4     7
         *
         * - 当前节点是 2 或 4 → 父节点是 3 → 祖父是 5 → 叔叔是 6
         * - 当前节点是 7 → 父节点是 6 → 祖父是 5 → 叔叔是 3
         * @return 叔叔节点
         */
        Node uncle() {
            // 如果当前节点没有父节点，或者父节点没有父节点（即祖父不存在），就返回 null
            if (parent == null || parent.parent == null) {
                return null;
            }
            // 判断父节点是祖父的左孩子还是右孩子
            if (parent.isLeftChild()) {
                return parent.parent.left;
            } else {
                return parent.parent.right;
            }
        }

        /**
         * 获取当前节点的兄弟节点（sibling）。
         *
         * 兄弟节点定义：与当前节点拥有同一个父节点的另一个子节点。
         * - 如果当前节点是父节点的左孩子，则兄弟节点是父节点的右孩子；
         * - 如果当前节点是父节点的右孩子，则兄弟节点是父节点的左孩子。
         *
         * 示例：
         *       父
         *      /  \
         *    当前  兄弟   ← 当前是左孩子，兄弟是右孩子
         *
         * 或：
         *       父
         *      /  \
         *   兄弟  当前   ← 当前是右孩子，兄弟是左孩子
         *
         * 注意：如果当前节点是根节点（没有父节点），则没有兄弟节点，返回 null。
         *
         * @return 当前节点的兄弟节点；若当前节点为根节点，则返回 null。
         */
        Node sibling() {
            // 根节点没有父节点，因此也没有兄弟节点
            if (parent == null) {
                return null;
            }

            // 判断当前节点是父节点的左孩子还是右孩子
            if (this.isLeftChild()) {
                // 是左孩子 → 兄弟在右边
                return parent.right;
            } else {
                // 是右孩子 → 兄弟在左边
                return parent.left;
            }
        }
    }

    /**
     * 判断节点的颜色是红色
     */
    boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    /**
     * 判断节点的颜色是黑色
     */
    boolean isBlack(Node node) {
        return node == null || node.color == Color.BLACK;
    }

    /**
     * 对指定节点执行右旋操作（Right Rotation）。
     *
     * 旋转前结构（简化）：
     *        node (失衡点)
     *       /
     *   leftChild
     *       \
     *    leftChildRight
     *
     * 旋转后结构：
     *      leftChild
     *         \
     *        node
     *         /
     *   leftChildRight
     *
     * 关键：必须正确维护所有节点的 parent 引用，以及与原父节点的连接。
     *
     * @param node 失衡的节点
     */
    private void rightRotate(Node node) {
        Node parent = node.parent;              // node 的父节点（旋转后要连接新根）
        Node leftChild = node.left;             // node 的左孩子，将成为新的子树根
        Node leftChildRight = leftChild.right;  // leftChild 的右子树（将变成 node 的左子树）

        // 调整 leftChildRight 的父指针
        if (leftChildRight != null) {
            leftChildRight.parent = node;       // 它的新父节点是原来的 node
        }

        // 将 node 变成 leftChild 的右孩子
        leftChild.right = node;
        node.parent = leftChild;                // node 的父节点更新为 leftChild

        // 将 leftChildRight 接到 node 的左边（填补 leftChild 离开后的空缺）
        node.left = leftChildRight;

        // 5. 将 leftChild 连接到原 parent
        if (parent == null) {
            // 情况1：node 原来是整棵树的根 → 现在 leftChild 成为新根
            root = leftChild;
            leftChild.parent = null;            // 新根没有父节点
        } else if (parent.left == node) {
            // 情况2：node 是 parent 的左孩子 → parent 的左指针指向 leftChild
            parent.left = leftChild;
        } else {
            // 情况3：node 是 parent 的右孩子 → parent 的右指针指向 leftChild
            parent.right = leftChild;
        }

        // 注意：leftChild 的 parent 在上面已通过 root 或 parent.left/right 隐式设置，
        // 但为了清晰，也可以显式设置（虽然上面逻辑已保证正确）：
        leftChild.parent = parent;
    }

    /**
     * 对指定节点执行左旋操作（Left Rotation）。
     *
     * 旋转前结构（简化）：
     *        node (失衡点)
     *             \
     *          rightChild
     *             /
     *        rightChildLeft
     *
     * 旋转后结构：
     *      rightChild
     *         /
     *      node
     *         \
     *   rightChildLeft
     *
     * 关键：必须正确维护所有节点的 parent 引用，以及与原父节点的连接。
     *
     * @param node 失衡的节点
     */
    private void leftRotate(Node node) {
        Node parent = node.parent;              // node 的父节点
        Node rightChild = node.right;           // node 的右孩子，将成为新的子树根
        Node rightChildLeft = rightChild.left;  // rightChild 的左子树（将变成 node 的右子树）

        // 调整 rightChildLeft 的父指针（如果存在）
        if (rightChildLeft != null) {
            rightChildLeft.parent = node;       // 它的新父节点是原来的 node
        }

        // 将 node 变成 rightChild 的左孩子
        rightChild.left = node;
        node.parent = rightChild;               // node 的父节点更新为 rightChild

        // 将 rightChildLeft 接到 node 的右侧
        node.right = rightChildLeft;

        // 将 rightChild 连接到原 parent
        if (parent == null) {
            // node 原来是根 → rightChild 成为新根
            root = rightChild;
        } else if (parent.left == node) {
            // node 是 parent 的左孩子
            parent.left = rightChild;
        } else {
            // node 是 parent 的右孩子
            parent.right = rightChild;
        }

        // 更新 rightChild 的父指针（关键！）
        rightChild.parent = parent;
    }

    /**
     * 新增或更新
     *
     * 正常增，遇到红红不平衡的情况进行调整
     * @param key   键
     * @param value 值
     */
    public void put(int key, Object value) {
        Node node = root;
        Node parent = null;
        while (node != null) {
            parent = node; // 每次循环的时候更新当前节点为父节点
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                node.value = value; // 找到了key，那就更新对象
                return;
            }
        }

        Node insertedNode = new Node(key, value);
        if (parent == null) {
            root = insertedNode;
            // 根节点的父亲就是null，可以省略一步
        } else if (key < parent.key) {
            parent.left = insertedNode;
            insertedNode.parent = parent;
        } else {
            parent.right = insertedNode;
            insertedNode.parent = parent;
        }
        fixRedRed(insertedNode); // 红红不平衡的情况进行调整
    }

    /**
     * 修复红色节点同时出现的情况
     * @param node 需要修复的节点
     */
    void fixRedRed(Node node){
        // 插入的case1: 插入节点是根节点，变黑就可以了
        if (node == root) {
            node.color = Color.BLACK;
            return;
        }
        // 插入的case2: 插入节点的父亲是黑色，无需进行调整
        if (isBlack(node.parent)) {
            return;
        }

        /**
         * 插入的case3:
         * 父亲是红色，此时触发红红相邻
         *
         * 如果此时叔叔为红色，那么父亲变为黑色，为了保证黑色的平衡，连带的叔叔也变为黑色
         * 祖父如果还是黑色，那会造成黑色太多，因为祖父节点变成红色
         *
         * 祖父如果变成红色，可能会接着触发红红相邻，因此对将祖父进行递归调整
         * 所以从祖父开始又是新一轮递归
         */
        Node parent = node.parent; // 父亲节点
        Node uncle = node.uncle(); // 叔叔节点
        Node grandParent = parent.parent; // 祖父
        if (isRed(uncle)) {
            parent.color = Color.BLACK;
            uncle.color = Color.BLACK;
            grandParent.color = Color.RED;
            fixRedRed(grandParent);
            return;
        }

        /**
         * 插入的case4 - 1:
         * 父亲为左孩子，插入节点也是左孩子，此时即 LL 不平衡
         *
         * 让父亲变黑，为了保证这颗子树黑色不变，将祖父变成红，但叔叔子树少了一个黑色
         * 祖父右旋，补齐一个黑色给叔叔，父亲旋转上去取代祖父，由于它是黑色，不会再次触发红红相邻
         */
        if (parent.isLeftChild() && node.isLeftChild()) {
            parent.color = Color.BLACK;
            grandParent.color = Color.RED;
            rightRotate(grandParent); // 右旋
        }
        /**
         * 插入的case4 - 2:
         * 父亲为左孩子，插入节点是右孩子，此时即 LR 不平衡
         *
         * 父亲左旋，变成 LL 情况，按 1. 来后续处理
         */
        else if (parent.isLeftChild()) {
            leftRotate(parent);
            node.color = Color.BLACK;
            grandParent.color = Color.RED;
            rightRotate(grandParent); // 右旋
        }
        /**
         * 插入的case4 - 3:
         * 父亲为右孩子，插入节点是右孩子，此时即 RR 不平衡
         *
         * 让父亲变黑，为了保证这颗子树黑色不变，将祖父变成红，但叔叔子树少了一个黑色
         * 祖父左旋，补齐一个黑色给叔叔，父亲旋转上去取代祖父，由于它是黑色，不会再次触发红红相邻
         */
        else if (!node.isLeftChild()) {
            parent.color = Color.BLACK;
            grandParent.color = Color.RED;
            leftRotate(grandParent); // 左旋
        }
        /**
         * 插入的case4 - 4:
         * 父亲为右孩子，插入节点是左孩子，此时即 RL 不平衡
         *
         * 父亲右旋，变成 RR 情况，按 3. 来后续处理
         */
        else {
            rightRotate(parent);
            node.color = Color.BLACK;
            grandParent.color = Color.RED;
            leftRotate(grandParent);
        }
    }
    
    public void remove(int key) {
        Node deleted = find(key);
        if (deleted == null) {
            return;
        }
        doRemove(deleted);
    }

    private void doRemove(Node deleted) {
        Node replaced = findReplaced(deleted); // 后继节点
        Node parent = deleted.parent; // 父亲
        // 没有孩子的情况
        if (replaced == null) {
            if (deleted == root) {
                root = null;
            } else {
                // 删除是需要进行平衡调整的
                // 删除节点和剩下节点都是黑, 剩下的节点是null, null默认是黑色
                if (isBlack(deleted)) {
                    // 复杂调整
                    fixDoubleBlack(deleted);
                } else {
                    // 红色叶子，无需进行处理
                }
                if (deleted.isLeftChild()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                deleted.parent = null;
            }
            return;
        }

        /**
         * 有一个孩子的情况
         * 注意如果只有一个孩子的话，那这个孩子是不可能再有任何子孩子的，要不然会触发不平衡，需要进行调整了
         */
        if (deleted.left == null || deleted.right == null) {
            // 根节点只有一个孩子的情况
            if (deleted == root) {
                // 将要保留的那个子节点（replaced）的数据复制到根节点
                root.key = replaced.key;
                root.value = replaced.value;

                // 清空根节点的左右子指针
                // 这里断开原根节点对其子树的引用，避免悬挂指针或重复删除
                root.left = null;
                root.right = null;
            } else {
                if (deleted.isLeftChild()){
                    parent.left = replaced;
                } else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null;

                if (isBlack(deleted) && isBlack(replaced)) {
                    // 被删除的和删剩下的都是黑
                    // 复杂处理
                    fixDoubleBlack(replaced);
                } else {
                    // 删的是黑，剩下的是红色，那么剩下的这个红节点变黑就行
                    replaced.color = Color.BLACK;
                }
            }
            return;
        }

        /**
         * 有两个孩子的情况 交换删除节点和后继节点的 key，value
         * 这种技巧叫李代桃僵 李树代替桃树而死。原比喻兄弟互相爱护互相帮助。后转用来比喻互相顶替或代人受过。
         *
         * 这样：两个孩子 => 有一个孩子 或者 没有孩子的情况
         */
        // 先交换一下删除节点和后继节点
        int tempKey = deleted.key;
        deleted.key = replaced.key;
        replaced.key = tempKey;

        Object tempValue = deleted.value;
        deleted.value = replaced.value;
        replaced.value = tempValue;

        // 递归处理，两个孩子的情况变成一个孩子或者没有孩子的情况
        doRemove(replaced);
    }

    /**
     * 遇到了双黑的情况，需要重新进行平衡调整
     * @param node 待调整节点
     */
    private void fixDoubleBlack(Node node) {
        if (node == root) {
            return;
        }
        Node parent = node.parent;
        Node sibling = node.sibling();
        // case3: 被调整节点的兄弟为红，此时两个侄子定为黑
        if (isRed(sibling)) { // 如果能进来，说明sibling节点必然是存在的
            if (node.isLeftChild()) {
                leftRotate(parent); // 进行左旋
            } else {
                rightRotate(parent);
            }
            // 保持颜色平衡
            parent.color = Color.RED;
            sibling.color = Color.BLACK;
            // 进入case4或者5情况的处理
            fixDoubleBlack(node);
            return;
        }
        if (sibling != null) {
            // case4: 被调整节点的兄弟为黑，两个侄子都为黑
            /**
             * 将兄弟变红，目的是将删除节点和兄弟那边的黑色高度同时减少 1
             * 如果父亲是红，则需将父亲变为黑，避免红红，此时路径黑节点数目不变
             * 如果父亲是黑，说明这条路径还是少黑，再次让父节点触发双黑，也就是递归，然后父亲节点再来走一次这些case看看咋变，如果还是不行就让爷爷走一遍
             */
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = Color.RED;
                if (isRed(parent)) {
                    parent.color = Color.BLACK;
                } else {
                    fixDoubleBlack(parent);
                }
            }
            // case5:被调整节点的兄弟为黑，但至少一个红侄子
            else {
                // LL的情况
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    // 右旋一次
                    rightRotate(parent);
                    sibling.left.color = Color.RED;
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                }
                // LR的情况
                else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    // TA要旋转上来，所以TA要变成父节点的颜色,先放上来是因为后面左右旋会导致其变化，这个sibling.right就为null了
                    sibling.right.color = parent.color;
                    // 先左旋
                    leftRotate(sibling);
                    // 再右旋
                    rightRotate(parent);
                    parent.color = Color.BLACK;
                }
                // RL的情况
                else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    // 先进行右旋
                    rightRotate(sibling);
                    // 再进行左旋
                    leftRotate(parent);
                    parent.color = Color.BLACK;
                }
                // RR的情况
                else {
                    leftRotate(parent);
                    sibling.right.color = Color.BLACK;
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                }
            }
        } else {
            // 让父亲递归的去做判断
            fixDoubleBlack(parent);
        }
    }

    /**
     * 查找删除节点
     */
    private Node find(int key) {
        Node p = root;
        while (p != null) {
            if (key < p.key) {
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    /**
     * 查找用于替代被删除节点的节点（replacement node）。
     *
     * 替代规则：
     * - 如果被删节点是叶子 → 无需替代，返回 null；
     * - 如果只有左子树或只有右子树 → 用存在的子树根替代；
     * - 如果有两个子树 → 用“中序后继”替代。
     *
     * 注意：中序后继 = 右子树中的最左节点（即右子树中最小的节点）。
     *
     * @param deleted 被删除的节点
     * @return 替代节点；若被删节点是叶子，返回 null
     */
    private Node findReplaced(Node deleted) {
        // 被删节点是叶子节点
        if (deleted.left == null && deleted.right == null) {
            return null;
        }
        // 只有左子树/只有右子树
        if (deleted.left == null) {
            return deleted.right;
        }
        if (deleted.right == null) {
            return deleted.left;
        }
        Node succeed = deleted.right;
        while (succeed.left != null) {
            succeed = succeed.left;
        }
        return succeed;
    }
}
