package com.vinux.push.client;

public class ThreadMain {

	public static void main(String[] args) {
		int loop = 100;
		ClientThread client = new ClientThread(loop);
		client.run();

		ClientThread client1 = new ClientThread(loop);
		client1.run();

		ClientThread client2 = new ClientThread(loop);
		client2.run();

		ClientThread client3 = new ClientThread(loop);
		client3.run();

		ClientThread client4 = new ClientThread(loop);
		client4.run();

	}

}
