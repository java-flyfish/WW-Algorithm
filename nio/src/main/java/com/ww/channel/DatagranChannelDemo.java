package com.ww.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class DatagranChannelDemo {

    public static void main(String[] args) {

    }

    //发送包读实现
    @Test
    public void sendDatagram() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);

        while (true){
            ByteBuffer buf = ByteBuffer.wrap("微微扬".getBytes("UTF-8"));
            channel.send(buf,sendAddress);
            System.out.println("完成发送");
            Thread.sleep(2000);
        }
    }

    //接收读实现
    @Test
    public void rceiveDatagram() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(9999));

        ByteBuffer buf = ByteBuffer.allocate(1024);

        //接收
        while (true){
            buf.clear();
            channel.receive(buf);
            //模式转换
            buf.flip();
            System.out.println(Charset.forName("UTF-8").decode(buf));
        }
    }
}
