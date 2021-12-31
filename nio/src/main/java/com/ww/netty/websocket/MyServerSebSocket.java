package com.ww.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServerSebSocket {

    public static void main(String[] args)  throws InterruptedException{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //服务器端端启动对象
            ServerBootstrap serverSootstrap = new ServerBootstrap();
            //设置相关参数
            serverSootstrap
                    //设置工作线程组
                    .group(bossGroup, workGroup)
                    //设置通道实现，客户端是NioSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到端连接数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动链接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))//对bossGroup生效，childHandler():对workGroup生效
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                           //使用http的编码/解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以块的方式写，添加一个ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 说明
                             * 1。http的数据在传输过中是分段的，
                             * 2。这就是为什么浏览器在发送大量数据时，会发送多次http请求
                             * 3。HttpObjectAggregator可以将多个段聚合起来
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1。对于webSocket数据是以帧的形式传递的
                             * 2。WebSocketFrame有6个子类
                             * 3。浏览器发送请求时ws://localhost:6668/xxx
                             * 4.WebSocketServerProtocolHandler用来处理请求，并且能将http协议升级为ws协议，才能保持长链接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义的handler，处理业务请求
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            System.out.println("server is ready.......");
            ChannelFuture cf = serverSootstrap.bind(8000).sync();
            //通过ChannelFuture可以得到channel
            //channel是netty用来进行网络通信对组件
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
