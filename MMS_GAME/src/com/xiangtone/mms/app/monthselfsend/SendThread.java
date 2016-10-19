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
					out.write("���ŷ���ʧ��,�����·���".getBytes(),0,"���ŷ���ʧ��,�����·���".getBytes().length);
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
				out.write("��ʼ���Ͳ���".getBytes(),0,"��ʼ���Ͳ���".getBytes().length);
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

		
			ms.setMes(12,"��ͨ����!");
			String diyText="[һ�������취]\rһ����ѧУ��������һ������,У���곤��Ůѧ����ʼ���ں�.��������ϴ�ּ�����ں�ʱ,���ǻὫ�촽ӡ�ھ��������´�ӡ.����������ò�����ʰ֮ǰ,���뵽һ��������ֹ.�������ټ����в��ں��Ů����Ҫ�������磲����ϴ�ּ伯��.��Ů�����ڣ��㵽ϴ�ּ�ʱ����У������������ǵȺ�.У�������ǽ���������������ÿ�����϶�������ϴ�ּ�ľ���.����ΪŮ���ǲ����˽������������������Ҫ�����Լ�Ŀ�þ����ж�������.�������㿪ʼʾ��.����ɺ����ó���һ�ѳ���ˢ��,�õ��������Ͱ��մˮ��,�����ߵ�����ǰ�濪ʼˢϴ����.���Ժ���Ҳû�˰Ѵ�ӡ���ھ�����.\r\n[�����׶�԰]\rС��ﱨ��ĳ�����׶�԰,����ʱ��ʦȡ��һ��10Ԫֽ����:����ʲô?С���:���������ؤ�ķ�ֽ.��,��ʦ˵,��¼ȡ��.\r\n\r\n�ƷѴ���=="+servicecode+"==send mms by @xiangtone";
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
