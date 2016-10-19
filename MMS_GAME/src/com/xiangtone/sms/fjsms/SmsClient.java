/**
 * Created on 2006-7-6
 *Copyright 2006 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
*/
package com.xiangtone.sms.fjsms;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

//import mms.fjsms.dispose;
//import mms.fjsms.sms_client;
import com.xiangtone.util.MD5;
/**
 * Document:SmsClient.java
 * Description:连接公司短信平台客户端，下发短信
 * Copyright:    Copyright (c) 2006
 * Company: xiaingtone
 * Created:2006-7-6 14:50:00
 */
public class SmsClient {
	//	应用的标识
	static final int apdid=1028;
	//	运营者标识
	static final int operatorid=1;
	//应用的连接用户名
	static final String loginname="sellmonth";
	//	应用的产生的密码
	static final String secre="yuejingpai2005";
	static private int app_random;
	static private int seqno=-1;
	static private byte[] send_mesg;
	static private byte[] last_mesg;
	static private int windows_num=5;
	static private boolean send_sign=true;
	static private boolean Req_sign=false;
	static private boolean forbidden_write = false;
	static private int check_messagetype;
	static private int send_type;
	static private String dst_phone;
	static private String fee_phone;
	static private String send_content;
	static final String sp_num="0551171";
	
	static Socket cli=null;
	static DataInputStream in=null;
	static DataOutputStream out=null;
	public static void execmain() throws IOException
	{
		try
		{
			cli=new Socket("202.109.249.20",1111);
			if (cli==null)
			{
				System.out.println("connect failed............");
			}
			System.out.println("ddddddd");
			in=new DataInputStream(cli.getInputStream());
			out=new DataOutputStream(cli.getOutputStream());
			app_random=GetRandom();//得到随机数
			byte[] mesg=Cmd_App_Connect();//向服务端发起请求	组织信息	
			System.out.println("eeeee..begin...");
			send_mesg=Send_To_App(mesg,1);//组织发送的信息//change at 2004-11-22
			//需要在这边进行两次的循环
			out.write(send_mesg,0,send_mesg.length);////change at 2004-11-22//rechange at 05-02-02
			/////	
			windows_num--;
			System.out.println("begin....");
			while (true)
			{
		
				int a=in.readInt();
				byte[] buf=new byte[a-4];
				try
				{
					in.read(buf);
				} 
				catch(IOException e) 
				{
					break;
				}	
				
				last_mesg=Deal_Appmesg(buf); //change at 2004-11-18
				send_mesg=Send_To_App(last_mesg,send_type); //change at 2004-11-18
				
				
				if (Req_sign==true)
				{
					while (windows_num<1)
					{
						try
						{
							Thread.sleep(3000);
							System.out.println("send 应用请求知道平台的接收缓冲窗口");
							send_mesg=Send_To_App();
							print_byte(send_mesg);
							out.write(send_mesg,0,send_mesg.length);
							a=in.readInt();
							buf=new byte[a-4];
							try
							{
								in.read(buf);
							} 
							catch(IOException e) 
							{
								break;
							}	
							last_mesg=Deal_Appmesg(buf);
						}
						catch (InterruptedException e)
						{
							System.out.println("e...."+e);
						}
					}
				}
					
				if (send_sign==true)
				{
					if(check_messagetype == 2){
						out.write(send_mesg,0,send_mesg.length);
						windows_num--;
						Req_sign=true;
					}
				}
				
			}
		}
		catch (IOException e)
		{
			try{
				Thread.sleep(95000);
			}catch(Exception i){
				System.out.println("exception have happened");	
			}
			
			System.out.println("server close.........");
			SmsClient t = new SmsClient();
			System.out.println("client reconnect......");
			t.execmain();
		}
	}
	/**
	 * 对平台返回的信息进行处理 得到消息类别，具体可以查看短信开发文档
	 * @param buf
	 * @return
	 */
	public static byte[] Deal_Appmesg(byte[] buf)
	{
		byte[] buf1=new byte[4];
		byte[] buf2=new byte[buf.length-4];
		System.arraycopy(buf,0,buf1,0,buf1.length);
		System.arraycopy(buf,4,buf2,0,buf2.length);
		int mesgtype=byte2int(buf1);
		System.out.println("mesgtype......"+mesgtype);
		
	//	int a=(int)buf1;
		//根据类型不同处理处理消息
		switch (mesgtype)
		{
			case 2:
			check_messagetype = 2;
			last_mesg=SetType2(buf2);
			send_type=3;
			send_sign=true;	
		//	Req_sign=true;		
			break;
			
			case 7:
			last_mesg=SetType7(buf2);//平台的接收缓冲窗口通知
			send_sign=false;
			break;
			
			case 255:
			last_mesg=SetType255(buf2);
			System.out.println("err....255....");			
			send_sign=false;
			break;
			
			case 6:
			System.out.println("SetType6...");
			SetType6(buf2);
			send_sign=false;
			break;
			
			default:
			print_byte(buf2);
			send_sign=false;
			
		}
		return last_mesg;
	}
	
	//public static byte[] SetType6(byte[] buf)
	/**
	 * 得到平台发送过来的数据。。进行处理后，下发短信
	 */
	public static void SetType6(byte[] buf)
	{
		print_byte(buf);
		byte[] b_dstspnum=new byte[21];
		byte[] b_contenttype=new byte[1];
		byte[] b_sendphone=new byte[32];
		byte[] b_linkid = new byte[20];
		byte[] b_content=new byte[buf.length-88];
		try
		{
			System.arraycopy(buf,12,b_dstspnum,0,b_dstspnum.length);
			System.arraycopy(buf,35,b_contenttype,0,b_contenttype.length);
			System.arraycopy(buf,36,b_sendphone,0,b_sendphone.length);
			System.arraycopy(buf,68,b_linkid,0,b_linkid.length);//change at 2004-11-15
			System.arraycopy(buf,88,b_content,0,b_content.length);		
		}
		catch (Exception e)
		{
			System.out.println("err in 21....exception"+e);
		}

		String dstspnum=new String (b_dstspnum,0,Byte_Long(b_dstspnum));
		
		int contenttype= byte2int8(b_contenttype);//new String(b_contenttype,0,Byte_Long(b_contenttype));
		String sendphone=new String(b_sendphone,0,Byte_Long(b_sendphone));
		String content=new String(b_content,0,Byte_Long(b_content));
	    String  linkid = new String(b_linkid,0,Byte_Long(b_linkid));//change at 2004-11-15

		System.out.println("dstspnum....."+dstspnum);
		System.out.println("contenttype....."+contenttype);
		System.out.println("sendphone....."+sendphone);
		System.out.println("linkid......:" + linkid);//change at 2004-11-15
		System.out.println("b_content....."+content);
		//
		
		//调用应用
		Vector vtUser=new  Vector();
        Vector vtContent=new Vector();
        Vector vtCost=new Vector();
        Vector vtFee=new Vector();
		Vector vtIsmgid=new Vector();
		Vector vtLinkid = new Vector();//change at 2004-11-15
		int feetype=0;
		String ismgid="1";
		try
		{
			DealMesg mesg=new DealMesg();
			String str=mesg.execMain(sendphone,content,ismgid,linkid,dstspnum,vtUser,vtFee,vtContent,vtCost,vtIsmgid,vtLinkid);
			/*
			dispose bob=new dispose();
			//(String mobile,String content,String ismgid,String linkid,String dst_mobile,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector vimgsid,Vector linkidset)
		    feetype=bob.execMain(sendphone,content,ismgid,linkid,dstspnum,vtUser,vtFee,vtContent,vtCost,vtIsmgid,vtLinkid); 
		  
		    fee_phone=(String)vtFee.elementAt(0);
		    dst_phone=(String)vtUser.elementAt(0);
		    send_content=(String)vtContent.elementAt(0);
		    linkid = (String)vtLinkid.elementAt(0);//change at 2004-11-15
		    *
		    if(feetype == 1){
		    	fee_phone = "";	
		    }
		   
		/*
		    int n=vtUser.size();    
		    for(int i=0;i<n;i++)
	   		{
	   			//System.out.println("Feetype:"  +feetype);
	   			
	   			System.out.println("目的手机：vtUser["+i+"]:   "+vtUser.elementAt(i));
	   			System.out.println("费用手机：vtFee["+i+"]: "+vtFee.elementAt(i));
	   			System.out.println("发送内容：vtContent["+i+"]:  "+vtContent.elementAt(i));
	   		    System.out.println("信息费用：vtCost["+i+"]:   "+vtCost.elementAt(i));	
				System.out.println("手机省份：vtIsmgid["+i+"]:   "+vtIsmgid.elementAt(i));	
	
	        }
	       
	       		System.out.println("dstspnum....."+dstspnum);
		System.out.println("contenttype....."+contenttype);
		System.out.println("sendphone....."+sendphone);
		System.out.println("linkid......:" + linkid);//change at 2004-11-15
		System.out.println("b_content....."+content);
	       */ 
		    Send_Message(sendphone,sendphone,str,linkid);
	 //      Send_Message(fee_phone,dst_phone,send_content,linkid);
	       
	        //组装应用发给平台的消息。。。。。
     
	        //last_mesg=Cmd_Sendsms(fee_phone,dst_phone,sp_num,send_content,linkid);
	  /*      send_mesg=Send_To_App(last_mesg,send_type);
	        //////////////////////////////
	        if (send_sign==true)
				{
					out.write(send_mesg,0,send_mesg.length);
					windows_num--;
					Req_sign=true;
				}
		//////////////////////////////
	*/	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
	   		System.out.println(e);
	   		System.out.println("hlkkkkkkkkk");
		}
		//return last_mesg;
		
	}
	
	public static void Send_Message(String fee_cpn,String dest_cpn,String send_content,String linkid){
		//message to long to splite it to two or more message to send
			System.out.println("the length is ::::::::::::" + send_content.length());
			int mess_count = (send_content.length() + 69)/70;
	        	System.out.println("短信条数:" + mess_count);
	        	String[] message = new String[mess_count];
	        	for(int j = 0;j < mess_count;j++){
	        		int j1 = j*70;
	        		int j2 = (j+1)*70;
	        		if(j < (mess_count - 1)){
	        			message[j] = send_content.substring(j1,j2);	
	        		}
	        		else{
	        			message[j] = send_content.substring(j1);
	        		}	
	        	}

	        	byte[] last_mesg1;
	        	byte[] send_mesg1;
	        	System.out.println("the message length is:" + message.length);
	        	for(int i = 0 ;i < message.length;i++){
	        		String mess_content = message[i];
	        		//if(i >= 1){
	        		//	fee_phone = "";	
	        		//}
	        		//System.out.println("will send mess is:" + message[i]);
	        		last_mesg1=Cmd_Sendsms(mess_count,i,fee_cpn,dest_cpn,sp_num,mess_content,linkid);
	        		
	        		send_mesg1=Send_To_App(last_mesg1,send_type);

	        		System.out.println("the sp code is:" + sp_num);
	        		System.out.println("the i value is:" + i);
	        			try{
	        				out.write(send_mesg1,0,send_mesg1.length);
						windows_num--;
						Req_sign=true;
	        			}catch(IOException e){
	        				System.err.println("write error......" + e);	
	        			}
	        	}
	}	
	
	public static byte[] Cmd_Sendsms(int total_num,int mess_id,String f_phone,String d_phone,String s_num,String s_content,String s_linkid)
	{
		System.out.println("send......content...."+s_content);
		System.out.println("linkid value is:" + s_linkid);
		
		System.out.println("mess_id value is:" + mess_id);
		System.out.println("total_num value is:" + total_num);
		System.out.println("f_phone value is:" + f_phone);
		System.out.println("d_phone value is:" + d_phone);
		System.out.println("s_num value is:" + s_num);
		System.out.println("linkid value is:" + s_linkid);
		byte[] c_type=new byte[1];
		c_type[0]=15;
		byte[] s_content_b=s_content.getBytes();
		byte[] f_phone_b=new byte[32];
		System.arraycopy(f_phone.getBytes(),0,f_phone_b,0,f_phone.getBytes().length);
		//byte[] f_phone_b=f_phone.getBytes();
	//	System.arra
		byte[] d_phone_b=new byte[32];
		System.arraycopy(d_phone.getBytes(),0,d_phone_b,0,d_phone.getBytes().length);
	//	byte[] d_phone_b=d_phone.getBytes();
		byte[] s_num_b=new byte[21];
		System.arraycopy(s_num.getBytes(),0,s_num_b,0,s_num.getBytes().length);
		byte[] s_linkid_b = new byte[20];//change at 2004-11-16
		System.arraycopy(s_linkid.getBytes(),0,s_linkid_b,0,s_linkid.getBytes().length);//change at 2004-11-16
		///////////////test 2004-11-16
		for(int i = 0;i < s_linkid_b.length;i++){
			System.out.print((char)s_linkid_b[i]);	
		}
		//////////////////test
	//	byte[] s_num_b=s_num.getBytes();
	//	byte[] c_type
	///////////////////////////////////////change at 2004-11-16
		byte[] demo = new byte[f_phone_b.length+d_phone_b.length+s_num_b.length+s_linkid_b.length];
		byte[] msgid = new byte[8];
		System.arraycopy(f_phone_b,0,demo,0,f_phone_b.length);
		System.arraycopy(d_phone_b,0,demo,f_phone_b.length,d_phone_b.length);
		System.arraycopy(s_num_b,0,demo,f_phone_b.length+d_phone_b.length,s_num_b.length);
		System.arraycopy(s_linkid_b,0,demo,f_phone_b.length+d_phone_b.length+s_num_b.length,s_linkid_b.length);
		//System.arraycopy(msgid,0,demo,f_phone_b.length+d_phone_b.length+s_num_b.length+s_linkid_b.length,msgid.length);//change at 2005-02-02
		System.out.println("demo value is:");
		for(int i = 0;i < demo.length; i++){
			System.out.print((char)demo[i]);	
		}
		System.out.println("");
		System.out.println("end");
	//////////////////////////////////////////////////////////////
		byte[] buf=new byte[130-12+s_content_b.length];
		//buf[0]=1;
		//buf[1]=1;
		String message_count = String.valueOf(total_num);
		String message_id = String.valueOf(mess_id);
		buf[0]=(byte)total_num;
		buf[1]=(byte)(mess_id + 1);
		buf[2]=0;
		buf[3]=0;
		System.out.println("the mess id is:" + (byte)(mess_id + 1));
		System.out.println("total num is:" + message_count);
		System.out.println("mess_id is:" + message_id);
		System.out.println("s_linkid_b length is:" + s_linkid_b.length);
		System.arraycopy(c_type,0,buf,4,c_type.length);
		System.arraycopy(demo,0,buf,4+c_type.length,demo.length);
		System.arraycopy(s_content_b,0,buf,4+c_type.length+demo.length+8,s_content_b.length);
		//System.arraycopy(f_phone_b,0,buf,4+c_type.length,f_phone_b.length);
		//System.arraycopy(d_phone_b,0,buf,4+c_type.length+f_phone_b.length,d_phone_b.length);
		//System.arraycopy(s_num_b,0,buf,4+c_type.length+f_phone_b.length+d_phone_b.length,s_num_b.length);
		//System.arraycopy(s_linkid_b,0,buf,4+c_type.length+c_type.length+f_phone_b.length+d_phone_b.length+s_num_b.length,s_linkid_b.length);//change at 2004-11-16
		//System.arraycopy(s_content_b,0,buf,4+c_type.length+f_phone_b.length+d_phone_b.length+s_num_b.length+s_linkid_b.length,s_content_b.length);//change at 2004-11-16
		
		send_type=5;
		///////////////////////////buf test 2004-11-16
		System.out.print("buf value is:");
		for(int i = 0;i < buf.length; i++){
			System.out.print((char)buf[i]);
		}
		System.out.println(";;;;;;;;;;;;");
		///////////////////////////
		return buf;
	}
	
	public static int Byte_Long(byte[] buf)
	{
		int i=0;
		for (i=0;i<buf.length;i++)
		{
			if (buf[i]==0)
			{
				break;
			}
		}
		return i;
	}
	public static byte[] SetType255(byte[] buf)
	{
		byte[] buf1 = new byte[buf.length-8];
		System.arraycopy(buf,8,buf1,0,buf1.length);
		System.out.println("errr.....type 255...."+new String(buf1,0,buf1.length));
		
		return buf1;		
	}

	public static byte[] SetType7(byte[] buf)
	{
		byte[] buf1 = new byte[4];
	//	print_byte(buf);
		System.arraycopy(buf,8,buf1,0,buf1.length);
		windows_num=byte2int(buf1);
		System.out.println("windows_num..."+windows_num);
		return buf1;
		
	}

/**
 * 平台对应用连接请求的响应处理
 * @param buf
 * @return
 */
	public static byte[] SetType2(byte[] buf)
	{
		byte[] buf1 = new byte[4];
		System.arraycopy(buf,20,buf1,0,buf1.length);
		//int int_random=byte2int(buf1);
		byte[] buf2=int2byte32(app_random);
		byte[] b_loginname=loginname.getBytes();
		byte[] b_secre = secre.getBytes();
		byte[] md5Byte=new byte[buf1.length+buf2.length+b_loginname.length+b_secre.length];
		System.arraycopy(buf1,0,md5Byte,0,buf1.length);
		System.arraycopy(buf2,0,md5Byte,buf1.length,buf2.length);
		System.arraycopy(b_loginname,0,md5Byte,buf1.length+buf2.length,b_loginname.length);
		System.arraycopy(b_secre,0,md5Byte,buf1.length+buf2.length+b_loginname.length,b_secre.length);
		MD5 md5=new MD5();
		byte[] bufMd5 = md5.getMD5ofStr(md5Byte, md5Byte.length); 
	
		return bufMd5;
	}
/**
 * 组织发送的头信息
 * @return
 */
	public static byte[] Send_To_App()
	{
		byte[] b_length=int2byte32(12);
		byte[] b_mesgtype=int2byte32(8);
		seqno++;
		if (seqno==0xffffffff)
		{
			seqno=0;
		}
		byte[] b_seqno=int2byte32(seqno);
		byte[] last =new byte[12];
		
		System.arraycopy(b_length,0,last,0,b_length.length);
		System.arraycopy(b_mesgtype,0,last,b_length.length,b_mesgtype.length);
		System.arraycopy(b_seqno,0,last,b_length.length+b_mesgtype.length,b_seqno.length);		
		return  last;
	}
	/**
	 * 最后组织发送的信息
	 * @param buf
	 * @param mesgtype
	 * @return
	 * */
	public static byte[] Send_To_App(byte[] buf,int mesgtype)
	{
		byte[] b_mesgtype=int2byte32(mesgtype);
		byte[] last=new byte[buf.length+12];
		byte[] b_length=int2byte32(buf.length+12);		
	//	seqno=0xfffffffe;
		seqno++;
		if (seqno==0xffffffff)
		{
			seqno=0;
		}
		byte[] b_seqno=int2byte32(seqno);
		try
		{
			System.arraycopy(b_length,0,last,0,b_length.length);
			System.arraycopy(b_mesgtype,0,last,b_length.length,b_mesgtype.length);
			System.arraycopy(b_seqno,0,last,b_length.length+b_mesgtype.length,b_seqno.length);
			System.arraycopy(buf,0,last,b_length.length+b_mesgtype.length+b_seqno.length,buf.length);
		}
		catch (Exception e)
		{
			System.out.println("system.arraycopy err....."+e);
		}
//		print_byte(last);
	//	System.out.println("seqno....."+seqno);
		return last;
	}


/**
 * 组织信息,向服务端发起请求连接	
 * @return
 */
	public static byte[] Cmd_App_Connect()
	{
		byte[] b_apdid = int2byte32(apdid);
		/* mod by yhq at 2004-10-21
		byte[] b_operatorid = int2byte32(operatorid);
		*/
		/*
		@mod begin
		*/
		byte[] b_operatorid=new byte[4];
		b_operatorid[0]=1;   //1：将短信派发到这个连接    2：短信不派发到这个连接，这个连接只用来提交短信
		b_operatorid[1]=0;	//保留
		b_operatorid[2]=0;	//保留	
		b_operatorid[3]=0;	//保留
		/*
			mod end
		*/
		
		byte[] b_loginname=new byte[20];
		//b_loginname=loginname.getBytes();
		//System.arraycopy(loginname.getBytes(),0,b_loginname,0,loginname.getBytes().length);
	//	System.out.println("b_loginname.lenth:"+b_loginname.length);
		byte[] b_app_random=int2byte32(app_random);
		byte[] last = new byte[32];
		try
		{

			System.arraycopy(b_apdid,0,last,0,b_apdid.length);
			System.arraycopy(b_operatorid,0,last,b_apdid.length,b_operatorid.length);
			System.arraycopy(b_loginname,0,last,b_apdid.length + b_operatorid.length,b_loginname.length);
			System.arraycopy(b_app_random,0,last,b_apdid.length + b_operatorid.length+b_loginname.length,b_app_random.length);
			
		//	print_byte(last);
			
		}
		catch (Exception e)
		{
			System.out.println("Exception e....."+e);
		}
		return last;
	}

	public static void print_byte(byte[] b)
	{
		for (int i=0;i<b.length ;i++ )
		{
			System.out.println("byte["+i+"]:"+b[i]);
		}
	}
	
	public static int byte2int8(byte[] buf)
	{
		return (buf[0]&0xff);
	}
	
	public static int byte2int(byte[] buf)
	{
		return  (((buf[0] & 0xFF)<<24)+((buf[1]&0xFF)<<16)+((buf[2]&0xff)<<8)+(buf[3]&0xff));
	}

	public static byte[] int2byte32(int i)
	{
		byte[] ret =new byte[4];
		ret[0] = (byte)(i >> 24 & 0xFF);
		ret[1] = (byte)(i >> 16 & 0xFF);
		ret[2] = (byte)(i >> 8 & 0xFF);
		ret[3] = (byte)(i & 0xFF);
		return ret;
	}

/**
 * 得到应用产生的随机数
 * @return
 */
	public static int GetRandom()
	{
		Random rand=new Random();
		int random_num=rand.nextInt(99999);
		return random_num;
	}
	public static void main(String[] args) {
	}
}
