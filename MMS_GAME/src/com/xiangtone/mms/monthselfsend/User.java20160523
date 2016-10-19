package com.xiangtone.mms.monthselfsend;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	//	GetUserMiscid("0005");
	}
	
	
	public User(String startDatetime,String endDatetime) {
		db = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		GetUserInfo(startDatetime,endDatetime);
		
	//	GetUserMiscid("0005");
	}
	public User(String code){
		db = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		GetUserCode(code);
	}
	
	public void GetUserCode(String code){
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		strsql="select mobile_id,service_id from mms_league.mms_month_detail where status=1 and service_id='"+code+"' ";
		System.out.println("strsql==="+strsql);
		try {
			rs=db.executeQuery(strsql);
			while (rs.next()) {
				boolean addStat=false;
				String mobile_id=rs.getString("mobile_id");
				String service_id=rs.getString("service_id");
//				strsql="select exchange_status from mms_league.mms_transaction_detail where src_phone='"+mobile_id+"' order by exchange_id desc limit 1";
//				ResultSet rs2=db2.executeQuery(strsql);
//				if (rs2.next()) {
//					if (rs2.getInt("exchange_status")==0)
//						addStat=true;
//					else
//						addStat=false;
//				}else {
//					strsql="select exchange_status from mms_league.mms_transaction_detail_succ where src_phone='"+mobile_id+"' order by exchange_id desc limit 1";
//					rs2=db2.executeQuery(strsql);
//					if (rs2.next()) {
//						if (rs2.getInt("exchange_status")==0)
//							addStat=true;
//						else
//							addStat=false;
//					}else{
//						 addStat=false;
//					}
//				}
//				if (addStat) {
					mobileCode.addElement(mobile_id+","+service_id);
			//	}
				
			}
		}catch (Exception e) {
			
		}finally {
			db2.dbclose();
		}
	}
	
	public void GetUserMiscid(String miscid){
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		strsql="select mobile_id,service_id from mms_league.mms_month_detail where status=1 and miscid='"+miscid+"' and service_id!='102302'";
		System.out.println("strsql==="+strsql);
		try {
			rs=db.executeQuery(strsql);
			while (rs.next()) {
				boolean addStat=false;
				String mobile_id=rs.getString("mobile_id");
				String service_id=rs.getString("service_id");
				strsql="select exchange_status from mms_league.mms_transaction_detail where src_phone='"+mobile_id+"' order by exchange_id desc limit 1";
				ResultSet rs2=db2.executeQuery(strsql);
				if (rs2.next()) {
					if (rs2.getInt("exchange_status")==0)
						addStat=true;
					else
						addStat=false;
				}else {
					strsql="select exchange_status from mms_league.mms_transaction_detail_succ where src_phone='"+mobile_id+"' order by exchange_id desc limit 1";
					rs2=db2.executeQuery(strsql);
					if (rs2.next()) {
						if (rs2.getInt("exchange_status")==0)
							addStat=true;
						else
							addStat=false;
					}else{
						 addStat=false;
					}
				}
				if (addStat) {
					mobileCode.addElement(mobile_id+","+service_id);
				}
				
			}
		}catch (Exception e) {
			
		}finally {
			db2.dbclose();
		}
	}
	
	
	
	public void GetUserInfo(String startDatetime,String endDatetime) {
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		strsql="select mobile_id,service_id from mms_month_detail where (status=1 or status=3) ";
	//	strsql+="and service_id='150301'";
		strsql+=" and custom_time>='2011-11-15 00:00:00' and custom_time<='2011-11-22 10:00:00'";
		System.out.println("strsql=="+strsql);
		try {
			rs = db2.executeQuery(strsql);
			while (rs.next()) {
				mobileCode.addElement(rs.getString("mobile_id")+","+rs.getString("service_id"));
			} 
		}catch (Exception e) {
			
		}
		db2.dbclose();
	}
	
	
	
	
	public void GetUserInfo() {
		db2 = new Mysqldb("jdbc:mysql://192.168.1.9/mms_league?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
		strsql="select mobile_id,service_id from mms_month_detail where (status=1 or status=3) ";
	//	strsql+="and service_id='150301'";
		strsql+=" and (mobile_id='15884869796' or mobile_id='13619518093' or mobile_id='13503260597' ) ";
		System.out.println("strsql=="+strsql);
		try {
			rs = db2.executeQuery(strsql);
			while (rs.next()) {
				mobileCode.addElement(rs.getString("mobile_id")+","+rs.getString("service_id"));
			} 
		}catch (Exception e) {
			
		}
		db2.dbclose();
	}
	
	public String getLastMonth(int month){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH,c.get(Calendar.MONTH)-month);
		
		String newYmd=format.format(c.getTime());
		System.out.println(newYmd); 
		return newYmd;
	}

	
	public boolean getSendSuccStat(String cpn){
		String lastMonth=getLastMonth(1);
		strsql="select exchange_status from mmscopy.mms_transaction_detail"+lastMonth+" where src_phone='"+cpn+"' order by  exchange_status asc limit 1 ";
		System.out.println(strsql); 
		rs=db.executeQuery(strsql);
		try {
			if (rs.next()){
				if (rs.getString("exchange_status").equals("0")) 
					return true;
				else
					System.out.println("exchange_status==="+rs.getString("exchange_status"));
			}else {
				return true;
			}
		}catch (Exception e) {
			
		}
		
	//	strsql="select "
		return false;
	}
	public String  getMiscid(String cpn) {
		String miscid="";
		String newcpn=cpn.substring(0,7);
		strsql="select miscid from mms_league.mobile_miscid_cmcc where mobile='"+newcpn+"'";
		rs=db.executeQuery(strsql);
		try {
			if (rs.next()) {
				miscid=rs.getString("miscid");
			}
		}catch (Exception e) {
			
		}
		return miscid;
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
		
		if (k==1){
			strsql="select mobile_id,service_id from tb_service_custom where ((status=1 or status=3) and (service_id='9999'"+tempsql+")";
			strsql+=" and mobile_id like '%"+k+"') or (miscid='0005' and status=1 and (service_id='9999'"+tempsql+")) order by id asc";
		}else{
			strsql="select mobile_id,service_id from tb_service_custom where (status=1 or status=3) and(service_id='9999'"+tempsql+")";
			strsql+=" and mobile_id like '%"+k+"' and miscid!='0005' order by id asc";
		}
	
		try {
			System.out.println(strsql);
			rs = db2.executeQuery(strsql);
			while (rs.next()) {
				mobileCode.addElement(rs.getString("mobile_id")+","+rs.getString("service_id"));

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
		System.out.println(test.getMiscid("13950000050"));
		System.out.println(test.getSendSuccStat("13950000050"));
		//test.update_user_time("13606059851","2006-03-27 17:05:00");

    //	System.out.println(test.get_sendtime_nday());

		//System.out.println(mobile);

	}

}