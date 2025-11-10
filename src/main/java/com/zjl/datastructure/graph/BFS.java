package com.zjl.datastructure.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 广度优先搜索
 *
 * @author zhangjlk
 * @date 2025/11/10 16:17
 */
public class BFS {

    /**
     * 广度优先搜索（BFS）迭代实现
     * 从给定顶点 vertex 开始，按层遍历图中所有可达顶点
     *
     * @param vertex 起始顶点
     */
    private static void bfs(Vertex vertex) {
        // 1. 创建队列，用于存储待访问的顶点
        Queue<Vertex> queue = new LinkedList<>();

        // 2. 将起始顶点加入队列
        queue.offer(vertex);

        // 3. 标记起始顶点为已访问，防止重复访问
        vertex.visited = true;

        // 4. 当队列不为空时，持续进行广度优先遍历
        while (!queue.isEmpty()) {
            // 5. 从队列头部取出一个顶点（FIFO：先进先出）
            Vertex poll = queue.poll();

            // 6. 访问（处理）当前顶点，这里是打印顶点信息
            System.out.println(poll);

            // 7. 遍历当前顶点的所有邻接边
            for (Edge edge : poll.edges) {
                // 8. 获取边所连接的相邻顶点
                Vertex neighbor = edge.linked;

                // 9. 检查相邻顶点是否已被访问
                if (!neighbor.visited) {
                    // 10. 标记相邻顶点为已访问（关键步骤，防止重复入队）
                    neighbor.visited = true;

                    // 11. 将未访问的相邻顶点加入队列，等待下一轮处理
                    queue.offer(neighbor);
                }
                // 如果 neighbor 已被访问，则跳过，不入队
            }
            // 继续 while 循环，处理队列中的下一个顶点
        }
        // 队列为空时，说明所有可达顶点都已访问完毕，遍历结束
    }
}
