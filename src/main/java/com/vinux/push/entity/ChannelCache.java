package com.vinux.push.entity;

import java.util.LinkedList;

//import com.vinux.push.server.BroadcastServer;

import io.netty.channel.Channel;

public class ChannelCache {

	public static Channel channel = null;
//	public static Voice voice = new Voice();
//	public static BroadcastServer broadcastServer;
	public static LinkedList<Message> messsageList = new LinkedList<Message>();
	
	public static User user;
	
	public static String macDddr = null;
	//public static LinkedList<Message> messsageList = new LinkedList<Message>();
	
}
