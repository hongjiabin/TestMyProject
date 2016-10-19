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

public class OutputInfo2 {
	public OutputInfo2() {
		
	}
	public void getUser(String Ym) {
		String sql="select src_phone,exchange_time,success_time,exchange_status,price,messageid,linkid,service_code from mms_transaction_detail_succ where league_id='1078' and left(exchange_time,7)='2012-04' order by exchange_id asc";
		

		System.out.println("sql=="+sql);
		MysqlDB db=new MysqlDB("league");
		ResultSet rs=db.execQuery(sql);
	
		StringBuffer sb=new StringBuffer();
		sb.append("手机号码,发送时间,接收时间,状态,资费,messageid,linkid,业务代码\r");
		try {
			while (rs.next()) {
				String cpn=rs.getString("src_phone");
				String exchange_time=rs.getString("exchange_time");
				String success_time=rs.getString("success_time");
				String exchange_status=rs.getString("exchange_status");
				String price=rs.getString("price");
				String messageid=rs.getString("messageid");
				String linkid=rs.getString("linkid");
				String service_code=rs.getString("service_code");
			
					
				sb.append(cpn+","+exchange_time+","+success_time+","+exchange_status+","+price+","+messageid+","+linkid+","+service_code+"\r");
			}
			sql="select src_phone,exchange_time,success_time,exchange_status,price,messageid,linkid,service_code from mms_transaction_detail where league_id='1078' and left(exchange_time,7)='2012-04' order by exchange_id asc";
			rs=db.execQuery(sql);
			while (rs.next()) {
				String cpn=rs.getString("src_phone");
				String exchange_time=rs.getString("exchange_time");
				String success_time=rs.getString("success_time");
				String exchange_status=rs.getString("exchange_status");
				String price=rs.getString("price");
				String messageid=rs.getString("messageid");
				String linkid=rs.getString("linkid");
				String service_code=rs.getString("service_code");
			
					
				sb.append(cpn+","+exchange_time+","+success_time+","+exchange_status+","+price+","+messageid+","+linkid+","+service_code+"\r");
			}
			PrintWriter pw=new PrintWriter(new FileWriter("/home/xtwebdevp/data/com/xiangtone/mms/test/1078.txt", true), true);
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
		OutputInfo2 OC=new OutputInfo2();
		OC.getUser(args[0]);
	}
}
