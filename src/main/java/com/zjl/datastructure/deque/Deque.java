package com.zjl.datastructure.deque;

/**
 * 双端队列
 *
 * 既可以对头进行添加和删除，也可以对尾进行添加和删除
 *
 * @author zhangjlk
 * @date 2025/9/29 上午10:37
 * @description Deque
 */
public interface Deque<E> {

    /**
     * 向队头添加元素
     */
    boolean offerFirst(E e);

    /**
     * 向队尾添加元素
     */
    boolean offerLast(E e);

    /**
     * 从队头删除元素
     */
    E pollFirst();

    /**
     * 从队尾删除元素
     */
    E pollLast();

    /**
     * 查询队头元素
     */
    E peekFirst();

    /**
     * 查询队尾元素
     */
    E peekLast();

    boolean isEmpty();

    boolean isFull();
}
