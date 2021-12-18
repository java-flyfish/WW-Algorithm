package com.ww.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //两个线程组，bossGroup只处理连接请求，workGroup处理工作请求
        //两个都是无限循环
        //两个含有的自线程个数（NioEventLoop）默认为实际CPU核心数*2
        //DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            //服务器端端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置相关参数
            bootstrap
                    //设置工作线程组
                    .group(bossGroup, workGroup)
                    //设置通道实现，客户端是NioSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到端连接数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动链接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给workGroup端EventLoop设置对应端处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //向workGroup关联的pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("server is ready.......");
            //绑定一个端口，设置同步方式
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
