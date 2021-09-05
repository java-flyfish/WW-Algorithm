package com.ww.sort;

import java.util.Arrays;

/**
 * 选择排序
 * 每次循环找到一个最小的数放到最左边
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,-1,10,-2,-20,30,-40};

        for (int i=0; i<arr.length-1; i++){
            int min = arr[i];
            int index = i;
            //找到最小的数和最小数的下标
            for (int j=i+1; j<arr.length; j++){
                if (arr[j] < min){
                    min = arr[j];
                    index = j;
                }
            }
            //交换
            if (index != i){
                arr[index] = arr[i];
                arr[i] = min;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
