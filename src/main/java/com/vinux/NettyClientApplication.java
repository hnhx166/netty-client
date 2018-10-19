package com.vinux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinux.cfg.Config;
import com.vinux.push.entity.Message;
import com.vinux.push.enu.MessageType;
import com.vinux.push.server.ClientServer;

@RestController
@SpringBootApplication
public class NettyClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);
//		System.out.println("开启播报线程***************************");
//		ChannelCache.broadcastServer = new BroadcastServer();
//		ChannelCache.broadcastServer.start();
//		System.out.println("播报线程启动完成***************************");
//		ClientServer client = new ClientServer();
//		try {
//			client.connect();
//			System.out.println("客户端线程启动。。。。。。。");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	

//	public static void main(String[] args) throws JSONException, CommunicationException, KeystoreException, InvalidDeviceTokenFormatException {
//		SpringApplication.run(NettyClientApplication.class, args);
//		
//		System.out.println("zsl==========开始推送消息");
//        int badge = 1; // 图标小红圈的数值
//        String sound = "default"; // 铃音
//        String msgCertificatePassword = "12345678";//导出证书时设置的密码
//        String deviceToken = "6846f471b699868036547869977935cbf98df3805ecb2c1b7e7140b4fc962c94"; //手机设备token号
//        String message = "test push message to ios device";
//
//        List<String> tokens = new ArrayList<String>();
//        tokens.add(deviceToken);
//
////      String certificatePath = requestRealPath
////              + "/WEB-INF/classes/certificate/msg.p12";
//        //java必须要用导出p12文件  php的话是pem文件
//        String certificatePath = "F:/mdm.p12";
//        boolean sendCount = true;
//
//        PushNotificationPayload payload = new PushNotificationPayload();
//        payload.addAlert(message); // 消息内容
//        payload.addBadge(badge);
//
//
//        //payload.addCustomAlertBody(msgEX);
//        if (null == sound || "".equals(sound)) {
//            payload.addSound(sound);
//        }
//
//        PushNotificationManager pushManager = new PushNotificationManager();
//        // true：表示的是产品测试推送服务 false：表示的是产品发布推送服务
//        pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
//                certificatePath, msgCertificatePassword, true));
//        List<PushedNotification> notifications = new ArrayList<PushedNotification>();
//        // 开始推送消息
//        if (sendCount) {
//            Device device = new BasicDevice();
//            device.setToken(deviceToken);
//            PushedNotification notification = pushManager.sendNotification(
//                    device, payload, true);
//            notifications.add(notification);
//        } else {
//            List<Device> devices = new ArrayList<Device>();
//            for (String token : tokens) {
//                devices.add(new BasicDevice(token));
//            }
//            notifications = pushManager.sendNotifications(payload, devices);
//        }
//
//        List<PushedNotification> failedNotification = PushedNotification
//                .findFailedNotifications(notifications);
//        List<PushedNotification> successfulNotification = PushedNotification
//                .findSuccessfulNotifications(notifications);
//        int failed = failedNotification.size();
//        int successful = successfulNotification.size();
//        System.out.println("zsl==========成功数：" + successful);
//        System.out.println("zsl==========失败数：" + failed);
//        pushManager.stopConnection();
//        System.out.println("zsl==========消息推送完毕");
//	}
//	
	@Autowired
	ClientServer clientServer;
	
	@Autowired
	Config cfg;
	
	@RequestMapping("push")
	public void push(String msg) {
		Message message = new Message();
        message.setMsgType(MessageType.MSG_BOX_PUSH.getValue());
        message.setMsg(msg);
        message.setAppId("client");
        message.setVersion(1);
        message.setUid(cfg.getUid());
        message.setReceiveId(cfg.getRecId());
        clientServer.push(message);
	}
}
