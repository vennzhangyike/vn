package com.hfepay.scancode.api.webank.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

public class Signature {
	public static String sign(List<String> values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}

		values.removeAll(Collections.singleton(null));// remove null
		java.util.Collections.sort(values);

		StringBuilder sb = new StringBuilder();
		for (String s : values) {
			sb.append(s);
		}
		System.out.println("###SIGN###:"+sb.toString());
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(sb.toString().getBytes("UTF-8"));
			String sign = bytesToHex(md.digest());
			return sign;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	 public static String bytesToHex(byte[] src) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < src.length; n++) {
			stmp = (java.lang.Integer.toHexString(src[n] & 0XFF));
			if (stmp.length() == 1){
				hs = hs + "0" + stmp;
			}else{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	 }
	 
	 public static String md5Sign(JSONObject respjson, String keystr) {
			JSONObject jsonObject = JSONObject.fromObject(respjson.toString());
			List<String> keylist = new ArrayList<String>();
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) { // 先遍历整个
				String key = (String) iter.next();
				if (!"sign".equals(key)) {
					keylist.add(key + "=" + jsonObject.getString(key));
				}
			}
			Collections.sort(keylist);
			String signtmp = "";

			int itmp = 0;
			for (String string : keylist) {
				// 空值的标签不参与签名
				String[] keyvalue = string.split("=");
				if (keyvalue.length >= 2) {
					if (itmp != 0) {
						signtmp = signtmp + "&" + string;
					} else {
						signtmp = string;
					}
				}
				itmp++;

			}
			signtmp = signtmp + "&key=" + keystr;
			String singnmd5 = MD5Encode(signtmp);
			return singnmd5;

		}
		
		static public String MD5Encode(String strSrc) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				String result = "";
				byte[] temp;
				temp = md5.digest(strSrc.getBytes("UTF8"));
				for (int i = 0; i < temp.length; i++) {
					result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
				}
				return result.toUpperCase();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
