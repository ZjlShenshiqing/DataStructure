package com.zjl.datastructure.queue;

/**
 * 简单队列实现
 *
 * @author zhangjlk
 * @date 2025/9/24 11:13
 */
public interface Queue<E> {

    /**
     * 向队列尾部插入值
     *
     * @param value 待插入值
     * @return      插入成功返回true，反之返回false
     */
    boolean offer(E value);

    /**
     * 从队列头获取值，并移除
     *
     * @return 队列如果非空就返回队头值，否则返回null
     */
    E poll();

    /**
     * 从队列头获取值，不移除
     *
     * @return 队列如果非空就返回队头值，否则返回null
     */
    E peek();

    /**
     * 检查队列是否为空
     *
     * @return 空返回true，非空返回false
     */
    boolean isEmpty();

    /**
     * 检查队列是否已满
     *
     * @return 满返回true，否则返回false
     */
    boolean isFull();
}
