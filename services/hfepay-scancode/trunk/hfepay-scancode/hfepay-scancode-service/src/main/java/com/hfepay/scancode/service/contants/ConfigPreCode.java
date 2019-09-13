package com.hfepay.scancode.service.contants;

/**
 * 配置编码抬头类型
 * @author panq
 *
 */
public class ConfigPreCode {
	
	/**
	 * 商户编码抬头
	 */
	public static String MERCHANT_PRE_CODE = "MER";
	
	/**
	 * 商户门店抬头
	 */
	public static String STORE_PRE_CODE = "STORE";
	
	/**
	 * 商户二维码抬头
	 */
	public static String QRCODE_PRE_CODE = "CODE";
	/**
	 * 商户编码抬头
	 */
	public static String CASHIER_PRE_CODE = "CASHIER";
	/**
	 * 商户图片idcardImg1
	 */
	public static final String KEY_IDCARDIMG1=":idcardImg1";
	/**
	 * 商户图片idcardImg2
	 */
	public static final String KEY_IDCARDIMG2=":idcardImg2";
	/**
	 * 商户图片idcardImg3
	 */
	public static final String KEY_IDCARDIMG3=":idcardImg3";
	/**
	 * 商户图片groupPhotoImg
	 */
	public static final String KEY_GROUPPHONEIMG=":groupPhotoImg";
	/**
	 * 商户图片storeImg
	 */
	public static final String KEY_STOREIMG=":storeImg";
	/**
	 * 商户图片storePhotosImg
	 */
	public static final String KEY_STOREPHONESIMG=":storePhotosImg";
	/**
	 * 商户图片merchantLicenseImg
	 */
	public static final String KEY_MERCHANTLICENSEIMG=":merchantLicenseImg";
	
	
	/**
	 * 图片在redis存放时间三天（微信服务器也只存放三天时间）
	 */
	public static final int KEY_LIVE_TIME = 259200;//三天
	
	public static final String SYSTEM_NODE_SEQ="system";//运营平台设定一个默认的nodeSeq
}
