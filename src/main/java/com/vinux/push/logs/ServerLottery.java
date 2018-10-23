package com.vinux.push.logs;

public class ServerLottery {

	public static void main(String[] args) {
		
		//13 154
		//12 154
		//11 153
		//10 153
		//09 154
		//08 154
		//07 153
		//06 154
		//05 153
		//04 122
		//03 89
		
//		Thread t13 = new Thread(new LotteryCode(13000, 154));
//		t13.start();
//		Thread t12 = new Thread(new LotteryCode(12000, 154, ""));
//		t12.start();
		
//		Thread t11 = new Thread(new LotteryCode(11000, 153, ""));
//		t11.start();
//		Thread t10 = new Thread(new LotteryCode(10000, 153, ""));
//		t10.start();
//		Thread t09 = new Thread(new LotteryCode(9000, 154, "0"));
//		t09.start();
		Thread t08 = new Thread(new LotteryCode(8000, 154, "0"));
		t08.start();
//		Thread t07 = new Thread(new LotteryCode(7000, 153, "0"));
//		t07.start();
//		Thread t06 = new Thread(new LotteryCode(6000, 154, "0"));
//		t06.start();
//		Thread t05 = new Thread(new LotteryCode(5000, 153, "0"));
//		t05.start();
//		Thread t04 = new Thread(new LotteryCode(4000, 122, "0"));
//		t04.start();
//		Thread t03 = new Thread(new LotteryCode(3000, 89, "0"));
//		t03.start();

	}

}
