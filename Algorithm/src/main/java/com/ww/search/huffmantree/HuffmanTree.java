package com.ww.search.huffmantree;

/**
 * 霍夫曼树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13,7,8,3,29,6,1};

    }

    public static void createHuffmanTree(int[] arr){

    }
}


/**
 * 节点
 */
class Node implements Comparable<Node>{
    //值
    int value;
    //左子节点
    Node left;
    //右子节点
    Node right;

    public Node(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node[ value=" + value + ']';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}