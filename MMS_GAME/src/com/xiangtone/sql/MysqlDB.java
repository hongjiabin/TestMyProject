/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.sql;
import java.sql.*;
import com.xiangtone.util.Log;
/**
 * Document:MysqlDB.java
 * Description:���ݿ⴦����
 * Copyright:    Copyright (c) 2006
 * Company: xiaingtone
 * Created:2006-7-6 14:30:00
 * @author: cyhq
 * @version:
 * 
 */

public class MysqlDB
{

	String sDBDriver="org.gjt.mm.mysql.Driver";
	/**���ü�¼��־��·��*/
//	String dbpath="/";
	Connection conn=null;
	ResultSet rs=null;
	Statement stmt=null;
	/**���ü�¼��־�Ĵ�������Ա���ٴӴ�����־�ж�λ������*/
	private static String type="com.xiang.sql.MysqlDB";
	public String dbip;
	public int dbport;
	public String dbname;
	public String dbuser;
	public String dbpwd;
	private static final boolean debug=false;
	
	String DBCon="";
	
	public void setDB_dbip(String _ip){this.dbip = _ip;}
	public void setDB_dbport(int _port){this.dbport = _port;}
	public void setDB_dbname(String _dbname){this.dbname = _dbname;}
	public void setDB_dbuser(String _dbuser){this.dbuser = _dbuser;}
	public void setDB_dbpwd(String _dbpwd){this.dbpwd =_dbpwd;}
	/**
	 * ͨ�����ݽ����Ĳ���ȥ���������ļ����Ӷ�ȡ���������ݿ��һЩ����
	 * ͬʱҲ�������ݿ�����
	 * @param poolName
	 */
 	public MysqlDB(String poolName)
 	{
 		this.dbip = MMSGameConfig.getConfigItem(poolName+"_dbip");
 		this.dbport =Integer.parseInt(MMSGameConfig.getConfigItem(poolName+"_dbport"));
 		this.dbname = MMSGameConfig.getConfigItem(poolName+"_dbname");
 		this.dbuser = MMSGameConfig.getConfigItem(poolName+"_dbuser");
 		this.dbpwd = MMSGameConfig.getConfigItem(poolName+"_dbpwd");
 		if (debug) {
 			System.out.println("this.dbip:"+this.dbip);
 			System.out.println("this.dbport:"+this.dbport);
 			System.out.println("this.dbname:"+this.dbname);
 			System.out.println("this.dbuser:"+this.dbuser);
 			System.out.println("this.dbpwd:"+this.dbpwd);
 		}
 		try
  		{
   			 Class.forName(sDBDriver);
        }
  		catch(java.lang.ClassNotFoundException e)
  		{
  			if (debug) {
  				System.out.println("Unable to load driver:"+e.getMessage());
  			}
  			Log.showLog(type+":::Unable to load driver:" + e.getMessage(),Log.logPath);
  			e.printStackTrace();
  		}
  		DBCon="jdbc:mysql://"+this.dbip+":"+this.dbport+"/"+this.dbname+"?";
 		DBCon+="user="+ this.dbuser+"&password="+this.dbpwd+"&useUnicode=true&characterEncoding=gb2312";
  //      System.out.println("SDBCon:"+DBCon);
        try
    	{
    		conn=DriverManager.getConnection(DBCon); 
    		stmt=conn.createStatement();
    	 	
    	 	
     	} catch (SQLException e) {
     		if (debug) {
  				System.out.println(":::SQLException:::by connect db:"+e);
  			}
     		Log.showLog(type+":::SQLException:::by connect db" + e,Log.logPath);
     	}
  	}
	
   
   /**
	*update .insert,delete �Ȳ���
	*
	*/
  	public int execUpdate(String sql)
  	{
  		int k=0;
    	try
    	{
    	 	k=stmt.executeUpdate(sql);
    	 	
     	}
	catch(SQLException ex)
	{
		System.out.println("SQLException ex::"+ex);
		Log.showLog(type+":::aq.executeInsert:" + ex.getMessage(),Log.logPath);
	 	Log.showLog(type+":::aq.executeInsert:" + sql,Log.logPath);
	 	ex.printStackTrace();
	 	return 0;
	}
    	
    		return k;
    	
    			
   }  
	
   /**
	*select ���ݲ���
	*
	*/
 	public ResultSet execQuery(String sql) 
  	{
 		 rs=null;
  		 try 
  		 {
   			rs=stmt.executeQuery(sql);
   		 }
    	 catch(SQLException ex)
    	 {
    	// 	Log.showLog(tool.getTime("yyyy-MM-dd HH:mm:ss")+type,dbpath);
    		Log.showLog(type+":::aq.executeQuery:" + ex.getMessage(),Log.logPath);
    		Log.showLog(type+":::aq.executeQuery: " + sql,Log.logPath);
    		ex.printStackTrace();
    	 }
    	return rs;
   }       	
	/**
	*�ر���������
	*
	*/ 
   public void close() throws Exception 
   {
		if (stmt != null)  
		{
			
			try
			{
				stmt.close();
				System.out.println("stmt close ok");
			}
			catch(SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		if(conn !=null)
		{
			try
			{
				conn.close();
				System.out.println("conn close ok");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}	
		}
	}
 }   