package com.zjl.algorithm.greedyAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 活动选择问题
 *
 * @author zhangjlk
 * @date 2025/11/20 11:59
 */
public class ActivitySelectionProblem {

    static class Activity {
        int index;
        int start;
        int finish;

        public Activity(int index, int start, int finish) {
            this.index = index;
            this.start = start;
            this.finish = finish;
        }

        public int getFinish() {
            return finish;
        }

        @Override
        public String toString() {
            return "Activity(" + index + ")";
        }
    }

    public static void main(String[] args) {
        Activity[] activities = new Activity[]{
                new Activity(0, 1, 3),
                new Activity(1, 2, 4),
                new Activity(2, 3, 5)
        };
//        Activity[] activities = new Activity[]{
//                new Activity(0, 1, 2),
//                new Activity(1, 3, 4),
//                new Activity(2, 0, 6),
//                new Activity(3, 5, 7),
//                new Activity(4, 8, 9),
//                new Activity(5, 5, 9)
//        };

        Arrays.sort(activities, Comparator.comparingInt(Activity::getFinish));

        select(activities, activities.length);
    }

    /**
     * 活动选择：在一个会议室中安排尽可能多且互不冲突的活动
     * 贪心策略：总是优先选择「结束时间最早」的活动
     *
     * 前提：activities 数组已经按 finish 升序排好，这样就不用比较谁的结束时间更早了
     *
     * @param activities 活动数组
     * @param n          活动数量（通常就是 activities.length）
     */
    public static void select(Activity[] activities, int n) {
        // 用来保存最终被选中的活动集合
        List<Activity> result = new ArrayList<>();

        // 先选择第 0 个活动：
        // 因为数组已经按结束时间排序，所以它是“结束最早”的
        Activity prev = activities[0];
        result.add(prev);

        // 从第 1 个活动开始，依次尝试选择
        for (int i = 1; i < n; i++) {
            // 当前考察的活动
            Activity current = activities[i];

            // 如果当前活动的开始时间 >= 上一个已选活动的结束时间
            // 说明它与之前已选的所有活动都不冲突，可以安排
            if (current.start >= prev.finish) {
                // 选中当前活动
                result.add(current);
                // 更新“上一个已选活动”为当前活动
                prev = current;
            }
        }
    }
}
