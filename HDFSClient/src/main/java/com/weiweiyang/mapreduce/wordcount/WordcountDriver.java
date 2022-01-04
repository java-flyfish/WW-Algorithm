package com.weiweiyang.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordcountDriver  {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        //设置路径
        job.setJarByClass(WordcountDriver.class);
        //关联mapper和reduce
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReducer.class);

        //设置map输出的key-value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置最终输出的key-value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入和输出路径
//        FileInputFormat.setInputPaths(job,new Path("/Users/tong/javaProject/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/inputword"));
//        FileOutputFormat.setOutputPath(job,new Path("/Users/tong/javaProject/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/outputword"));
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0: 1);
    }
}
