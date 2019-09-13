package com.hfepay.scancode.commons.contants;

public enum MerchantStatus {
	MERCHANT_STATUS_0("申请中", "0"),
	MERCHANT_STATUS_1("上级审核通过", "1"),
	MERCHANT_STATUS_2("上级审核拒绝", "2"),
	MERCHANT_STATUS_3("平台审核通过", "3"),
	MERCHANT_STATUS_4("平台审核拒绝", "4"),
	MERCHANT_STATUS_5("停用", "5"),
	MERCHANT_STATUS_6("民生审核中", "6"),
	MERCHANT_STATUS_7("民生审核拒绝", "7"),
	MERCHANT_STATUS_8("初始状态", "8");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private MerchantStatus(String desc, String code) {  
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
