package com.ww.nio.selector;

import com.sun.org.apache.bcel.internal.generic.Select;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class SelectoeDemo1 {

    /**
     * 服务端
     */
    @Test
    public void serverDemo() throws Exception {
        //1.获取服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //3.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //4.绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //5.获取选择器
        Selector selector = Selector.open();

        //6.通道注册到选择器进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //7.选择器进行轮询
        while (selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                if (selectionKey.isAcceptable()){
                    //接受就绪状态
                    //获取连接
                    SocketChannel accept = serverSocketChannel.accept();
                    //切换非阻塞模式
                    accept.configureBlocking(false);
                    //注册
                    accept.register(selector,SelectionKey.OP_READ);

                }else if (selectionKey.isReadable()){
                    //是否可读状态
                    SocketChannel channel = (SocketChannel)selectionKey.channel();

                    int length = 0;
                    while ((length = channel.read(byteBuffer))> 0){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,length));
                        byteBuffer.clear();
                    }
                }
            }
            selectionKeyIterator.remove();
        }


    }

    /**
     * 客户端
     */
    @Test
    public void clientDemo() throws Exception {

        //1.获取通道，绑定诸暨端口
        SocketChannel socketChannel = SocketChannel.open (new InetSocketAddress("127.0.0.1",8080));

        //2.切换通道为非阻塞模式
        socketChannel.configureBlocking(false);

        //3.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //4.向buffer中写入数据
        byteBuffer.put(new Date().toString().getBytes());

        //5.切换读模式
        byteBuffer.flip();

        //6.写入通道
        socketChannel.write(byteBuffer);

        //7.清空buffer
        byteBuffer.clear();
    }
}
