/*
 * Created on 2007-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.smspush;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xiangtone.sql.MysqlDB;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class smspush extends Thread {

	public void run() {
		
		while (true) {
			String sql="";
			System.out.println("push_app send begin");
			ResultSet rs=null;
			MysqlDB db=new MysqlDB("push_app");
			sql="select exchange_id,src_phone,exchange_status,mms_id,mms_name,league_id from mms_transaction_detail where isPush='0'  and (UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(exchange_time))>360";
			rs=db.execQuery(sql);
			try {
				while (rs.next()) {
					String src_phone=rs.getString("src_phone");
					String mms_id=rs.getString("mms_id"); 
					String mms_name=rs.getString("mms_name");
					String league_id =rs.getString("league_id");
					String exchange_status=rs.getString("exchange_status");
					int exchange_id=rs.getInt("exchange_id");
					if (exchange_status.equals("0")) {
						try {
							System.out.println("send succ");
							sendMeg("http://www.bupu.cn/download/smspush/success.php?src_phone="+src_phone+"&mms_id="+mms_id+"&mms_name="+mms_name+"&league_id="+league_id);

						} catch (Exception e3) {
							System.out.println(e3);
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					} else {
						try {
							System.out.println("send unsucc");
							sendMeg("http://www.bupu.cn/download/smspush/unsuccess.php?src_phone="+src_phone+"&mms_id="+mms_id+"&mms_name="+mms_name+"&league_id="+league_id);

						} catch (Exception e3) {
							System.out.println(e3);
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					}
					sql="update mms_transaction_detail set isPush='1' where exchange_id="+exchange_id;
					db.execUpdate(sql);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				db.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(3*1000*60);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	public static void sendMeg(String sendurl) throws Exception {
		
		try {
		
			URL u = new URL(sendurl);
			URLConnection uc=u.openConnection();			
			InputStream raw=uc.getInputStream();
	//		InputStream in=new BufferedInputStream(raw);
	//		in.close();
			raw.close();
		} catch (Exception e) {
			System.out.println("exception :"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		smspush sp=new smspush();
		sp.start();
		/*
		try {
		//	sendMeg("http://www.bupu.cn/download/smspush/unsuccess.php?mms_id=61127008&mms_name=¡∫…Ω≤Æ”Î÷Ï¿Ú“∂&src_phone=15959285436&league_id=9009");
		} catch (Exception e) {
			System.out.println("exception "+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
