package com.ww.nio.search;

/**
 * 二分查找
 * 数组必须有序
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = new int[]{-40, -20, -3, -2, -1, 3, 9, 10, 30};
        int resultIndex = binarySearch(arr, 0, arr.length - 1, -30);
        System.out.println("找到的所索引位置：" + resultIndex);
    }

    public static int binarySearch(int[] arr,int left,int right,int findVal){
        if (left > right){
            return -1;
        }
        int mid = (left + right)/2;
        if (arr[mid] > findVal){
            //向左递归
            return binarySearch(arr,left,mid-1, findVal);
        } else if (arr[mid] < findVal){
            //向右递归
            return binarySearch(arr,mid+1,right, findVal);
        } else {
            return mid;
        }
    }
}
