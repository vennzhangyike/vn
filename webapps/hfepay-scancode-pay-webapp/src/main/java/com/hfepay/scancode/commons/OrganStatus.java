package com.hfepay.scancode.commons;

public enum OrganStatus {
	ORGAN_1("正常", "1"),
	
	ORGAN_2("关闭", "2");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private OrganStatus(String desc, String code) {  
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
