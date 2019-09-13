package com.hfepay.scancode.channel.commons;

/** 交易编码 */
public enum TransCodeEnum {
	
	CHANNEL_BANKCARD_CODE("渠道银行卡变更",10001),
	CHANNEL_PAYWAY_CODE("渠道支付方式变更",10002),
	MERCHANT_BANKCARD_CODE("商户银行卡变更",20001);
	
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private TransCodeEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
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
	
}
