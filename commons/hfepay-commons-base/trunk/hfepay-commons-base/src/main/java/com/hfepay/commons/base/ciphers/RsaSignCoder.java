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

public class RsaSignCoder {

	//数字签名，密钥算法  
    public static final String KEY_ALGORITHM="RSA";  
      
    /** 
     * 数字签名 
     * 签名/验证算法 
     * */  
    public static final String SIGNATURE_ALGORITHM="MD5withRSA";  
      
    /** 
     * RSA密钥长度，RSA算法的默认密钥长度是1024 
     * 密钥长度必须是64的倍数，在512到65536位之间 
     * */  
    private static final int KEY_SIZE=512;  
    //公钥  
    private static final String PUBLIC_KEY="RSAPublicKey";  
      
    //私钥  
    private static final String PRIVATE_KEY="RSAPrivateKey";  
      
    /** 
     * 初始化密钥对 
     * @return Map 甲方密钥的Map 
     * */  
    public static Map<String,Object> initKey() throws Exception{  
        //实例化密钥生成器  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);  
        //初始化密钥生成器  
        keyPairGenerator.initialize(KEY_SIZE);  
        //生成密钥对  
        KeyPair keyPair=keyPairGenerator.generateKeyPair();  
        //甲方公钥  
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();  
        //甲方私钥  
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();  
        //将密钥存储在map中  
        Map<String,Object> keyMap=new HashMap<String,Object>();  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
          
    }  
      
      
    /** 
     * 签名 
     * @param data待签名数据 
     * @param privateKey 密钥 
     * @return byte[] 数字签名 
     * */  
    public static String sign(String datas,String privates) throws Exception{  
    	byte[] data = datas.getBytes();
    	byte[] privateKey = CipherBase64.decryptBASE64(privates);  
        //取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //生成私钥  
        PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //实例化Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //初始化Signature  
        signature.initSign(priKey);  
        //更新  
        signature.update(data);  
        return CipherBase64.encryptBASE64(signature.sign());  
    }  
    /** 
     * 校验数字签名 
     * @param data 待校验数据 
     * @param publicKey 公钥 
     * @param sign 数字签名 
     * @return boolean 校验成功返回true，失败返回false 
     * */  
    public static boolean verify(String datas,String publicKeys,String signs) throws Exception{  
    	byte[] data = datas.getBytes();
    	byte[] publicKey = CipherBase64.decryptBASE64(publicKeys);
    	byte[] sign = CipherBase64.decryptBASE64(signs);
        //转换公钥材料  
        //实例化密钥工厂  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //初始化公钥  
        //密钥材料转换  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);  
        //产生公钥  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //实例化Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //初始化Signature  
        signature.initVerify(pubKey);  
        //更新  
        signature.update(data);  
        //验证  
        return signature.verify(sign);  
    }  
    /** 
     * 取得私钥 
     * @param keyMap 密钥map 
     * @return byte[] 私钥 
     * */  
    public static String getPrivateKey(Map<String,Object> keyMap) throws Exception{  
        Key key=(Key)keyMap.get(PRIVATE_KEY);  
        return CipherBase64.encryptBASE64(key.getEncoded()); 
    }  
    /** 
     * 取得公钥 
     * @param keyMap 密钥map 
     * @return byte[] 公钥 
     * */  
    public static String getPublicKey(Map<String,Object> keyMap) throws Exception{  
        Key key=(Key) keyMap.get(PUBLIC_KEY);  
        return CipherBase64.encryptBASE64(key.getEncoded()); 
    }  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        //初始化密钥  
        //生成密钥对  
        Map<String,Object> keyMap=RsaSignCoder.initKey();  
        //公钥  
        String publicKey=RsaSignCoder.getPublicKey(keyMap);  
          
        //私钥  
        String privateKey=RsaSignCoder.getPrivateKey(keyMap);  
        System.out.println("公钥："+publicKey);  
        System.out.println("私钥："+privateKey);  
        privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEA1n4+euY55NFs4zChDnshmX3eRlv5LVB+RBRjG0CrFJFXkQoa9t6wxS1zv+KtV7qzCJrLLnLW2K8q8++bZybzcQIDAQABAkA/K1zxSv5/4iGkYzz7i83ga45bwFiJPOyC1lI9w3TfEEjbdRSkcynhlSC1YvNMmsijJOgt57jMQ3KgELKBUoPFAiEA8MV1YSr+LgZqAFTn5QsrELsonO9JMwsbEyOmDkuzutMCIQDkD0rR46Xa2FlJEuM3nN7vAbQwaUMz4qNEnHLz1U/mKwIgCMmsNxeyn0tsowZ2UE3QDJRILzIsochN/KzYar0omjECIQCv4RNElxnbkqoY7tYIl5ReIqRCCYvoQcrEKRuF7GY4DQIgfuJmtCEMCR+YP0IPsbFKeqdhPoftzXs8f5NzD9ucqLE=";
        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");  
        String str="usrX3RgXVQk=";  
        System.out.println("原文:"+str);  
        //甲方进行数据的加密  
        String sign=RsaSignCoder.sign(str, privateKey);  
        System.out.println("产生签名："+sign);  
        //验证签名  
        boolean status=RsaSignCoder.verify(str, "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANZ+PnrmOeTRbOMwoQ57IZl93kZb+S1QfkQUYxtAqxSRV5EKGvbesMUtc7/irVe6swiayy5y1tivKvPvm2cm83ECAwEAAQ==", "S4xYg41LRTj/qAt5ekrKsWbetKngI5Af6O3VtJVPkt/fmyvPx8q11Szzzx+vQBGKpVDjMznQUT5qm5AtsFuLtg==");  
        System.out.println("状态："+status+"/n/n");  
          
          
    }  
}
