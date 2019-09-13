package com.hfepay.commons.base.ciphers;


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public abstract class CipherRSA{
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	public static final String PUBLIC_KEY = "RSAPublicKey";
	public static final String PRIVATE_KEY = "RSAPrivateKey";


	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return CipherBase64.encryptBASE64(signature.sign());
	}

	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(CipherBase64.decryptBASE64(sign));
	}

	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = CipherBase64.decryptBASE64(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}


	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return CipherBase64.encryptBASE64(key.getEncoded());
	}


	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return CipherBase64.encryptBASE64(key.getEncoded());
	}

	public static Map<String, String> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, String> keyMap = new HashMap<String, String>(2);
		keyMap.put(PUBLIC_KEY, CipherBase64.encryptBASE64(publicKey.getEncoded()));
		keyMap.put(PRIVATE_KEY, CipherBase64.encryptBASE64(privateKey.getEncoded()));
		return keyMap;
	}
	
	public static void main(String[] args) {
		try {
			Map<String, String> keyMaps = CipherRSA.initKey();
			System.out.println("私钥："+keyMaps.get(PRIVATE_KEY));
			System.out.println("公钥："+keyMaps.get(PUBLIC_KEY));
			String data = "张波sdfasdfasfasdf";
			byte[] datee = CipherRSA.encryptByPublicKey(data.getBytes(), keyMaps.get(PUBLIC_KEY));
			String d = CipherBase64.encryptBASE64(datee);
			System.out.println("加密："+d);
			System.out.println("加密长度："+d.length());
			byte[] datee2 = CipherBase64.decryptBASE64(d);
			byte[] dated = CipherRSA.decryptByPrivateKey(datee2, keyMaps.get(PRIVATE_KEY));
			System.out.println("解密："+new String(dated));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
