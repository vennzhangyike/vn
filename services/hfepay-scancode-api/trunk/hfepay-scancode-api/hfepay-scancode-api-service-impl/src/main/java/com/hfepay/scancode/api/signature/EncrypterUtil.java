package com.hfepay.scancode.api.signature;

import org.apache.commons.codec.binary.Base64;

import com.hfepay.commons.base.ciphers.Encrypter;
import com.hfepay.scancode.api.entity.vo.Message;
import com.hfepay.scancode.api.exception.ValidatException;
import com.hfepay.scancode.api.service.config.HfepayConfig;


public class EncrypterUtil {
	
	/**
	 * 加密报文
	 * @param message
	 * @param publickey
	 * @param privatekey
	 * @return
	 * @throws Exception
	 */
	public static Message encode(final String message,final String publickey, final String privatekey) throws Exception {
		String aesKey = Encrypter.getRandomString(16);
		Encrypter encrypter = new Encrypter(publickey, privatekey);
		String encryptData = new String(Base64.encodeBase64((encrypter.AESEncrypt(message, aesKey))), HfepayConfig.input_charset);
		String signData = new String(Base64.encodeBase64(encrypter.digitalSign(message.getBytes(HfepayConfig.input_charset))), HfepayConfig.input_charset);
		String encrtptKey = new String(Base64.encodeBase64(encrypter.RSAEncrypt(aesKey)), HfepayConfig.input_charset);
		return new Message(encryptData, signData, encrtptKey);
	}

	/**
	 * 解密报文
	 * @param content
	 * @param sign
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decode(final String content, final String sign, final String key, final String publickey, final String privatekey) throws Exception {
		Encrypter encrypter = new Encrypter(publickey, privatekey);
		byte[] decodeBase64KeyBytes = Base64.decodeBase64(key.getBytes(HfepayConfig.input_charset));
		byte[] merchantAESKeyBytes = encrypter.RSADecrypt(decodeBase64KeyBytes);
		// 使用base64解码商户请求报文
		byte[] decodeBase64DataBytes = Base64.decodeBase64(content.getBytes(HfepayConfig.input_charset));
		byte[] realText = encrypter.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes);
		byte[] signBytes = Base64.decodeBase64(sign.getBytes(HfepayConfig.input_charset));
		if (!encrypter.verifyDigitalSign(realText, signBytes)) {
			throw new ValidatException("100012", "报文解密失败");
		}
		return new String(realText, HfepayConfig.input_charset);
	}
	
	
}
