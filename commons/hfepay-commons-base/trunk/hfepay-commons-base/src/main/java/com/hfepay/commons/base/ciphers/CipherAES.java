package com.hfepay.commons.base.ciphers;

import org.apache.commons.codec.binary.Base64;


/**
 * @Description AES数据加密算法和RSA数字签名算法
 * @author lemon
 * @Date 2016年10月10日 下午5:15:31
 */
public class CipherAES {
	
	private static String CHARSET = "utf-8";
	
	/**
	* @Description 签名
	* @param
	* @author lemon 
	* @Date 2016年10月10日 下午5:48:44
	 */
	public static String getSign(String content, String publicKey, String privateKey) throws Exception{
		Encrypter encrypter = new Encrypter(publicKey, privateKey);
		String signData = new String(Base64.encodeBase64(encrypter.digitalSign(content.getBytes(CHARSET))),CHARSET);
		return signData;
	}
	
	/**
	* @Description 加密串
	* @param
	* @author lemon 
	* @Date 2016年10月10日 下午5:48:44
	 */
	public static String getKey(String publicKey, String privateKey, String aesKey) throws Exception{
		Encrypter encrypter = new Encrypter(publicKey, privateKey);
		String encrtptKey = new String(Base64.encodeBase64(encrypter.RSAEncrypt(aesKey)), CHARSET);
		return encrtptKey;
	}
	
	
	/**
	* @Description 加密报文
	* @param
	* @author lemon 
	* @Date 2016年10月10日 下午5:57:36
	 */
	public static String encryptMsg(String content, String publicKey, String privateKey, String aesKey) throws Exception{
		Encrypter encrypter = new Encrypter(publicKey, privateKey);
		String encryptData = new String(Base64.encodeBase64((encrypter.AESEncrypt(content, aesKey))), CHARSET);
		return encryptData;
	}
	
	/**
	* @Description 解密报文
	* @param
	* @author lemon 
	* @Date 2016年10月10日 下午5:59:32
	 */
	public static String decryptMsg(String content, String sign, String encrtptKey, String publicKey, String privateKey) throws Exception{
		Encrypter encrypter = new Encrypter(publicKey, privateKey);
	    byte[] decodeBase64KeyBytes = Base64.decodeBase64(encrtptKey.getBytes(CHARSET));
        byte[] merchantAESKeyBytes = encrypter.RSADecrypt(decodeBase64KeyBytes);

        // 使用base64解码商户请求报文
        byte[] decodeBase64DataBytes = Base64.decodeBase64(content.getBytes(CHARSET));
        byte[] realText = encrypter.AESDecrypt(decodeBase64DataBytes,merchantAESKeyBytes);
        byte[] signBytes = Base64.decodeBase64(sign.getBytes(CHARSET));
        if(!encrypter.verifyDigitalSign(realText, signBytes)) {
        	throw new Exception("报文不完整");
        }
        return new String(realText, CHARSET);
	}
}
