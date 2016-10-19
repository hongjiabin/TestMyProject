package com.xiangtone.mms.monthselfsend;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * ��������Ҫ��������log4j.properties,���ⷴ�����ظ��ļ����ɵ���������Logger��ʹ�ã�
 * Ŀǰֻ��װ��debug��info��error��warn��������������Ҫ�����������
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
	 * ����lo4j���������
	 */
	public static void initLog4j() {
		//Ԥ������ȱʧlo4j�����ļ�������ȱʡ��������
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "debug,appender1");
		prop.setProperty("log4j.appender.appender1", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.R.DatePattern", "'.'yyyy-MM-dd");
		prop.setProperty("log4j.appender.appender1.File", "ht_sdk.log");
		prop.setProperty("log4j.appender.appender1.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.appender1.layout.ConversionPattern"," %-5l%-d{yyyy-MM-dd HH:mm:ss}%p]%m%n" );
		PropertyConfigurator.configure(prop);
		//���ع���binĿ¼�µ�lo4j�����ļ�
	    PropertyConfigurator.configure("log4j.properties");
	}

}
