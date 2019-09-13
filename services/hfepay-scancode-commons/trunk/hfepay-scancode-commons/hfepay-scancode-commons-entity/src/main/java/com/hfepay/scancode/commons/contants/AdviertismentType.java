package com.hfepay.scancode.commons.contants;

/**
 * 广告枚举
 * @author Administrator
 *
 */
public enum AdviertismentType {
	HOME_BANNEL("首页广告", "0"),
	PAY_BANNEL("支付页面广告", "1");
	// 成员变量  
    private String desc;  
    private String code;  
    
    // 构造方法  
    private AdviertismentType(String desc, String code) {  
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
