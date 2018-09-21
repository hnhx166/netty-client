package com.vinux.push.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//应用ID
    private String appId;
    
    //设备ID（暂时设置为MAC地址）
//    private final String deviceId = Device.getMacAddr();
 
    //版本
    private int version;
 
    //用户ID
    private String uid  = Device.getMacAddr();
 
    //消息类型 0：登录 1：文字消息 2:文件 3：视频
    private byte msgType;
    
    //消息：系统消息，聊天消息
    //系统消息：所有用户，部分用户，单个用户
    //聊天消息: 群聊，好友
    //0：群发，1：单发
//    private int sendType;
//    //群组ID
//    private Integer groupId;
    //接收方
    private String receiveId;
 
    //消息内容
    private String msg;
 
}
