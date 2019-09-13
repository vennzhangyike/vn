package com.hfepay.scancode.commons.contants;

public enum PayType {
	PAYTYPE_QB("钱包支付", "0000"),
	PAYTYPE_ZFB("支付宝支付", "0001"),
	PAYTYPE_WX("微信支付", "0002"),
	PAYTYPE_UNION("银联支付", "0003"),
	PAYTYPE_QQ("QQ支付", "0005"),
	PAYTYPE_WXGZH("微信公众号支付", "0004");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private PayType(String desc, String code) {  
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
