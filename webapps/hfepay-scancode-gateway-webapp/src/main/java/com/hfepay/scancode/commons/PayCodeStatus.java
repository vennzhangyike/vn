package com.hfepay.scancode.commons;

public enum PayCodeStatus {
	PAY_CODE_STATUS_1("正常", "1"),
	PAY_CODE_STATUS_2("关闭", "2");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private PayCodeStatus(String desc, String code) {  
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
