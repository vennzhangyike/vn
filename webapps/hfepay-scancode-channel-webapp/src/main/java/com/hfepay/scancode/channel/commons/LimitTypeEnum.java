package com.hfepay.scancode.channel.commons;

/** 限额类型 */
public enum LimitTypeEnum {
	
	LIMIT_TYPE_FRTX("分润提现限额","0"),
	LIMIT_TYPE_JY("交易限额","1"),
	LIMIT_TYPE_TX("提现限额","2"),
	LIMIT_TYPE_XYK("信用卡限额","3");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private LimitTypeEnum(String desc, String value) {
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
