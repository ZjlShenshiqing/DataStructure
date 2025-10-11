package com.zjl.datastructure.stack;

import java.util.LinkedList;

/**
 * 中缀表达式转后缀
 * @author zhangjlk
 * @date 2025/9/27 17:07
 */
public class InfixToSuffix {

    /**
     * 思路（完整版）：
     *
     * 1. 初始化一个空栈用于存放运算符，初始化一个空列表（或字符串）用于存放输出结果。
     *
     * 2. 从左到右扫描中缀表达式的每个字符（或 token）：
     *    - 如果是 操作数（数字、变量等）：直接添加到输出结果中。
     *
     *    - 如果是 左括号 '('：
     *        直接压入运算符栈，ta的优先级最低。
     *
     *    - 如果是 右括号 ')'：
     *        不断弹出栈顶运算符并加入输出结果，直到遇到左括号 '('；
     *        弹出并丢弃该左括号（左右括号不加入结果）。
     *
     *    - 如果是 运算符（如 +, -, *, /, ^ 等）：
     *        在将其压入栈之前，需比较其与栈顶运算符的优先级：
     *            • 若栈非空，且栈顶不是 '('，且栈顶运算符的 优先级 ≥ 当前运算符（注意：对于左结合运算符，如 + - * /），
     *              则不断弹出栈顶运算符并加入输出结果；
     *
     *            • 若栈顶运算符的优先级小于当前运算符，那么就压入栈
     *
     *            • 特例：对于右结合运算符（如 '^'），仅当栈顶优先级 > 当前才弹出；
     *        将当前运算符压入栈。
     *
     * 3. 扫描结束后，将栈中剩余的所有运算符依次弹出，并加入输出结果。
     *
     * 4. 最终输出结果即为后缀表达式（逆波兰表达式）。
     *
     * 关键规则总结：
     * - 操作数：直接输出
     * - '('：入栈
     * - ')'：弹出直到 '('
     * - 运算符：弹出栈中优先级 ≥ 它的运算符（左结合），再入栈
     * - 结束：清空栈
     *
     * 示例：
     *   中缀：a + b * c + (d * e + f) * g
     *   后缀：a b c * + d e * f + g * +
     */

    public static void main(String[] args) {
    }

    /**
     * 判断运算符优先级
     * @param c 运算符
     * @return  优先级
     */
    static int priority(char c) {
        return switch (c) {
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            case '(' -> 0; // 这里将括号的优先级调整成最低
            default -> throw new IllegalArgumentException("不合法的运算符" + c);
        };
    }

    static String infixToSuffix(String expression) {
        LinkedList<Character> stack = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder(expression);
        for (int i = 0; i < expression.length() - 1; i++) {
            char c = expression.charAt(i);
            switch (c) {
                case '*', '/', '+', '-' -> {
                    if (stack.isEmpty()) {
                        stack.push(c);
                    } else {
                        if (priority(c) > priority(stack.peek())) { // 本字符串优先级 > 栈顶字符串优先级, 入栈
                            stack.push(c);
                        } else {
                            while(!stack.isEmpty() && priority(stack.peek()) >= priority(c)) { // 本字符串优先级小于栈顶字符串优先级，把栈顶元素小于的都取出来放到字符串
                                stringBuilder.append(stack.pop());
                            }
                            stack.push(c); // 优先级小的运算符入栈
                        }
                    }
                }
                case '(' -> {
                    stack.push(c);
                }
                case ')' -> {
                    while(!stack.isEmpty() && stack.peek() != '(') {
                        stringBuilder.append(stack.pop()); // 不等于左括号的都得出栈
                    }
                    stack.pop(); // 左括号直接弹出不要了
                }
                default -> {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }
}
