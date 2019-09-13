package com.hfepay.scancode.commons.contants;

/**
 * 
 * @author liushuyu
 * Desc//0:订单已创建 1:处理中2:处理成功 3:处理失败
 */
public enum WithdrawsStatus {
	WD_STATUS_CREATE("订单已创建", "0"),
	WD_STATUS_CHANNEL_TREATE("处理中", "1"),
	WD_STATUS_SUCCESS("处理成功", "2"),
	WD_STATUS_FAIL("处理失败", "3");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private WithdrawsStatus(String desc, String code) {  
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
