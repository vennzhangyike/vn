package com.hfepay.scancode.commons.contants;

public enum BusinessType {
	BUSINESSTYPE_0("基础商品类业务", "0"),
	BUSINESSTYPE_1("普通消费类业务", "1"),
	BUSINESSTYPE_2("二维码收款业务", "2");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private BusinessType(String desc, String code) {  
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
