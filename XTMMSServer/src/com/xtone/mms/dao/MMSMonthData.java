package com.xtone.mms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.common.util.ConfigManager;
import org.common.util.ConnectionService;

import com.system.database.ConnConfigMain;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.xtone.mms.model.AddInfo;

public class MMSMonthData {
	
	/**
	 * ÐÂÔö
	 */
//	@SuppressWarnings("unchecked")
//	public List<AddInfo> SelectMonthAdd(String league,String serverid,String startcustomtime,String endcustomtime){
//		String sql = "SELECT c.`province`,COUNT(*) AS COUNT FROM `mms_league`.`mms_month_detail` AS a "
//				+ "LEFT JOIN `mms_league`.`down_service_info` AS b ON a.`service_id`=b.`oper_code` "
//				+ "LEFT JOIN `mms_league`.`miscid` AS c ON c.`miscid`=a.`miscid` "
//				+ "WHERE 1=1 AND "
//				+ "a.`leagueid` ='"+league+"' "
//				+ "AND a.`service_id`= '"+serverid+"' "
//				+ "AND LEFT(a.`custom_time`,10) >= '"+startcustomtime+"' "
//				+ "AND LEFT(a.`custom_time`,10) < '"+endcustomtime+"' "
//				+ "GROUP BY a.`miscid` HAVING COUNT>1;";
//		return (List<AddInfo>) new JdbcControl().query(sql, new QueryCallBack() {
//			
//			@Override
//			public Object onCallBack(ResultSet rs) throws SQLException {
//				List<AddInfo> list = new ArrayList<AddInfo>();
//				while (rs.next()) {
//					AddInfo model = new AddInfo();
//					model.setProvince(rs.getString("province"));
//					//model.setCOUNT(rs.getInt("count")+"");
//					list.add(model);
//				}
//				return list;
//			}
//		});
//	}
	
	public List<AddInfo> SelectMonthAdd(String league,String serverid,String startcustomtime,String endcustomtime){
		String sql = "SELECT c.`province`,COUNT(*) AS COUNT FROM `mms_league`.`mms_month_detail` AS a "
				+ "LEFT JOIN `mms_league`.`down_service_info` AS b ON a.`service_id`=b.`oper_code` "
				+ "LEFT JOIN `mms_league`.`miscid` AS c ON c.`miscid`=a.`miscid` "
				+ "WHERE 1=1 AND ";
		if(league.equals("")){
			sql+= "a.`leagueid` ="+league+" ";
		}
		if(serverid.equals("")){
			sql += "AND a.`service_id`= "+serverid+" ";
		}
				
				sql+= "AND LEFT(a.`custom_time`,10) >= ? "
				+ "AND LEFT(a.`custom_time`,10) < ? "
				+ "GROUP BY a.`miscid` HAVING COUNT>1;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AddInfo> list = new ArrayList<AddInfo>();
		try {
			conn = ConnectionService.getInstance().getConnectionForLocal();
			ps = conn.prepareStatement(sql);
			ps.setString(1, startcustomtime);
			ps.setString(2, endcustomtime);
			rs = ps.executeQuery();
			while (rs.next()) {
				AddInfo model = new AddInfo();
				model.setProvince(rs.getString("province"));
				model.setCOUNT(rs.getInt("count")+"");
				list.add(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				ps.close();
				conn.close();
				if(ps!=null){
					ps=null;
				}
				if(conn!=null){
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
		
	}
	
	public static void main(String[] args) {
//		MMSMonthData data = new MMSMonthData();
//		System.out.println(data.SelectMonthAdd("1254", "102302", "2016-07-20", "2016-08-20"));
		Calendar a = Calendar.getInstance();
		System.out.println(a.DATE);
		int b = a.get(5);
		System.out.println(b);
		
	}
}
