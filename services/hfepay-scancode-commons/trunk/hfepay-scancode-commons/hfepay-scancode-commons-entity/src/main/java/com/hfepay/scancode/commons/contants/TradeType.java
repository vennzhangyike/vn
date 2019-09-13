package com.hfepay.scancode.commons.contants;

public enum TradeType {
	TRADE_TYPE_PAY("支付", "02"),
	TRADE_TYPE_WITHDRAW("提现", "01");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private TradeType(String desc, String code) {  
        this.desc = desc;  
        this.code = code; 
    }  
    //覆盖方法  
    @Override  
    public String toString() {  
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
