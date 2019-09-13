package com.hfepay.scancode.commons.contants;

/**
 * 
 * @author liushuyu
 * Desc //订单状态 00：等待付款、01：交易成功、02：交易失败
 */
public enum OrderPaymentStatus {
	
	STATUS_DDFK("等待付款","00"),
	STATUS_JYCG("交易成功","01"),
	STATUS_JYSB("交易失败","02");
	
	private String desc;
	private String code;
	
	private OrderPaymentStatus(String desc,String code){
		this.desc=desc;
		this.code=code;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.code+"_"+this.desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
