package com.vinux.push.handler;

import com.vinux.push.entity.ChannelCache;
import com.vinux.push.entity.Message;
import com.vinux.push.enu.MessageType;
import com.vinux.push.logs.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PushMsgHandler extends SimpleChannelInboundHandler<Message>{
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if(message.getMsgType() == MessageType.MSG_CHAT_SINGLE_PUSH.getValue()){//点对点
        	Logger.logInfo(message.getReceiveId() + " 收到 " + message.getUid() + "发送的消息：" + message.getMsg());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

    }
}
