package com.ww.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HeartServer {

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
                            /**
                             * IdleStateHandler是netty提供端空闲状态处理器
                             * 读-客户端往服务端发消息
                             * 写-服务端往客户端写消息
                             * long readerIdleTime, 表示多长时间没读就会发送一个心跳
                             * long writerIdleTime, 表示多长时间没写数据就会发送一个心跳
                             * long allIdleTime, 表示多长时间即没有读也没有写就会发送一个心跳包
                             * 会触发一个IdleStateEvent事件，类型看具体情况
                             * IdleStateEvent触发后就会传递给管道（pipeline）对下一个handler去处理，调用下一个handler对userEventTiggered
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个对空闲检测自定义对handler
                            pipeline.addLast(new HeartServerHandler());
                        }
                    });
            System.out.println("server is ready.......");
            ChannelFuture cf = serverSootstrap.bind(6668).sync();
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
