package com.ww.sort;

import java.util.Arrays;

/**
 * 插入排序
 * 把左边当作有序列表，每次从右边取一个数，在左边找到相应的位置
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = new int[]{-3,3,9,-1,10,-2,20,30,40};
        for(int i=1; i<arr.length; i++){
            for (int j=i; j>0; j--){
                if (arr[j] < arr[j-1]){
                    int tmp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = tmp;
                }else {
                    break;
                }
            }
            System.out.println("第" + i + "轮：");
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(arr));
    }
}
