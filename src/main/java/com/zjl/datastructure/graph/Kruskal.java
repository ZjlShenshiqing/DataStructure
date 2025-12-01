package com.zjl.datastructure.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Kruskal算法
 *
 * @author zhangjlk
 * @date 2025/11/15 11:35
 */
public class Kruskal {

    static class Edge implements Comparable<Edge> {
        List<Vertex> vertices;
        int start;
        int end;
        int weight;

        public Edge(List<Vertex> vertices, int start, int end, int weight) {
            this.vertices = vertices;
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return vertices.get(start).name + "<->" + vertices.get(end).name + "(" + weight + ")";
        }
    }

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        List<Vertex> vertices = List.of(v1, v2, v3, v4, v5, v6, v7);
        PriorityQueue<Edge> queue = new PriorityQueue<>(List.of(
                new Edge(vertices,0, 1, 2),
                new Edge(vertices,0, 2, 4),
                new Edge(vertices,0, 3, 1),
                new Edge(vertices,1, 3, 3),
                new Edge(vertices,1, 4, 10),
                new Edge(vertices,2, 3, 2),
                new Edge(vertices,2, 5, 5),
                new Edge(vertices,3, 4, 7),
                new Edge(vertices,3, 5, 8),
                new Edge(vertices,3, 6, 4),
                new Edge(vertices,4, 6, 6),
                new Edge(vertices,5, 6, 1)
        ));

        kruskal(vertices.size(), queue);
    }

    // Kruskal 算法核心逻辑：给定顶点数量 size 和一堆按权重排序好的边 queue
    // 计算出一棵最小生成树，并把选中的边打印出来
    static void kruskal(int size, PriorityQueue<Edge> queue) {
        // 用来存放最终选中的边（也就是 MST 里的那 size-1 条边）
        List<Edge> result = new ArrayList<>();

        // 并查集：一开始每个顶点自己是一个独立的集合
        DisjointSet set = new DisjointSet(size); // 与顶点的个数保持一致

        // Kruskal 的终止条件：一棵生成树需要 (size - 1) 条边
        while (result.size() < size - 1) {
            // 从优先队列中取出当前权重最小的边
            Edge poll = queue.poll();

            // 找到这条边两端顶点所在的“集合代表”（并查集的根）
            int s = set.find(poll.start);
            int e = set.find(poll.end);

            // 如果两个端点不在同一个集合里，说明加上这条边不会形成环
            if (s != e) {
                // 把这条边收入结果集，成为 MST 的一部分
                result.add(poll);
                // 在并查集中把这两个集合合并，表示它们已经通过这条边连通了
                set.union(s, e);
            }
            // 如果 s == e，就说明这条边的两个端点本来就连在一起了
            // 再加上这条边会形成环，所以直接丢弃（什么都不做，继续下一条边）
        }

        // 输出整棵最小生成树上的所有边
        for (Edge edge : result) {
            System.out.println(edge);
        }
    }
}
