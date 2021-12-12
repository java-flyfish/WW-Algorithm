package com.ww.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 启动后浏览器访问localhost:8888端口即可
 */
public class ServerSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //端口号
        int port = 8888;
        //buffer
        ByteBuffer buf = ByteBuffer.wrap("hello wwy".getBytes());

        //ServerSocketChannel
        ServerSocketChannel ssc =ServerSocketChannel.open();

        //绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式,false非阻塞，true阻塞
        ssc.configureBlocking(false);

        //监听是否有新读连接传入
        while (true){
            SocketChannel sc = ssc.accept();
            if (sc == null){
                System.out.println("没有新的连接传入");
                Thread.sleep(2000);
            }else {
                System.out.println("有一个连接传入：" + sc.socket().getRemoteSocketAddress());
                buf.rewind();//指向0
                sc.write(buf);
                sc.close();
            }
        }
    }
}
