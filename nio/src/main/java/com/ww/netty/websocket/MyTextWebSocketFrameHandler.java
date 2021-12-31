package com.ww.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * @param msg 表示一个文本帧
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到信息：" + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDateTime.now() + " " + msg.text()));
    }

    /**
     * 当客户端连接后就会触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一值LongText是唯一的，ShortText不是唯一的
        System.out.println("handlerAdded被调用：" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用：" + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用：" + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved被调用：" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常："+ cause.getMessage());
        ctx.close();
    }
}
