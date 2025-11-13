package com.zjl.datastructure.graph;

import java.util.*;

/**
 * Dijkstra 最短路径算法
 *
 * @author zhangjlk
 * @date 2025/11/11 16:06
 */
public class Dijkstra {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");

        v1.edges = List.of(new Edge(v3, 9), new Edge(v2, 7), new Edge(v6, 14));
        v2.edges = List.of(new Edge(v4, 15));
        v3.edges = List.of(new Edge(v4, 11), new Edge(v6, 2));
        v4.edges = List.of(new Edge(v5, 6));
        v5.edges = List.of();
        v6.edges = List.of(new Edge(v5, 9));

        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6);

        dijkstra(graph, v1);
    }

    /**
     * 算法实现
     *
     * @param graph 图
     * @param start 开始的节点
     */
    private static void dijkstra(List<Vertex> graph, Vertex start) {
        // 未访问顶点的集合
        // 创建一个新的 优先级队列，并将 graph 中的所有元素（也就是所有顶点）复制到新的队列中
        PriorityQueue<Vertex> unvisited = new PriorityQueue<>(Comparator.comparingInt(v -> v.dist));
        start.dist = 0;
        start.prev = null; // 确保起点的prev是null

        for (Vertex v : graph) {
            unvisited.offer(v);
        }

        while (!unvisited.isEmpty()) {
            // 选取当前节点
            Vertex current = unvisited.peek();
            // 更新当前顶点的邻居距离
            if (!current.visited) {
                updateNeighboursDist(current, unvisited);
                current.visited = true;
            }
            // 移除当前顶点
            unvisited.poll();
        }

        for (Vertex vertex : graph) {
            System.out.println(vertex.name + " " + vertex.dist + " " + (vertex.prev != null ? vertex.prev.name : "null"));
        }
    }

    /**
     * 更新当前节点邻居的距离
     *
     * @param curr      当前节点
     */
    private static void updateNeighboursDist(Vertex curr, PriorityQueue<Vertex> unvisited) {
        for (Edge edge : curr.edges) {
            // 邻居节点
            Vertex n = edge.linked;

            // 检查这个邻居顶点是否还在未访问集合中
            // 如果已经访问过了，就不需要再更新了
            if (!n.visited) {
                // 计算从起点经过当前节点到达邻居的新距离
                // 新距离 = (从起点到当前节点的已知最短距离) + (当前节点到邻居的边的权重)
                int dist = curr.dist + edge.weight; // edge.weight 就是边的权重！

                // 如果新计算出的距离比邻居顶点当前记录的距离更短
                if (dist < n.dist) {
                    // 更新邻居顶点的最短距离
                    n.dist = dist;
                    n.prev = curr;
                    unvisited.offer(n);
                }
            }
        }
    }

//    /**
//     * 选取最短距离的节点
//     *
//     * @param list 未访问顶点的集合
//     * @return 最短距离的节点
//     */
//    private static Vertex chooseMinDistVertex(ArrayList<Vertex> list) {
//        // 假定索引0处的是最小的
//        Vertex min = list.get(0);
//        for (int i = 1; i < list.size() - 1; i++) {
//            // 如果有更小的，就做一下更新
//            if (list.get(i).dist < min.dist) {
//                min = list.get(i);
//            }
//        }
//        return min;
//    }
}
