package com.zjl.datastructure.graph;

/**
 * 并查集合
 *
 * @author zhangjlk
 * @date 2025/11/15 11:59
 */
public class DisjointSet {

    int [] s;

    int [] size;

    public DisjointSet(int size) {
        s = new int[size];
        this.size = new int[size]; // 维护每个集合（树）的节点数量
        // 索引对应顶点
        // 元素是用来表示与之有关系的顶点
        /*
            索引  0  1  2  3  4  5  6
            元素 [0, 1, 2, 3, 4, 5, 6] 表示一开始顶点直接没有联系（只与自己有联系）

        */
        for (int i = 0; i < size; i++) {
            s[i] = i;
            this.size[i] = 1; // 每个顶点下面的节点数量初始化为1
        }
    }

    // 找到 x 所在集合的“老大”（根节点）
    public int find(int x) {

        // 如果 s[x] == x，说明 x 的上级就是 x 自己，
        // 也就是说：x 是这个集合的老大（根节点）
        if (s[x] == x) {
            return x;   // 找到老大，返回
        }
        // 否则说明 x 不是老大，
        // 那么继续往上找它的父节点 s[x] 的老大
        return s[x] = find(s[x]); // 把 x 路上的所有节点都直接挂到最终老大下面，使树变得更扁平，下次查找更快。
    }

    // 合并时让“小树挂大树”，避免树过高
    public void union(int p, int q) {
        // 换老大
        if (size[p] < size[q]) {
            int t = p;
            p = q;
            q = t;
        }
        s[q] = p;
        size[p] = size[p] + size[q];
    }
}
