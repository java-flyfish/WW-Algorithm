package com.weiweiyang.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * KEYIN, Reducer阶段输入的key的类型：IntWritable
 * VALUEIN, Reducer阶段输入的value类型：Text
 * KEYOUT, Reducer阶段输出的key的类型：Text
 * VALUEOUT, Reducer阶段输出的value类型：IntWritable
 */
public class WordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        context.write(key,new IntWritable(sum));
    }
}
