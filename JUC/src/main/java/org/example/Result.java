package org.example;


public class Result<T> {

    private String str;

    private T data;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
