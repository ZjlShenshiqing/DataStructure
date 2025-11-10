package com.zjl.datastructure.graph;

import java.util.List;

/**
 * 顶点
 *
 * @author zhangjlk
 * @date 2025/11/10 09:43
 */
public class Vertex {

    /**
     * 节点名
     */
    String name;

    /**
     * 边
     */
    List<Edge> edges;

    /**
     * 入度
     */
    int inDegree;

    /**
     * 状态 0-未访问 1-访问中 2-访问过
     */
    int status;

    /**
     * 节点是否被访问
     */
    boolean visited;

    // 求解最短距离相关
    private static final int INF = Integer.MAX_VALUE;
    int dist = INF;
    Vertex prev = null;

    public Vertex(String name) {
        this.name = name;
    }
}
