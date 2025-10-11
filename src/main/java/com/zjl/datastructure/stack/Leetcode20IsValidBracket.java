package com.zjl.datastructure.stack;

/**
 * 判断是否为有效的括号
 *
 * @author zhangjlk
 * @date 2025/9/26 下午12:00
 * @description Leetcode20IsValidBracket
 */
public class Leetcode20IsValidBracket {

    public boolean isValid(String s) {

        ArrayStack<Character> stack = new ArrayStack<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // 遇到左括号，就把右括号放到栈里
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            }

            // 遇到右括号，先和栈顶元素进行对比,同时还需要考虑到栈里面的元素为空的情况
            else {
                if (!stack.isEmpty() && c == stack.peek()) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
