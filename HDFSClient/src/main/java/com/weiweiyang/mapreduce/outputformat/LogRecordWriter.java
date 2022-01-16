package com.weiweiyang.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    //两个输出流
    private FSDataOutputStream out1;
    private FSDataOutputStream out2;

    public LogRecordWriter(TaskAttemptContext job) {
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            out1 = fs.create(new Path("/Users/tongweiyang/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/outLog1"));
            out2 = fs.create(new Path("/Users/tongweiyang/IdeaProjects/WW-Algorithm/HDFSClient/src/main/resources/outLog2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个才是真正的往外写出数据的方法
     * 在这个案例中是创建两个流，输出结果到不同文件
     * 在其他需求中，可以写入到数据库，ES等其他地方
     */
    @Override
    public void write(Text text, NullWritable nullWritable) throws IOException, InterruptedException {
        //往外面写数据
        out1.writeBytes(text.toString());
        out2.writeBytes(text.toString());
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(out1);
        IOUtils.closeStream(out2);
    }
}
