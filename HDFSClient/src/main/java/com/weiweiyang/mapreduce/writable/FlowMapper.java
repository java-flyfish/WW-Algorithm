package com.weiweiyang.mapreduce.writable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class FlowMapper extends Mapper<LongWritable, Text,Text, FlowBean> {

    private Text outK = new Text();
    FlowBean outV = new FlowBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行信息
        //1        13736230513      192.196.100.1      www.atquigu.com      2481  24681     200
        //2        13846544121      192.196.100.2                           264   0         200
        String line = value.toString();
        //切割
        String[] split = line.split("\\s+");
        //获取想要的数据
        int length = split.length;
        String phone = split[1];
        String up = split[length - 3];
        String down = split[length - 2];
        outK.set(phone);

        outV.setUpFlow(Long.valueOf(up));
        outV.setDownFlow(Long.valueOf(down));
        outV.setSumFlow();
        //写出
        context.write(outK, outV);
    }
}
