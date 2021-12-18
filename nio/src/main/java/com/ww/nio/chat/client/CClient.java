package com.ww.nio.chat.client;

import java.io.IOException;

public class CClient {

    public static void main(String[] args) throws IOException {
        new ChatClient().startClient("王武");
    }
}
