package com.whtlkj.duku.utils;

import java.util.Stack;

/**
 * @Auther: Linxxx
 * @Date: 2018/11/8 16:18
 * @Description: 使用两个栈实现一个队列
 */
public class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.empty()){
            while (!stack1.empty())
                stack2.push(stack1.pop());
        }
        return stack2.pop();
    }

    // 测试
    public static void main(String[] args){
        // 1 2 3 4 5
        Solution s = new Solution();
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println(s.pop());
        System.out.println(s.pop());
        s.push(4);
        System.out.println(s.pop());
        s.push(5);
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
