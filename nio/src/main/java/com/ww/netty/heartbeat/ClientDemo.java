package com.ww.netty.heartbeat;

public class ClientDemo {
    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 6668).run();
    }
}
