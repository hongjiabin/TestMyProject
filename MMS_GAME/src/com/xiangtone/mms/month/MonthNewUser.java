/*
 * Created on 2006-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.mms.month;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;

import com.xiangtone.sql.MysqlDB;
import com.xiangtone.util.Tool;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MonthNewUser extends Thread{
	String[] code;
	String[] receiver;
	MysqlDB db;
	public void run() {
		System.out.println("oooooo");
		
		GetCodeUser();
		try {
			DealCode();
		} catch (SecurityException e1) {
			System.out.println("11111111:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			System.out.println("2222222222222:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.out.println("3333333333333:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			System.out.println("44444444444444:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			System.out.println("555555555555555:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			System.out.println("6666666666666666:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			System.out.println("77777777777777777777:"+e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true) {
		//	GetCodeUser();
			
			System.out.println("send....begin....");
			int dayOfMonth=Tool.getDayOfMonth();
			int hourOfDay=Tool.getHour();
			int dayOfWeek=Tool.getDayOfWeek();
			System.out.println("dayOfWeek  "+dayOfWeek);
			try {
				if(dayOfMonth%3==1) {
					if (hourOfDay==9) {
						code=null;
						receiver=null;
						GetCodeUser();
						DealCode();
						System.out.println("day%3==1 and hour==11 sleep 45min");
						Thread.sleep(1000*60*50);
					} else {
						System.out.println(" only hourOfDay==9 sleep 45min");
						Thread.sleep(1000*60*50);
						
					}
				} else  {
					System.out.println(" only sleep 55min ");
					Thread.sleep(1000*60*55);
				}
			} catch (Exception e) {
				System.out.println("e===="+e);
			}
		}
	}
	/**
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	private void DealCode() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		for (int i=0;i<code.length;i++) {
			System.out.println("code["+i+"]==="+code[i]);
			System.out.println("receiver["+i+"]==="+receiver[i]);
			try {
				sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (code[i].equals("133301")){
			//	db=new MysqlDB("133301");
				System.out.println("dddddd");
				String classname="mms.mmsgame.wxtd.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("123312")) {
				String classname="mms.mmsgame.tmanl.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("123314")) {
				String classname="mms.mmsgame.hctl.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("162301")) {
				String classname="mms.mmsgame.xamag.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("170300")) {
				String classname="mms.mmsgame.kyxrl.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("135300")) {
				String classname="mms.mmsgame.qamj.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} else if (code[i].equals("145301")) {
				String classname="mms.mmsgame.xhyzx.Info";
				String classmethod="send";
				System.out.println("classname="+classname);
				System.out.println("classmethod="+classmethod);
				
				Class toRun=Class.forName(classname);
				Method mainMethod=toRun.getDeclaredMethod(classmethod,new Class[]{String.class,String.class});
				String feeType=mainMethod.invoke(toRun.newInstance(), new Object[]{receiver[i],"999"}).toString();
			} 
		}
		
	}
	/**
	 * 
	 */
	private void GetCodeUser() {
		System.out.println("iiiiiiii");
		db=new MysqlDB("mmsgm_app");
		System.out.println("kkkkkkkkkkkk");
		String sql="select receiver,code from cyhq_user where status=1 order by code asc";
		ResultSet rs=db.execQuery(sql);
		String[] tempA=new String[1000];
		String[] tempB=new String[1000];
		int i=0;
		try {
			while (rs.next()) {
				tempA[i]=rs.getString("code");
				tempB[i]=rs.getString("receiver");
				i++;
			}
		} catch (Exception e) {
			System.out.println("999999999999999"+e);
		}
		code=new String[i];
		receiver=new String[i];
		for (int k=0;k<i;k++) {
			code[k]=tempA[k];
			System.out.println("code["+k+"]=="+code[k]);
			receiver[k]=tempB[k];
		}
		try {
			db.close();
			// TODO Auto-generated method stub
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		MonthNewUser mnu=new MonthNewUser();
		mnu.start();
	}
}
