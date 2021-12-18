package com.ww.nio.search.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 霍夫曼树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3,29,6,1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    public static void preOrder(Node node){
        if (node != null){
            node.preOrder();
        }else {
            System.out.println("树为空");
        }
    }

    public static Node createHuffmanTree(int[] arr){
        //遍历所有数组，将
        List<Node> nodes = new ArrayList<>();
        for (int i=0; i<arr.length; i++){
            nodes.add(new Node(arr[i]));
        }
        Collections.sort(nodes);

        while(nodes.size()>1){
            Node left = nodes.get(0);
            Node right = nodes.get(1);

            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
            nodes.remove(left);
            nodes.remove(right);
        }

        System.out.println(nodes);
        return nodes.get(0);
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

    public void preOrder(){
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }


    @Override
    public String toString() {
        return "Node[ value=" + value + ']';
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排序
        return this.value - o.value;
    }
}