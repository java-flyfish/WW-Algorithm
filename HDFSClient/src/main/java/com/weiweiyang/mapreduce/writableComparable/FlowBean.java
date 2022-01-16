package com.weiweiyang.mapreduce.writableComparable;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 1、定义类实现Writable接口
 * 2、重新序列号和反序列化接口
 * 3、重写空参构造器
 * 4、重写toString方法
 */
public class FlowBean implements WritableComparable<FlowBean> {

    //上行流量
    private Long upFlow;

    //下行流量
    private Long downFlow;

    //总流量
    private Long sumFlow;

    public FlowBean() {
    }

    public Long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow() {
        this.sumFlow = this.upFlow + this.downFlow;
    }

    /**
     * 序列化方法
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);

    }

    /**
     * 反序列化方法
     * 顺序很重要，需要跟序列化的顺序一致
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow = in.readLong();
        this.sumFlow = in.readLong();
    }

    @Override
    public String toString() {
        return this.upFlow + "\t" + this.downFlow + "\t" + this.sumFlow;
    }

    @Override
    public int compareTo(FlowBean o) {
        //总流量的倒序排序
        if (this.sumFlow > o.sumFlow){
            return -1;
        }else if (this.sumFlow < o.sumFlow){
            return 1;
        }else {
            return 0;
        }
    }
}
