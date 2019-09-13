package com.hfepay.scancode.commons.contants;

public enum OrderStatus {
	ORDER_TREATE("等待付款", "00"),
	ORDER_SUCCESS("交易成功", "01"),
	ORDER_FAIL("交易失败", "02");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private OrderStatus(String desc, String code) {  
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
