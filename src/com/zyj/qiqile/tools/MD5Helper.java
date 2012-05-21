package com.zyj.qiqile.tools;

import java.security.MessageDigest;

public class MD5Helper {
	public final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public final static String MD5(String s) {
		if (s != null) {
			try {
				byte[] btInput = s.getBytes();
				// ���MD5ժҪ�㷨�� MessageDigest ����
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				// ʹ��ָ�����ֽڸ���ժҪ
				mdInst.update(btInput);
				// �������
				byte[] md = mdInst.digest();
				// ������ת����ʮ�����Ƶ��ַ�����ʽ
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
