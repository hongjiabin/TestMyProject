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
 * Description:���������Ϣ���·�����
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
	final static private String title="���Ų����Żݾ�";
	private final static boolean debug=true;
	private MysqlDB db=new MysqlDB("fjsms");
	/**
	 * ���ط��͵Ķ�����Ϣ
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
        	if (motool.checkDigit(input)) {  //�ж��Ƿ�Ϊ����
        		String strsql = "select pic_name,add_time,description from xmamoy_up where pic_code='"+input+"' and status=1 limit 1";
        		ResultSet rs=db.execQuery(strsql);
        		try {
					if (rs.next()) {
						sendMesg(cpn,input,true);
						res="���ǽ�Ϊ���·�һ�������Ż���Ϣ";
					} else {
						sendMesg(cpn,input,false);
						res="�Բ��������еı�Ų����ڣ����ǽ�Ϊ������·�һ���Ż���Ϣ��";
					}
				} catch (SQLException e) {
					sendMesg(cpn,input,false);
					res="�Բ��������еı���������ǽ�Ϊ������·�һ���Ż���Ϣ��";
					e.printStackTrace();
				}
        	} else {
        		sendMesg(cpn,input,false);
            	res="�Բ��������еı���������е����ݲ�ȫΪ���֣����ǽ�Ϊ������·�һ���Ż���Ϣ��";
        	}
        } else {
        	sendMesg(cpn,input,false);
        	res="�Բ��������еı���������ǽ�Ϊ������·�һ���Ż���Ϣ��";
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
	 *���Ͳ��ţ�Ϊfalse�����ȡһ����Ϣ
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
		        String monthpath=addtime.substring(0,4)+"/"+addtime.substring(5,7);//ȡ�·���Ϣ
		        //   ͼƬ�洢Ŀ¼
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
	 * ����һ��smil�ļ�
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
	 * �ѽ��յ��Ķ��ż��뵽���ݿ���
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
		 * ==1ֱ��ת����url,ƽ̨�����Ͳ���
		 * ==2ֱ��ת����url,ƽ̨Ҳ���Ͳ���
		 * ==3ֱ��socket,ƽ̨�����Ͳ���
		 * ==4ֱ��socket,ƽ̨Ҳ���Ͳ���
		 * ==0��������,ƽֱ̨�ӷ��Ͳ���
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
			mtl.SetMes(12,"������ͨ��ӭ��!");
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

