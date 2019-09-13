package com.hfepay.scancode.commons.contants;

public enum RateType {
	RATE_TYPE_T0("T0", "1"),
	RATE_TYPE_T1("T1", "2");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private RateType(String desc, String code) {  
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
