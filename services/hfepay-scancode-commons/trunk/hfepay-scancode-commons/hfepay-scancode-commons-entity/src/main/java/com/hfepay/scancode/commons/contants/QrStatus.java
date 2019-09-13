package com.hfepay.scancode.commons.contants;

public enum QrStatus {
	QR_1("二维码正常", "1"),
	
	QR_2("该二维码已禁用", "2");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private QrStatus(String desc, String code) {  
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
