package com.weiweiyang.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 自定义的结果输出方式
 * 范型为Reducer输出的类型
 */
public class LogOutPutFormat extends FileOutputFormat<Text, NullWritable> {
    /**
     * 需要返回一个RecordWriter
     * job：任务,实际上就是Driver中的job
     */
    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        LogRecordWriter lrw = new LogRecordWriter(job);
        return lrw;
    }
}
