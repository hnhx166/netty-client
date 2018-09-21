package com.vinux.push.entity;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class Device {

	public static String getMacAddr() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			System.out.println("获取mac地址出错。");
			e.printStackTrace();
		}
		return null;
	}
}
