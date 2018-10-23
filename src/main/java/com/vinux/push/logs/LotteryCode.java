package com.vinux.push.logs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LotteryCode implements Runnable {
	
	private Integer year_code;
	private Integer num;
	private String x = "0";
	
	public LotteryCode(Integer year_code, Integer num, String x) {
		this.year_code = year_code;
		this.num = num;
		this.x = x;
	}

	@Override
	public void run() {
		for(int i=1;i<=num;i++){
			try {
				getLuckyNumbers(x + (i+year_code)+"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	private static void getLuckyNumbers(String date) throws Exception{
		Document doc = Jsoup.connect("http://kaijiang.500.com/shtml/ssq/"+date+".shtml").get();
		Elements eles = doc.getElementsByClass("ball_box01").get(0).getElementsByTag("li");
		StringBuffer sb = new StringBuffer();
		for(Element ele : eles){
			sb.append(ele.html()+",");
		}
		Logger.logInfo(sb.toString() + "\n");
		System.out.println("=======第"+date+"期："+sb.toString());
	}

}
