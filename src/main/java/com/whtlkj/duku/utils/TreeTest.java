package com.whtlkj.duku.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Linxxx
 * @Date: 2018/11/9 17:56
 * @Description:
 */
public class TreeTest {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        TreeNode tree = new TreeNode(10);
        TreeNode tree1 = new TreeNode(5);
        TreeNode tree2 = new TreeNode(12);
        tree.left = tree1;
        tree.right = tree2;
        tree1.left = new TreeNode(4);
        tree1.right = new TreeNode(7);
//        tree2.left = new TreeNode(6);
//        tree2.right = new TreeNode(7);
        FindPath(tree,15);
    }

    // 打印二叉树所有和为target的路径
    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> pathList = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        FindSubPath(root, target, path, pathList);
        return pathList;
    }

    private static void FindSubPath(TreeNode root, int target, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> pathList) {
        if (root == null)
            return;
        path.add(root.val);
        if (root.val == target) {
            ArrayList<Integer> temp = (ArrayList<Integer>) path.clone();
            pathList.add(temp);
            return;
        }
        if (root.val > target) {
            return;
        }
        if (root.val < target) {
            if (root.left != null) {
                FindSubPath(root.left, target - root.val, path, pathList);
                path.remove(path.size()-1);
            }
            if (root.right != null) {
                FindSubPath(root.right, target - root.val, path, pathList);
                path.remove(path.size()-1);
            }
        }
    }

    // 镜像二叉树
    public static void Mirror(TreeNode root) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        // 交换左右子节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        // 向下遍历
        Mirror(root.left);
        Mirror(root.right);
    }

    // 判断root2是否是root1的子树
    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null || root1 == null)
            return false;
        // 根节点比较
        if (isSubtree(root1, root2))
            return true;
        // 分别向左右子树查找
        return HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    // 比较两棵树是否相同
    private static boolean isSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null)
            return true;
        if (root1 == null)
            return false;
        if (root1.val != root2.val)
            return false;
        return isSubtree(root1.left, root2.left) && isSubtree(root1.right, root2.right);
    }

    private static List<TreeNode> preIterator(TreeNode node) {

        List<TreeNode> list = new ArrayList<TreeNode>();
        // 处理根节点
        list.add(node);
        System.out.println(node.val);

        // 递归处理左子树
        if (node.left != null) {
            list.addAll(preIterator(node.left));
        }

        // 递归处理右子树
        if (node.right != null) {
            list.addAll(preIterator(node.right));
        }

        return list;

    }
}
