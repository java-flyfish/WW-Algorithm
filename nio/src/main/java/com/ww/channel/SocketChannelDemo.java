package com.ww.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //创建SocketChannel方式1
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        //创建SocketChannel方式2
        /*SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("www.baidu.com",80));*/
        //设置阻塞模式,false非阻塞模式
        sc.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(16);
        sc.read(buf);
        sc.close();
        System.out.println("读取完成");
    }
}
