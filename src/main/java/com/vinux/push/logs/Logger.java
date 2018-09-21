package com.vinux.push.logs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 推送日志输出
 * 
 * 项目名称：netty-client 类名称：Logger 类描述： 创建人：guohaixiang 创建时间：2018年9月18日 下午2:05:58
 * 修改人：Administrator 修改时间：2018年9月18日 下午2:05:58 修改备注：
 * 
 * @version 1.0
 *
 */
public class Logger {
	
	/**
	 * 日志输出时间格式
	 */
	private static DateFormat logTimeDf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	
	/**
	 * 日志文件名字(时间格式)
	 */
	private static DateFormat filenameDf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 日志输出位置
	 */
	private static String logDir;
	
	/**
	 * 日志输出文件
	 */
	private static String logPath;
	
	/**
	 * 初始化文件输出位置
	 */
	static {
		logDir = System.getProperty("user.dir") + System.getProperty("file.separator", "\\") + "logs\\";// 文件名改为日期
	}
	
	/**
	 * 日志输出目录及输出文件
	 */
	private static void buildPath(){
		File f = new File(logDir);
		File ff = null;
		if (!f.exists()) {
			f.mkdirs();
			logPath = logDir + fileName();
			ff = new File(logPath);
		}else {
			logPath = logDir + fileName();
			ff = new File(logPath);
			if(!ff.exists()) {
				try {
					ff.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 异常日志输出
	 * @param e
	 */
	public static void logError(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(sw.toString());
	}
	
	/**
	 * 普通日志输出
	 * @param msg
	 */
	public static void logInfo(String msg){
		log(msg);
	}

	/**
	 * NIO文件流日志输出
	 * @param msg
	 */
	private static void log(String msg) {
		//构建日志文件名称
		buildPath();
		
		FileOutputStream fos = null;
		FileChannel fc = null;
		ByteBuffer buffer = null;
		try {
			fos = new FileOutputStream(new File(logPath), true);//日志追加写入日志文件
			fc = fos.getChannel();
			msg = logTime() + ": " + msg ;
			buffer = ByteBuffer.wrap(msg.getBytes());
			fos.flush();
			fc.write(buffer);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				fc.close();
				fos.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * 日志输出文件名(yyyy-mm-dd.txt)
	 * 
	 * @return
	 */
	private static String fileName() {
		return filenameDf.format(new Date()) + ".txt";
	}
	
	/**
	 * 
	 * 日志输出时间
	 * 
	 * @return
	 */
	private static String logTime() {
		return logTimeDf.format(new Date());
	}

	
	public static void main(String[] args) {
		try {
			System.out.println(1/0);
		}catch(Exception e) {
			logError(e);
		}
	}

}
