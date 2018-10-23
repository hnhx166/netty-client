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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

	enum LOG_TYPE {
		LOG_TYPE_INFO("INFO", 0), LOG_TYPE_ERROR("ERROR", 1);

		private String name;
		private int index;

		LOG_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public static String getName(int index) {
			for(LOG_TYPE type : LOG_TYPE.values()) {
				if(type.index == index) {
					return type.getName();
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

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
	private static void buildPath() {
		File f = new File(logDir);
		File ff = null;
		if (!f.exists()) {
			f.mkdirs();
			logPath = logDir + fileName();
			ff = new File(logPath);
		} else {
			logPath = logDir + fileName();
			ff = new File(logPath);
			if (!ff.exists()) {
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
	 * 
	 * @param e
	 */
	public static void logError(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(sw.toString(), LOG_TYPE.LOG_TYPE_ERROR);
	}

	/**
	 * 普通日志输出
	 * 
	 * @param msg
	 */
	public static void logInfo(String msg) {
		log(msg, LOG_TYPE.LOG_TYPE_INFO);
	}

	/**
	 * NIO文件流日志输出
	 * 
	 * @param msg
	 */
	private static void log(String msg, LOG_TYPE type) {
		// 构建日志文件名称
		buildPath();

		FileOutputStream fos = null;
		FileChannel fc = null;
		ByteBuffer buffer = null;
		try {
			fos = new FileOutputStream(new File(logPath), true);// 日志追加写入日志文件
			fc = fos.getChannel();
			msg = logTime() + "	" + type.getName() + "	"  + msg + "\n";
			// msg = msg ;
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
			System.out.println(1 / 0);
		} catch (Exception e) {
			logError(e);
		}

		logInfo("11122221");
		logInfo("收快递费是刚开始干");
		logInfo("卒");
		logInfo("82深V");
		try {

			// 17 154
			// 16 153
			// 15 154
			// 14 152
			// 13 154
			// 12 154
			// 11 153
			// 10 153
			// 09 154
			// 08 154
			// 07 153
			// 06 154
			// 05 153
			// 04 122
			// 03 89

			// for(int i=1;i<=152;i++){
			// int s = 14000;
			// getLuckyNumbers((i+s)+"");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getLuckyNumbers(String date) throws Exception {
		Document doc = Jsoup.connect("http://kaijiang.500.com/shtml/ssq/" + date + ".shtml").get();
		Elements eles = doc.getElementsByClass("ball_box01").get(0).getElementsByTag("li");
		StringBuffer sb = new StringBuffer();
		for (Element ele : eles) {
			sb.append(ele.html() + ",");
		}
		logInfo(sb.toString() + "\n");
		System.out.println("=======第" + date + "期：" + sb.toString());
	}

	// public static void main(String[] args) throws Exception {
	// //18001-18123
	// for(int i=1;i<123;i++){
	// int s = 18000;
	// getLuckyNumbers((i+s)+"");
	// }
	//
	// }

}
