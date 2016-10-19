package com.xiangtone.mms.monthselfsend.copy;

import java.io.*;

import java.net.*;

import java.sql.ResultSet;


//mobileΪƽ̨���͹����û����ֻ���

public class Info {



	final static String leaguename = "mms_all";//����countermand;ʵ��mmsgm_shdzh
	final static String leaguepwd = "mms_all";//����testleague;ʵ��mmsgm_shdzh
	static String league = "6000";//����6000;ʵ��5003

	final static int nameCode = 9; //��½�û�����־
	final static int pwdCode = 10; //��½�����־
	final static int leagueCode = 11; //���˴����־
	final static int subjectCode = 12; //����
	final static int smilCode = 13; //smil�ļ���־
	final static int imgCode = 14; //ͼƬ��־
	final static int ringToneCode = 15; //������־
	final static int diytextCode = 16; //�Լ��༭�ı�
	final static int toCode = 17; //����(���շ�)
	final static int serviceCode = 20; //��������־
	final static int senderCode = 21; //���ͷ�
	public static String send(String mobile,String code,int user_days) {

		String mmsPlatUrl = "http://192.168.1.7:2000/mmLeague";//����211.136.87.227;ʵ��192.168.1.7
		String mmsPlatUrlMonth= "http://192.168.1.7:2000/month";
System.out.println("mmsPlatUrl=="+mmsPlatUrl);
		String put = "";

		//	String mmsPlatUrl="http://127.0.0.1:2000/mmLeague";

		//String fileName = "d:\\www\\mmstest.zip";

		try {
			URL url=null;
			//URL url = new URL(mmsPlatUrl);
			if (code.equals("145304"))
				url = new URL(mmsPlatUrlMonth);
			else
				url = new URL(mmsPlatUrl);
			URLConnection connection = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) connection;



			byte[] b = getCon(mobile,code,user_days);

			httpConn.setRequestProperty("Content-Length", String

					.valueOf(b.length));



			httpConn.setRequestMethod("POST");

			httpConn.setDoOutput(true);

			httpConn.setDoInput(true);



			OutputStream out = httpConn.getOutputStream();

			out.write(b);

			out.close();



			// Read the response and write it to standard out.

			InputStreamReader isr = new InputStreamReader(httpConn

					.getInputStream());

			BufferedReader in = new BufferedReader(isr);



			String inputLine;



			while ((inputLine = in.readLine()) != null) {

				put += inputLine;

			//	System.out.println(inputLine);

			}

			System.out.println("put:"+put);

			in.close();

		} catch (Exception e) {

			System.out.println("send message:" + e.getMessage());
			//��¼��־
			function fun=new function();
			mklog.showLog(fun.getcurrent_time()+","+e.getMessage());

		}

		return put;

	}
	
	
	
	public static String send(String mobile,String code,String user_days) {

		String mmsPlatUrl = "http://192.168.1.7:2000/mmLeague";//����211.136.87.227;ʵ��192.168.1.7
		String mmsPlatUrlMonth="http://192.168.1.7:2000/month";
		String put = "";

		//	String mmsPlatUrl="http://127.0.0.1:2000/mmLeague";

		//String fileName = "d:\\www\\mmstest.zip";

		try {
			URL url=null;
		//	URL url = new URL(mmsPlatUrl);
			if (code.equals("145304"))
				url = new URL(mmsPlatUrlMonth);
			else
				url = new URL(mmsPlatUrl);
			URLConnection connection = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) connection;



			byte[] b = getCon(mobile,code,Integer.parseInt(user_days));

			httpConn.setRequestProperty("Content-Length", String

					.valueOf(b.length));



			httpConn.setRequestMethod("POST");

			httpConn.setDoOutput(true);

			httpConn.setDoInput(true);



			OutputStream out = httpConn.getOutputStream();

			out.write(b);

			out.close();



			// Read the response and write it to standard out.

			InputStreamReader isr = new InputStreamReader(httpConn

					.getInputStream());

			BufferedReader in = new BufferedReader(isr);



			String inputLine;



			while ((inputLine = in.readLine()) != null) {

				put += inputLine;

				System.out.println(inputLine);

			}
			System.out.println("send msg by no 1 order");

			//System.out.println("put:"+put);

			in.close();

		} catch (Exception e) {

			System.out.println("send message:" + e.getMessage());
			//��¼��־
			function fun=new function();
			mklog.showLog(fun.getcurrent_time()+","+e.getMessage());

		}

		return put;

	}
	//������������

	public static void copy(InputStream in, OutputStream out)

			throws IOException {

		synchronized (in) {

			synchronized (out) {

				byte[] buffer = new byte[256];

				while (true) {

					int bytesRead = in.read(buffer);

					if (bytesRead == -1)

						break;

					out.write(buffer, 0, bytesRead);

				}

			}

		}

	}



	//mms��Ϣ�ķ�װ

	public static byte[] getCon(String mobile,String code,int user_days) {

		ByteCode bc = new ByteCode(2000); //wait for adjuct set the smillest

		String sender = mobile;//������,��Ϊ����շ�һ��

		try {

			/*---------------------ȡ��Ϣ����Ϣ-------------------------------------------*/

			int mmsday = user_days;//��Ϣ���

			String smile="";

			/*-------------------mms��Ϣ����֯--------------------------------------------*/

			bc.AddInt32(nameCode);//add leaguename

			bc.AddInt32(leaguename.getBytes().length);

			bc.AddBytes(leaguename.getBytes());
                        System.out.println("leaguename::"+leaguename);


			//add pwdCode;

			byte[] md5Byte = leaguepwd.getBytes();
			int lengthMd5 = md5Byte.length;
			MD5 md5 = new MD5();
			byte[] bufMd5 = md5.getMD5ofStr(md5Byte, md5Byte.length);
			bc.AddInt32(pwdCode);
			bc.AddInt32(bufMd5.length);
			bc.AddBytes(bufMd5);



			//add leagueCode

			bc.AddInt32(leagueCode);
			bc.AddInt32(league.getBytes().length);
			bc.AddBytes(league.getBytes());



			// add subject

			Message msg=new Message();
			function fun=new function();
			String title=msg.get_message_title(mmsday,code);
			if(title!=""){
				bc.AddInt32(subjectCode);
				bc.AddInt32(title.getBytes().length);
				bc.AddBytes(title.getBytes());
				System.out.println("title:" + title);
				mklog.showLog("title:"+title+",mmday:"+mmsday);
			}
			 	int text_size=0;
			   int pic_size=0;
			   int ring_size=0;
			   String smile_path="";

			   String dy_smile="";

			   Mysqldb db3 = new Mysqldb("jdbc:mysql://192.168.1.9/cn_sms_mms?user=mmsdbdevp&password=VnhFc3F5Vg60Zs&useUnicode=true&characterEncoding=gb2312");

			   ResultSet rs3 = null;
			   int titleid=msg.titleid;
		//	String sql="select * from s_message where pid="+mmsday+" and pid>0 order by position asc";
			   String sql="select id,date,seconds,txt,ring,img,page  from month_content where titleid="+titleid+" order by page asc";
			try {

				rs3 = db3.executeQuery(sql);
				//String path="/home/www/nonomomo.com/material/new-nomo/mms_app/cnmonth/";
				String path="/home/wap/web/material/new-nomo/mms_app/cnmonth/";
				while (rs3.next()) {
					
					String id = rs3.getString("id");
					String date = rs3.getString("date");
					String page = rs3.getString("page");
					String txt = rs3.getString("txt");
					String ring = rs3.getString("ring");
					String img = rs3.getString("img");
					String second=rs3.getString("seconds");
					dy_smile+="<par dur=\""+second+"000ms\">";

					if(!img.equals("")){
					  
					  bc.AddInt32(imgCode);

					  String imgPath=path+date+"/"+titleid+"/"+page+"."+img;
					  System.out.println("imgPath����"+imgPath);
					  String imgName=page+"."+img;
					  bc.AddInt32(imgName.getBytes().length);
					  bc.AddBytes(imgName.getBytes());
					  FileInputStream finImg = new FileInputStream(imgPath);
					  ByteArrayOutputStream boutImg = new ByteArrayOutputStream(); //�ļ�תΪ�ֽ�..
					  copy(finImg,boutImg);
					  finImg.close();
					  bc.AddInt32(boutImg.toByteArray().length);
					  bc.AddBytes(boutImg.toByteArray());
					  dy_smile+="<img src=\""+imgName+"\" region=\"Image\" />";
			//		  System.out.println("img_length:"+boutImg.toByteArray().length);
					 }

					if(!txt.equals("")){

					 bc.AddInt32(diytextCode);

					 String textPath=path+date+"/"+titleid+"/"+page+"."+txt;

					 String textName=page+"."+txt;

					 bc.AddInt32(textName.getBytes().length);

					 bc.AddBytes(textName.getBytes());
					 FileInputStream finText = new FileInputStream(textPath);
					 ByteArrayOutputStream boutText = new ByteArrayOutputStream(); 
					 copy(finText,boutText);
					 finText.close();
					 bc.AddInt32(boutText.toByteArray().length);
					 bc.AddBytes(boutText.toByteArray());
					 dy_smile+="<text src=\""+textName+"\" region=\"Text\" />";
					 //System.out.println("diyText:"+"\n"+boutText);

					}

					if(!ring.equals("")){

					  bc.AddInt32(ringToneCode);
					  String ringTonePath=path+date+"/"+titleid+"/"+page+"."+ring;
					  String ringToneName=page+"."+ring;
					  bc.AddInt32(ringToneName.getBytes().length);
					  bc.AddBytes(ringToneName.getBytes());
					  FileInputStream finRing = new FileInputStream(ringTonePath);
					  ByteArrayOutputStream boutRing = new ByteArrayOutputStream(); 
					  copy(finRing,boutRing);
					  finRing.close();
					  bc.AddInt32(boutRing.toByteArray().length);
					  bc.AddBytes(boutRing.toByteArray());
					  dy_smile+="<audio src=\""+ringToneName+"\" />";
			//		  System.out.println("Ring:"+boutRing.toByteArray().length);	  

					}		
					dy_smile+="</par>";
					//���smile

			//		  smile_path=path+"/"+smile_name;

			//		  String pic_ring_smile=fun.read_file(smile_path);//�õ�smile����

			//		  smile+=pic_ring_smile;
				     //��¼��־
					  //mklog.showLog(fun.getcurrent_time()+","+mobile+",����smile\n"+pic_ring_smile);
					 //System.out.println("����smile\n:"+pic_ring_smile);

				}

				

				//���smile

				smile="<smil>"+"\n"+

		                        "<head>"+"\n"+

		                          "<layout>"+"\n"+

			                        "<root-layout width=\"128\" height=\"128\" />"+"\n"+

				                    "<region id=\"Image\" top=\"0\" left=\"0\" height=\"128\" width=\"128\" />"+"\n"+

				                    "<region id=\"Text\" top=\"128\" left=\"0\" height=\"128\" width=\"128\" />"+"\n"+

		                          "</layout>"+"\n"+

		                       "</head>"+"\n"+

		                       "<body>"+"\n";

			   smile+=dy_smile;

		       smile+=  "</body>"+"\n"+

			         "</smil>";

		  //     System.out.println("smile:\n"+smile);
               //��¼��־
		       //mklog.showLog(fun.getcurrent_time()+","+mobile+",����smile\n"+smile);
		       
				bc.AddInt32(smilCode);

				bc.AddInt32("007.smil".getBytes().length);

				bc.AddBytes("007.smil".getBytes());

				bc.AddInt32(smile.getBytes().length);

				bc.AddBytes(smile.getBytes());

			} catch (Exception e) {

				System.out.println("���ұ������ݳ����쳣��" + e.getMessage());

		//		db3 = new Mysqldb();
				mklog.showLog(fun.getcurrent_time()+","+e.getMessage());

			}

			

			//add sender

			bc.AddInt32(senderCode);

			bc.AddInt32(sender.getBytes().length);

			bc.AddBytes(sender.getBytes());



			//add to����(receiver)

			bc.AddInt32(toCode);

			bc.AddInt32(mobile.getBytes().length);

			bc.AddBytes(mobile.getBytes());



			// add serviceCode

			bc.AddInt32(serviceCode);

			bc.AddInt32(code.getBytes().length);

			bc.AddBytes(code.getBytes());

			//System.out.println(bc.getOrigBytes());

			//�ر�����

			db3.dbclose();

			msg.db.dbclose();

		} catch (Exception e) {

			System.out.println(e.getMessage());

			e.printStackTrace();

			System.out.println("make up information occur exception!");
			function fun=new function();
			mklog.showLog(fun.getcurrent_time()+","+e.getMessage());

		}

		return bc.getOrigBytes();

	}



		public static void main(String args[]) {

		try {
			System.out.println("----------");
				String a=Info.send(args[0],args[1],Integer.parseInt(args[2]));

				System.out.println("sending start:"+a);

			} catch (Exception e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

				System.out.println("sending error:" + e.getMessage());

			}

	

		}

}