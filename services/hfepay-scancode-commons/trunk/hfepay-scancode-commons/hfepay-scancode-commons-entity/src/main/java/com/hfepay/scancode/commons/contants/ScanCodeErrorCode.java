package com.hfepay.scancode.commons.contants;

public enum ScanCodeErrorCode {
	SYSTEM_000000("交易成功", "000000"),
	
	SYSTEM_999999("系统异常", "999999"),
	SYSTEM_999998("通讯异常", "999998"),
	
	//校验类错误码定义：
	VALIDAT_100001("报文格式不合法", "100001"),
	VALIDAT_100002("请求参数不能为空", "100002"),
	VALIDAT_100003("请求参数不合法", "100003"),
	
	//交易类错误码定义
	TRADE_200001("支付方式已被禁用，不能支付","200001"),
	TRADE_200002("未配置支付方式，不能支付","200002"),
	TRADE_200003("支付方式已被禁用，不能支付","200003"),
	TRADE_200004("原交易订单不存在","200004"),
	TRADE_200005("原支付订单不存在","200005"),
	TRADE_200006("该商户已产生资金交易，不能删除","200006"),
	
	TRADE_300001("渠道支付异常","300001"),
	TRADE_300002("渠道支付失败","300002"),
	TRADE_300003("该渠道暂不能支付","300003"),
	
	TRADE_400001("提现渠道处理异常","400001"),
	TRADE_400002("提现渠道处理","400002"),
	TRADE_400000("提现渠道受理中","400000"),
	
	PAY_500000("消息推送子类未设置payCode","500000"),
	PAY_500001("子类未实现消息推送","500001"),
	PAY_500002("计算限额失败","500002"),
	PAY_500003("支付方式不存在","500003"),
	PAY_500004("子类需要指定支付接口","500004"),
	PAY_500005("支付订单不存在","500005"),
	PAY_500006("商户费率未设置","500006"),
	
	
	WITHDRAW_600000("提现策略不存在","600000"),
	WITHDRAW_600001("提现支付类型未设置","600001"),
	WITHDRAW_600002("提现消息推送支付方式未设置","600002"),
	
	QRCODE_700000("二维码不在支持范围内","700000")
	;
	
	
	
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private ScanCodeErrorCode(String desc, String code) {  
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
