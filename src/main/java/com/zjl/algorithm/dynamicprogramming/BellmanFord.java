package com.zjl.algorithm.dynamicprogramming;

import com.zjl.datastructure.graph.Edge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zhangjlk
 * @date 2025/11/22 23:25
 */
public class BellmanFord {

    static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

        /*
            f(v) 用来表示从起点出发，到达 v 这个顶点的最短距离
            初始时
            f(v) = 0   当 v==起点 时
            f(v) = ∞   当 v!=起点 时

            之后
            新           旧     所有from
            f(to) = min(f(to), f(from) + from.weight)

            from 从哪来
            to   到哪去

            f(v4) = min( ∞, f(v3) + 11 ) = 20
            f(v4) = min( 20, f(v2) + 15 ) = 20


            v1  v2  v3  v4  v5  v6
            0   ∞   ∞   ∞   ∞   ∞
            0   7   9   ∞   ∞   14  第一轮
            0   7   9   20  23  11  第二轮
            0   7   9   20  20  11  第三轮
            0   7   9   20  20  11  第四轮
            0   7   9   20  20  11  第五轮

     */

    public static void main(String[] args) {
        List<Edge> edges = List.of(
                new Edge(6, 5, 9),
                new Edge(4, 5, 6),
                new Edge(1, 6, 14),
                new Edge(3, 6, 2),
                new Edge(3, 4, 11),
                new Edge(2, 4, 15),
                new Edge(1, 3, 9),
                new Edge(1, 2, 7)
        );

        /**
         * 数组存储从起点到图中所有其他顶点的当前已知最短距离。
         * dp 数组的作用
         * 初始化存储起点为 0，其余为 ∞ 的初始距离。
         * 迭代过程随着每一次对所有边的松弛，dp 数组中的值会不断更新，逐渐收敛到更小的最短距离。
         * 最终结果经过迭代后，dp 数组中存储的就是从起点 1 到所有其他顶点（2 到 6）的最终最短距离。
         *
         * 索引值就是每个顶点的编号
         */
        int[] dp = new int[7];

        // 初始化操作
        dp[1] = 0;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        print(dp);

        // Bellman-Ford 算法核心循环
        // 外层循环迭代 N-1 次 (N为顶点数)。本例中 N=6，所以迭代 5 次。
        // 每次迭代保证至少有一条新的最短路径被确定。
        // 注意：这里 i < edges.size() = 8，迭代 8 次，次数是足够的，但通常用 V-1 (5) 次更标准。
        // 如果图中没有负权环，迭代 V-1 次就足够了。
        for (int i = 0; i < 5; i++) {
            // 内部循环：遍历图中的所有边，进行松弛操作
            for (Edge edge : edges) {

                // 检查：只有当起点到 edge.from 的距离已知 (不是无穷大) 时，才进行松弛
                if (dp[edge.from] != Integer.MAX_VALUE) {

                    // 松弛操作 (Relaxation): 尝试更新 dp[edge.to]
                    // 新距离候选值 = dp[edge.from] + edge.weight
                    // dp[edge.to] = min(旧的 dp[edge.to], 新距离候选值)
                    dp[edge.to] = Integer.min(dp[edge.to], dp[edge.from] + edge.weight);
                }
            }
        }

        print(dp); // 打印最终计算得到的最短距离数组
    }

    static void print(int[] dp) {
        System.out.println(Arrays.stream(dp)
                .mapToObj(i -> i == Integer.MAX_VALUE ? "∞" : String.valueOf(i))
                .collect(Collectors.joining(",", "[", "]")));
    }
}
