package com.weiweiyang.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Combiner是在mapper阶段之后执行的，它实际上还是归属于mapper
 * 它是对mapper阶段的数据进行进一步处理，减轻reducer阶段的处理工作
 * 在这个wordCount案例中，在Combiner先进行一次单词数量的统计
 * 所以Combiner阶段的输入参数为mapper的输出参数，输出参数为reducer阶段的输入参数
 */
public class WordCountCombiner extends Reducer<Text, IntWritable,Text, IntWritable> {

    private IntWritable outV = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        outV.set(sum);
        context.write(key,outV);
    }
}
