/**
 *Copyright 2004 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
 */
package com.xiangtone.mms.monthselfsend.copy;

/**
 *
 *
 */
public class DeByteCode {
	private int len;//控制数组的动态大小

	private int size;//数组大小

	public int offset;//当前读到数组的位置

	private byte bytes[];//数组

	public DeByteCode(byte a[]) {
		bytes = a;
		size = a.length;
		len = a.length;
		offset = 0; //长度偏移指针
	}

	/**
	 *得到偏移指针
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 *得到动态长度
	 */
	public int getLen() {
		return len;
	}

	/**
	 *得到数组大小
	 */
	public int getSize() {
		return size;
	}

	public DeByteCode(int i) {
		bytes = new byte[i];
		size = i;
		len = 0;
		offset = 0;
	}

	/**
	 *跳过n个字节
	 *@parm int n
	 */
	public void skip(int n) {
		offset += n;
	}

	/**
	 *读取 i  个 字节
	 *@parm int i
	 */
	public byte[] getBytes(int i) throws Exception {
		if (offset + i > size) {
			throw new Exception("underrun.int16.");
		} else {
			byte b[] = new byte[i];
			for (int j = 0; j < i; j++) {
				b[j] = bytes[offset + j];
			}
			offset += i;//该指针向后移动i
			return b;
		}
	}

	/**
	 *读一个16 bit的int型数据
	 *
	 */
	public short int16() throws Exception {
		if (offset + 1 > size) {
			throw new Exception("underrun.int16.");
		} else {
			short word0 = (short) ((bytes[offset] & 0xff) << 8 | bytes[offset + 1] & 0xff);
			offset += 2;
			return word0;
		}
	}

	/**
	 *读一个8 bit的int型数据
	 *
	 */
	public byte int8() throws Exception {
		if (offset > size) {
			throw new Exception("underrun.int8.");
		} else {
			byte byte0 = bytes[offset];
			offset++;
			return byte0;
		}
	}

	/**
	 *读一个32 bit的int型数据
	 *
	 */
	public int int32() throws Exception {
		if (offset + 3 > size) {
			throw new Exception("underrun.int32.");
		} else {
			int i = (bytes[offset] & 0xff) << 24
					| (bytes[offset + 1] & 0xff) << 16
					| (bytes[offset + 2] & 0xff) << 8 | bytes[offset + 3]
					& 0xff;
			offset += 4;
			return i;
		}
	}

	/*
	 **
	 *md5加密后的字节转为字符串
	 *@
	 */
	public String byte2String(byte[] buffer) {
		String str = "";
		for (int j = 0; j < buffer.length; j++) {
			byte b = buffer[j];
			int bb = b;
			if (bb < 0)
				bb = 256 + b;
			String a = Integer.toHexString(bb);
			if (a.length() == 1)
				a = "0" + a;
			str += a;
		}
		return str;
	}

	/**
	 *取得i个字节的数据并转换为String
	 *@parm int i
	 */
	public String asciiz(int i) {
		String str = null;
		byte abyte0[] = new byte[i];
		int j;
		for (j = 0; j < abyte0.length; j++)
			abyte0[j] = bytes[offset + j];
		offset += i;

		if (j == 0) {
			str = null;
		} else {
			str = new String(abyte0);
		}
		return str;

		/* byte abyte0[] = new byte[i + 1];
		 int j;
		 for(j = 0; j < i + 1 && offset + j < size && bytes[offset + j] != 0; j++)
		 abyte0[j] = bytes[offset + j];
		 
		 if(offset + j == size)
		 throw new Exception("underrun.string.");
		 // if(j == i + 1)
		 // throw new Exception("toolong.string.");
		 offset += i;
		 if(j == 0)
		 {
		 return null;
		 } else
		 {
		 abyte0[j] = 0;
		 String str = new String(abyte0, 0, 0, j);
		 if(str == null) str = "null!";
		 return str;
		 }*/
	}

	/*
	 public static String asHex (byte hash[]) {
	 StringBuffer buf = new StringBuffer(hash.length * 2);
	 int i;
	 
	 for (i = 0; i < hash.length; i++) {
	 if (((int) hash[i] & 0xff) < 0x10)
	 buf.append("0");
	 buf.append(Long.toString((int) hash[i] & 0xff, 16));
	 }
	 
	 return buf.toString();
	 }
	 */
	/**
	 *打印数组
	 */
	public void printBytes() {
		int i;
		int j = 0;
		for (i = 0; i < len; i++) {
			if (i % 10 == 0) {
				System.out.println('\n');
				j++;
				System.out.print("Line Number " + j + "  :");
			}
			System.out.print(' ');
			System.out.print(' ');
			System.out.print('[');
			System.out.print(Long.toString((int) bytes[i] & 0xff, 16));
			System.out.print('|');
			System.out.print((char) bytes[i]);
			System.out.print(']');

		}
		System.out
				.println("\n-------------------------------------------------------------------");
	}
}