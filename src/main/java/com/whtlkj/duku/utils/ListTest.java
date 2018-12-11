package com.whtlkj.duku.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Auther: Linxxx
 * @Date: 2018/11/8 15:01
 * @Description:
 */
public class ListTest {
    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
        @Override
        public String toString(){
            return this.val+"";
        }

        public void print(){
            System.out.print(val+" ");
            ListNode n = next;
            while (n != null){
                System.out.print(n.val+" ");
                n = n.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode h = node1;
        int i = 3;
        while (i<10) {
            h.next = new ListNode(i);
            i += 2;
            h = h.next;
        }
        h = node2;
        int j = 4;
        while (j<10) {
            h.next = new ListNode(j);
            j += 2;
            h = h.next;
        }
        node1.print();
        node2.print();
        ListNode reList = Merge(node1,node2);
        reList.print();
    }


    // 合并两个单调递增链表
    public static ListNode Merge(ListNode list1,ListNode list2) {
        ListNode headNode;
        if(list1.val < list2.val){
            headNode = new ListNode(list1.val);
            list1 = list1.next;
        }else{
            headNode = new ListNode(list2.val);
            list2 = list2.next;
        }
        ListNode curr = headNode;
        while(list1 != null && list2 != null){
            if(list1.val < list2.val){
                curr.next = new ListNode(list1.val);
                curr = curr.next;
                list1 = list1.next;
            }
            else{
                curr.next = new ListNode(list2.val);
                curr = curr.next;
                list2 = list2.next;
            }
        }
        if (list1 != null)
            curr.next = list1;
        if (list2 != null)
            curr.next = list2;
        return headNode;
    }

    // 反转链表,输出新链表的表头
    public static ListNode ReverseList(ListNode head) {
        // 1. 递归
//        if (head == null || head.next == null) {
//            return head;
//        } else {
//            ListNode headNode = ReverseList(head.next);
//            head.next.next = head;
//            head.next = null;
//            return headNode;
//        }
        // 2. 非递归
        ListNode currentNode = head;
        ListNode pNode = null;
        ListNode headNode = null;
        while(currentNode != null){
            ListNode nextNode = currentNode.next;
            if(nextNode == null)
                headNode = currentNode;
            currentNode.next = pNode;
            pNode = currentNode;
            currentNode = nextNode;
        }
        return headNode;
    }

    // 获取链表倒数第K个节点
    public static ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k <= 0)
            return null;
        ListNode p=head,q = head;
        int i = 0;
        while(p.next != null){
            if (i++ >= k-1)
                q = q.next;
            p = p.next;
        }
        if (k-1 > i)
            return null;
        else
            return q;
    }

    // 将一个数组的奇数全部放左边,偶数全部放右边,且不打乱其相对顺序
    public static void reOrderArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (array[j] % 2 == 0 && array[j + 1] % 2 == 1) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag)
                break;
        }
    }

    // 给出一个整数的二进制数有多少个1 (负数使用其补码)
    public static int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    // 跳台阶 每次可以跳n阶
    public static int JumpFloorII(int target) {
        // F(n) = F(n-1)+...+F(2)+F(1)+F(0)
        if (target <= 2)
            return target;
        return 2 * JumpFloorII(target - 1);
    }

    public static String replaceSpace(StringBuffer str) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(16);
        return str.toString().replaceAll(" ", "%20");
    }
}
