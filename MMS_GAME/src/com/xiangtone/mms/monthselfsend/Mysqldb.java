package com.xiangtone.mms.monthselfsend;



import java.sql.*;



/*******************************************************************************

 * 数据库处理类 *

 ******************************************************************************/

public class Mysqldb {



	String sDBDriver = "org.gjt.mm.mysql.Driver";
	Connection conn = null;
	ResultSet rs = null;
	Statement stmt = null;

	//String DBCon = "jdbc:mysql://192.168.1.39/mmsgame_wxtd?user=root&password=&useUnicode=true&characterEncoding=gb2312";

//	String DBCon = "jdbc:mysql://192.168.1.7/mmsgame_wxtd?user=mgmcnmccamag&password=Ase4R9iGYhy&useUnicode=true&characterEncoding=gb2312";
    String DBCon;
	public Mysqldb(String dbc) {

		try {
			this.DBCon=dbc;
			Class.forName(sDBDriver); //装入驱动程序

			conn = DriverManager.getConnection(this.DBCon);

			stmt = conn.createStatement();

		} catch (Exception e) {

			System.err.println("Unable to load driver:" + e.getMessage());

			e.printStackTrace();

		}



	}

	/***************************************************************************

	 * update .insert,delete 等操作 *

	 **************************************************************************/

	public int executeUpdate(String sql) {

		int status=0;

		try {

			status=stmt.executeUpdate(sql);

		} catch (SQLException ex) {

			System.err.println("aq.executeInsert:" + ex.getMessage());

			System.err.println("aq.executeInsert:" + sql);

			ex.printStackTrace();

			status=1;

		}

		return status;

	}



	/***************************************************************************

	 * SELECT 操作 *

	 **************************************************************************/

	public ResultSet executeQuery(String sql) {

		rs = null;

		try {

			rs = stmt.executeQuery(sql);

		} catch (SQLException ex) {

			System.err.println("aq.executeQuery:" + ex.getMessage());

			System.err.println("aq.executeQuery: " + sql);

			ex.printStackTrace();

		}

		return rs;

	}



	/***************************************************************************

	 * 关闭所有连接 *

	 **************************************************************************/

	public void dbclose() {

		if (stmt != null) {

			try {

				stmt.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		if (conn != null) {

			try {

				conn.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

}