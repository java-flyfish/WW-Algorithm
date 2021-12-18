package com.ww.nio.sort;

import java.util.Arrays;

public class MergetSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,9,-1,10,-2,-20,-20,30,-40};
        int[] temp = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 分+合
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp){
        if (left < right){
            int mid = (left + right)/2;
            //向左拆分
            mergeSort(arr,left,mid,temp);
            //向右拆分
            mergeSort(arr,mid+1,right,temp);
            //合并
            merge(arr,left,mid,right,temp);
        }
    }
    /**
     * 合并
     * @param arr 原始数组,1,2,3
     * @param left 左边索引
     * @param mid 中间索引
     * @param right 右边索引
     * @param temp 临时数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp){
        int i = left;
        int j = mid + 1;
        int t = 0;
        //先把左右两边当数据按照规则填充到临时数组
        while (i<=mid && j<=right){
            if(arr[i] <= arr[j]){
                temp[t] = arr[i];
                i++;
                t++;
            } else {
                temp[t] = arr[j];
                j++;
                t++;
            }
        }

        //把剩下当数据复制到临时数组
        while (i<=mid){
            temp[t] = arr[i];
            t++;
            i++;
        }
        while (j<=right){
            temp[t] = arr[j];
            t++;
            j++;
        }
        //把临时数组的数据复制到原始数组
        int tempt = 0;
        int tempLeft = left;
        while(tempLeft <= right){
            arr[tempLeft] = temp[tempt];
            tempLeft ++;
            tempt ++;
        }
    }
}
