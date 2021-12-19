package com.ww.netty.group_chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪触发该方法
     * @param ctx 上下文信息
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("小猫叫。。喵～～～", CharsetUtil.UTF_8));
    }

    /**
     * 通道读取事件时触发
     * @param ctx 上下文
     * @param msg 要读取的信息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + msg);
        //将msg转成 ByteBuf
        ByteBuf buf = (ByteBuf)msg;

        System.out.println("服务端回复的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端的地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 异常的处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
