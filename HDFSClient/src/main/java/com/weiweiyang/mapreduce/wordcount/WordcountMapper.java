package com.weiweiyang.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN, map阶段输入的key的类型：LongWritable
 * VALUEIN, map阶段输入的value类型：Text
 * KEYOUT, map阶段输出的key的类型：Text
 * VALUEOUT, map阶段输出的value类型：IntWritable
 */
public class WordcountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    private Text outK = new Text();
    private IntWritable outV = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取每一行数据
        //weiweiyang weiweiyang
        String line = value.toString();
        //切割单词
        //weiweiyang
        //weiweiyang
        String[] words = line.split(" ");
        //循环写出
        for (String word : words) {
            outK.set(word);
            context.write(outK,outV);
        }
    }
}
