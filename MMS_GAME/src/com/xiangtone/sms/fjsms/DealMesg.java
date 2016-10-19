/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.sms.fjsms;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.xiangtone.mms.MmsSend;
import com.xiangtone.sql.MysqlDB;
import com.xiangtone.util.Tool;

/**
 * Document:DealMesg.java
 * Description:处理短信信息，下发彩信
 * Copyright:    Copyright (c) 2006
 * Company: xiaingtone
 * Created:2006-7-6 15:20:00
 */
public class DealMesg {
	ResultSet rs=null;
	String strsql = null;        
	String ret_msg = null;        
	String gateway = null;
	final static private String leaguename="asdfcbb";
	final static private String leaguepwd="testleague";
	final static private String league_id="1000";
	final static private String servicecode="100";
	final static private String linkid="";
	final static private String url="http://192.168.1.6:2000/mmLeague";
	final static private String title="厦门彩信优惠卷";
	private final static boolean debug=true;
	private MysqlDB db=new MysqlDB("fjsms");
	/**
	 * 返回发送的短信信息
	 */
	public String execMain(String mobile,String content,String ismgid,String linkid,String dst_mobile,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector vimgsid,Vector linkidset)
	{
		if (debug) {
        	System.out.println("=============begin============");
        }
		Tool motool=new Tool();
		String res="";
		gateway = ismgid.trim();  
        String cpn = mobile.trim();
        String input  = content.trim().toUpperCase();
        linkid = linkid.trim();
        //log sms
        if (debug) {
        	System.out.println("=============begin22222============");
        }
        insertInfo(cpn,dst_mobile,input,linkid);
        if (debug) {
        	System.out.println("=============begin33333333============");
        }
        if((!input.equals(""))&&(input!=null)){         //
        	if (motool.checkDigit(input)) {  //判断是否为数字
        		String strsql = "select pic_name,add_time,description from xmamoy_up where pic_code='"+input+"' and status=1 limit 1";
        		ResultSet rs=db.execQuery(strsql);
        		try {
					if (rs.next()) {
						sendMesg(cpn,input,true);
						res="我们将为您下发一条彩信优惠信息";
					} else {
						sendMesg(cpn,input,false);
						res="对不起，您上行的编号不存在，我们将为您随机下发一条优惠信息！";
					}
				} catch (SQLException e) {
					sendMesg(cpn,input,false);
					res="对不起，您上行的编号有误，我们将为您随机下发一条优惠信息！";
					e.printStackTrace();
				}
        	} else {
        		sendMesg(cpn,input,false);
            	res="对不起，您上行的编号有误，上行的内容不全为数字，我们将为您随机下发一条优惠信息！";
        	}
        } else {
        	sendMesg(cpn,input,false);
        	res="对不起，您上行的编号有误，我们将为您随机下发一条优惠信息！";
        }
        try {
			db.close();
		} catch (Exception e) {
			if (debug) {
	        	System.out.println("e::"+e);
	        }
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (debug) {
        	System.out.println("res::"+res);
        }
	    return res;
	}
	
	/**
	 *发送彩信，为false是随机取一条信息
	 */
	private void sendMesg(String cpn, String input, boolean b) {
		// TODO Auto-generated method stub
		String strsql="";
		String pic_name="";
		String description="";
		String addtime="";
		String imgPath="";
		if (b) {
			strsql="select pic_name,add_time,description from xmamoy_up where pic_code='"+input+"' and status=1 limit 1";
		} else {
			strsql="select pic_name,add_time,description from xmamoy_up where status=1 order by rand() limit 1";
		}
		ResultSet rs=db.execQuery(strsql);
		try {
			if (rs.next()) {
				description=rs.getString("description");
				pic_name = rs.getString("pic_name");
		        addtime=rs.getString("add_time");
		        String monthpath=addtime.substring(0,4)+"/"+addtime.substring(5,7);//取月份信息
		        //   图片存储目录
		        imgPath ="/home/www/nonomomo.com/material/new-nomo/xmamoy/"+monthpath+"/"+pic_name;
			}
		} catch (SQLException e) {
			if (debug) {
	        	System.out.println("SQLException e::"+e);
	        }
			e.printStackTrace();
		}
		String smil=getSmil("a.gif","a.txt");
		MmsSend ms=new MmsSend();
		try {
			ms.setMes(9,leaguename);
			ms.setMes(10,ms.getMD5buf(leaguepwd));
			ms.setMes(11,league_id);
			ms.setMes(12,title);
			ms.setMes2(13,smil,"a.smil");
			ms.setMes(14,imgPath,"a.gif");
			ms.setMes2(16,description,"a.txt");
			ms.setMes(21,cpn);
			ms.setMes(17,cpn);
			ms.setMes(20,servicecode);
			ms.setMes(22,linkid);
			ms.sendMeg(url);
		} catch (Exception e) {
			if (debug) {
	        	System.out.println("  Exception e::"+e);
	        }
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 生成一个smil文件
	 * @return
	 */
	private String getSmil(String name1, String name2) {
		String smil="";
		smil="<smil><head><layout><root-layout height=\"160px\" width=\"120px\" />\r";
		smil+="<region id=\"text\"  height=\"128px\"  width=\"128px\" left=\"0px\" top=\"128px\" fit=\"hidden\" />\r";
		smil+="<region id=\"Image\"  height=\"128px\"  width=\"128px\" left=\"0px\" top=\"0px\" fit=\"hidden\" />\r";
		smil+="</layout></head>\r";
		smil+="<body><par dur=\"60000ms\"><img src=\"a.gif\" type=\"image/gif\" region=\"Image\" />\r";
		smil+="<text src=\"a.txt\" type=\"text/plain\" region=\"text\" />\r";
		smil+="</par></body></smil>";
		return smil;
	}

	/**
	 * 把接收到的短信加入到数据库中
	 */
	private void insertInfo(String cpn, String dst_mobile, String input, String linkid) {
		// TODO Auto-generated method stub
		String sql="insert into xmamoy_sms_up  set content='"+input+"',sender='"+cpn+"',toer='"+dst_mobile+"',linkid='"+linkid+"',datetime=now()";
		System.out.println(sql);
		try {
			db.execUpdate(sql);
		} catch (Exception e) {
			if (debug) {
				System.out.println("inser sql exception :"+e);
			}
		}
	}

	/**
	 * @param cpn
	 * @param input
	 * @param linkid
	 * @param mt
	 * @param url
	 * @param socketip
	 * @param sendstatus
	 * @return
	 */
	//		res=Dispath(cpn,input,linkid,servicecode,url,socketip,sendstatus);
	private int dispath(String cpn,String dst_mobile, String input, String linkid, String servicecode, String url, String socketip, int sendstatus) {
		/*
		 * sendstatus
		 * ==1直接转发到url,平台不发送彩信
		 * ==2直接转发到url,平台也发送彩信
		 * ==3直接socket,平台不发送彩信
		 * ==4直接socket,平台也发送彩信
		 * ==0或者其它,平台直接发送彩信
		 */
	//	sendleague s1=;
	//	sendleague sl;
	//	SendLeagueSocket s2;
		/*
		switch (sendstatus) {
			case 1:
				new sendleague(cpn,dst_mobile,input,linkid,servicecode,url).start();
		//		sl.start();
				break;
			case 2:
				new sendleague(cpn,dst_mobile,input,linkid,servicecode,url).start();
	//			sl.start();
	//			SendMT();		
				break;
			case 3:
			//	s2=
				new SendLeagueSocket(cpn,dst_mobile,input,linkid,servicecode,socketip).start();
				break;
			case 4:
				new SendLeagueSocket(cpn,dst_mobile,input,linkid,servicecode,socketip).start();
				break;
			default:
				SendMT(cpn,dst_mobile,input,linkid,servicecode);
				break;
		}
		*/
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param cpn
	 * @param dst_mobile
	 * @param input
	 * @param linkid
	 * @param servicecode
	 */
	private void SendMT(String cpn, String dst_mobile, String input, String linkid, String servicecode) {
		// TODO Auto-generated method stub
	/*
		motool mtl=new motool();
		try {
			mtl.SetMes(9,leaguename);
			mtl.SetMes(10,mtl.GetMD5buf(leaguepwd));
			mtl.SetMes(11,league_id);
			mtl.SetMes(12,"厦门翔通欢迎你!");
			mtl.SetMes(16,"D:\\www\\www\\test\\mms\\smil\\kkk.gif","a.gif");
			mtl.SetMes(21,cpn);
			mtl.SetMes(17,cpn);
			mtl.SetMes(20,servicecode);
			mtl.SetMes(22,linkid);
		//	mtl.SetMes(23,"88878887");
			mtl.SendMeg("http://211.136.87.229:2000/mmLeague");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	public static void main(String[] args) {
	}
}

