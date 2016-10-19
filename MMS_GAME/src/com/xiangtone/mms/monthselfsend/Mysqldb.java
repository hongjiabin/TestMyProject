package com.xiangtone.mms.monthselfsend;



import java.sql.*;



/*******************************************************************************

 * ���ݿ⴦���� *

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
			Class.forName(sDBDriver); //װ����������

			conn = DriverManager.getConnection(this.DBCon);

			stmt = conn.createStatement();

		} catch (Exception e) {

			System.err.println("Unable to load driver:" + e.getMessage());

			e.printStackTrace();

		}



	}

	/***************************************************************************

	 * update .insert,delete �Ȳ��� *

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

	 * SELECT ���� *

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

	 * �ر��������� *

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