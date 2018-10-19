//package com.vinux.push.server;
//
//import org.loon.framework.jtts.Engine;
//import org.loon.framework.jtts.JTTS;
//
//import com.vinux.push.entity.ChannelCache;
//import com.vinux.push.entity.Message;
//import com.vinux.push.logs.Logger;
//
//public class BroadcastServer extends Thread{
//	
//	public void run() {
//		JTTS jtts = Engine.getTTS();
//		jtts.setLanguage("zh+f3");
//		jtts.setRate(100);// 设定朗读速度
//		jtts.setPitch(50);// 设定混音参数
//		jtts.setWordgap(2);// 设定朗读间隔
//		
//		while(true) {
//			Message message = ChannelCache.messsageList.pollFirst();
//			if(null != message && message.getMsg() != null) {
//				Logger.logInfo("播放消息：" + message.getMsg());
//				jtts.speak(message.getMsg()); // 自动朗读指定的字符串信息
//				jtts.synchronize(); // 当所有线程朗读完毕时转为此线程发言
//			}
//		}
//	}
//}
