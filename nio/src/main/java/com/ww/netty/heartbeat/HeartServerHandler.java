package com.ww.netty.heartbeat;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            String eventType = null;
            IdleStateEvent event = (IdleStateEvent)evt;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
                    eventType= "其他";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + "发生了 " + eventType);

            //关闭通道
            ctx.channel().close();
        }
    }
}
