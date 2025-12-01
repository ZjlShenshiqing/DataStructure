package com.zjl.algorithm.greedyAlgorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 哈夫曼树
 *
 * @author zhangjlk
 * @date 2025/11/18 15:23
 */
public class HuffmanTree {

    /**
     * 整颗树的构建过程
     *
     * 1. 将统计了出现频率的字符，放入优先级队列
     * 2. 每次出队将两个频次最低的元素，给他俩找爹，爹记录两个孩子频次之和
     * 3，把爹重新放入队列，重复 2-3
     * 4. 当队列只剩一个元素时，树构建完成
     */
    static class Node {
        Character ch; // 字符
        int frequency; // 频次
        Node left;
        Node right;
        String code; // 编码

        public Node(Character ch) {
            this.ch = ch;
        }

        public Node(int frequency, Node left, Node right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        public int getFrequency() {
            return frequency;
        }

        boolean isLeaf() {
            return left == null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "ch=" + ch +
                    ", frequency=" + frequency +
                    ", left=" + left +
                    ", right=" + right +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    // 原始字符串
    String str;

    Map<Character, Node> map = new HashMap<>();

    // 根节点
    Node root;

    public HuffmanTree(String str) {
        this.str = str;
        char[] chars = str.toCharArray();
        // 统计字符频率
        for (char c : chars) {
            // 1. 检查哈希表中是否还没有这个字符的记录（即第一次遇到该字符）
            if (!map.containsKey(c)) {
                // 如果是第一次遇到，就初始化一个新的节点放入 Map 中
                // 此时频率(frequency)通常在 Node 构造函数里默认为 0 或 1
                map.put(c, new Node(c));
            }

            // 2. 无论是不是第一次遇到，都要更新频率
            // 先取出该字符对应的 Node 对象，然后将它的 frequency 属性加 1
            map.get(c).frequency++;
        }

        // 1. 初始化优先级队列
        PriorityQueue<Node> queue = new PriorityQueue<>(
                // 定义队列的排序规则：按照 Node 对象的 frequency 属性进行升序排序。
                // 优先级队列基于最小堆实现，因此频率最低的节点排在最前面。
                Comparator.comparingInt(Node::getFrequency)
        );

        // 2. 将所有初始字符节点放入队列
        // map.values() 是上一步统计出的所有字符（键是字符，值是 Node 对象）。
        queue.addAll(map.values());

        // 3. 开始合并节点，直到队列中只剩下一个节点（即根节点）
        while (queue.size() >= 2) {
            // 3.1 取出队列中频率最小的节点，作为新节点的左子节点
            Node x = queue.poll();
            // 3.2 取出队列中频率第二小的节点，作为新节点的右子节点
            Node y = queue.poll();

            // 3.3 计算新父节点的频率：等于两个子节点频率之和
            int parentFreq = x.frequency + y.frequency;

            // 3.4 构造新的父节点，并关联左右子节点
            // 注意：这个父节点不存储任何字符信息，只存储频率和左右引用
            Node parent = new Node(parentFreq, x, y);

            // 3.5 将新合并的父节点重新加入队列
            // 队列会自动根据 parentFreq 重新排序，保证最小的节点总是排在前面。
            queue.offer(parent);
        }

        // 4. 循环结束，队列中剩下的唯一一个节点就是整个哈夫曼树的根节点
        root = queue.poll();

        // 计算每个字符的编码
        dfs(root, new StringBuilder());
        for (Node node : map.values()) {

        }
    }

    /**
     * 深度优先遍历 Huffman 树
     *
     * 作用：
     *  1. 为每个叶子节点生成 Huffman 编码（保存在 node.code 中）
     *  2. 计算整棵树的带权路径长度之和：Σ(频率 * 编码长度)
     *
     * @param node 当前遍历到的节点
     * @param code 当前从根走到这个节点的“路径编码”（用 0/1 组成的 StringBuilder）
     * @return 以当前节点为根的这棵子树的总代价
     */
    private int dfs(Node node, StringBuilder code) {

        // 用来累加当前子树的总代价
        int sum = 0;

        // 递归终止条件：如果当前是叶子节点
        if (node.isLeaf()) {

            // 当前路径上的 0/1 序列就是这个字符的 Huffman 编码
            node.code = code.toString();

            // 该叶子节点的代价，也就是总编码长度 = 出现频率 * 编码长度
            sum = node.frequency * code.length();

        } else {
            // 非叶子节点，需要继续往下走左右子树

            // 约定：往左走路径上增加一个 '0'
            // dfs 里会在返回前删掉最后一个字符，相当于“回溯”
            sum += dfs(node.left, code.append("0"));

            // 左子树返回后，code 已经通过回溯恢复成“当前节点”的路径
            // 约定：往右走路径上增加一个 '1'
            sum += dfs(node.right, code.append("1"));
        }

        // 3. 回溯：从当前节点返回父节点之前，把刚刚加上的最后一位删掉
        //    这样父节点看到的 code 就是自己的路径，不会被子节点污染
        if (code.length() > 0) { // 防止根节点首次调用时越界
            code.deleteCharAt(code.length() - 1);
        }

        // 返回当前子树的总代价
        return sum;
    }

    /**
     * 编码
     */
    public String encode() {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(map.get(c).code);
        }
        return sb.toString();
    }

    /**
     * 解码
     * 从根节点，寻找数字对应的字符
     *      数字是0，向左走
     *      数字是1，向右走
     * 如果没走到头，每走一步的数字的索引 i++
     * 走到头可以找到解码字符，再将node重置为根节点
     */
    public String decode(String str) {
        // 严格来说这里应该是二进制比特流，这里简化为字符串按字符处理
        char[] chars = str.toCharArray();
        // 用来保存解码后的原始文本
        StringBuilder sb = new StringBuilder();
        // 当前处理到的比特位下标
        int index = 0;
        // 从 Huffman 树的根节点开始解码
        Node node = root;

        // 只要还有未处理的比特，就持续往树里走
        while (index < chars.length) {

            // 只有在当前节点不是叶子时，才根据 0/1 往下走
            if (!node.isLeaf()) {
                if (chars[index] == '0') {        // 遇到 0，向左子树走
                    node = node.left;
                } else if (chars[index] == '1') { // 遇到 1，向右子树走
                    node = node.right;
                }
                // 消耗掉这一位比特，继续看下一位
                index++;
            }

            // 无论是本轮还是上一轮刚走到叶子，
            // 只要当前节点是叶子，就说明已经完整解出一个字符
            if (node.isLeaf()) {
                // 把叶子节点上保存的字符追加到结果中
                sb.append(node.ch);
                // 解出一个字符后，要从根重新开始走，解下一个字符
                node = root;
            }
        }

        // 所有比特都处理完，sb 中就是完整的原始字符串
        return sb.toString();
    }
}
