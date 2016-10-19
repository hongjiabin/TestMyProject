/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.mms.monthselfsend.copy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Document:Tool
 * Description:处理短信信息，下发彩信
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
	*该函数根据timeFormat的格式返回响应格式的时间
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
	* 该函数根据yyyy-MM-dd,得到m个月前
	* @method getPassDate()
	* @param String ymd,int m
	* @return String yyyy-MM
	* @time 2006-8-6 10:51
	* @author yhq
	*/
	public static synchronized String getMonth(String ymd,int months) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();   // 当时的日期和时间
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
		int m = c.get(Calendar.MONTH);  // 取出“日”数
		m=m+months;
		c.set(Calendar.MONTH,m);
		return df.format(c.getTime());
	}
	
	/**
	* 该函数得到指定天数的日期
	* @method getPassDate()
	* @param int days
	* @return String
	* @time 2006-8-6 10:51
	* @author yhq
	*/
	public static synchronized String getPassDay(int days) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Calendar c = Calendar.getInstance();   // 当时的日期和时间
	int d = c.get(Calendar.DAY_OF_MONTH);  // 取出“日”数
	d=d-days;                                   // 将“日”减一，即得到前一天
	c.set(Calendar.DAY_OF_MONTH, d);       // 将“日”数设置回去
	return df.format(c.getTime());


	}
/**
 * 得到应用产生的随机数
 * @return
 */
	public static int GetRandom(int maxrandom)
	{
		Random rand=new Random();
		int random_num=rand.nextInt(maxrandom);
		return random_num;
	}
	public static void main(String[] args) {
	}
}
