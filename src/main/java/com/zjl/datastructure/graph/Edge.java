package com.zjl.datastructure.graph;

/**
 * 边
 *
 * @author zhangjlk
 * @date 2025/11/10 09:44
 */
public class Edge {

    /**
     * 边链接的终点
     */
    Vertex linked;

    /**
     * 权重
     */
    int weight;

    public Edge(Vertex linked) {
        this(linked, 1);
    }

    public Edge(Vertex linked, int weight) {
        this.linked = linked;
        this.weight = weight;
    }

    public Edge(int from, int to, int weight) {

    }
}
