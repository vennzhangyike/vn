package com.hfepay.commons.base.lang.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.alibaba.druid.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 环迅加解密工具
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class Encrypter {
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	/**
	 * 定义 DES加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String Algorithm_DES = "DESede";
	
	/**
	 * 定义 DES padding模式
	 */
	private static final String AlgorithmMode_DES = "/CBC/PKCS5Padding";
	
	/**
	 * 定义 RSA加密算法
	 */
	private static final String Algorithm_RSA = "RSA";
	
	/**
	 * 定义RSA padding模式
	 */
	private static final String AlgorithmMode_RSA = "/ECB/PKCS1Padding";

	private static Encrypter ins = null;
	
	private Encrypter(){}
	
	public static Encrypter getInstance(){
		if(ins == null){
			ins = new Encrypter();
		}
		return ins;
	}
	
	/**
	 * 3DES加密
	 * @param toEncode
	 * @param key 私钥
	 * @param vector 偏移量
	 * @return
	 * @throws Exception
	 */
	public String encrypt3Des(String toEncode,String key,String vector)throws Exception{
		Cipher cipher = Cipher.getInstance(Algorithm_DES + AlgorithmMode_DES);
		SecureRandom sr = new SecureRandom();
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes(),0,cipher.getBlockSize());
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		byte[] encoded = cipher.doFinal(toEncode.getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(encoded).replace("\r\n", "");
	}
	
	/**
	 * 3DES解密
	 * @param toDecode
	 * @param key 私钥
	 * @param vector偏移量
	 * @return
	 * @throws Exception
	 */
	public String decrypt3Des(String toDecode,String key,String vector)throws Exception{
		Cipher cipher = Cipher.getInstance(Algorithm_DES + AlgorithmMode_DES);
		SecureRandom sr = new SecureRandom();
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes(),0,cipher.getBlockSize());
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		byte[] todecodeBytes = new BASE64Decoder().decodeBuffer(toDecode);
		String decoded = new String(cipher.doFinal(todecodeBytes),"utf-8");
        return decoded;
	}
	
	/**
	 * RSA签名
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public String signRSA(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm_RSA);  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance("MD5withRSA");  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return encryptBASE64(signature.sign());
    }
	
	/**
	 * MD5签名
	 * @param inStr
	 * @return
	 */
	public String signMD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }
	
	/**
	 * RSA公钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public String encryptRSAByPublicKey(byte[] data, String publicKey)
			throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(Algorithm_RSA);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(Algorithm_RSA + AlgorithmMode_RSA);
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptBASE64(encryptedData);
	}
	
	/**
	 * RSA公钥加密
	 * @param data
	 * @param modulus
	 * @param exponent
	 * @return
	 * @throws Exception
	 */
	public String encryptRSAByPublicKey(byte[] data, String modulus,String exponent) throws Exception {
		BigInteger b1 = new BigInteger(modulus, 16);
		BigInteger b2 = new BigInteger(exponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
		KeyFactory keyFactory = KeyFactory.getInstance(Algorithm_RSA);
		Key publicK = keyFactory.generatePublic(keySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptBASE64(encryptedData);
	}
	
	/**
	 * BASE64解码
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptBASE64(String s) throws Exception {  
	    return new BASE64Decoder().decodeBuffer(s);  
	}
	
	/**
	 * BASE64编码
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public String encryptBASE64(byte[] s) throws Exception {  
		return new String(new Base64().byteArrayToBase64(s));
	    //return new BASE64Encoder().encode(s);  
	}
	
	/**
	 * 生成3des私钥
	 * @return
	 * @throws Exception
	 */
	public String generate3DesPrivateKey()throws Exception{
		KeyGenerator kg = KeyGenerator.getInstance(Algorithm_DES);
		return encryptBASE64(kg.generateKey().getEncoded());
	}
	
	/**
	 * 生成RSA密钥对
	 * @return
	 * @throws Exception
	 */
	public EncryptKeyPair generateRSA()throws Exception{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Algorithm_RSA);
		KeyPair key = keyGen.generateKeyPair();
		return new EncryptKeyPair(
				encryptBASE64(key.getPublic().getEncoded()),
				encryptBASE64(key.getPrivate().getEncoded()));
	}
	
	public String encryptSHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
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
	
    public static void main(String[] args)throws Exception{
    	Encrypter ins = new Encrypter();
    	String desKey = "oemS3HyKTBVhASkIjD3Cl67VtRWKLMv4";
    	String desVector = "sadfdddddlkjdddddddddddddddd";
    	String desVector2 = "sddfddd9";
    	String toEncodeString = "中国人";
    	System.out.println(desKey);
    	System.out.println("");
    	try {
			String encoded = ins.encrypt3Des(toEncodeString,desKey,desVector);
			System.out.println(encoded);
			System.out.println(ins.decrypt3Des(encoded,desKey,desVector2));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}