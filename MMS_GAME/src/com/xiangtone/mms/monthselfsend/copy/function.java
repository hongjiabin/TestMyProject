/*
 * �������� 2005-11-6
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.xiangtone.mms.monthselfsend.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author user
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class function {
	
	public String read_file(String path) throws FileNotFoundException {
		String str1="";
		String str2="";
		//String path="e:\\www\\baoyue\\upload\\hctl\\conn\\6\\6.smil";
		FileReader d1= new FileReader(path);
		BufferedReader br1=new BufferedReader(d1);
		try {
			while((str2=br1.readLine())!=null){
				str1+=str2+"\r\n";
			}
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		return str1;
	}
	public String getcurrent_time() {//�õ���ǰϵͳ��ʱ�䣬��ʽΪdatetime
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}

	public String get_current_time() {//�õ���ǰϵͳ��ʱ�䣬��ʽΪHH:mm:ss
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("HH:mm:ss");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	public String get_current_day() {//�õ���ǰϵͳ���ڣ���ʽΪdd
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	public String getcurrent_sdate() {//�õ���ǰϵͳ��ʱ�䣬��ʽΪY_m_d
		long ll = System.currentTimeMillis();
		String sbf = new String("");
		String sss = new String("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
		sbf = sdf.format(new java.util.Date(ll));
		String cur_time = sbf.toString();
		return cur_time;
	}
	//��������ʱ�����������
	public int nSecondsBetweenTwoDate(String firstString, String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			System.out.println("�������ַ�����ʽ�����쳣��" + e.getMessage());
		}
		int nDay = (int) (secondDate.getTime() - firstDate.getTime());
		return nDay;
	}
	public void make_log_file(String path,String value){//��¼ת�������־�ļ�,pathΪ·����valueΪ��־����
		String filename =path+"/log.txt";
		File outshow=new File(filename);
		PrintWriter log;
		try {
			log = new PrintWriter(new FileWriter(filename, true),true);
			log.println(value);
			//log.println("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("log error:"+e.getMessage());
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
      function fun=new function();
      //String str=fun.read_file("e:\\www\\baoyue\\upload\\hctl\\conn\\6\\6.smil");      
	  System.out.println(fun.getcurrent_sdate());
	}
}
