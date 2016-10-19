/*
 * Created on 2006-8-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.mms.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadServerPic {
	public static void copy(InputStream in, OutputStream out) throws IOException 
	{
		synchronized(in) 
		{
			synchronized(out) 
			{
			
				byte[] buffer = new byte[256];
				while (true) 
				{
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			URL u = new URL("http://211.136.87.209/material/fj/dring/2006-08/2724608070128z3F.mp3");
			URLConnection uc=u.openConnection();
			String contentType=uc.getContentType();
			int contentLength=uc.getContentLength();
			System.out.println("contentType:"+contentType);
			System.out.println("contentLength:"+contentLength);
			InputStream raw=uc.getInputStream();
			InputStream in=new BufferedInputStream(raw);
			byte[] data=new byte[contentLength];
			int bytesRead=0;
			int offset=0;
			while (offset<contentLength) {
				bytesRead=in.read(data,offset,data.length-offset);
				if (bytesRead==-1) break;
				offset+=bytesRead;
			}
			in.close();
			String filename=u.getFile();
			filename=filename.substring(filename.lastIndexOf('/')+1);
			FileOutputStream fout=new FileOutputStream(filename);
			fout.write(data);
			fout.flush();
			fout.close();
			/*
			BufferedReader in = new BufferedReader(new InputStreamReader(urlfile.openStream()));
			
			String content="";
			
			String inputLine = in.readLine();
			while(inputLine!=null){
				content += inputLine;
				inputLine = in.readLine();
			}			
			System.out.println(content);
			in.close();	
			*/
			/*
			String a="http://211.136.87.209/material/fj/dring/2006-08/2724608070128z3F.mp3";
			System.out.println(a);
			FileInputStream finImg = new FileInputStream(a);
			ByteArrayOutputStream boutImg = new ByteArrayOutputStream(); //文件转为字节..
			copy(finImg,boutImg);
			finImg.close();
			*/
		//	System.out.println(boutImg.toByteArray().length);
			
		} catch (Exception e) {
			System.out.println("exception :"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
