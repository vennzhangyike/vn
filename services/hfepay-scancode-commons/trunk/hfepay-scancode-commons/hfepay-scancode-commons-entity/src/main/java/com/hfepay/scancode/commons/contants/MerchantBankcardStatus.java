package com.hfepay.scancode.commons.contants;

public enum MerchantBankcardStatus {
	MERCHANTBANKCARD_STATUS_1("有效", "1"),
	MERCHANTBANKCARD_STATUS_2("无效", "2"),
	MERCHANTBANKCARD_STATUS_3("审核中", "3"),
	MERCHANTBANKCARD_STATUS_4("审核不通过", "4"),
	MERCHANTBANKCARD_STATUS_5("渠道审核中", "5");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private MerchantBankcardStatus(String desc, String code) {  
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
