package com.xiangtone.mms.monthselfsend.copy;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class User {

	public Mysqldb db;
	public Mysqldb db2;
	ResultSet rs = null;
	String strsql = "";
	function fun=new function();//时间类
//	ArrayList ArrMobile=new ArrayList();
//	ArrayList ArrCode=new ArrayList();
	public Vector mobileCode = new Vector();
	public User(int k) {
		db = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		
		GetUserInfo(k);
	}
	public User() {
		db = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		GetUserInfo();
	}
	public void GetUserInfo() {
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		strsql="select mobile_id,service_id from mms_month_detail where (status=1 or status=3) ";
		
		strsql+=" and  custom_time >='2010-11-01' and custom_time <='2010-11-31'";
		System.out.println("strsql=="+strsql);
		try {
			rs = db2.executeQuery(strsql);
			while (rs.next()) {
				mobileCode.addElement(rs.getString("mobile_id")+","+rs.getString("service_id"));
			} 
		}catch (Exception e) {
			
		}
	//	db2.dbclose();
	}
	
	
	public void GetUserInfo(int k) {
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
	
		strsql="select oper_code from month_service_info where sendflag=1";
		System.out.println("strsql:"+strsql);
		String tempsql="";
		
		try {
			rs=db.executeQuery(strsql);
			while (rs.next()) {
				tempsql+="or service_id='"+rs.getString("oper_code")+"'";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("tempsql:"+tempsql);
		
	//	strsql="select mobile_id,service_id from tb_service_custom where (status=1 or status=3) and(service_id='123314') order by id asc";
		strsql="select mobile_id,service_id from tb_service_custom where (status=1 or status=3) and(service_id='9999'"+tempsql+")";
		strsql+=" and mobile_id like '%"+k+"' order by id asc";
		/*
		if (k==0) {
			strsql+=" and (mobile_id like '%0' or mobile_id like '%5')";
		} else if (k==1) {
			strsql+=" and (mobile_id like '%1' or mobile_id like '%6')";
		}
		else if (k==2) {
			strsql+=" and (mobile_id like '%2' or mobile_id like '%7')";
		}
		else if (k==3) {
			strsql+=" and (mobile_id like '%3' or mobile_id like '%8')";
		}
		else  {
			strsql+=" and (mobile_id like '%4' or mobile_id like '%9')";
		}
		*/
	//	strsql="select mobile_id,service_id from tb_service_custom where (status=1 or status=3) and service_id!='145301' and service_id!='162301' and service_id!='135300'";
		try {
			System.out.println(strsql);
			rs = db2.executeQuery(strsql);
			while (rs.next()) {
				mobileCode.addElement(rs.getString("mobile_id")+","+rs.getString("service_id"));
	//			this.ArrMobile.add(rs.getString("mobile_id"));
	//			this.ArrCode.add(rs.getString("service_id"));
			} 
		} catch (SQLException e) {
			System.out.println("得到用户信息:" + e.getMessage());
		}
		db2.dbclose();
	}
	

	/***************************************************************************

	 * 判断消户表中是否存在该用户*

	 **************************************************************************/

	

	/***************************************************************************

	 * 	得到发送信息时间,对应的信息编号nday(YYYY-mm-dd)*

	 **************************************************************************/

	public int get_sendtime_nday() {

		int nday=0;

        //得到当前日期

		String now_date=(new function()).getcurrent_sdate();//时间格式为YYYY-mm-dd

		//查询

		strsql = "select days from f_message where left(sendtime,10)='"+now_date+"' and status=1 order by days asc limit 1";//从最近的消息算起

		System.out.println(strsql);

		try {

			rs = db.executeQuery(strsql);

			if (rs.next()) {

				nday = rs.getInt("days");

			}

		} catch (Exception e) {

			System.out.println("得到用户发送时间以应的编号出现异常：" + e.getMessage());

		}

		return nday;

	}


	public boolean getMmsSendStatus(String cpn,String code) {
		strsql="select exchange_id from mms_transaction_detail_succ where src_phone='"+cpn+"' and service_code='"+code+"'";
		try {
			rs = db2.executeQuery(strsql);
			if (rs.next()){
				return true;
			}else {
				strsql="select exchange_id from mms_transaction_detail where src_phone='"+cpn+"' and service_code='"+code+"'";
				rs = db2.executeQuery(strsql);
				if (rs.next())
					return true;
				else
					return false;
					
			}
		}catch (Exception e) {
			System.out.println("exception e=="+e+  "   strsql=="+strsql);
		}
		return false;
	}
	public boolean getSendStatus(String cpn,String code) {
		strsql="select nday from month_send_log  where mobile='"+cpn+"' and code='"+code+"' and date_format(sdtime,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d') order by id desc limit 1";
		try {
			rs = db.executeQuery(strsql);
			if (rs.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			
		}
		return true;
	}
	

	/***************************************************************************

	 * 添加发送出错日志,发送方与接收方设为一致,nday与times一致 *

	 **************************************************************************/

	public String getLastSendDay(String cpn,String code) {

		//添加发送记录
		strsql="select nday from month_send_log  where mobile='"+cpn+"' and code='"+code+"' order by id desc limit 1";
	//	System.out.println("sql::"+strsql);
		String nday="";
		try {
			rs = db.executeQuery(strsql);
			if (rs.next()) {
				nday=rs.getString("nday");
			} else {
				nday="0";
			}
		} catch (SQLException e) {
			System.out.println("得到用户信息:" + e.getMessage());
		}
		strsql="select A.day from month_title as A,month_service_info as B  where A.day>'"+nday+"' and B.oper_code='"+code+"' and B.id=A.serviceid  order by A.day asc limit 1";
		//System.out.println("strsql:::+++++"+strsql);
		try {
			rs = db.executeQuery(strsql);
			if (rs.next()) {
				nday=rs.getString("day");
			} else {
				nday="1";
			}
		} catch (SQLException e) {
			System.out.println("得到用户信息:" + e.getMessage());
		}
	//	System.out.println("get days:::"+strsql);
		return nday;
	}
	/***************************************************************************

	 * 添加发送出错日志,发送方与接收方设为一致,nday与times一致 *

	 **************************************************************************/

	public void insert_log(String cpn, String code,int nday) {

		//添加发送记录

		strsql = "insert into month_send_log (mobile,sender,nday,times,sdtime,code) values('"
				+ cpn+ "','"+ cpn+ "',"+ nday+ ","+ 1 +",now(),'" + code + "')";
		//System.out.println(strsql);
		try {
			db.executeUpdate(strsql);
		} catch (Exception e) {
			System.out.println("添加发送日志出现异常:" + e.getMessage());
		}
	}
	
	/***************************************************************************

	 * 添加发送出错日志,发送方与接收方设为一致,nday与times一致 *

	 **************************************************************************/

	public void insert_log(String cpn, String code,int nday, String regtime) {

		//添加发送记录

		strsql = "insert into month_send_log (mobile,sender,nday,times,sdtime,code) values('"
				+ cpn
				+ "','"
				+ cpn
				+ "',"
				+ nday
				+ ","
				+ nday
				+ ",'"
				+ regtime + "','" + code + "')";
		//System.out.println(strsql);
		try {
			db.executeUpdate(strsql);
		} catch (Exception e) {
			System.out.println("添加发送日志出现异常:" + e.getMessage());
		}
	}

	/***************************************************************************

	 * 测试用的main方法 *

	 **************************************************************************/

	public static void main(String args[]) {

		User test = new User(2);

		//test.update_user_time("13606059851","2006-03-27 17:05:00");

		System.out.println(test.get_sendtime_nday());

		//System.out.println(mobile);

	}

}