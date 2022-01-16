package com.ww.netty.heartbeat;

import com.ww.netty.groupchat2.GroupChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 群聊客户端
 */
public class GroupChatClient {

    private final String host;
    private final int port;

    public GroupChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run()  throws InterruptedException{

        //客户端只需要一个循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //服务器端端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap
                    //设置工作线程组
                    .group(group)
                    //设置通道实现，客户端是NioSocketChannel
                    .channel(NioSocketChannel.class)
                    //设置线程队列得到端连接数
                    //handler():对bossGroup生效，childHandler():对workGroup生效
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己对业务处理器
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect(host, port).sync();
            Channel channel = cf.channel();
            //通过ChannelFuture可以得到channel
            //channel是netty用来进行网络通信对组件
            // 对关闭通道进行监听
//            channel.closeFuture().sync();
            System.out.println("--------" + channel.localAddress() + " 准备好了");

            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()){
                String msg = sc.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 6668).run();
    }
}
