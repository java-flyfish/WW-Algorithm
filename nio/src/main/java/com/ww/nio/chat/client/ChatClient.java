package com.ww.nio.chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 聊天客户端
 */
public class ChatClient {




    public void startClient(String name) throws IOException {
        //连接服务器端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

        //接收服务端端消息
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        new Thread(new ClientThread(selector)).start();
        //发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String msg = scanner.nextLine();
            if (msg != null && msg.length() > 0){
                socketChannel.write(Charset.forName("UTF-8").encode(name + ": " + msg));
            }
        }
        //接收响应消息
    }
}
