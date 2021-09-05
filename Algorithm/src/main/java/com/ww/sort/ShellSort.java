package com.ww.sort;

import java.util.Arrays;

/**
 * 希尔排序
 * 是插入排序的改进版本
 * 按长度/2进行分组，每次只排序分组的部分
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,-1,10,-2,20,30,40};

        int gap = arr.length/2;
        for (int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j+=gap){
                if (arr[j] < arr[j+gap]){
                    int tmp = arr[j];
                    arr[j] = arr[j+gap];
                    arr[j+gap] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

}
