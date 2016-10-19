package com.xiangtone.mms.monthselfsend;

import com.xiangtone.util.Tool;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class MMSThread0 extends Thread
{
//  PropertyConfigurator.configure("log4j.properties");
//  private static Logger log = Logger.getLogger("MMSThread0");
	private static Lo4jUtil log = new Lo4jUtil("MMSThread0");
  String mobile = null;
  String rec = null;
  ResultSet rs = null;
  String strsql = "";
  int number = 0;
  User localUser;
  private static String[] test_code = { "145302", "131302", "145301", "120320", "123312", "133305", "150301", "133301", "133313" };

  public void run()
  {
    int i = 1;
    log.info("send....");
    //System.out.println("send....");

    int j = 0;

    while (i != 0)
    {
      log.info("send....");
      //System.out.println("send....");
      int k = Tool.getDayOfMonth();
      int m = Tool.getHour();
      int n = Tool.getDayOfWeek();
      log.info("dayOfWeek:  " + n+" ,hourOfDay:  " + m+" ,dayOfMonth:  " + k);
//      System.out.println("dayOfWeek  " + n);
//      System.out.println("hourOfDay  " + m);
//      System.out.println("dayOfMonth  " + k);
      try
      {
        
        String str3;
        String str4;
        if ((m == 10) && (k % 2 == 0) && (k > 10)) {
          log.info("now hourOfDay===" + m);
          //System.out.println("now hourOfDay===" + m);
          localUser = new User();
          String str1 = "";
          String str2 = "";
          str3 = "";
          while (localUser.mobileCode.size() > 0) {
        	log.info("now 还有 " + localUser.mobileCode.size() + " 个用户需要发送数据");
//            System.out.println("now 还有 " + localUser.mobileCode.size() + " 个用户需要发送数据");
            str1 = localUser.mobileCode.firstElement().toString();
            str2 = str1.substring(0, 11);
            str3 = str1.substring(12);
            str4 = localUser.getLastSendDay(str2, str3);

            if (!localUser.getSendStatus(str2, str3))
            {
              log.info("mmsday=" + str4);
              log.info("mobile=" + str2);
              log.info("code=" + str3);
//              System.out.println("mmsday=" + str4);
//              System.out.println("mobile=" + str2);
//              System.out.println("code=" + str3);
              this.rec = Info.send(str2, str3, Integer.parseInt(str4));
              sleep(3000L);
              if (this.rec.length() >= 4)
                if (this.rec.substring(0, 4).equals("0000"))
                  localUser.insert_log(str2, str3, Integer.parseInt(str4));
                else
                  log.info("....rec...=" + this.rec);
//                  System.out.println("....rec...=" + this.rec);
            }
            else
            {
            	log.info(str2 + "====" + str3 + "====this cpn have send......");
//              System.out.println(str2 + "====" + str3 + "====this cpn have send......");
            }
            log.info("usr.mobileCode.size()==" + localUser.mobileCode.size());
//            System.out.println("usr.mobileCode.size()==" + localUser.mobileCode.size());

            localUser.mobileCode.removeElementAt(0);
            sleep(100L);
          }
        }
        log.info("==now hourOfDay===" + m);
        log.info("==now dayOfMonth===" + k);
//        System.out.println("==now hourOfDay===" + m);
//        System.out.println("==now dayOfMonth===" + k);

        if (m > 8)
        {
          for (j = 0; j < 10; j++)
          {
        	  log.info("dom=======" + j);
//            System.out.println("dom=======" + j);

            localUser = new User(j);
            int i1 = 0;
            int i2 = 0;
            str3 = "";
            str4 = "";
            String str5 = "";
            String str10 = localUser.mobileCode.size()+"";
            log.info(str10);
//            System.out.println(localUser.mobileCode.size());
            while (localUser.mobileCode.size() > 0)
            {
              str3 = localUser.mobileCode.firstElement().toString();
              str4 = str3.substring(0, 11);
              str5 = str3.substring(12);

              String str6 = localUser.getLastSendDay(str4, str5);

              if (!localUser.getSendStatus(str4, str5))
              {
            	  log.info("mmsday=" + str6);
            	  log.info("mobile=" + str4);
            	  log.info("code=" + str5);
//                System.out.println("mmsday=" + str6);
//                System.out.println("mobile=" + str4);
//                System.out.println("code=" + str5);
                String str7 = localUser.getMiscid(str4);
                if ((str7.equals("0008")) || (str7.equals("0005"))) {
                	log.info("现在发送内蒙古/黑龙江的号码");
//                  System.out.println("现在发送内蒙古/黑龙江的号码");
                  if (localUser.getSendSuccStat(str4)) {
                    this.rec = Info.send(str4, str5, Integer.parseInt(str6));
                    sleep(3000L);
                    if (this.rec.length() >= 4)
                      if (this.rec.substring(0, 4).equals("0000"))
                        localUser.insert_log(str4, str5, Integer.parseInt(str6));
                      else
                    	  log.info("....rec...=" + this.rec);
//                        System.out.println("....rec...=" + this.rec);
                  }
                }
                else
                {
                  this.rec = Info.send(str4, str5, Integer.parseInt(str6));
                  sleep(3000L);
                  if (this.rec.length() >= 4)
                    if (this.rec.substring(0, 4).equals("0000"))
                      localUser.insert_log(str4, str5, Integer.parseInt(str6));
                    else
                    	log.info("....rec...=" + this.rec);
//                      System.out.println("....rec...=" + this.rec);
                }
              }
              else
              {
            	  log.info(str4 + "====" + str5 + "====this cpn have send......");
//                System.out.println(str4 + "====" + str5 + "====this cpn have send......");
              }

              localUser.mobileCode.removeElementAt(0);
              i1++;
              i2++;

              if (i1 > 200) {
                sleep(2000L);
                log.info("sleep 2s    jj=" + i1);
//                System.out.println("sleep 2s    jj=" + i1);
                i1 = 0;
              }
              log.info("kk===" + i2);
//              System.out.println("kk===" + i2);
              sleep(100L);
            }

            localUser.db.dbclose();

            log.info("day%3==1 and hour==11 sleep 45min");
//            System.out.println("day%3==1 and hour==11 sleep 45min");

            sleep(10000L);
          }

          break;
        }

        if (m == 9) {
        	log.info(" only hourOfDay==9 sleep 1min");
//          System.out.println(" only hourOfDay==9 sleep 1min");
          sleep(60000L);
        }
        else
        {
        	log.info(" only hourOfDay!=9 and hourOfDay!=9 sleep 50min");
//          System.out.println(" only hourOfDay!=9 and hourOfDay!=9 sleep 50min");
          sleep(3000000L);
        }

      }
      catch (Exception localException)
      {
    	  log.error("e====" + localException);
//        System.out.println("e====" + localException);
      }
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    MMSThread0 localMMSThread0 = new MMSThread0();
    localMMSThread0.start();
  }
}