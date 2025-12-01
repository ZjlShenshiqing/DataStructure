package com.zjl.datastructure.graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FloydWarshall - 多源最短路径计算
 *
 * @author zhangjlk
 * @date 2025/11/13 18:15
 */
public class FloydWarshall {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");

        v1.edges = List.of(new Edge(v3, -2));
        v2.edges = List.of(new Edge(v1, 4), new Edge(v3, 3));
        v3.edges = List.of(new Edge(v4, 2));
        v4.edges = List.of(new Edge(v2, -1));
        List<Vertex> graph = List.of(v1, v2, v3, v4);

        /*
                直接连通
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   3   ∞
            v3  ∞   ∞   0   2
            v4  ∞   -1  ∞   0

                k=0 借助v1到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   2   ∞
            v3  ∞   ∞   0   2
            v4  ∞   -1  ∞   0

                k=1 借助v2到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   2   ∞
            v3  ∞   ∞   0   2
            v4  3   -1  1   0

                k=2 借助v3到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  0
            v2  4   0   2   4
            v3  ∞   ∞   0   2
            v4  3   -1  1   0

                k=3 借助v4到达其它顶点
                v1  v2  v3  v4
            v1  0   -1   -2  0
            v2  4   0   2   4
            v3  5   1   0   2
            v4  3   -1  1   0
         */
        floydWarshall(graph);
    }

    private static void floydWarshall(List<Vertex> graph) {
        // 顶点的个数
        int size = graph.size();
        // 二维表格：记录顶点到其他顶点的最短路径值
        int[][] dist = new int[size][size];
        // 记录走过来的上一个顶点从哪走过来的
        // prev[i][j] 存储的就是：在“从 i 到 j 的最短路径”上，紧挨着 j 的前一个点是谁
        Vertex[][] prev = new Vertex[size][size];
        // 外层循环：遍历出行的顶点
        for (int i = 0; i < size; i++) {
            Vertex out = graph.get(i);
            Map<Vertex, Integer> map = out.edges.stream().collect(Collectors.toMap(e -> e.linked, e -> e.weight));

            // 内层循环：遍历出列的顶点
            for (int j = 0; j < size; j++) {
                Vertex in = graph.get(j);
                /**
                 * 1. in out是同一个顶点，初始化成0；
                 * 2. in out连通，初始化成边的权重
                 * 3. in out不连通，初始化成∞
                 */
                if (in == out) {
                    dist[i][j] = 0;
                } else {
                    // 如果起点和终点不是同一个顶点，
                    // 就从 map 里取出它们之间的边的权重（比如 v1->v3 的长度）
                    // 如果 map 中没有对应的记录（说明没有这条边），
                    // 就用 Integer.MAX_VALUE 来表示“无穷大”，也就是当前不可达
                    dist[i][j] = map.getOrDefault(in, Integer.MAX_VALUE);
                    prev[i][j] = map.get(in) != null ? out : null;
                }
            }
        }

        print(prev);

        // 看看能不能借路到达其他顶点
        // k代表节点数，也就是需要借路的次数
        /*
            可以模拟一下一部分过程，看看下面，就是v2借助v1到达其他节点
            可以分割成下面两步：
            第一步：v2到v1，第二步，v1到其他节点
            v2->v1          v1->v?
            dist[1][0]   +   dist[0][0]
            dist[1][0]   +   dist[0][1]
            dist[1][0]   +   dist[0][2]
            dist[1][0]   +   dist[0][3]
         */
        for (int k = 0; k < size; k++) {
            // 每一次借路都要处理每一行每一列
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // 现在检查：从 i 到 j，如果通过 k 中转，会不会更短
                    // 也就是比较：dist[i][j]  VS  dist[i][k] + dist[k][j]

                    // 先判断 i -> k 这条路存在（不是无穷大）
                    if (dist[i][k] != Integer.MAX_VALUE
                            // 再判断 k -> j 这条路存在
                            && dist[k][j] != Integer.MAX_VALUE
                            // 再判断：走 i -> k -> j 的路径是否比当前 i -> j 更短
                            && dist[i][k] + dist[k][j] < dist[i][j]) {

                        // 如果更短，就更新 i -> j 的最短距离
                        dist[i][j] = dist[i][k] + dist[k][j];

                        // 现在发现：从 i 到 j 走 i -> k -> j 更短
                        // 从 i 到 j 的最短路，最后一跳还是 “? -> j” 这一步
                        // 这一步其实和 “k 到 j 的最短路” 的最后一跳完全一样
                        // 所以：i -> j 的“前驱（紧挨着 j 的那个点）”
                        //      就应该等于 k -> j 最短路里的前驱，也就是 prev[k][j]
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }
    }

    static void print(int[][] dist) {
        System.out.println("-------------");
        for (int[] row : dist) {
            System.out.println(Arrays.stream(row).boxed()
                    .map(x -> x == Integer.MAX_VALUE ? "∞" : String.valueOf(x))
                    .map(s -> String.format("%2s", s))
                    .collect(Collectors.joining(",", "[", "]")));
        }
    }

    static void print(Vertex[][] prev) {
        System.out.println("-------------------------");
        for (Vertex[] row : prev) {
            System.out.println(Arrays.stream(row).map(v -> v == null ? "null" : v.name)
                    .map(s -> String.format("%5s", s))
                    .collect(Collectors.joining(",", "[", "]")));
        }
    }
}
