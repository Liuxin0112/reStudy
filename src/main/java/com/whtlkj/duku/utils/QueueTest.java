package com.whtlkj.duku.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Auther: Linxxx
 * @Date: 2018/12/4 09:27
 * @Description:
 */
public class QueueTest {
    public static void main(String[] agrs){
        int[] input = {4,5,1,6,2,7,3,8};
        ArrayList<Integer> integers = GetLeastNumbers_Solution(input, 5);
        for (Integer i: integers){
            System.out.println(i);
        }
        assert input.length > 1 : "The length is more than 1!!!";
    }

    // 求数组中最小的k个数
    public static ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (k == 0 || k > input.length){
            return result;
        }
        // 构造最大堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        // 将数字与堆顶元素比较,如果数字小于堆顶元素,用数字将其替代
        for (int i=0;i<input.length;i++){
            if (maxHeap.size() != k){
                maxHeap.offer(input[i]);
            }else if (maxHeap.peek() > input[i]){
                maxHeap.poll();
                maxHeap.offer(input[i]);
            }
        }
        // 遍历堆中元素赋值给List
        for (Integer num: maxHeap){
            result.add(num);
        }
        return result;
    }
}
