package com.xiangtone.mms.monthselfsend;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * 本方法主要用来加载log4j.properties,避免反复加载该文件，可当作正常的Logger类使用，
 * 目前只封装了debug，info，error，warn方法，有其他需要的请自行添加
 * @author hongjiabin
 * Created on 2016-07-13
 */

public class Lo4jUtil {
	
	private static Logger logger ;
	
	public Lo4jUtil() {
	}
	
	public Lo4jUtil(String utilClass) {
		logger = Logger.getLogger(utilClass); 
		initLog4j();
	}
	
	public static void debug(String msg){
		logger.debug(msg);
	}
	
	public static void info(String msg){
		logger.info(msg);
	}
	
	public static void error(String msg){
		logger.error(msg);
	}
	
	public static void warn(String msg){
		logger.warn(msg);
	}
	
	/*
	 * 加载lo4j的相关配置
	 */
	public static void initLog4j() {
		//预防工程缺失lo4j配置文件，设置缺省配置设置
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "debug,appender1");
		prop.setProperty("log4j.appender.appender1", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.R.DatePattern", "'.'yyyy-MM-dd");
		prop.setProperty("log4j.appender.appender1.File", "ht_sdk.log");
		prop.setProperty("log4j.appender.appender1.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.appender1.layout.ConversionPattern"," %-5l%-d{yyyy-MM-dd HH:mm:ss}%p]%m%n" );
		PropertyConfigurator.configure(prop);
		//加载工程bin目录下的lo4j配置文件
	    PropertyConfigurator.configure("log4j.properties");
	}

}
