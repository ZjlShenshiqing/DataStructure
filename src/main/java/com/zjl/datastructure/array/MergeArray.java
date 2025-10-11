package com.zjl.datastructure.array;

/**
 *
 * @author zhangjlk
 * @date 2025/9/24 10:37
 */
public class MergeArray {

    public static void merge1(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2, int k) {

        if (i > iEnd) {
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        } else if (j > jEnd) {
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }

        if (a1[i] < a1[j]) {
            a2[k]  = a1[i];
            merge1(a1, i + 1, iEnd, j, jEnd, a2, k + 1);
        } else {
            a2[k]  = a1[j];
            merge1(a1, i, iEnd, j + 1, jEnd, a2, k + 1);
        }
    }


    public static void merge2(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2) {
        int k = 0;
        while (i <= iEnd && j <= jEnd) {
            if (a1[i] < a2[j]) {
                a2[k] = a1[i];
                i++;
            } else {
                a2[k] = a2[j];
                j++;
            }

            k++;
        }

        if (i > iEnd) {
            System.arraycopy(a1, j, a2, k, jEnd - k + 1);
        }
        if (j > jEnd) {
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }
    }
}
