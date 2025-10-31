package com.zjl.datastructure.HashTable;

import java.util.prefs.PreferenceChangeEvent;

/**
 * 哈希表实现
 *
 * @author zhangjlk
 * @date 2025/10/29 18:27
 */
public class HashTable {

    /**
     * 哈希表中的节点类
     */
    static class Entry{
        int hash;        // 缓存的键的哈希值，避免重复计算，提升性能
        Object key;      // 键（key），用于标识该条目
        Object value;    // 值（value），与 key 关联的数据
        Entry next;      // 指向链表中的下一个 Entry 节点，用于解决哈希冲突（链地址法）

        /**
         * 构造方法：初始化一个 Entry 节点
         *
         * @param hash  键的哈希值（通常由 key.hashCode() 计算并扰动后得到）
         * @param key   键对象
         * @param value 值对象
         */
        public Entry(int hash, Object key, Object value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    // 初始化哈希表
    Entry[] table = new Entry[16];

    // 哈希表的元素数量
    int size = 0;

    // 负载因子
    float loadFactor = 0.75f;

    // 阈值
    int threshold = (int) (loadFactor * table.length);

    /**
     * 根据哈希码获取value
     *
     * @param hash 哈希码
     * @param key  键
     * @return     值
     */
    public Object get(int hash, Object key) {
        // 计算该哈希值在哈希表（table 数组）中的索引位置
        int index = hash & (table.length - 1);
        // 检查该索引位置是否为空（即没有发生哈希冲突或该位置从未插入数据）
        if (table[index] == null) {
            return null; // 该桶为空，说明 key 不存在
        }

        // 从该桶（链表头）开始遍历，查找具有相同 key 的 Entry
        Entry p = table[index];
        while (p != null) {
            if (p.key.equals(key)) {
                return p.value; // 找到匹配的键，返回对应的值
            }
            p = p.next; // 移动到链表下一个节点
        }
        // 遍历完整个链表仍未找到匹配的 key，说明该键不存在
        return null;
    }

    /**
     * 获取value,哈希码自己自动生成
     *
     * @param key  键
     * @return     值
     */
    public Object get(Object key) {
        int hash = key.hashCode(); // 自动生成hash码
        return get(hash, key);
    }

    /**
     * 存入键和value,哈希码自己自动生成
     *
     * @param key  键
     * @return     值
     */
    public void put(Object key, Object value) {
        int hash = key.hashCode(); // 自动生成hash码
        put(hash, key, value);
    }

    /**
     * 删除键和value,哈希码自己自动生成
     *
     * @param key  键
     * @return     值
     */
    public Object remove(Object key) {
        int hash = key.hashCode(); // 自动生成hash码
        return remove(hash, key);
    }

    public static void main(String[] args) {
        String s1 = "bac";
        String s2 = new String("abc");

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());

        // 原则：值相同的字符串生成相同的 hash 码, 尽量让值不同的字符串生成不同的 hash 码
        /*
            对于 abc  a * 100 + b * 10 + c
            对于 bac  b * 100 + a * 10 + c
         */
        int hash = 0;
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            System.out.println((int) c);
            // (a*10 + b)*10 + c  ==>  a*100 + b*10 + c  2^5
            hash = (hash << 5) - hash + c;
        }
        System.out.println(hash);
    }

    /**
     * 向hash表存入新的 key value，如果 key 重复，就只更新value
     *
     * @param hash  哈希码
     * @param key   键
     * @param value 值
     */
    void put(int hash, Object key, Object value) {
        // 计算该哈希值在哈希表（table 数组）中的索引位置
        int index = hash & (table.length - 1);

        // 检查目标桶（index 位置）是否为空
        if (table[index] == null) {
            // 桶为空，说明没有哈希冲突，直接创建新节点并放入
            table[index] = new Entry(hash, key, value);
            size++; // 哈希表元素数量加一
        } else
        // 桶非空，说明该位置已存在一个或多个 Entry（链表结构）
        {
            // 从链表头开始遍历，查找是否已存在相同的 key
            Entry p = table[index];
            while (true) {
                // 检查当前节点的 key 是否与传入的 key 相等
                if (p.key.equals(key)) {
                    p.value = value; // 相等就进行更新
                    return;
                }
                // 如果当前节点是链表的最后一个节点（next 为 null）
                if (p.next == null) {
                    break; // // 跳出循环，在末尾插入新节点
                }

                // 继续遍历，移动到下一个节点
                p = p.next;
            }
            // 遍历到链表末尾仍未找到相同 key，说明是新 key
            // 在链表尾部追加新节点
            p.next = new Entry(hash, key, value);
            size++; // 元素总数增加
        }
        // 判断加完了是否触发阈值，触发了就得扩容
        if (size > threshold) {
            resize();
        }
    }

    /**
     * 扩容哈希表：将底层数组容量翻倍，并重新分配所有元素
     *
     * 利用 “新容量 = 旧容量 × 2” 且容量为 2 的幂的特性，
     * 每个元素在新表中的位置只有两种可能：
     *   - 原索引 i
     *   - 原索引 i + old 链表长度
     *
     * 因此，每个桶中的链表最多被拆成两个子链表，分别放入新表的两个位置。
     */
    private void resize() {
        // 创建新数组，容量为原数组的 2 倍（左移 1 位 = ×2）
        Entry[] newTable = new Entry[table.length << 1];

        // 遍历原哈希表的每个桶（每个数组索引）
        for (int i = 0; i < table.length; i++) {
            Entry p = table[i];
            if (p != null) {

                // 拆分链表移动到新数组
                // 准备两个链表头尾指针，用于拆分原链表
                // - aHead / a：存放 hash & old 链表长度 == 0 的节点（留在原索引 i）
                // - bHead / b：存放 hash & old 链表长度 != 0 的节点（移到 i + old 链表长度）
                /**
                 * 示例（假设 oldCap = 8）：
                 * 原链表：0->8->16->24->32->40->48->null
                 * 拆分后：
                 *   a 链表（&8==0）：0->16->32->48->null   → 放入 newTable[i]
                 *   b 链表（&8!=0）：8->24->40->null       → 放入 newTable[i + 8]
                 */

                Entry a = null;        // a 链表的尾节点
                Entry b = null;        // b 链表的尾节点
                Entry aHead = null;    // a 链表的头节点
                Entry bHead = null;    // b 链表的头节点

                // 遍历当前桶的整个链表，按规则拆分
                while (p != null) {
                    // 判断当前节点应归属哪一组
                    if ((p.hash & table.length) == 0) {
                        // 属于 A 组
                        if (a != null) {
                            a.next = p; // 将 p 接到 a 链表尾部
                        } else {
                            aHead = p;  // 第一个节点作为 a 链表头
                        }
                        a = p; // 更新 a 指针，让它指向 p，方便下一次接新节点
                    } else {
                        // 属于 B 组（新索引 = i + table.length）
                        if (b != null) {
                            b.next = p; // 将 p 接到 b 链表尾部
                        } else {
                            bHead = p;  // 第一个节点作为 b 链表头
                        }
                        b = p; // 更新 b 为当前尾节点（分配到b）
                    }
                    p = p.next;
                }

                if (a != null) {
                    a.next = null;           // 断开 a 链表尾部，防止残留旧引用
                    newTable[i] = aHead;     // a 链表放入新数组的原索引位置
                }
                if (b != null) {
                    b.next = null;           // 断开 b 链表尾部
                    newTable[i + table.length] = bHead; // b 链表放入新数组的高位索引位置
                }
            }
        }
        table = newTable; // 新数组代替旧数组
        threshold = (int) (loadFactor * table.length); // 更新扩容阈值
    }

    /**
     * 根据哈希码删除
     *
     * @param hash 哈希码
     * @param key  键
     * @return     值
     */
    Object remove(int hash, Object key) {
        // 计算该哈希值在哈希表（table 数组）中的索引位置
        int index = hash & (table.length - 1);

        // 检查目标桶（index 位置）是否为空
        if (table[index] == null) {
            return null; // 桶为空，说明 key 一定不存在
        }

        // - p：当前正在检查的节点
        // - prev：p 的前驱节点（用于在删除时维护链表结构）
        Entry p = table[index];
        Entry prev = null;

        // 遍历该桶对应的链表，查找匹配的 key
        while (p != null) {
            if (p.key.equals(key)) {
                // 分两种情况更新链表指针：
                if (prev == null) {
                    // 情况一：p 是链表头节点（即第一个节点）
                    // 直接将 table[index] 指向 p 的下一个节点
                    table[index] = p.next;
                } else {
                    // 情况二：p 是中间或尾部节点
                    // 将前驱节点的 next 指向 p 的下一个节点，跳过 p
                    prev.next = p.next;
                }

                // 更新哈希表元素总数
                size--;

                // 返回被删除节点的值
                return p.value;
            }

            // 移动指针：prev 指向当前节点，p 指向下一个节点
            prev = p;
            p = p.next;
        }

        // 遍历完整个链表仍未找到匹配的 key，说明该键不存在
        return null;
    }
}
