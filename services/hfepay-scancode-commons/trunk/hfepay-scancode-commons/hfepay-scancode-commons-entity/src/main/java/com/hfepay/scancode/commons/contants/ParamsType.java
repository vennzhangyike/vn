package com.hfepay.scancode.commons.contants;

public enum ParamsType {
	PARAMS_SMS("短信参数", "0"),
	PARAMS_SETTLEAMT("渠道结算费率", "1"),
	PARAMS_DOMAIN_INFO("渠道域名参数", "2"),
	PARAMS_CALLBACK_INFO("回调地址", "3"),
	PARAMS_OUTHER("其他", "99");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private ParamsType(String desc, String code) {  
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
