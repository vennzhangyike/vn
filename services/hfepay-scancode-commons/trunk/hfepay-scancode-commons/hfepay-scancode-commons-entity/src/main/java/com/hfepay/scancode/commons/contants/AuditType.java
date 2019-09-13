package com.hfepay.scancode.commons.contants;

public enum AuditType {
	MERCHANT_INFO("商户入驻审核 (上级，平台)", "0"),
	MERCHANT_BANKCARD("银行卡变更审核(平台)", "1"),
	ADVIERTISEMENT_INFO("广告信息审核(平台)", "2");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private AuditType(String desc, String code) {  
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
