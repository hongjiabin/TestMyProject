package com.xiangtone.mms.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
//import java.util.HashMap;

import com.xiangtone.sql.MysqlDB;

public class OutputInfo {
	public OutputInfo() {
		
	}
	public void getUser(String Ym) {
		
	//	String sql="select mobile_id,custom_time,pause_time,cancel_time,cancel_status from mms_month_detail where custom_time>='"+Ym+"-01' and custom_time<='"+Ym+"-31 23:59:59' and service_id='150301' and closedstatus=0 and autostatus=0";
		String sql="select mobile_id,cancel_time,service_id from mms_month_detail where miscid='0029' and left(cancel_time,7)='2011-12' order by cancel_time asc";
	//	String sql="select count(*) as num,src_phone from mms_transaction_detail"+Ym+" where ";
	//	sql+=" (statustext='1000' or statustext='9999') and service_code='103' and exchange_time>='"+Ym2+"-01 00:00:00'";
	//	sql+=" and exchange_time<='"+Ym2+"-31 23:59:59' and  miscid='0025' group by src_phone";
		System.out.println("sql=="+sql);
		MysqlDB db=new MysqlDB("league");
		ResultSet rs=db.execQuery(sql);
	//	List<String> list = new LinkedList<String>();
	//	Vector vecUser = new Vector();
		StringBuffer sb=new StringBuffer();
		sb.append("手机号码,订购时间,退订时间,计费状态\r");
		try {
			while (rs.next()) {
				String cpn=rs.getString("mobile_id");
				String cancel_time=rs.getString("cancel_time");
				String service_id=rs.getString("service_id");
			
					
				sb.append(cpn+","+cancel_time+",801038,"+service_id+"\r");
			}
		
			PrintWriter pw=new PrintWriter(new FileWriter("/home/xtwebdevp/data/com/xiangtone/mms/test/海南.txt", true), true);
			pw.println(sb.toString());
			pw.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public long compareTime(String timeA,String timeB) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds=0;
		   
		try {
			Date d1 = df.parse(timeA);
			Date d2 = df.parse(timeB);
			 long diff = d1.getTime() - d2.getTime();
			 seconds = diff / 1000;
			 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return seconds;
	}
	public static void main(String[] args) {
		OutputInfo OC=new OutputInfo();
		OC.getUser(args[0]);
	}
}
