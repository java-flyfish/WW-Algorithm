package com.ww.chat.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天室服务器
 */
public class ChatServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        new ChatServer().startServer();
    }

    /**
     * 服务器启动
     */
    public void startServer() throws Exception {
        //1.获取服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //4.绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //5.获取选择器
        Selector selector = Selector.open();
        //6.通道注册到选择器进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器启动成功");

        //7.选择器进行轮询
        while (true){

            int select = selector.select();
            if (select == 0){
                continue;
            }
            //获取可用的seletor
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                //移除当前端SelectionKey
                selectionKeyIterator.remove();
                if (selectionKey.isAcceptable()){
                    accpectOperator(serverSocketChannel,selector);

                }
                if (selectionKey.isReadable()){
                    readOperator(selectionKey,selector);
                }
            }

        }

    }

    /**
     * 处理接入状态
     */
    private void accpectOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws Exception {
        //获取连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //切换非阻塞模式
        socketChannel.configureBlocking(false);
        //注册
        socketChannel.register(selector,SelectionKey.OP_READ);

        //回复客户端信息
        socketChannel.write(Charset.forName("UTF-8").encode("欢迎进入聊天室"));
    }

    /**
     * 处理接入状态
     */
    private void readOperator(SelectionKey selectionKey, Selector selector) throws Exception {
        //获取连接
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //循环读取客户端信息
        String msg = "";
        if (socketChannel.read(byteBuffer)> 0){
            //切换模式
            byteBuffer.flip();
            msg += Charset.forName("UTF-8").decode(byteBuffer);
        }
        //将chennel重新注册到选择器，监听可读状态
        socketChannel.register(selector,SelectionKey.OP_READ);
        //将消息广播到其他客户端
        System.out.println("要广播到消息：" + msg);

        castOtherClient(msg,selector,socketChannel);
    }

    /**
     * 广播给其他客户端
     */
    private void castOtherClient(String msg, Selector selector, SocketChannel socketChannel) throws Exception {

        //获取所有已经接入到客户端
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        for (SelectionKey selectionKey : selectionKeys) {
            Channel tarChannel = selectionKey.channel();
            if (tarChannel instanceof SocketChannel && tarChannel != socketChannel){
                //往其他客户端发送消息
                ((SocketChannel) tarChannel).write(Charset.forName("UTF-8").encode(msg));
            }
        }
    }
}
