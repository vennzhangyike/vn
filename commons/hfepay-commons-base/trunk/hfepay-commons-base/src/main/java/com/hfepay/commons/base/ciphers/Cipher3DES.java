package com.hfepay.commons.base.ciphers;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**   
* @Title: Desde.java 
* @Package com.hfepay.util 
* @Description: TODO
* @author maozk
* @date 2016��5��16�� ����2:43:15 
*/
public class Cipher3DES {
	
	/**
	 * 定义 DES加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String Algorithm_DES = "DESede";
	
	/**
	 * 定义 DES padding模式
	 */
	private static final String AlgorithmMode_DES = "/CBC/PKCS5Padding";
	
	/**
	 * 3DES加密
	 * @param toEncode
	 * @param key 私钥
	 * @param vector 偏移量
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String toEncode,String key,String vector)throws Exception{
		Cipher cipher = Cipher.getInstance(Algorithm_DES + AlgorithmMode_DES);
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes(),0,cipher.getBlockSize());
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		byte[] encoded = cipher.doFinal(toEncode.getBytes("UTF-8"));
		return CipherBase64.encryptBASE64(encoded);
	}
	
	/**
	 * 3DES解密
	 * @param toDecode
	 * @param key 私钥
	 * @param vector偏移量
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String toDecode,String key,String vector)throws Exception{
		Cipher cipher = Cipher.getInstance(Algorithm_DES + AlgorithmMode_DES);
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes(),0,cipher.getBlockSize());
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		byte[] todecodeBytes = CipherBase64.decryptBASE64(toDecode);
		String decoded = new String(cipher.doFinal(todecodeBytes),"utf-8");
        return decoded;
	}
	
	/**
	 * 生成3des私钥
	 * @return
	 * @throws Exception
	 */
	public static String generate3DesPrivateKey()throws Exception{
		KeyGenerator kg = KeyGenerator.getInstance(Algorithm_DES);
		String key = CipherBase64.encryptBASE64(kg.generateKey().getEncoded());
		key = key.substring(0, 24);
		return key;
	}
	
	public static void main(String[] args) {
		String key = "emmaOvn4fewBs1cPGKEh4g==";
		String aInput = "测试";
		try {
			String keys = generate3DesPrivateKey();
			System.out.println("key:"+keys+":"+key.length());
			String aesdata = Cipher3DES.encrypt(aInput, keys, "dfadfget");
			System.out.println("加密后："+aesdata);
			String aa = Cipher3DES.decrypt(aesdata, keys, "dfadfget");
			System.out.println("解密后："+aa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
