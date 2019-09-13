package com.hfepay.common.web.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayChannelsEnum {
	
	
	WX_APP("WX_APP",1001,"微信APP","0"),
	WX_NATIV("WX_NATIV",1002,"微信web扫码支付","0"),
	WX_JSAPI("WX_JSAPI",1003,"微信公众号支付","0"),
	ALI_APP("ALI_APP",2001,"支付宝APP","0"),
	ALI_QRCODE("ALI_QRCODE",2002,"支付宝 扫一扫","0"),
	ALI_OFFLINE_QRCODE("ALI_OFFLINE_QRCODE",2003,"支付宝线下二维码","0"),
	ALI_WAP("ALI_WAP",2004,"支付宝wap支付","0"),
	ALI_DIRECT("ALI_DIRECT",2005,"支付宝即时到账支付","1"),
	UN_APP("UN_APP",3001,"银联手机控件支付","0"),
	UN_WEB("UN_WEB",3002,"银联网关支付","1"),
	JD_WEB("JD_WEB",4001,"京东网关支付","0"),
	JD_WAP("JD_WAP",4002,"京东WAP支付","0"),
	BD_APP("BD_APP",5001,"百度APP","0"),
	BD_WAP("BD_WAP",5002,"百度WAP","0"),
	BD_WEB("BD_WEB",5003,"百度网关支付","0"),
	YB_WAP("YB_WAP",6001,"易宝WAP","0"),
	YB_WEB("YB_WEB",6002,"易宝网关支付","0"),
	
	MY_WALLET("MY_WALLET",7001,"钱包支付","1");
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;
	/** 名称 */
	private String name;
	/** 是否可用 */
	private String status;

	private PayChannelsEnum(String desc, int value,String name,String status) {
		this.value = value;
		this.desc = desc;
		this.name = name;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static PayChannelsEnum getEnum(int value){
		PayChannelsEnum resultEnum = null;
		PayChannelsEnum[] enumAry = PayChannelsEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		PayChannelsEnum[] ary = PayChannelsEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].getName());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		PayChannelsEnum[] ary = PayChannelsEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			map.put("name", ary[num].getName());
			enumMap.put(key,map);
		}
		return enumMap;
	}

}
