package com.weiweiyang.mapreduce.writable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区实现
 * 范型为mapper的输出类型
 * 该案例按照手机号前三位分区，136～139每个段一个分区，其他号段一个分区
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        String phone = text.toString();
        String prePhone = phone.substring(0,3);
        int partition = 0;
        if ("136".equals(prePhone)){
            partition = 0;
        }else if ("137".equals(prePhone)){
            partition = 1;
        }else if ("138".equals(prePhone)){
            partition = 2;
        }else if ("139".equals(prePhone)){
            partition = 3;
        }else {
            partition = 4;
        }
        return partition;
    }
}
