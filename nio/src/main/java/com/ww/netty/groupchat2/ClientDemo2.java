package com.ww.netty.groupchat2;

public class ClientDemo2 {
    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 6668).run();
    }
}
