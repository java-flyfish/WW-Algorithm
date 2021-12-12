package com.ww.stack;

/**
 * 数组模拟的栈
 */
public class ArrayStack{
    //栈的大小
    private final int maxSize;
    //数组
    private final int[] stack;
    //top表示栈顶，初始化-1
    private int top = -1;

    //构造器
    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //栈满
    public boolean isFull(){
        return top == maxSize -1;
    }

    //栈空
    public boolean isEmpty(){
        return top == -1;
    }

    //入栈
    public void push(int value){
        if (isFull()){
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈，将栈顶的数据返回
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈为空！");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //返回栈顶的值
    public int pick(){
        if (isEmpty()){
            throw new RuntimeException("栈为空！");
        }
        return stack[top];
    }

    //显示栈中的信息
    public void list(){
        if (isEmpty()){
            throw new RuntimeException("栈为空！");
        }
        for (int i=top; i>=0; i--){
            System.out.printf("stack[%d] = %d\n",i,stack[i]);
        }
    }
}
