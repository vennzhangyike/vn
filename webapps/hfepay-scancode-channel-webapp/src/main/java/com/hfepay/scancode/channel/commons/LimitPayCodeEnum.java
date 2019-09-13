package com.hfepay.scancode.channel.commons;

/** 限额通道 */
public enum LimitPayCodeEnum {
	
	LIMIT_TYPE_QB("钱包","0"),
	LIMIT_TYPE_WXGZH("微信公众号","1"),
	LIMIT_TYPE_ZFB("支付宝","2");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private LimitPayCodeEnum(String desc, String value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
