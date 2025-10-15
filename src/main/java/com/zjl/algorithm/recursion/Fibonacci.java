package com.zjl.algorithm.recursion;

/**
 *
 * @author zhangjlk
 * @date 2025/9/15 10:56
 */
public class Fibonacci {

    public static int fibonacci(int n) {

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        int x = fibonacci(n - 1);
        int y = fibonacci(n - 2);

        return x + y;
    }
}
