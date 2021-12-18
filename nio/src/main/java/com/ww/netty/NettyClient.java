package com.ww.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyClient {

    public static void main(String[] args) {
        //客户端只需要一个循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        //创建客户端启动对象
        //客户端使用Bootstrap而不是ServerBootstrap
        Bootstrap bootstrap = new Bootstrap();
        //设置相关参数
        bootstrap.group(eventExecutors)
    }


}
