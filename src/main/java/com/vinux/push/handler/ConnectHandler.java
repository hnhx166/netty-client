package com.vinux.push.handler;

import com.vinux.push.entity.ChannelCache;
import com.vinux.push.entity.Message;
import com.vinux.push.enu.MessageType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectHandler extends SimpleChannelInboundHandler<Message>{

	//三次握手成功,发送登录验证
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new Message();
        message.setMsgType(MessageType.CONNECT_REQ.getValue());
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read");
        Message message = (Message) msg;
        //登录验证失败
        if(message != null && message.getMsgType() == MessageType.CONNECT_FAIL.getValue()){
            ctx.close();
        }else if(message.getMsgType() == MessageType.CONNECT_SUCCESS.getValue()){//登录验证成功
            System.out.println("login is ok....");
            ctx.fireChannelRead(message);
            ChannelCache.channel = ctx.channel();
        }else{
            ctx.fireChannelRead(message);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  Message message) throws Exception {

    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

}
