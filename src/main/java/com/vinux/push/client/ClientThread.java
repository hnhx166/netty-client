package com.vinux.push.client;

import com.vinux.push.entity.User;
import com.vinux.push.server.ClientServer;

public class ClientThread extends Thread {

	private int no =1;
	public ClientThread(int no) {
		this.no = no;
	}
	
	public void run() {
		System.out.println(no);
//		try {
//			Thread.sleep(60000);
//			System.out.println("----------" + no);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//			String uid = "uid" + (no);
//			ClientServer client = new ClientServer(new User(uid));
//			try {
//				client.connect();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
	}
}
