package com.zjl.datastructure.graph;

import java.util.List;
import java.util.Objects;

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

    /**
     * 初始顶点距离：无穷大
     */
    private static final int INF = Integer.MAX_VALUE;

    /**
     * 到各个顶点的距离
     */
    int dist = INF;

    /**
     * 顶点的上一个节点
     */
    Vertex prev = null;

    public Vertex(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
