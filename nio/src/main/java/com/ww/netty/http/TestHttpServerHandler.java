package com.ww.netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 * HttpObject 客户端和服务端通信数据的封装
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            System.out.println("msg类型："+ msg.getClass());
            System.out.println("客户端地址："+ ctx.channel().remoteAddress());

            HttpRequest request = (HttpRequest)msg;
            String uri = request.uri();
            if ("/favicon.ico".equals(uri)){
                System.out.println("请求了 /favicon.ico 不作响应");
                return;
            }

            //回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);
            //netty是支持http协议的，所以它有相关的一些类
            //构造一个http响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH ,content.readableBytes());
            //返回httpResponse
            ctx.writeAndFlush(response);
        }
    }
}
