package com.hfepay.scancode.channel.commons;

/** 限制方式 */
public enum LimitModeEnum {
	
	LIMIT_MODE_DB("单笔","0"),
	LIMIT_MODE_QR("全日","1");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private LimitModeEnum(String desc, String value) {
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
