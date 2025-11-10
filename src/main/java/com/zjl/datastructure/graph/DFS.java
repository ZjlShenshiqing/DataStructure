package com.zjl.datastructure.graph;

import java.util.LinkedList;
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
     * 深度优先搜索（DFS）迭代实现
     * 使用显式栈代替递归调用栈，实现图的深度优先遍历
     *
     * @param v 起始顶点
     */
    private static void dfs2(Vertex v) {
        // 1. 创建一个栈，用于模拟递归过程中的函数调用栈
        LinkedList<Vertex> stack = new LinkedList<>();

        // 2. 将起始顶点压入栈中，作为遍历的起点
        stack.push(v);

        // 3. 当栈不为空时，持续进行遍历
        while (!stack.isEmpty()) {
            // 4. 弹出栈顶顶点，作为当前要处理的顶点
            // 这一步相当于递归中的“当前节点”
            Vertex pop = stack.pop();

            // 5. 标记当前顶点为已访问，防止重复访问
            pop.visited = true;

            // 6. 访问（处理）当前顶点，这里是打印顶点名称
            System.out.println(pop.name);

            // 7. 遍历当前顶点的所有邻接边，寻找下一个要访问的顶点
            for (Edge edge : pop.edges) {
                // 8. 获取边所连接的相邻顶点
                Vertex neighbor = edge.linked;

                // 9. 检查相邻顶点是否已经被访问过
                if (!neighbor.visited) {
                    // 10. 如果未被访问，则将其压入栈中
                    // 这样在下一次循环时，会优先处理这个邻居（深度优先）
                    stack.push(neighbor);
                }
                // 如果 neighbor 已被访问，则跳过，不入栈
            }
            // 继续 while 循环，处理下一个栈顶元素
        }
        // 栈为空时，说明所有可达顶点都已访问完毕，遍历结束
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
