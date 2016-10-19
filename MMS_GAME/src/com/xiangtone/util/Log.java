/**
*@(#)Log.java 2006-7-6 15:31
*All Rights Reserved 
*CopyRight@Xiamen Xiangtone infomation Technology Co.,LTD
*/
package com.xiangtone.util;
import java.io.*;
import java.util.Date;
//import org.gjt.mm.mysql.*;
/**
*
*
*/
public class Log
{  
	
//	public static String configPath="/home/mmsdevp/code/www/webapps/WEB-INF/classes/com/xiangtone/mms/config/";
	public static String logPath="/home/xtwebdevp/data/logs/";
	public static String reportLogPath="/home/xtwebdevp/data/logs/report/";
	private static boolean DEBUG = true;//true;//调试标志位
	private static final String fp = File.separator;
	
	//null constructor
	protected Log()
	{
	}
	//
	/**
	*debug function
	*@parm String msg
	*/
	public static void debug(String msg)
	{
		if(DEBUG)
		{
			System.out.println("DEBUG_INFO:" + msg);
	//		LOG.showLog(msg);
		}
	}
	
	
	/**
	*该函数根据timeFormat的格式返回响应格式的时间
	*@method getTime()
	*@param String timeFormat
	*@return String 
	*@time 2004-7-21 22:34
	*/
	public static synchronized String getTime(String timeFormat)
	{   
		long second = System.currentTimeMillis();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(timeFormat);
		return sdf.format(new java.util.Date(second)).toString();

	}


	/**
	*makeDir(String path,int kind)
	*@parm String path int kind
	*@return true or false
	*@time 2004-04-30 15:00
	*/
	public static synchronized boolean makeDir(String path,int kind)
	{
		if(kind == 1)
			path = path + fp + getTime("yyyy") + fp + getTime("MM") + fp + getTime("dd") ;	
		File cf = new File(path);
		if(!cf.exists())
		{
			try
			{
				if(cf.mkdirs())
					return true;
				return false;
			}
			catch(SecurityException e)
			{
				debug(" makedir fialed");
				return false;
			}
			catch(Exception e)
			{
				debug(e.getMessage());
				return false;
			}
		}
		return true;
	}
	/**
	*createFile(String path,kind)
	*@parm String path:the URI int kind:the name of the folder
	*@return true or false
	*@author sheep
	*@time 2004-04-30 14:57
	*/
	public static synchronized boolean createFile(String path,int kind)
	{
		if(kind == 1)	
			path += fp + getTime("yyyy") + fp + getTime("MM") + fp + getTime("MM");
		File cf = new File(path);
		
		
		if(cf.exists())
		{
			try
			{
				if(cf.createNewFile())
					return true;
				return false;
			}
			catch(IOException e)
			{
				debug("fialed:" + e.getMessage());
				return false;
			}
		}
		return true;
	}
/**
 * 以yyyy-MM-dd'T'HH:mm:ss的形式格式化时间
 * @return
 */
	public static Date getTimeStamp()
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try
		{
			return sdf.parse(getTime("yyyy-MM-dd'T'HH:mm:ss"));
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
			return null;
		}
	}
	

	
	/**
	 * 记录日志，传入参数为记录的数据和保存的位置
	 * example: showLog("adfsd","/home/wap/");
	 * @param value
	 * @param paths
	 */
	
	public static synchronized void showLog(String value,String paths)
	{
		String path=paths;
		makeDir(path,0);
		
		String out_data=getTime("yyyy_MM_dd");
		String filename="";
		File outShow=null;
		PrintWriter log=null;
		int filesn=1;
		while (true)
		{
			filename=path+out_data+"_"+String.valueOf(filesn)+".txt";
			
			outShow =new File(filename);
		//	PrintWriter log = null;
			if (outShow.exists())
			{
				if (outShow.length()>10*1024*1024)
				{
					filesn++;
				}
				else
				{
					try 
					{
					
						log = new PrintWriter(new FileWriter(filename, true), true);
						log.println(getTime("HH:mm:ss")+"    "+value);
				//		log.println(value);
					//	log.println(" ");
						break;
					}
					catch (Exception e)
					{
						log = new PrintWriter(System.err);
						break;
					}
				}
			}
			else
			{
				try 
				{
					log = new PrintWriter(new FileWriter(filename, true), true);
					log.println(getTime("HH:mm:ss")+"    "+value);
				//	log.println(value);
				//	log.println(" ");
				//	log.println("\r\n".getBytes("GBK"));
					break;
				}
				catch (Exception e)
				{
					log = new PrintWriter(System.err);
					break;
				}
			}
		}
	}
	
	
	
	
	
	/**
	 * 记录日志，传入参数为记录的数据和保存的位置
	 * example: showLog("adfsd","/home/wap/");
	 * @param value
	 * @param paths
	 */
	
	public static synchronized void showLog(String value,String paths,String name)
	{
		String path=paths;
		makeDir(path,0);
		
		String out_data=getTime("yyyy_MM_dd");
		String filename="";
		File outShow=null;
		PrintWriter log=null;
		int filesn=1;
		while (true)
		{
			filename=path+name+".txt";
			
			outShow =new File(filename);
		//	PrintWriter log = null;
			if (outShow.exists())
			{
				if (outShow.length()>10*1024*1024)
				{
					filesn++;
				}
				else
				{
					try 
					{
					
						log = new PrintWriter(new FileWriter(filename, true), true);
						log.println(getTime("HH:mm:ss")+"    "+value);
					//	log.println(value);
					//	log.println(" ");
						break;
					}
					catch (Exception e)
					{
						log = new PrintWriter(System.err);
						break;
					}
				}
			}
			else
			{
				try 
				{
					log = new PrintWriter(new FileWriter(filename, true), true);
					log.println(getTime("HH:mm:ss")+"    "+value);
				//	log.println(value);
				//	log.println(" ");
				//	log.println("\r\n".getBytes("GBK"));
					break;
				}
				catch (Exception e)
				{
					log = new PrintWriter(System.err);
					break;
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		debug(getTimeStamp().toString());
		
	}
	
}
