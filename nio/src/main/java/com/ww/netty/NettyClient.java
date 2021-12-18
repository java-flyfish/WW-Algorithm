package com.ww.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //客户端使用Bootstrap而不是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap
                    .group(eventExecutors)
                    //设置通道实现,服务器端是NioServerSocketChannel
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //向eventExecutors关联的pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ready......");
            //启动客户端连接服务器端
            //关于ChannelFuture的分析，要涉及到netty的一步模型
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }


}
