package com.ww.chat.client;

import java.io.IOException;

public class AClient {
    public static void main(String[] args) throws IOException {
        new ChatClient().startClient("张三");
    }
}
