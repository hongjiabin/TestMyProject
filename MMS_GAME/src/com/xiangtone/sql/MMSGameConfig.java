/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sql;
//import mms.util.*;
/**
 *参数类
 *
 */
public class MMSGameConfig
{
	
	/**
	 *@param item
	 *@return itemVal
	 */
	static String getConfigItem(String item)
	{
		return (String)ConfigManager.getInstance().getConfigItem(item,item+" not found!");
	}
	

/*
	protected static String getIsmg_spCode(String ismgId)
	{
		String str = "8996";
		if(ismgId.equals("01"))
		{
			str="bj_ismg_spCode";
		}
		else if(ismgId.equals("06"))
		{	
			str="ln_ismg_spCode";
		}
		else if(ismgId.equals("08"))
		{
			str="hl_ismg_spCode";
		}
		else if(ismgId.equals("15"))
		{
			str="sd_ismg_spCode";
		}
		else if(ismgId.equals("19"))
		{
			str="gd_ismg_spCode";
		}
		else
		{
			str="hl_ismg_spCode";
		}
		
		return (String)ConfigManager.getInstance().getConfigItem(str,str+" not found!");
			
		
	}  
	
	*/ 
	/**
	 *
	 *测试用例
	 */
	public static void main(String[] args)
	{
		//MMSConfig confie = new MMSConfig();
	//	System.out.println("test");
		
		try
		{
			while(true)
			{
				System.out.println(MMSGameConfig.getConfigItem("league_dbip"));
				Thread.sleep(2000);
			}
		}
		catch(Exception e)
		{
		}
		
	} 
}