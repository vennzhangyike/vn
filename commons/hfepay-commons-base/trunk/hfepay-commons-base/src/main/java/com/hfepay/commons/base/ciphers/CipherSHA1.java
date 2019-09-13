package com.hfepay.commons.base.ciphers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**   
* @Title: SHA1.java 
* @Package com.hfepay.util 
* @Description: TODO
* @author maozk
* @date 2016��5��12�� ����4:07:26 
*/
public class CipherSHA1 {
	
	public static String encrypt(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// �ֽ�����ת��Ϊ ʮ������ ��
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
