package com.whtlkj.duku.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Auther: Linxxx
 * @Date: 2018/11/13 10:35
 * @Description:
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

//        test1(-5);
        test2(-3);
    }

    private static void test1(int a){
        assert a > 0;
        System.out.println(a);
    }
    private static void test2(int a){
        assert a > 0 : "something goes wrong here, a cannot be less than 0";
        System.out.println(a);
    }

    // 寻找字符串中第一个只出现一次的字符,返回它所在的位置
    public static int FirstNotRepeatingChar(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        int[] letter = new int[58];
        int[] index = new int[58];
        for (int i = 0; i < str.length(); i++) {
            int x = str.charAt(i) - 'A';
            arrayList.add(String.valueOf(str.charAt(i)));
            letter[x]++;
            if (index[x] == 0) {
                index[x] = i;
            }
        }
        for (String num : arrayList) {
            if (letter[num.charAt(0) - 'A'] == 1) {
                return index[num.charAt(0) - 'A'];
            }
        }
        return -1;
    }

    // 寻找第index个丑数(质因子只有2,3,5)
    public static int GetUglyNumber_Solution(int index) {
        if (index < 7)
            return index;
        int q1 = 0, q2 = 0, q3 = 0;
        ArrayList<Integer> array = new ArrayList<Integer>();
        array.add(1);
        while (array.size() < index) {
            int min = Math.min(array.get(q1) * 2, Math.min(array.get(q2) * 3, array.get(q3) * 5));
            array.add(min);
            if (min == array.get(q1) * 2)
                q1++;
            if (min == array.get(q2) * 3)
                q2++;
            if (min == array.get(q3) * 5)
                q3++;
        }
        return array.get(index - 1);
    }

    // 将数组中所有的数字拼接成最小数字
    public static String PrintMinNumber(int[] numbers) {
        for (int i = numbers.length; i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                compareNum(numbers, j, j + 1);
            }
        }
        String s = "";
        for (int n : numbers) {
            s += n;
        }
        return s;
    }

    public static int[] compareNum(int[] array, int a, int b) {
        String s1 = String.valueOf(array[a]) + String.valueOf(array[b]);
        String s2 = String.valueOf(array[b]) + String.valueOf(array[a]);
        if (s1.compareTo(s2) > 0) {
            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }
        return array;
    }

    // 连续子数组最大和
    public static int FindGreatestSumOfSubArray(int[] array) {
        if (array.length == 1) {
            return array[0];
        }
        int max = array[0];
        for (int i = 1; i <= array.length; i++) {
            for (int j = 0; j <= array.length - i; j++) {
                int num = 0;
                for (int x = j; x < j + i; x++) {
                    num += array[x];
                }
                if (max < num) {
                    max = num;
                }
            }
        }
        return max;
    }

    public static int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0)
            return 0;
        if (array.length == 1)
            return array[0];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                map.put(array[i], map.get(array[i]) + 1);
            } else {
                map.put(array[i], 1);
            }
        }
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > array.length / 2)
                return entry.getKey();
        }
        return 0;
    }

    // 字符串的全排列
    private static Stack<String> strStack = new Stack<String>();

    public static ArrayList<String> Permutation(String str) {
        if (str.equals(""))
            return null;
        // 遍历
        strStack.push(str);
        for (int i = 0; i < str.length() - 1; i++) {
            Stack<String> tempStack = (Stack<String>) strStack.clone();
            strStack.clear();
            while (!tempStack.empty()) {
                sortAll(tempStack.pop(), i);
            }
        }
        // 传入List
        Set<String> set = new HashSet<>();
        while (!strStack.empty()) {
            set.add(strStack.pop());
        }
        return new ArrayList<>(set);
    }

    private static void sortAll(String str, int index) {
        if (str.length() < 2)
            return;
//        Stack<String> stack = new Stack<String>();
        strStack.push(str);
        String temp = str.substring(index, index + 1);
        for (int i = index + 1; i < str.length(); i++) {
            StringBuffer sb = new StringBuffer(str);
            sb.replace(index, index + 1, str.substring(i, i + 1));
            sb.replace(i, i + 1, temp);
            strStack.push(sb.toString());
        }
//        strStack = stack;
        //return stack;
    }

    // 输入两个整数序列，第一个序列表示栈的压入顺序，判断第二个序列是否可能为该栈的弹出顺序
    public static boolean IsPopOrder(int[] pushA, int[] popA) {

        return false;
    }

    // 顺时针输出数组矩阵
    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (matrix.length == 0)
            return list;
        do {
            matrix = reversalMatrix(matrix, list);
        } while (matrix != null);
        return list;
    }

    public static int[][] reversalMatrix(int[][] matrix, ArrayList<Integer> list) {
        // 读取第一行
        for (int i = 0; i < matrix[0].length; i++) {
            list.add(matrix[0][i]);
        }
        // 从下一行开始翻转数组
        int x = matrix[0].length;
        int y = matrix.length;
        if (x == 1 && y == 2) {
            list.add(matrix[1][0]);
            return null;
        } else if (y == 1) {
            return null;
        }
        int a = 0, b = 0;
        int[][] subMatrix = new int[x][y - 1];
        for (int i = x - 1; i >= 0; i--) {
            for (int j = 1; j < y; j++) {
                subMatrix[a][b++] = matrix[j][i];
            }
            a++;
            b = 0;
        }
        return subMatrix;
    }
}
