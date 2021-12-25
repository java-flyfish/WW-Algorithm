package com.ww.netty.groupchat2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 群聊服务端
 */
public class GroupChatServer {
    /**
     * 监听端口
     */
    private final int port;

    public GroupChatServer(int port){
        this.port = port;
    }

    public void run()  throws InterruptedException{
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
                    //handler():对bossGroup生效，childHandler():对workGroup生效
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己对业务处理器
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("server is ready.......");
            ChannelFuture cf = serverSootstrap.bind(port).sync();
            //通过ChannelFuture可以得到channel
            //channel是netty用来进行网络通信对组件
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(6668).run();
    }
}
