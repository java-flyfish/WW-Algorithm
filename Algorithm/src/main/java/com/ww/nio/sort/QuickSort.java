package com.ww.nio.sort;

import java.util.Arrays;

/**
 * https://www.cnblogs.com/nicaicai/p/12689403.html
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,9,-1,10,-2,-20,-20,30,-40};
//        int[] arr = new int[]{9,9};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr,int left, int right){
        System.out.println(Arrays.toString(arr) + ":" + left + ":" + right);
        if (left >= right){
            return;
        }
        int l = left;//左下标
        int r = right;//右下标
        //选择一个中位数，在一次循环中这个数是一个标准，不能变
        int pivot = arr[left];

        while (l < r){
            while (l < r && arr[r] >= pivot){
                r--;
            }
            //找到一个比pivot小当数，把它放到索引l当位置
            //因为第一次l当位置就是pivot，所以数据不会丢
            arr[l] = arr[r];
            //从左边找到一个比pivot大当数
            while (l < r && arr[l] < pivot){
                l++;
            }
            //放到索引r当位置
            arr[r] = arr[l];

            if (l==r){
                //第一遍遍历完成，并且l或者r为分割数组当一个中位，对两边当数据递归遍历
                arr[r] = pivot;
                break;
            }
        }
        //左边递归
        quickSort(arr,left,l-1);
        //右边递归
        quickSort(arr,l+1,right);

    }
}
