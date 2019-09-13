package com.hfepay.scancode.commons.contants;

public enum RefundStatus {
	REFUND_Y("已退款", "Y"),
	REFUND_N("未退款", "N");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private RefundStatus(String desc, String code) {  
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
