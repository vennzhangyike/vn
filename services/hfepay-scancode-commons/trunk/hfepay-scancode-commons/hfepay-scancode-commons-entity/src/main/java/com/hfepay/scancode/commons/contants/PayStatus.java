package com.hfepay.scancode.commons.contants;

public enum PayStatus {
	PAYSTATUS_ACCOUNTS_WAIT("等待支付", "00"),
	PAYSTATUS_CHANNEL_TREATE("渠道支付中", "01"),
	PAYSTATUS_SUCCESS("支付成功", "02"),
	PAYSTATUS_FAIL("支付失败", "03");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private PayStatus(String desc, String code) {  
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
