package com.ww.netty.group_chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * 自定义对handler处理器，需要继承netty的handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据事件（这里可以读取客户端发送来的消息）
     * @param ctx 上下文信息、通道、管道pepiline
     * @param msg 消息体
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + msg);
        //将msg转成 ByteBuf
        ByteBuf buf = (ByteBuf)msg;

        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 读取数据完毕
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //将数据写入缓存并刷新
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello 客户端～～～", CharsetUtil.UTF_8));
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
