package com.hfepay.scancode.commons.contants;

/** 
 * redis key 定义
 */
public enum RedisKeyEnum {
	
	CHANNEL_BASE("CHANNEL","BASE"),//渠道基本信息,如：CHANNEL：BASEXXXXXXX
	CHANNEL_BASE_EX("CHANNEL","BASE_EX"),//渠道基本信息,如：CHANNEL：BASEXXXXXXX
	CHANNEL_PAYWAY("CHANNEL","PAYWAY"),
	AGENT_BASE("AGENT","BASE"),		//代理商基本信息
	AGENT_PAYWAY("AGENT","PAYWAY"),
	MERCHANT_BASE("MERCHANT","BASE"),//商户基本信息
	MERCHANT_PAYWAY("MERCHANT","PAYWAY"),//商户基本信息
	STORE_BASE("STORE","BASE"),//门店基本信息
	MERCHANT_IMAGE_WECHAT("MERCHANT:IMG:","WECHAT"),//商户上传的微信图片,有效期三天，用于修正图片下载的问题
	ORGANNO_SEQ("ORGAN:NO:","SEQ"),//节点的seq对应的key,用户父节点，直接点的递归查询
	ORGAN_LIMIT("ORGAN","LIMIT"),//机构限额,如ORGAN:LIMIT:XXX
	PARAMS_INFO("PARAMS:","PARAMS_INFO"),
	MERCHANT_CASHIER_BASE("MERCHANT_CASHIER","BASE");//收银员基本信息;
	/** 枚举值 */
	private String key;
	/** 描述 */
	private String group;

	private RedisKeyEnum(String group, String key) {
		this.key = key;
		this.group = group;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
