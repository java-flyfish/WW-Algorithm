package com.ww.nio.search.sort;

import java.util.Arrays;

/**
 * 堆排序
 */
public class HeapSort {

    public static void main(String[] args) {
        //数组升序排列
        int[] arr = {4,6,8,5,9,-1,45,56,8,23,45};
        heapSort(arr);
    }

    //编写一个堆排序方法
    public static void heapSort(int[] arr){
        int temp;
        /*System.out.println("堆排序！");
        adjustHeap(arr,1,arr.length);
        System.out.println("第一次： " + Arrays.toString(arr));
        adjustHeap(arr,0,arr.length);
        System.out.println("第二次： " + Arrays.toString(arr));*/

        //第一次现将数组调整成一个大顶堆
        for (int i=arr.length/2-1; i>=0; i--){
            adjustHeap(arr,i,arr.length);
        }
        System.out.println("调整大顶堆： " + Arrays.toString(arr));

        for (int j=arr.length-1; j>0; j--){
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr,0,j);
        }
        System.out.println("排序结果： " + Arrays.toString(arr));
    }

    /**
     * 完成将以i为非叶子结点的树调整成大顶堆
     * @param arr 数组
     * @param i 非叶子结点索引
     * @param length 表示多少个元素继续调整，length逐渐减少
     */
    public static void adjustHeap(int[] arr, int i, int length){
        int temp = arr[i];
        for (int k = i*2+1; k<length; k=k*2+1){
            //两个叶子节点比较，将索引k指向较大的节点
            if (k+1<length && arr[k] < arr[k+1]) {
                k++;
            }
            if (temp < arr[k]){
                //如果父节点比子节点小，将子节点上移
                arr[i] = arr[k];
                //将当前节点的索引指向所在位置节点
                i = k;
            }else {
                /**
                 * 因为遍历是从左到右，从上到下的，
                 * 所以最开始遍历的肯定是最左变的父节点
                 * 这样的话只要父节点比子节点大，子节点下面的子节点就不可能比自己更大
                 */
                break;
            }
        }
        //循环跳出以后，将一动过的子节点更新成父节点的值
        arr[i] = temp;
    }
}
