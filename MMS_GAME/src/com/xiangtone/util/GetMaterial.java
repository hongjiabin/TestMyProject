/*
 * Created on 2006-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.util;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetMaterial {
	private ArrayList lname=new ArrayList();
	private ArrayList lpath=new ArrayList();
	private int k=0;
	private boolean DEBUG=true;
	
	public String getType(String filename,String str) {
		String type="";
		int nPostion=filename.indexOf(str);
		if (nPostion==-1) {
			return filename;
		} else {
			type=getType(filename.substring(nPostion+1),str);
		}
		return type;
	}
	
	public String[][]  checkPath(String path) {
		getNameAndPath(path);
		String[][] strs=new String[lname.size()][2];
		for (int i=0;i<lname.size();i++) {
			strs[i][0]=(String)lname.get(i);
			strs[i][1]=(String)lpath.get(i);
		}
		return strs;
	}
	private void getNameAndPath(String path) {
		
		File f=new File(path);
		if(f.isDirectory()) {
			File[] fList=f.listFiles();
			for(int j=0;j<fList.length;j++) 
			{ 
				if(fList[j].isDirectory()) 
				{ 
					getNameAndPath(fList[j].getPath()); //在getDir函数里面又调用了getDir函数本身 
				} 
			} 
			for(int j=0;j<fList.length;j++) 
			{ 

				if(fList[j].isFile()) 
				{ 
					
					String filename=fList[j].getName();
				//	filename.replace("<","A");
				//	System.out.println("filename:"+filename);
					filename=filename.replaceAll("<","");
				//	System.out.println("filename:"+filename);
					filename=filename.replaceAll(">","");
					lname.add(filename);
					lpath.add(fList[j].toString());
				
					System.out.println("filename:"+filename);
				} 

			} 
		}
		System.out.println("ssssssssssssss");
		
		return ;
		
	}

	public static void main(String[] args) {
		GetMaterial gm=new GetMaterial();
		String[][] strs=gm.checkPath("d:\\sms\\");
	
			for (int i=0;i<strs.length;i++) {
			//	System.out.println("strs["+i+"][0]"+strs[i][0]);
				for (int j=0;j<strs[i].length;j++) {
					System.out.println("strs["+i+"]["+j+"]"+strs[i][j]);
				}
			}
		
		
		String teststr="adfsadfsadf.a";
		System.out.println(gm.getType("aaa.bbb.cccc.ddd.eeee","."));
		System.out.println("indexOf:"+teststr.indexOf("."));
		System.out.println("last byte:"+teststr.substring(teststr.indexOf(".")+1));
	}
}
