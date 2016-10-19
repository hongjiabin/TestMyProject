package com.xiangtone.mms.monthselfsend;

import java.sql.ResultSet;

class Message {
	
	String strsql = null;
	ResultSet rs = null;
	Mysqldb db = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");
	
	//标题内容
	String titcontent = null;
	//相关内容
	String path=null;
	String text_name=null;
	String pic_name=null;
	String ring_name=null;
	String smile_name=null;
	int text_size=0;
	int pic_size=0;
	int ring_size=0;
	int titleid=0;
	
	public Message() {
	}
	/***************************************************************************
	 * 从f_message表中查询title信息*
	 **************************************************************************/	
	public String get_message_title(int sday,String code) {
		strsql = "select A.id,A.title from month_title as A,month_service_info as B where A.day='" + sday + "' and B.oper_code='"+code+"' and B.id=A.serviceid";
		System.out.println("sql=="+strsql);
		try {
			rs = db.executeQuery(strsql);
			if (rs.next()) {
				titcontent = rs.getString("title");
				this.titleid=rs.getInt("id");
			}else {
				strsql = "select A.id,A.title from month_title as A,month_service_info as B where A.day='1' and B.oper_code='"+code+"' and B.id=A.serviceid";
				rs = db.executeQuery(strsql);
				if (rs.next()) {
					titcontent = rs.getString("title");
					this.titleid=rs.getInt("id");
				}
			}
			
		} catch (Exception e) {
			System.out.println("获取messge信息出错:" + e.getMessage());
		//	db = new Mysqldb();
		}
		return titcontent;
	}
	/***************************************************************************
	 * 查询message表中信息*
	 **************************************************************************/
	public void get_message_content(int sday) {
		strsql = "select * from message where position='" + sday + "' and position>0";
		System.out.println(strsql);
		try {
			rs = db.executeQuery(strsql);
			if (rs.next()) {
				path = rs.getString("path");
				text_name = rs.getString("txtname");
				pic_name = rs.getString("picname");
				ring_name = rs.getString("ringname");
				smile_name=rs.getString("smilname");
				text_size=rs.getInt("text_size");
				pic_size=rs.getInt("pic_size");
				ring_size=rs.getInt("ring_size");
			}else{
				path=null;
				text_name=null;
				pic_name=null;
				ring_name=null;
				smile_name=null;
				text_size=0;
				pic_size=0;
				ring_size=0;
			}
		} catch (Exception e) {
			System.out.println("获取messge信息出错:" + e.getMessage());
	//		db = new Mysqldb();
		}
	}
/*------------------------------------------查询s_message表中的数据--------------------------------------------*/
//	public static void main(String args[]) {
//		Message msg = new Message();
//		msg.get_message_content(2);
//		msg.get_message_content(2);
//		System.out.println("pic_name:" + msg.text_size);
//	}
}
