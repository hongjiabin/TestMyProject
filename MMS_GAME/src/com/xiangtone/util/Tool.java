/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * Document:Tool
 * Description:���������Ϣ���·�����
 * Copyright:    Copyright (c) 2006
 * Company: xiaingtone
 * Created:2006-7-6 15:20:00
 */
public class Tool {
	public boolean checkDigit(String s) {
		boolean isnum=true;
		for (int i=0;i<s.length();i++) {
		//	System.out.println(s.substring(i,i+1));
			if (!Character.isDigit(s.charAt(i))) {
				System.out.println(i+s.charAt(i));
				isnum=false;
				break;
			}
		}
		return isnum;
	}
	/**
	*�ú�������timeFormat�ĸ�ʽ������Ӧ��ʽ��ʱ��
	*@method getTime()
	*@param String timeFormat
	*@return String 
	*@time 2005-7-26 09:34
	*@author cyhq
	*/
	public static synchronized String getTime(String timeFormat)
	{
		long second = System.currentTimeMillis();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(timeFormat);
		return sdf.format(new java.util.Date(second)).toString();

	}
	public static synchronized int getHour() 
	{
	//	HOUR_OF_DAY,
		Calendar c = Calendar.getInstance();
		int d = c.get(Calendar.HOUR_OF_DAY); 
		return d;
	}
	public static synchronized int getDayOfMonth() {
		Calendar c=Calendar.getInstance();
		int d=c.get(Calendar.DAY_OF_MONTH);
		return d;
	}
	public static synchronized int getDayOfWeek() {
		Calendar c=Calendar.getInstance();
		int d=c.get(Calendar.DAY_OF_WEEK);
		//Calendar
		return d;
	}
	/**
	* �ú�������yyyy-MM-dd,�õ�m����ǰ
	* @method getPassDate()
	* @param String ymd,int m
	* @return String yyyy-MM
	* @time 2006-8-6 10:51
	* @author yhq
	*/
	public static synchronized String getMonth(String ymd,int months) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();   // ��ʱ�����ں�ʱ��
		try {
			Date newDate=df.parse(ymd);
	//		System.out.println(newDate);
			c.setTime(newDate);
	//		c = Calendar.getInstance(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("eee:"+e);
		}
		int m = c.get(Calendar.MONTH);  // ȡ�����ա���
		m=m+months;
		c.set(Calendar.MONTH,m);
		return df.format(c.getTime());
	}
	
	/**
	* �ú����õ�ָ������������
	* @method getPassDate()
	* @param int days
	* @return String
	* @time 2006-8-6 10:51
	* @author yhq
	*/
	public static synchronized String getPassDay(int days) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Calendar c = Calendar.getInstance();   // ��ʱ�����ں�ʱ��
	int d = c.get(Calendar.DAY_OF_MONTH);  // ȡ�����ա���
	d=d-days;                                   // �����ա���һ�����õ�ǰһ��
	c.set(Calendar.DAY_OF_MONTH, d);       // �����ա������û�ȥ
	return df.format(c.getTime());


	}
/**
 * �õ�Ӧ�ò����������
 * @return
 */
	public static int GetRandom(int maxrandom)
	{
		Random rand=new Random();
		int random_num=rand.nextInt(maxrandom);
		return random_num;
	}
	
	
	/*
	 * 
	 *������������	
	 */
	public static  String sendSmsMsg(String msg,String[] cpn) throws Exception{
		String url="http://211.147.7.243/code/sms_sys_manage/smscustomer/msgsend/receiveMsg.php";
		String returnmsg="";
		for (int i=0;i<cpn.length;i++) {
			String getInfo="?pwd=a910999ffe7bf3eeab1516da198f9f43&msg="+java.net.URLEncoder.encode(msg,"GBK")+"&cpn="+cpn[i];				
			returnmsg=openUrl(url+getInfo,"GBK");
			System.out.println(url+getInfo);
			System.out.println("returnmsg==="+returnmsg);
			System.out.println("sleep 5s");
			Thread.sleep(5000);
		}
		return returnmsg;
	}
	private static String openUrl(String url,String encoding) {
		URL ConnectUrl=null;
		String responseContent=null;
		try {
			ConnectUrl = new URL(url);
		}catch (Exception e) {
			System.out.println("excetpino1111"+e);
		}
		try {
			
			URLConnection uc=ConnectUrl.openConnection();
			BufferedReader in =new BufferedReader(new InputStreamReader(uc.getInputStream(),encoding));
			responseContent=in.readLine();
	
		
		} catch (Exception e) {
			System.out.println("333333333333");
			System.out.println("eee"+e.toString());
		}
	
		return responseContent;
	}
	public static void main(String[] args) {
	}
}
