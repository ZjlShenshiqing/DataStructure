package com.zjl.datastructure.graph;

import java.util.List;

/**
 * 深度优先搜索
 *
 * @author zhangjlk
 * @date 2025/11/10 10:01
 */
public class DFS {

    public static void main(String[] args) {
//        Vertex v1 = new Vertex("v1");
//        Vertex v2 = new Vertex("v2");
//        Vertex v3 = new Vertex("v3");
//        Vertex v4 = new Vertex("v4");
//        Vertex v5 = new Vertex("v5");
//        Vertex v6 = new Vertex("v6");
//
//        v1.edges = List.of(new Edge(v3), new Edge(v2), new Edge(v6));
//        v2.edges = List.of(new Edge(v4));
//        v3.edges = List.of(new Edge(v4), new Edge(v6));
//        v4.edges = List.of(new Edge(v5));
//        v5.edges = List.of();
//        v6.edges = List.of(new Edge(v5));
//
//        dfs1(v1);
    }

    /**
     * 深度优先搜索（DFS）递归实现
     * 从给定的顶点 v 开始，遍历图中所有可达的顶点
     *
     * @param v 起始顶点
     */
    private static void dfs1(Vertex v) {
        // 1. 标记当前顶点为已访问，防止重复访问形成无限循环
        v.visited = true;

        // 2. 访问（处理）当前顶点，这里是打印顶点的名称
        System.out.println(v.name);

        // 3. 遍历当前顶点的所有邻接边
        for (Edge edge : v.edges) {
            // 4. 获取边所连接的另一个顶点
            Vertex neighbor = edge.linked;

            // 5. 检查该邻接顶点是否已被访问
            if (!neighbor.visited) {
                // 6. 如果未被访问，则递归调用 DFS，继续深入探索
                dfs1(neighbor);
            }
            // 如果 neighbor.visited 为 true，说明该顶点已被访问过
        }
        // 当前顶点的所有未访问邻居都已递归处理完毕，函数返回
        // 此时自动回溯到上一层递归调用的顶点
    }
}
