package com.weiweiyang;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class  HdfsClient {
    @Test
    public void testMkdirs() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行
        // configuration.set("fs.defaultFS",
        //"hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop1:8020"), configuration, "weiweiyang");
        // 2 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan"));
        // 3 关闭资源
        fs.close();
    }
}
