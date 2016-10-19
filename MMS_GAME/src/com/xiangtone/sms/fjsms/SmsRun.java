/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.sms.fjsms;
import java.io.IOException;
import com.xiangtone.util.Log;


/**
 * @author cyhq
 * Document:SmsRun.java
 * Description:连接公司短信平台客户端线程
 * Copyright:    Copyright (c) 2006
 * Company: xiaingtone
 * Created:2006-7-6 14:50:00
 */
public class SmsRun extends Thread {
	public void run () {
		try 
		{
			SmsClient.execmain();
		}
		catch (IOException e)
		{
			Log.showLog("com.xiangtone.mms.sms.SmsRun::"+e,Log.logPath);
			System.out.println("IOException  at gd_run.java  line14" +e);
		}
	}

	public static void main(String[] args) {
		SmsRun fj_sms = new SmsRun();
		fj_sms.start();
	}
}
