package com.hfepay.scancode.commons.contants;

public enum IdentityType {
	Identity_Channel("渠道","1"),//渠道
	Identity_Agent("代理商","2"),//代理商
	Identity_Merchant("商户","3"),//商户
	Identity_Cashier("收银员","4");//收银员
	
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private IdentityType(String desc, String code) {  
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

