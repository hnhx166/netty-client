package com.vinux.push.client;

import com.vinux.push.entity.Message;
import com.vinux.push.enu.MessageType;
import com.vinux.push.server.ClientServer;

public class ClientThread extends Thread {

	private int no =1;
	public ClientThread(int no) {
		this.no = no;
	}
	
	public void run() {
		for(int i = 0; i < no; i++) {
			ClientServer client = new ClientServer();
			try {
				client.connect();
				Message message = new Message();
				message.setMsg("消息:" + Thread.currentThread());
				message.setMsgType(MessageType.MSG_BOX_PUSH.getValue());
//				message.setUid(Thread.currentThread().getId()+"");
				message.setReceiveId("1001");
				client.push(message);
				System.out.println(Thread.currentThread().getId()+", " + (i+1));
				
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
