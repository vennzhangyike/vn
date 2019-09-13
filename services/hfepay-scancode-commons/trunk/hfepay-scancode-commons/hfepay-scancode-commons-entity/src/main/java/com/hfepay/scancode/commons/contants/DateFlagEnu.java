package com.hfepay.scancode.commons.contants;

public enum DateFlagEnu {
	DAY("启用", "D"),
	
	MONTH("禁用", "M"),
	
	YEAR("禁用", "Y");
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private DateFlagEnu(String desc, String code) {  
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
