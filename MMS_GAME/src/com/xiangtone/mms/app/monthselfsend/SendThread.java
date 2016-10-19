/*
 * Created on 2006-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.mms.app.monthselfsend;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import com.xiangtone.mms.MmsSend;
import com.xiangtone.sql.MysqlDB;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendThread extends Thread{
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private Socket socket=null;
	private int Ires=0;
	final static private String leaguename="mms_all";
	final static private String leaguepwd="mms_all";
	final static private String league_id="6000";
	private MysqlDB db=null;
	public SendThread(Socket socket) {
		this.socket=socket;
		
	}
	public  void run()  {
		try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("IOException e..."+e);
				Ires=-1;
				try {
					out.write("彩信发送失败,请重新发送".getBytes(),0,"彩信发送失败,请重新发送".getBytes().length);
					in.close();
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		if (Ires==0) {
			
			db=new MysqlDB("league");
			String str=getInput(in);
			try {
				out.write("开始发送彩信".getBytes(),0,"开始发送彩信".getBytes().length);
				out.close();
				in.close();
				
			} catch (Exception e) {
				System.out.println("exception e===="+e);
			}
			System.out.println("=============="+str+"============");
			StringTokenizer st=new StringTokenizer(str,",");
			while (st.hasMoreTokens()) {
				String phone=st.nextToken();
				System.out.println("phone===="+phone);
				try {
					GetCode(phone);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			try {
				db.close();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	private void GetCode(String mobile) throws Exception{
		
		ResultSet rs=null;
	
			String sql="select service_id from tb_service_custom where status=1 and mobile_id='"+mobile+"'";
			rs=db.execQuery(sql);
			while (rs.next()) {
				SendMT(mobile,mobile,"","",rs.getString("service_id"));
				sleep(3*1000);
			}
			
		
		
	}
	
	private static void SendMT(String cpn, String dst_mobile, String input, String linkid, String servicecode) {
		// TODO Auto-generated method stub
	
		MmsSend ms=new MmsSend();
		try {
			System.out.println("send begin");
			ms.setMes(9,leaguename);
			ms.setMes(10,ms.getMD5buf(leaguepwd));
			ms.setMes(11,league_id);

		
			ms.setMes(12,"翔通彩信!");
			String diyText="[一个聪明办法]\r一家中学校长面临着一个问题,校内年长的女学生开始擦口红.当她们在洗手间里擦口红时,她们会将嘴唇印在镜子上留下唇印.在这个问题变得不可收拾之前,他想到一个方法阻止.于是他召集所有擦口红的女生并要她们下午２点在洗手间集合.当女孩们在２点到洗手间时发现校长及舍监已在那等候.校长对她们解释这个问题让舍监每天晚上都得清理洗手间的镜子.他认为女孩们并不了解问题的严重性所以他要她们自己目睹镜子有多难清理.接着舍监便开始示范.舍监由盒内拿出了一把长柄刷子,拿到最近的马桶里沾水后,接着走到镜子前面开始刷洗镜子.那以后再也没人把唇印留在镜子上.\r\n[贵族幼儿园]\r小莉达报考某贵族幼儿园,面试时教师取出一张10元纸币问:这是什么?小莉达:是妈妈给乞丐的废纸.好,教师说,你录取了.\r\n\r\n计费代码=="+servicecode+"==send mms by @xiangtone";
			ms.setMes2(14,diyText,"a.txt");
			
			ms.setMes(21,cpn);
			ms.setMes(17,cpn);
			ms.setMes(20,servicecode);
		//	ms.setMes(22,linkid);
		//	ms.setMes(23,"8887"+servicecode);
			String res=ms.sendMeg("http://192.168.1.7:2000/mmLeague");
			System.out.println("res:"+res+"====cpn="+cpn+"===servicecode="+servicecode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	static public String getInput(DataInputStream ins) {
		String str="";
		
		int len=0;
		try {
			len=ins.available();
			System.out.println("len::"+len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] buf = new byte[len];
		System.out.println(buf.length);
		int i = 0;
		try {
		
			synchronized (ins){
			i = ins.read(buf);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		str = new String(buf, 0, i);

		return str;
		
	}
	public static void main(String[] args) {
	}
}
