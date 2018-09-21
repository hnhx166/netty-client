package com.vinux.push.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Voice {

	private LinkedList<Message> messsageList = new LinkedList<Message>();
	private boolean hasMessage = false;

	public synchronized void putMessage(Message message) {
		messsageList.addLast(message);
		hasMessage = true;
		play();
	}

	public void play() {
		System.out.println("执行play。。。。。。。。。。。。。。。。。。。。。");
		Message message = messsageList.pollFirst();
		if(null != message) {
			InputStream is = null;
			try {
				is = ClassLoader.getSystemResourceAsStream("xiazhilv.wav");//Voice.class.getClassLoader().getResourceAsStream("xiazhilv.wav");
				System.out.println(is);
				AudioStream as = new AudioStream(is);
				System.out.println(as);
				AudioPlayer.player.start(as);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(null != is) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
