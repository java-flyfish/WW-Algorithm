package com.ww.chat.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientThread implements Runnable {
    private Selector selector;

    public ClientThread(Selector selector){
        this.selector = selector;
    }
    @Override
    public void run() {
        try {
            while (true){
                int select = selector.select();
                if (select == 0 ){
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

                    if (selectionKey.isReadable()){
                        readOperator(selectionKey,selector);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        System.out.println("ji：" + msg);
    }
}
