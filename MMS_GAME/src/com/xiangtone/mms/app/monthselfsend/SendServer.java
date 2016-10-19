/*
 * 自消费包月信息发送服务端
 * Created on 2006-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.mms.app.monthselfsend;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendServer {

	public static void main(String[] args) throws IOException
	{
		
			
		
		ServerSocket serverSocket=null;
		
		boolean listening=true;
		
		try
		{
			serverSocket=new ServerSocket(3721);
		}
		catch (IOException e)
		{
			System.err.println("Could not listen on port");
			System.exit(-1);
		}
		
		while (listening)
		{
			System.err.println("listening now on port:3721");
			new SendThread(serverSocket.accept()).start();
			
		//	new MoServerThread(serverSocket.accept()).start();
	//		new SmsServerThread(serverSocket.accept()).start();
			
		}
		serverSocket.close();
		
	}
}
