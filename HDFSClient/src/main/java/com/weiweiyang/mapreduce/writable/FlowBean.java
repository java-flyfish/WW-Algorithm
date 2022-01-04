package com.weiweiyang.mapreduce.writable;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 1、定义类实现Writable接口
 * 2、重新序列号和反序列化接口
 * 3、重写空参构造器
 * 4、重写toString方法
 */
public class FlowBean implements Writable {

    public FlowBean() {
    }

    /**
     * 序列化方法
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    /**
     * 反序列化方法
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }
}
