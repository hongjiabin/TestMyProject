/*
 * 创建日期 2005-11-6
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.xiangtone.mms.monthselfsend.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author user
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class function {
	
	public String read_file(String path) throws FileNotFoundException {
		String str1="";
		String str2="";
		//String path="e:\\www\\baoyue\\upload\\hctl\\conn\\6\\6.smil";
		FileReader d1= new FileReader(path);
		BufferedReader br1=new BufferedReader(d1);
		try {
			while((str2=br1.readLine())!=null){
				str1+=str2+"\r\n";
			}
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return str1;
	}
	public String getcurrent_time() {//得到当前系统的时间，格式为datetime
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}

	public String get_current_time() {//得到当前系统的时间，格式为HH:mm:ss
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("HH:mm:ss");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	public String get_current_day() {//得到当前系统日期，格式为dd
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	public String getcurrent_sdate() {//得到当前系统的时间，格式为Y_m_d
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	//计算两个时间相隔的秒数
	public int nSecondsBetweenTwoDate(String firstString, String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			System.out.println("日期型字符串格式出现异常：" + e.getMessage());
		}
		int nDay = (int) (secondDate.getTime() - firstDate.getTime());
		return nDay;
	}
	public void make_log_file(String path,String value){//记录转换后的日志文件,path为路径，value为日志内容
		String filename =path+"/log.txt";
		File outshow=new File(filename);
		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(filename, true),true);
			log.println(value);
			//log.println("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("log error:"+e.getMessage());
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
      function fun=new function();
      //String str=fun.read_file("e:\\www\\baoyue\\upload\\hctl\\conn\\6\\6.smil");      
	  System.out.println(fun.getcurrent_sdate());
	}
}
