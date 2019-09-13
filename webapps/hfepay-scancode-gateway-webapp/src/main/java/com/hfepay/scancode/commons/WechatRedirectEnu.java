package com.hfepay.scancode.commons;

/**
 * 微信菜单进入个人中心的跳转类型
 * @ClassName: WechatRedirectEnu
 * @Description: TODO
 * @author: husain
 * @date: 2016年10月31日 下午3:22:15
 */
public enum WechatRedirectEnu {
	MY("个人中心", "my"),
	RECEIVABLES("收款码", "receivables"),
	WECHANT_IN("商户入驻","mechantIn"),
	WECHANT_MSG("商户信息","mechantMsg"),
	BILL("交易记录", "bill"),
	SWEEPCOLLECTION("收银员收款", "sweepcollection"),
	CAMERALSCAN("扫码收款", "cameralscan");
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private WechatRedirectEnu(String desc, String code) {  
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
