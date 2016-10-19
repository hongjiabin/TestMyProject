package com.xiangtone.mms.monthselfsend;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class mklog {

	public static void main(String[] args) {
		String str = getTime("yy-MM-dd");
		//System.out.println(str);
		showLog("smile:<smile><text src='1.txt'/><par src='1.gif'></par></smile>");
		showLog("==============================================================================================");
	}

	public static void debug(String msg) {
		System.out.println("DEBUG_INFO:" + msg);
	}
	public static synchronized void showLog(String value) {//记录日志文件
		String path = "/home/xtwebdevp/data/mms/mmsgame/log/all/";//服务器路径
		//String path = "d:/mmshyzx/mms/";//本地测试
		if (makeDir(path,1));
		String monthpath=getTime("yyyy")+"/"+getTime("MM")+"/";
		//System.out.println(monthpath);
		String out_data = getTime("yyyy_MM_dd");
		String filename = "";
		File outShow = null;
		PrintWriter log = null;
		// add end
		int filesn = 1;
		while (true) {
			filename = path + monthpath+out_data + "_" + String.valueOf(filesn) + ".txt";

			outShow = new File(filename);
			//	PrintWriter log = null;
			if (outShow.exists()) {
				if (outShow.length() > 10 * 1024 * 1024) {
					filesn++;
				} else {
					try {

						log = new PrintWriter(new FileWriter(filename, true),
								true);
						//	log.println(getTime("yyyy-MM-dd HH:mm:ss") + "::");
						log.println(value);
						log.println(" ");
						//	log.println("\r\n".getBytes("GBK"));
						break;
					} catch (Exception e) {
						log = new PrintWriter(System.err);
						break;
					}
					//	break;
				}
			} else {
				try {
					log = new PrintWriter(new FileWriter(filename, true), true);
					//	log.println(getTime("yyyy-MM-dd HH:mm:ss") + "::");
					log.println(value);
					log.println(" ");
					//	log.println("\r\n".getBytes("GBK"));
					break;
				} catch (Exception e) {
					log = new PrintWriter(System.err);
					break;
				}
			}
		}
	}
	public static synchronized boolean makeDir(String path, int kind) {//创建目录
		String fp = System.getProperty("file.separator");
		if (kind == 1)
			path = path + fp + getTime("yyyy") + fp + getTime("MM");
		  //path = path + fp + getTime("yyyy") + fp + getTime("MM") + fp + getTime("dd");
		File cf = new File(path);
		if (!cf.exists()) {
			try {
				if (cf.mkdirs())
					return true;
				return false;
			} catch (SecurityException e) {
				debug(" makedir fialed");
				return false;
			} catch (Exception e) {
				debug(e.getMessage());
				return false;
			}
		}
		return true;
	}

	public static synchronized String getTime(String timeFormat) {//该函数根据timeFormat的格式返回响应格式的时间
		long second = System.currentTimeMillis();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				timeFormat);
		return sdf.format(new java.util.Date(second)).toString();
	}
}

