package com.zjl.datastructure.stack;

/**
 * 栈
 * @author zhangjlk
 * @date 2025/9/25 下午6:54
 * @description Stack
 */
public interface Stack<E> {

    /**
     * 向栈顶压入元素
     * @param value 元素
     * @return      压入结果
     */
    boolean push(E value);

    /**
     * 从栈顶弹出元素
     * @return  栈非空返回栈顶元素，栈为空返回null
     */
    E pop();

    /**
     * 返回栈顶元素，不弹出
     * @return  栈非空返回栈顶元素，栈为空返回null
     */
    E peek();

    /**
     * 判断栈是否为空
     * @return 空返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 判断栈是否已满
     * @return 满返回true，否则返回false
     */
    boolean isFull();
}
