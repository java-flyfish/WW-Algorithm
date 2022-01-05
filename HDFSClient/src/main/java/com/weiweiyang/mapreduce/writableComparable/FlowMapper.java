package com.weiweiyang.mapreduce.writableComparable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class FlowMapper extends Mapper<LongWritable, Text,FlowBean,Text> {

    private Text outV = new Text();
    FlowBean outK = new FlowBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行信息
        //13736230513  2481  24681  27162
        String line = value.toString();
        //切割
        String[] split = line.split("\t");
        //获取想要的数据
        String phone = split[0];
        String up = split[1];
        String down = split[2];

        outK.setUpFlow(Long.valueOf(up));
        outK.setDownFlow(Long.valueOf(down));
        outK.setSumFlow();

        outV.set(phone);
        //写出
        context.write(outK, outV);
    }
}
