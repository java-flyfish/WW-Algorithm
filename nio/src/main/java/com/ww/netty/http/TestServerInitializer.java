package com.ww.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * http服务器段
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec是netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加一个自定义的一个处理器
        pipeline.addLast("MyTestHttpServerHandler ",new TestHttpServerHandler());

    }
}
