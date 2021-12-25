package com.ww.netty.groupchat2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，管理所有对channel
    //GlobalEventExecutor.INSTANCE全局对事件执行器，是一个单例
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将当前channel加入到ChannelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将channel加入到channel组中管理
        channels.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天\n");
        channels.add(channel);
    }

    /**
     * 将当前channel断开连接
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        channels.remove(channel);
        channels.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开聊天\n");
        System.out.println("当前channels大小：" + channels.size());
    }

    /**
     * 表示channel处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了～");
    }

    /**
     * 表示channel处于非活动状态
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了～");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //转发消息
        Channel channel = ctx.channel();
        //发送消息时，自己不发送
        channels.forEach(ch->{
            if (ch != channel){
                //向其他客户端转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送的消息：" + msg);
            }
        });
    }

    /**
     * 发生了异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生了异常后关闭通道
        ctx.close();
    }
}
