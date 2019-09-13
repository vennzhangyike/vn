package com.hfepay.scancode.commons;

/** 交易编码 */
public enum StoreStatusEnum {
	STORE_STATUS_0("初始","0"),
	STORE_STATUS_1("待审核","1"),
	STORE_STATUS_2("审核中","2"),
	STORE_STATUS_3("审核通过","3"),
	STORE_STATUS_4("审核拒绝","4");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private StoreStatusEnum(String desc, String value) {
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
