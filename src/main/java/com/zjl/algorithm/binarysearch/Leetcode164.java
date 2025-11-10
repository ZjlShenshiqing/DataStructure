package com.zjl.algorithm.binarysearch;

import com.zjl.datastructure.array.DynamicArray;

/**
 * 相邻元素的最大间距
 *
 * @author zhangjlk
 * @date 2025/11/9 12:48
 */
public class Leetcode164 {

    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        // --- 1. 找出最大值和最小值 ---
        // 这是为了确定总的数据范围，以便计算需要多少个桶
        int max = nums[0];
        int min = nums[0];
        for (int i1 = 1; i1 < nums.length; i1++) {
            if (nums[i1] > max) {
                max = nums[i1];
            }
            if (nums[i1] < min) {
                min = nums[i1];
            }
        }

        // --- 2. 准备桶 - 期望桶个数---
        // (max - min) / range 计算出了数据范围需要 "跨越" 多少个桶
        // +1 是为了确保最后一个桶（包含 max）也能被分配
        // 例如：max=99, min=0, range=10. (99-0)/10 = 9 (取整).
        // 但我们需要 10 个桶 (0-9, ..., 90-99)，所以 +1 得到 10.
        int range = Math.max((max - min) / (nums.length - 1), 1);
        DynamicArray[] buckets = new DynamicArray[(max - min) / range + 1];

        // 初始化每个桶（因为此时 buckets 数组里存的还是 null）
        for (int i1 = 0; i1 < buckets.length; i1++) {
            buckets[i1] = new DynamicArray();
        }

        // --- 3. 放入数据（分配） ---
        // 遍历原始数组中的每一个元素
        for (int arr : nums) {
            // 计算当前元素 arr 应该被放入哪个桶
            // (arr - min) 是元素相对于最小值的偏移量
            // / range 将这个偏移量转换为桶的索引
            // 例如：min=0, range=10.
            //   arr=5,  (5-0)/10 = 0 -> 放入 buckets[0]
            //   arr=23, (23-0)/10 = 2 -> 放入 buckets[2]
            buckets[(arr - min) / range].addLast(arr);
        }

        int index = 0; // 用于跟踪原始数组 arrs 的填充位置

        // --- 4. 排序桶内元素重新进行排序 ---
        // 遍历每一个桶
        for (DynamicArray bucket : buckets) {

            // a. 从桶中获取其内部的 int[] 副本
            int[] array = bucket.array();

            // b. 对这个小数组进行排序
            // 这里使用了插入排序
            InsertionSort.sort(array);

            // c. 将排好序的桶内元素，合并（写回）到原始数组 arrs
            for (int value : array) {
                nums[index] = value;
                index++;
            }
        }
        // 当所有桶都合并完毕后，arrs 数组就整体有序了

        int m = 0;
        for (int i = 1; i < nums.length; i++) {
            m = Math.max(max, nums[i] - nums[i - 1]);
        }
        return m;
    }

//    public int maximumGap(int[] nums) {
//        // 边界条件检查：如果数组元素少于2个，则不存在相邻元素，返回0
//        if (nums.length < 2) {
//            return 0;
//        }
//
//        // 遍历整个数组，找到最大值，这个最大值决定了我们需要处理多少位数字
//        // 因为基数排序需要从最低位（个位）开始，一直处理到最高位
//        int max = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            max = Math.max(max, nums[i]);
//        }
//
//        // 创建10个桶，编号为0-9，用于存放当前位数字相同的元素
//        // 每个桶是一个动态数组，可以存放多个元素
//        ArrayList<Integer>[] buckets = new ArrayList[10];
//        for (int i = 0; i < 10; i++) {
//            buckets[i] = new ArrayList<>();
//        }
//
//        // 开始按位进行基数排序，从个位开始，逐步处理十位、百位、千位...
//        int m = 1; // m表示当前处理的位，个位为1，十位为10，百位为100...
//        // 当m超过最大值时，说明所有位都已处理完毕
//        while (m <= max) {
//            // 第一步：分配阶段
//            // 遍历原始数组，将每个数字按照当前位的数字放入对应的桶中
//            // i / m % 10 的计算逻辑：
//            // i / m：将数字右移，使得当前位移动到个位位置
//            // % 10：取个位数字，即当前处理位的数字
//            // 例如当m=100时，要处理百位数字，对于数字12345：
//            // 12345 / 100 = 123（右移两位），然后123 % 10 = 3（取个位）
//            for (int i : nums) {
//                // 计算当前数字在当前位上的数字，作为桶的索引
//                int digit = (i / m) % 10;
//                // 将该数字添加到对应编号的桶中
//                buckets[digit].add(i);
//            }
//
//            // 第二步：收集阶段
//            // 按照桶的编号顺序（0-9），将桶中的元素依次取出，重新填入原数组
//            // 这样就完成了当前位的排序，同时保持了之前位排序的结果
//            int key = 0; // key用于追踪当前在原数组中的填充位置
//            for (ArrayList<Integer> bucket : buckets) {
//                // 遍历每个桶，将桶中的所有元素按顺序放回原数组
//                // 桶的编号从小到大对应数字从小到大，保证了排序的正确性
//                for (Integer num : bucket) {
//                    nums[key++] = num;
//                }
//                // 清空桶，为下一轮分配做准备
//                bucket.clear();
//            }
//            // 将权重乘以10，准备处理下一位（个位→十位→百位...）
//            m = m * 10;
//        }
//
//        // 经过完整的基数排序后，数组已经按升序排列
//        // 此时要找到相邻元素的最大差值
//        int result = 0; // 存储最大相邻差值
//        // 从第二个元素开始遍历，计算每个元素与前一个元素的差值
//        for (int i = 1; i < nums.length; i++) {
//            // 计算相邻元素的差值
//            int currentGap = nums[i] - nums[i - 1];
//            // 更新最大差值，保留较大的那个
//            result = Math.max(result, currentGap);
//        }
//
//        // 返回计算得到的最大相邻差值
//        return result;
//    }
}
