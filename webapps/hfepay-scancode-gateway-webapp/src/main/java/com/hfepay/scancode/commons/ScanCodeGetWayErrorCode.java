package com.hfepay.scancode.commons;

public enum ScanCodeGetWayErrorCode {
	SYSTEM_000000("交易成功", "000000"),
	
	SYSTEM_999999("系统异常", "999999"),
	
	//校验类错误码定义：
	VALIDAT_100001("报文格式不合法", "100001"),
	VALIDAT_100002("请求参数不能为空", "100002"),
	
	VALIDAT_100003("商户状态不正确", "100003"),
	VALIDAT_100004("代理商已被禁用", "100004"),
	VALIDAT_100005("渠道已被禁用", "100005"),
	
	VALIDAT_100006("商户不存在", "100006"),
	VALIDAT_100007("代理商不存在", "100007"),
	VALIDAT_100008("渠道不存在", "100008"),
	VALIDAT_100009("该扫码方式不支持", "100009"),
	VALIDAT_100010("该支付通道已关闭", "100010"),
	VALIDAT_100011("商户该支付通道未开通", "100011"),
	VALIDAT_100012("代理商该支付通道未开通", "100012"),
	VALIDAT_100013("渠道该支付通道未开通", "100013"),
	VALIDAT_100014("商户不支持提现", "100014"),
	VALIDAT_100015("商户未审核通过不能提现", "100015"),
	VALIDAT_100016("商户未审核通过,请先认证", "100016"),
	VALIDAT_100017("对公账户不支持提现", "100017"),
	VALIDAT_100018("商户存在异常", "100018"),
	
	//交易类错误码定义
	TRADE_200001("交易处理中，请稍后","200001"),
	TRADE_300001("账户无余额，提现失败","300001"),
	
	TRADE_400001("您是收银员，无此权限","400001");
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private ScanCodeGetWayErrorCode(String desc, String code) {  
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
