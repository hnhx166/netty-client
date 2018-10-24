package com.vinux.push.handler;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.vinux.push.entity.ChannelCache;
import com.vinux.push.entity.Config;
import com.vinux.push.entity.Message;
import com.vinux.push.enu.MessageType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * 心跳包检测
 * 
 * 项目名称：netty-push 类名称：HeartBeatHandler 类描述： 创建人：guohaixiang 创建时间：2018年8月14日
 * 下午6:07:32 修改人：Administrator 修改时间：2018年8月14日 下午6:07:32 修改备注：
 * 
 * @version 1.0
 *
 */
public class HeartBeatHandler extends SimpleChannelInboundHandler<Message> {
	
	private volatile ScheduledFuture<?> heartBeat;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		ChannelCache.channel = ctx.channel();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
		System.out.println("channel :  " + ctx.channel());
		//连接成功，发送用户信息
		if (message != null && message.getMsgType() == MessageType.CONNECT_SUCCESS.getValue()) {
			// 50秒钟发一个心跳
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 50000, TimeUnit.MILLISECONDS);
			Message msg = new Message();
			msg.setUid(ChannelCache.user.getUid());
			msg.setVersion(Config.CLIENT_VERSION);
			msg.setMsgType(MessageType.MSG_BOX_USER_INFO.getValue());
			msg.setSendTime(new Date(System.currentTimeMillis()));
	        ctx.channel().writeAndFlush(msg);
		} else if (message != null && message.getMsgType() == MessageType.HEARTBEAT_RESP.getValue()) {
			System.out.println("Client reciver heart beat message : ----> " + message.getMsg());
		} else {
			// 编码好的Message传递给下一个Handler
			ctx.fireChannelRead(message);
		}
	}

	// 心跳包发送任务
	private class HeartBeatTask implements Runnable {

		private ChannelHandlerContext ctx;

		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		public void run() {
			Message message = buildHeartMessage();
			System.out.println("Client send heart beat message : ----> " + message);
			ctx.writeAndFlush(message);
		}

		private Message buildHeartMessage() {
			Message message = new Message();
			message.setMsgType(MessageType.HEARTBEAT_REQ.getValue());
			return message;
		}
	}
}
