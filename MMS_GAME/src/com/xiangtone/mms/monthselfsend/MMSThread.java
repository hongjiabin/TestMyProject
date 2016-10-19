/*
 * 针对所有包月用户下发
 * 应用说明:按预定的日期下发ＭＭＳ给用户,下发时间由后台控制:08:00--23:00
 * lastest create time 2006/05/31 11:00:00
 */
package com.xiangtone.mms.monthselfsend;
import java.sql.ResultSet;
import com.xiangtone.util.Tool;
class MMSThread extends Thread {
	String mobile = null;//接收方（用户）手机号
	String rec = null;//接收从平台的返回值
	ResultSet rs = null;
	String strsql = "";
    int number=0;
    private static String test_code[]={"145302","131302","145301","120320","123312","133305","150301","133301","133313"};
 //   private static String test_code="'','','','','','','','','','','',''";
 //   private String nosendnums="'13606001600','13584051025','13862746135','13936668371','13916514274','13916514174','13527509708','13578703292','13583753473','13512924849','13458517334','15977425160','15994276805','13696955853','13779930641'";
  //  private String nosendnums="'13606001600','13584051025','13862746135','13936668371','13916514274','13916514174','13784035901','13527509708','13578703292','13583753473','13512924849','13458517334','15977425160','15994276805','13696955853','13779930641'";
	public MMSThread() {
	}
	public void run() {
	//Random r=new Random();
		boolean flag = true;
		System.out.println("send....");
		
		while (flag) {
			
			System.out.println("send....");
			int dayOfMonth=Tool.getDayOfMonth();
			int hourOfDay=Tool.getHour();
			int dayOfWeek=Tool.getDayOfWeek();
			System.out.println("dayOfWeek  "+dayOfWeek);
			System.out.println("hourOfDay  "+hourOfDay);
			System.out.println("dayOfMonth  "+dayOfMonth);
		//	System.out.pril
			try {
					
					
			
					if (hourOfDay==10&&dayOfMonth%2==0&&dayOfMonth>10) {
						System.out.println("now hourOfDay==="+hourOfDay);
						User usr=new User();
						String mobileCodeStr="";
						String mobileStr="";
						String codeStr="";
						while (usr.mobileCode.size()>0) {	
							System.out.println("now 还有 "+usr.mobileCode.size()+" 个用户需要发送数据");
							mobileCodeStr=usr.mobileCode.firstElement().toString();
							mobileStr=mobileCodeStr.substring(0,11);
							codeStr=mobileCodeStr.substring(12);
							String mmsday=usr.getLastSendDay(mobileStr,codeStr);
						//	String mmsday=usr.getLastSendDay(usr.ArrMobile.get(i).toString(),usr.ArrCode.get(i).toString());
							
							if (!usr.getSendStatus(mobileStr,codeStr)){
								
							
								System.out.println("mmsday="+mmsday);
								System.out.println("mobile="+mobileStr);
								System.out.println("code="+codeStr);
								rec = Info.send(mobileStr,codeStr,Integer.parseInt(mmsday));
								if (rec.length()>=4) {
									if (rec.substring(0,4).equals("0000")) {
										usr.insert_log(mobileStr, codeStr,Integer.parseInt(mmsday));
									} else {
										System.out.println("....rec...="+rec);
									}
								}
							}else {
								System.out.println(mobileStr+"===="+codeStr+"====this cpn have send......");
							}
							System.out.println("usr.mobileCode.size()=="+usr.mobileCode.size());
						//	System.out.println("jj==="+jj);
							usr.mobileCode.removeElementAt(0);					
							sleep(100);
						}
					}
					System.out.println("==now hourOfDay==="+hourOfDay);
					System.out.println("==now dayOfMonth==="+dayOfMonth);
					
					//huo 
					if (hourOfDay==9&&dayOfMonth<11) {
						
					
						int dom=dayOfMonth%10;//huo  2016-50-25
						//int dom=6;
						System.out.println("dom======="+dom);

						User usr=new User(dom);
						int jj=0;
						int kk=0;
						String mobileCodeStr="";
						String mobileStr="";
						String codeStr="";
					//	for (int i=0;i<usr.ArrMobile.size();i++) {
						System.out.println(usr.mobileCode.size());
						while (usr.mobileCode.size()>0) {	
							
							mobileCodeStr=usr.mobileCode.firstElement().toString();
							mobileStr=mobileCodeStr.substring(0,11);
							codeStr=mobileCodeStr.substring(12);
							
							
							
							
							String mmsday=usr.getLastSendDay(mobileStr,codeStr);
						//	String mmsday=usr.getLastSendDay(usr.ArrMobile.get(i).toString(),usr.ArrCode.get(i).toString());
							
							if (!usr.getSendStatus(mobileStr,codeStr)){
								
								System.out.println("mmsday="+mmsday);
								System.out.println("mobile="+mobileStr);
								System.out.println("code="+codeStr);
								String miscid=usr.getMiscid(mobileStr);
								if (miscid.equals("0008")||miscid.equals("0005")) {
									System.out.println("现在发送内蒙古/黑龙江的号码");
									if (usr.getSendSuccStat(mobileStr)) {
										rec = Info.send(mobileStr,codeStr,Integer.parseInt(mmsday));
										if (rec.length()>=4) {
											if (rec.substring(0,4).equals("0000")) {
												usr.insert_log(mobileStr, codeStr,Integer.parseInt(mmsday));
											} else {
												System.out.println("....rec...="+rec);
											}
										}
									}
								}else {
									rec = Info.send(mobileStr,codeStr,Integer.parseInt(mmsday));
									if (rec.length()>=4) {
										if (rec.substring(0,4).equals("0000")) {
											usr.insert_log(mobileStr, codeStr,Integer.parseInt(mmsday));
										} else {
											System.out.println("....rec...="+rec);
										}
									}
								}
							}else {
								System.out.println(mobileStr+"===="+codeStr+"====this cpn have send......");
							}
							
							usr.mobileCode.removeElementAt(0);
							jj++;
							kk++;
							
							if (jj>200) {
								sleep(1000*2);
								System.out.println("sleep 2s"+"    jj="+jj);
								jj=0;
							}
								System.out.println("kk==="+kk);
							sleep(100);
							
							
					
							
						}
						 usr.db.dbclose();
				//		 usr.db2.dbclose();
						System.out.println("day%3==1 and hour==11 sleep 45min");
						//sleep(1000*60*60);
						sleep(1000);
					} else {
						if (hourOfDay==9) {
							System.out.println(" only hourOfDay==9 sleep 1min");
							sleep(1000*60);
							//sleep(10000);

						}else {
							System.out.println(" only hourOfDay!=9 and hourOfDay!=9 sleep 50min");
							sleep(1000*60*50);
							//sleep(10000);
						}
					}
				/*
				} else  {
					System.out.println(" only sleep 45min ");
					sleep(1000*60*50);
				}
				*/
			} catch (Exception e) {
				System.out.println("e===="+e);
			}
			
			
		}

	}

	public static void main(String args[]) {
		
		MMSThread mt = new MMSThread();
		mt.start();
		
		/*
		System.out.println("send....");
		int dayOfMonth=Tool.getDayOfMonth();
		int hourOfDay=Tool.getHour();
		int dayOfWeek=Tool.getDayOfWeek();
		System.out.println("dayOfWeek  "+dayOfWeek);
		
		User usr=new User();
		for (int i=0;i<test_code.length;i++)
		{
		try {
			Thread.sleep(1000);
			System.out.println("1111s");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	private String test_code="'133301','134304','145301','123312','120320','133313','135300','123314','111','102301','131301','136300'";
		String mmsday=usr.getLastSendDay("13588076832",test_code[i]);
		System.out.println("mmsday::"+mmsday);
		String rec = Info.send("13588076832",test_code[i],Integer.parseInt(mmsday));
		if (rec.substring(0,4).equals("0000")) {
			System.out.println("send ..ok");
			usr.insert_log("13588076832", test_code[i],Integer.parseInt(mmsday));
		} else {
			System.out.println("....rec...="+rec);
		}
		}
		*/
	}
}