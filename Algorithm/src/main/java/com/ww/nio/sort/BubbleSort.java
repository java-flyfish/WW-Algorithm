package com.ww.nio.sort;

import java.util.Arrays;


/**
 * 冒泡排序
 * 从做到右依次比较两个相邻的元素，如果左边比右边大，就交换相邻为止的值
 * 交换过后右边就是最大的
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,-1,10,-2,20,30,40};
        int[] sortArr = sort(arr);
        System.out.println(Arrays.toString(sortArr));
    }

    private static int[] sort(int[] arr) {
        boolean flag = true;
        for (int j = arr.length-1; j> 0; j--){
            flag = true;
            for (int i=0; i<j; i++){
                if (arr[i] > arr[i+1]){
                    //如果前面的数比后面的数大，进行交换
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    flag = false;
                }
            }
            System.out.println("第" + (arr.length - j) + "趟排序：");
            System.out.println(Arrays.toString(arr));
            if (flag){
                break;
            }
        }
        return arr;
    }
}
