package com.ww.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
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
                    .childHandler(new TestServerInitializer());

            ChannelFuture cf = serverSootstrap.bind(6969).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
