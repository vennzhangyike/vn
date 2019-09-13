package com.hfepay.scancode.commons;

/** 交易编码 */
public enum TransCodeEnum {
	
	CHANNEL_BANKCARD_CODE("渠道银行卡变更",10001),
	CHANNEL_PAYWAY_CODE("渠道支付方式变更",10002),
	MERCHANT_BANKCARD_CODE("商户银行卡变更",20001),
	MERCHANT_PAYWAY_CODE("商户支付方式变更",20002),
	AGENT_PAYWAY_CODE("代理商支付方式变更",30002),
	AGENT_BANKCARD_CODE("代理商银行卡变更",30001);
	
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
