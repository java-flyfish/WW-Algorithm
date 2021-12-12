package com.ww.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel读取数据到buffer中
 * rw读写模式
 */
public class FileChannelDemo1 {
    public static void main( String[] args ) throws Exception {
        //创建FileChannel
        RandomAccessFile aFile = new RandomAccessFile("/Users/tongweiyang/IdeaProjects/WW-Algorithm/nioFile/FileChannelDemo1.txt","rw");
        FileChannel channel = aFile.getChannel();
        write(channel,"hahaha");
        //read(channel);

        channel.close();
        aFile.close();
    }

    /**
     * 读
     */
    public static void read(FileChannel channel) throws IOException {
        //创建buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //读取数据到buffer,读到最后了，read会返回-1
        int read = channel.read(buf);
        while (read != -1){
            System.out.println("读到了：" + read);
            //读写模式转换
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char)buf.get());
            }
            buf.clear();
            read = channel.read(buf);
        }

        System.out.println("操作结束");
    }

    /**
     * 写
     */
    public static void write(FileChannel channel,String str) throws IOException {
        //创建buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());
        //读写模式转换
        buf.flip();
        while (buf.hasRemaining()){
            channel.write(buf);
        }
        System.out.println("操作结束");
    }

}
