package com.vinux.push.client;

public class ThreadMain {

	public static void main(String[] args) {
		
		int loop = 2500;
		for(int i = 1 ; i <= loop ; i ++) {
			new ClientThread(i).start();
			//Thread.sleep(millis);
		}

	}

}
