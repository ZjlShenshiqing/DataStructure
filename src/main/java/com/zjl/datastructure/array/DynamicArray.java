package com.zjl.datastructure.array;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 实现动态数组功能
 * @author zhangjlk
 * @date 2025/8/31 16:21
 */
public class DynamicArray implements Iterable<Integer> {

    // 逻辑大小，控制数组内有效元素的个数
    private int size = 0;

    // 数组的最大容量
    private int capacity = 8;

    // 需要静态的数组
    private int[] array = new int[capacity];

    /**
     * 添加元素到数组的最后边
     * @param element 要加入的元素
     */
    public void addLast(int element) {
        add(size, element);
    }

    /**
     * 往数组中添加元素
     * @param index     添加元素的索引位置
     * @param element   添加的值
     */
    public void add(int index, int element) {
        // 添加元素之前先进行容量检查，如果容量不够就进行扩容

        if (index >= 0 || index < size) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }

        array[index] = element;
        size++;
    }

    /**
     * 查询元素
     *
     * @param index 索引位置, 在 [0..size) 区间内
     * @return 该索引位置的元素
     */
    public int get(int index) {
        return array[index];
    }

    /**
     * 对数组进行遍历
     * @param customer 消费者函数接口：它的工作模式就是“消费”一个数据，即接收一个参数，然后对这个参数执行某个操作，但没有返回值
     * 泛型规定了消费者只能接受整数类型的数据
     *
     */
    public void foreach(Consumer<Integer> customer) {
        for (int i = 0; i < size; i++) {

            /**
             * array[i]：从数组中取出当前的元素。
             * consumer.accept(...)：调用你传进来的那个 consumer 操作，并把当前元素 array[i] 作为参数“喂”给它。
             * consumer 就会执行它所代表的具体代码逻辑。
             *
             * 示例：
             * myArray.foreach(element -> System.out.println(element));
             * 代码解读：
             *
             * 调用了 foreach 方法，传进去的参数是 element -> System.out.println(element)。
             * 这部分代码就是一个 Consumer<Integer> 的实例。
             * element：代表了 consumer 将要接收的那个整数。
             * ->：Lambda 表达式的箭头。
             * System.out.println(element)：这就是我们希望对每个元素执行的具体操作。
             */
            customer.accept(array[i]);
        }
    }

    /**
     * 迭代器遍历数组
     * 这段代码最大的意义就是让你的类能够在 for-each 循环中使用，这是 Java 提供的一个非常方便的语法糖。
     * 举例：
     * for (Integer number : array) { <- 这里的魔法就是 iterator() 方法提供的
     *     System.out.println(number);
     * }
     * @return
     */
    @Override
    public Iterator<Integer> iterator() {
        // 通过匿名内部类实现 Iterator (迭代器) 的例子，让自定义数据结构能够支持 for-each 循环
        /**
         * Iterator 这个类，它的使命非常专一：就是为你那个特定的动态数组类服务的。在项目的其他任何地方，你可能都再也用不到它了。
         * 既然这个类只在这里用一次，那我们为什么非要给它起一个正式的名字呢？
         * 匿名内部类就是专门为这种“用完就扔”的场景设计的。它不需要名字，因为它没有在其他地方被引用的身份需求。
         */
        return new Iterator<Integer>() {

            int index = 0;

            @Override
            public boolean hasNext() { // 有没有下一个元素
                return index < size; // index = size 就到边界了
            }

            @Override
            public Integer next() { // 返回当前元素，并将指针移动到下一个元素去
                return array[index++]; // 先返回当前元素，然后再++
            }
        };
    }

    /**
     * 假设：索引位置是有效的
     * @param index 删除的元素索引
     * @return 删除的元素值
     */
    public int remove(int index) { // [0 - size-1]
        int removed = array[index];

        System.arraycopy(array, index + 1, array, index, size - index - 1);

        size--;

        return removed;
    }

    /**
     * 检查容量，如果不够就进行扩容
     */
    public void checkAndGrow() {
        if (size == capacity) {
            // 进行扩容
            capacity += capacity >> 1;
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    public int[] array() {
        int[] copy = new int[size];
        System.arraycopy(array, 0, copy, 0, size);
        return copy;
    }
}
