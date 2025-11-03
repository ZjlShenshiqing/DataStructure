package com.zjl.algorithm.binarysearch;

/**
 * 希尔排序
 *
 * @author zhangjlk
 * @date 2025/11/3 下午5:06
 * @description ShellSort
 */
public class ShellSort {

    public static void sort(int[] arr) {
        // 1. 外层循环：控制 gap 的大小
        //    初始间隙为数组长度的一半 (>> 1 是位运算，等同于 / 2)
        //    每轮循环后，间隙都会减半 (gap = gap >> 1)
        //    当 gap = 1 时，执行最后一次（等同于标准的插入排序）
        for (int gap = arr.length >> 1 ; gap >= 1 ; gap = gap >> 1) {

            // 2. 内层循环：对每个 gap 组进行插入排序
            //    low 从 gap 开始，遍历所有元素
            //    (索引 0 到 gap-1 的元素作为每个组的“初始已排序”元素)
            for (int low = gap; low < arr.length; low ++) {

                // 3. temp 是当前需要被插入到“已排序”组中的元素 (a[low])
                int temp = arr[low];

                // 4. 初始化“已排序”组的指针 i：
                //    i 指向 a[low] 同一组中的前一个元素
                int i = low - gap;

                // 5. 寻找插入位置 (标准的插入排序核心)
                //    在 "a[i] > t" (需要移动) 并且 "i >= 0" (没越界) 时循环
                while (i >= 0 &&  arr[i] > temp) {
                    // 将 a[i]（较大的元素）向后移动 gap 个位置
                    arr[i + gap] = arr[i];
                    // 指针 i 向前移动 gap 个位置，继续比较同组的前一个元素
                    i -= gap;
                }

                // 6. 插入, 此时 i+gap 就是 temp 应该被插入的正确位置

                // 优化: if (i != low - gap)
                // 这个判断检查 `while` 循环是否至少执行了一次。
                // 如果 i 仍然等于 low - gap，意味着它已经在正确的位置，不需要执行赋值操作。
                // 只有当 i 发生了变化（i -= gap 被执行）时，才需要插入。
                if (i != low - gap) {
                    arr[i + gap] = temp;
                }
            }
        }
    }
}
