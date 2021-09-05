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

        int gap = arr.length;
        while((gap = gap/2)>0){
            for (int i=0; i<gap;i++){
                System.out.println(Arrays.toString(arr));
                sort(arr, i, gap);
                System.out.println(Arrays.toString(arr));
                System.out.println("===");
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr,int first,int gap){
        for(int i=first+gap; i<arr.length; i+=gap){
            for (int j=i-gap; j>=0; j-=gap){
                if (arr[j+gap] < arr[j]){
                    int tmp = arr[j];
                    arr[j] = arr[j+gap];
                    arr[j+gap] = tmp;
                }else {
                    break;
                }
            }
        }
    }
}
