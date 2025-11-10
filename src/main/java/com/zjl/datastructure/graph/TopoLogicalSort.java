package com.zjl.datastructure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 拓扑排序实现
 *
 * @author zhangjlk
 * @date 2025/11/10 16:49
 */
public class TopoLogicalSort {

    public static void main(String[] args) {
        // --- 1. 构建图 ---
        // 初始化图中的所有顶点（课程）
        Vertex v1 = new Vertex("网页基础");
        Vertex v2 = new Vertex("Java基础");
        Vertex v3 = new Vertex("JavaWeb");
        Vertex v4 = new Vertex("Spring框架");
        Vertex v5 = new Vertex("微服务框架");
        Vertex v6 = new Vertex("数据库");
        Vertex v7 = new Vertex("实战项目");

        // 定义图的“邻接表”表示法
        v1.edges = List.of(new Edge(v3)); // v1 -> v3
        v2.edges = List.of(new Edge(v3)); // v2 -> v3
        v3.edges = List.of(new Edge(v4)); // v3 -> v4
        v6.edges = List.of(new Edge(v4)); // v6 -> v4
        v4.edges = List.of(new Edge(v5)); // v4 -> v5
        v5.edges = List.of(new Edge(v7)); // v5 -> v7
        v7.edges = List.of();             // v7 是终点

        // 将所有顶点放入一个列表中，方便统一管理
        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6, v7);

        // --- 2. 拓扑排序核心算法 (Kahn's Algorithm) ---

        // 步骤 2.1: 统计所有节点的“入度”
        for (Vertex v : graph) {
            for (Edge edge : v.edges) {
                // edge.linked 是 v 的“下游”顶点，其入度+1
                edge.linked.inDegree++;
            }
        }

        // 步骤 2.2: 将所有“入度为0”的顶点（起点）加入队列
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex v : graph) {
            if (v.inDegree == 0) {
                queue.offer(v);
            }
        }

        // 步骤 2.3: 处理队列，直到队列为空

        // 创建一个列表，用于按顺序存储拓扑排序的结果
        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            // 从队列中取出一个“已解锁”的顶点 v
            Vertex v = queue.poll();

            // 将该顶点（的名称）加入到结果列表中
            // 这标志着该顶点已被“访问”或“排序”
            result.add(v.name);

            // 遍历 v 的所有“下游”课程
            for (Edge edge : v.edges) {
                // 对应下游课程 (edge.linked) 的入度减 1
                // （含义是：v 这个前置依赖已被满足）
                edge.linked.inDegree--;

                // 如果这个下游课程的入度变成了 0
                if (edge.linked.inDegree == 0) {
                    // 说明它的所有前置依赖都已满足，可以入队了
                    queue.offer(edge.linked);
                }
            }
        }

        // --- 3. 结果校验（环检测） ---

        // 比较“结果列表的大小”和“图中顶点的总数”
        if (result.size() == graph.size()) {
            // 如果相等，说明所有顶点都被成功访问（排序）了
            // 证明图是一个“有向无环图”(DAG)
            System.out.println("拓扑排序成功（无环）:");
            System.out.println(String.join(" -> ", result));
        } else {
            // 如果不相等（result.size() < graph.size()）
            // 说明算法在中途卡住了（队列空了，但还有顶点没入队）
            // 这证明图中必定存在“环”（循环依赖）
            System.out.println("拓扑排序失败，检测到环！");
            System.out.println("已排序的部分：" + String.join(" -> ", result));
            // 那些没能加入 result 列表的顶点，就是环的一部分或受环影响的下游顶点
        }
    }
}
