package com.mms.tj;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.xiangtone.sql.MysqlDB;
public class UpdateContent {
	public MysqlDB db=null;
	public String sql="";
	public ResultSet rs=null;
	public UpdateContent(){
		while (true){
			Random ra=new Random();
			
			db=new MysqlDB("league");
			sql="select serverid from type_tmp where kindid=2";
			rs=db.execQuery(sql);
			try {
				while (rs.next()){
					String serverid=rs.getString("serverid");
			
					sql="update mms_transaction_detail set content=2 where service_code='"+serverid+"' and content=''";
					System.out.println("sql=="+sql);
					db.execUpdate(sql);
					
					sql="update mms_transaction_detail_succ set content=2 where service_code='"+serverid+"' and content=''";
					System.out.println("sql=="+sql);
					db.execUpdate(sql);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean stat=true;
			while (stat){
				sql="select * from mms_transaction_detail where content=2 limit 1";
				rs=db.execQuery(sql);
				try {
					if (!rs.next()){
						stat=false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int selectNums=ra.nextInt(5);
				sql="select sccode from t_sc_name where (catename='01' or catename='04' or catename='05' ) and  m_path1<>'' and LEFT(m_addtime,7)<left(now(),7) and LEFT(m_addtime,7)>='2011-01'  order by rand() limit "+selectNums;
				System.out.println("sql"+sql);
				rs=db.execQuery(sql);
				try {
					while (rs.next()){
					//	int changeNums=ra.nextInt(100)+100;
						int changeNums=1;
						String sccode=rs.getString("sccode");
						sql="update mms_transaction_detail set content='"+sccode+"' where content=2 order by rand() limit "+changeNums;
						System.out.println("sql"+sql);
						db.execUpdate(sql);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			stat=true;
			while (stat){
				sql="select * from mms_transaction_detail_succ where content=2 limit 1";
				rs=db.execQuery(sql);
				try {
					if (!rs.next()){
						stat=false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int selectNums=ra.nextInt(10)+25;
				sql="select sccode from t_sc_name where (catename='01' or catename='04' or catename='05' ) and  m_path1<>'' and LEFT(m_addtime,7)<left(now(),7) and LEFT(m_addtime,7)>='2011-01'  order by rand() limit "+selectNums;
				System.out.println("sql"+sql);
				rs=db.execQuery(sql);
				try {
					while (rs.next()){
						int changeNums=ra.nextInt(100)+100;
						String sccode=rs.getString("sccode");
						sql="update mms_transaction_detail_succ set content='"+sccode+"' where content=2 order by rand() limit "+changeNums;
						System.out.println("sql"+sql);
						db.execUpdate(sql);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("update over......");
			
			
			
			
/***************************update ring*****************************/
			sql="select serverid from type_tmp where kindid=4";
			rs=db.execQuery(sql);
			try {
				while (rs.next()){
					String serverid=rs.getString("serverid");
			
					sql="update mms_transaction_detail set content=4 where service_code='"+serverid+"' and content=''";
					System.out.println("sql=="+sql);
					db.execUpdate(sql);
					
					sql="update mms_transaction_detail_succ set content=4 where service_code='"+serverid+"' and content=''";
					System.out.println("sql=="+sql);
					db.execUpdate(sql);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stat=true;
			while (stat){
				sql="select * from mms_transaction_detail where content=4 limit 1";
				rs=db.execQuery(sql);
				try {
					if (!rs.next()){
						stat=false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int selectNums=ra.nextInt(5);
				sql="select ring_id  from ring_name_product where left(sendtime,7)<left(now(),7) order by rand() limit "+selectNums;
			//	sql="select sccode from t_sc_name where (catename='01' or catename='04' or catename='05' ) and  m_path1<>'' and LEFT(m_addtime,7)<'2011-11' and LEFT(m_addtime,7)>='2011-01'  order by rand() limit "+selectNums;
				System.out.println("sql"+sql);
				rs=db.execQuery(sql);
				try {
					while (rs.next()){
						int changeNums=ra.nextInt(100);
					//	int changeNums=1;
						String sccode=rs.getString("ring_id");
						sql="update mms_transaction_detail set content='"+sccode+"' where content=4 order by rand() limit "+changeNums;
						System.out.println("sql"+sql);
						db.execUpdate(sql);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			stat=true;
			while (stat){
				sql="select * from mms_transaction_detail_succ where content=4 limit 1";
				rs=db.execQuery(sql);
				try {
					if (!rs.next()){
						stat=false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int selectNums=ra.nextInt(10)+25;
				sql="select ring_id  from ring_name_product where left(sendtime,7)<left(now(),7) order by rand() limit "+selectNums;
			//	sql="select sccode from t_sc_name where (catename='01' or catename='04' or catename='05' ) and  m_path1<>'' and LEFT(m_addtime,7)<'2011-11' and LEFT(m_addtime,7)>='2011-01'  order by rand() limit "+selectNums;
				System.out.println("sql"+sql);
				rs=db.execQuery(sql);
				try {
					while (rs.next()){
						int changeNums=ra.nextInt(100);
						String sccode=rs.getString("ring_id");
						sql="update mms_transaction_detail_succ set content='"+sccode+"' where content=4 order by rand() limit "+changeNums;
						System.out.println("sql"+sql);
						db.execUpdate(sql);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (db!=null)
					db.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("update over......");
			
			
			
			
			
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[]) {	    	
		UpdateContent Uc=new UpdateContent();
		
	}
}
