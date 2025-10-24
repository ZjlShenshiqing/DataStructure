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

        /**
         * 最小度数：一个节点最少有几个孩子
         *
         * 一般由我们自己去指定，最小度数越大，树的高度越低
         */
        int t;

        public Node(int t) {
            this.t = t;
            this.keys = new int[2 * t - 1];  // 可能拥有的最多的节点数
            this.children = new Node[2 * t]; // 可能拥有的最多的孩子数
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * 多路查找方法
         */
        Node get(int key) {
            // 先去找节点里面的数组，如果找到直接返回，找不到再进入下一步
            int i = 0;
            while (i < keyNumber) {
                if (keys[i] == key) {
                    return this;
                }
                if (keys[i] > key) {
                    break;
                }
                i++;
            }
            // 执行到此时 keys[i] > key 或者 i == keyNumber
            if (isLeaf) {
                return null;
            }
            //
        }
    }
}
