package com.hfepay.scancode.commons.contants;

public enum OrderResultCode {
	PAY_SUCCESS("无异常", "00"),
	PAY_ACCOUNT_FAIL("记账失败", "01"),
	PAY_FAIL("支付失败", "02");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private OrderResultCode(String desc, String code) {  
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
