package com.hfepay.scancode.commons.contants;

/**
 * 
 * @author liushuyu
 * Desc //支付状态 00：支付成功、01：支付失败、02：渠道处理中、03：记账处理中
 */
public enum OrderPayStatus {
	
	STATUS_ZFCG("支付成功","00"),
	STATUS_ZFSB("支付失败","01"),
	STATUS_QDCLZ("渠道处理中","02"),
	STATUS_JZCLZ("记账处理中","03");
	
	
	private String desc;
	private String code;
	
	private OrderPayStatus(String desc,String code){
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
