package com.zjl.algorithm.recursion;

import javax.swing.plaf.metal.MetalRadioButtonUI;

/**
 * 杨辉三角打印
 * @author zhangjlk
 * @date 2025/9/16 15:46
 */
public class PascalTriangle {
    private static int element(int[][] triangle ,int i, int j) {
        if (triangle[i][j] > 0) {
            return triangle[i][j];
        }

        if (j == 0 || i == j) {
            triangle[i][j] = 1;
            return 1;
        }

        triangle[i][j] = element(triangle, i - 1, j - 1) + element(triangle,i - 1, j);
        return triangle[i][j];
    }

    public static void print(int n) {

        int[][] triangle = new int[n][];

        for (int i = 0; i < n; i++) {

            triangle[i] = new int[i + 1];

            for (int j = 0; j <= i; j++) {
                System.out.print(element(triangle, i, j));
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        print(6);
    }
}
