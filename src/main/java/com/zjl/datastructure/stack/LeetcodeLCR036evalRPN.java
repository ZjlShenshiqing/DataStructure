package com.zjl.datastructure.stack;

import java.util.LinkedList;

/**
 * 逆波兰表达式求值
 *
 * @author zhangjlk
 * @date 2025/9/26 下午12:45
 * @description LeetcodeLCR036evalRPN
 */
public class LeetcodeLCR036evalRPN {

    /**
     * 算法思路：
     * 从左向右去进行遍历，遇到数字就放入栈，遇到了运算符就取出栈中前两个元素进行计算，再将计算的结果加入栈中
     */
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            switch (token) {
                case "+" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a + b);
                }
                case "-" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a * b);
                }
                case "/" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a / b);
                }
                default -> { // 数字
                    stack.push(Integer.parseInt(token));
                }
            }
        }

        return stack.pop();
    }
}
