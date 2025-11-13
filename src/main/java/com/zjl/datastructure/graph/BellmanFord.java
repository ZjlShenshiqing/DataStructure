package com.zjl.datastructure.graph;

import java.util.List;

    /**
     * BellmanFord算法实现
     *
     * @author zhangjlk
     * @date 2025/11/11 17:58
     */
    public class BellmanFord {

        /**
         * 算法实现
         *
         * @param graph 图
         * @param start 开始节点
         */
        private static void bellmanFord(List<Vertex> graph, Vertex start) {
            start.dist = 0;
            int size = graph.size();

            // V-1 轮松弛操作
            for (int i = 0; i < size - 1; i++) {
                // 遍历所有的边
                for (Vertex startTemp : graph) {
                    for (Edge edge : startTemp.edges) {
                        Vertex end = edge.linked;
                        if (startTemp.dist != Integer.MAX_VALUE &&
                                startTemp.dist + edge.weight < end.dist) {
                            end.dist = startTemp.dist + edge.weight;
                            end.prev = start;
                        }
                    }
                }
            }

            // 检测负权环：再做一轮松弛，看是否还能更新
            boolean hasNegativeCycle = false;
            for (Vertex startTemp : graph) {
                for (Edge edge : startTemp.edges) {
                    Vertex end = edge.linked;
                    if (startTemp.dist != Integer.MAX_VALUE &&
                            startTemp.dist + edge.weight < end.dist) {
                        System.out.println("发现负权环！");
                        hasNegativeCycle = true;
                        break; // 可以选择继续或直接退出
                    }
                }
                if (hasNegativeCycle) break;
            }

            // 如果存在负权环，可以选择不输出结果或标记
            if (!hasNegativeCycle) {
                for (Vertex vertex : graph) {
                    System.out.println(vertex.name + " " + vertex.dist + " " +
                            (vertex.prev != null ? vertex.prev.name : "null"));
                }
            } else {
                System.out.println("图中存在负权环，最短路径无解！");
            }
        }
    }
