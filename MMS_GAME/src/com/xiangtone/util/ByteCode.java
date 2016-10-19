/**
 * Created on 2006-7-20
 * Copyright 2006 Xiamen Xiangtone Co. Ltd.
 * All right reserved.
*/
package com.xiangtone.util;

/**
 * Document:     ByteCode.java
 * Description:  ������֯
 * Copyright:    Copyright (c) 2006
 * Company:      xiaingtone
 * Created:		 2006-7-20 11:30:00
 * @author: 	 cyhq
 * @version:
 */
public class ByteCode {
	/**
	 * len           ��������Ķ�̬��С
	 * size 		 �����ʵ�ʴ�С
	 * bytes[]		 ������
	 */
	public int len;
	public int size;
	public byte bytes[];
	/**
	*���캯�� ��ʼ��һ����СΪi������
	*@parm int i
	*/
	public ByteCode(int i)
	{
		bytes = new byte[i];
	      	size = i;
	     	len =0;
	}
	/**
	*���캯�� ͨ�����е�����������
	*@parm byte[] a
	*/
	public ByteCode(byte a[])
	{
		bytes = a;
	    size = a.length;
	    len =a.length;
	}
	/**
	*�������鶯̬����
	*/
	public int getLen()
	{
		return len;
	}
	/**
	*���������С
	*/
	public int getSize()
	{
		return size;
	}
	/**
	*ֱ�ӷ���bytes,������Ҫʹ���������
	*/
	public byte[] getOrigBytes()
	{
		return bytes;
	}

	/**
	*ͨ��һ���������鷵�أ����Ա���ֱ�ӷ���bytes�����
	*reference��������������ı�bytes
	*/
	public byte[] getBytes()
	{
		byte a[] = new byte[len];
	      	for( int i = 0; i< len; i++)
	      		a[i] = bytes[i];
		return a;
	}
	/**
	*�������С����������Ҫ�Ž�������ʱ��������Ĵ�С
	*
	*/
	public void increase(int i)
	{
		int j = size+i;
	      	byte a[] = new byte[j];
	      	for(int k = 0; k <size; k++)
	        	a[k] = bytes[k];
	      	bytes = a;
	      	size = j;
	}	
	/**
	*����һ��16λ��int�ͱ�����bytes��
	*@parm a 16 bit format int 
	*
	*/
	public void AddInt16(short b)
	{
		if(len + 2 > size )
	      		increase(2);
	      	bytes[len] = (byte) (b >>>8 & 0xff);
	      	len ++;
	      	bytes[len] = (byte) (b & 0xff);
	      	len ++;
	}
	/**
	*����һ��16λ��short�ͱ�����bytes��
	*@parm a 16 bit format short 
	*
	*/
	public void AddShort(short b)
	{
		if(len + 2 > size )
	      		increase(2);
	      	bytes[len] = (byte) (b >>>8 & 0xff);
	      	len ++;
	      	bytes[len] = (byte) (b & 0xff);
	      	len ++;
	}
	/**
	*����һ��32λ��int�ͱ�����bytes��
	*@parm a 32 bit format int 
	*/
	public void AddInt32( int i)
	{
		if(len + 4 > size)
            increase(4);
        bytes[len] = (byte)(i >>> 24 & 0xff);
        len++;
        bytes[len] = (byte)(i >>> 16 & 0xff);
        len++;
        bytes[len] = (byte)(i >>> 8 & 0xff);
        len++;
        bytes[len] = (byte)(i & 0xff);
        len++;
	}

	/**
	*����һ��8λ��byte�ͱ�����bytes��
	*@parm byte b
	*/	  
	public void AddInt8( byte b)
	{
		if(len + 1 >size)
			increase(1);
      	bytes[len] = b;
      	len ++;
	}
	/**
	*����һ��8λ��int or short�ͱ�����bytes��
	*@parm short b
	*/	
	public void AddInt8( short b)
	{
		AddInt8((byte)b);
	}
	/**
	*����һ��8λ��byte�ͱ�����bytes��
	*@parm byte b
	*/
	public void AddByte( byte b)
	{
		if(len + 1 >size)
			increase(1);
      	bytes[len] = b;
      	len ++;
	}
	/**
	*����һ�����鵽��bytes��
	*@parm byte[] b
	*/		
	public void AddBytes( byte b[]) throws Exception
	{
		if(b == null)
     		throw new java.lang.Exception(" Byte[] is null");
		if(len == 0)
	  	{
     		bytes = new byte[b.length];
     		for(int i=0;i<b.length; i++)
     			bytes[i]=b[i];
     		len = b.length;
     		size = len;
	 	}
	 	else
	 	{
	    	int i;
	    	int j;
			byte  c[] = new byte[len + b.length];
			for(i=0 ;i<len; i++)
	       		c[i]=bytes[i];
	       		for(j=0;j<b.length; j++)
	       			c[i+j]=b[j];
	       		bytes=c;
	       		len = len+b.length;
	       		size = len;
	      	}
	}
	/**
	*��string�е�iλ�ӵ�bytes��
	*@parm String s , int i
	*
	*/
	public void addAsciiz(String s, int i) throws Exception
	{
		int j = s.getBytes().length;
	        if(j > i) 
	        	throw new Exception("toolong.string.");
	        if(len + i > size)
	        	increase(i);
		//byte asc = s.getBytes();
	        for(int k = 0; k < j; k++)
	        {
			bytes[len] = (byte)(s.charAt(k) & 0xff);
	            	len ++;
	        }
	        len += (i-j);
	}

	/**
	*��ӡ������Ϣ
	*/
	public void  printBytes()
	{
		int i;
	        int j =0;
	        for (i=0; i<len; i++)
	        {
	        	if( i % 10 == 0)
	             	{
	                	System.out.println('\n');
	                	j++;
	                	System.out.print("Line Number "+j+"  :");
	              	}
	              	System.out.print(' ');
	              	System.out.print(' ');
	              	System.out.print('[');
	              	System.out.print(Long.toString((int)bytes[i]& 0xff, 16));
	              	System.out.print('|');
	              	System.out.print((char)bytes[i]);
	              	System.out.print(']');
		}
	                System.out.print("\n-------------------------------------------------------------------");
	}
	public static void main(String[] args) {
	}
}
