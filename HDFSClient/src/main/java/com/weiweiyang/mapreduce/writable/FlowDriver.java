package com.weiweiyang.mapreduce.writable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        //设置路径
        job.setJarByClass(FlowDriver.class);
        //关联mapper和reduce
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //设置map输出的key-value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //设置最终输出的key-value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置自定义分区实现
        job.setPartitionerClass(ProvincePartitioner.class);
        //设置NumReduceTasks个数，这个个数必须和ProvincePartitioner的分区个数一致
        job.setNumReduceTasks(5);
        //设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path("/Users/tongweiyang/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/inputflow"));
        FileOutputFormat.setOutputPath(job,new Path("/Users/tongweiyang/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/outflow"));
        //提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0: 1);
    }
}
