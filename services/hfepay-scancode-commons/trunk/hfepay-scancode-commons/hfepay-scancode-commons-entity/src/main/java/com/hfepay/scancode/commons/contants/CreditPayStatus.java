package com.hfepay.scancode.commons.contants;

public enum CreditPayStatus {
	CREDIT_PAY_STATUS_OPEN("正常", "1"),
	CREDIT_PAY_STATUS_CLOSE("关闭", "2");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private CreditPayStatus(String desc, String code) {  
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
