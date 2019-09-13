package com.hfepay.scancode.commons.exception;

/**
 * 对外接口返回码定义
 * @author husain
 *
 */
public enum ApiErrorCode {
	//系统级别错误码
	SYSTEM_000000("交易成功", "000000"),
	SYSTEM_000001("请求成功", "000001"),//头部报文，000001的情况下才有body
	SYSTEM_999999("系统异常", "999999"),
	SYSTEM_999998("通讯异常", "999998"),
	SYSTEM_100000("交易失败", "100000"),
	
	//校验类错误码定义：
	VALIDAT_100001("报文格式不合法", "100001"),
	VALIDAT_100002("请求参数不能为空", "100002"),
	VALIDAT_100003("请求参数不合法", "100003"),
	VALIDAT_100004("报文解密失败", "100004"),
	VALIDAT_100005("合作方公钥未提供或不可用", "100005"),
	VALIDAT_100006("报文内容不正确","100006"),
	VALIDAT_100007("交易流水号，请求流水号至少指定一个","100007"),
	VALIDAT_100008("商户状态有误不能支付","100008"),
	VALIDAT_100009("IP地址未绑定","100009"),
	
	//订单查询类
	QUERY_200000("查询成功","200000"),
	QUERY_200001("查询失败","200001"),
	QUERY_200002("收款人不存在","200002"),
	QUERY_200003("订单不存在","200003"),
	QUERY_200004("订单状态异常","200004"),//订单中不存在必须的查询参数
	
	//退款类
	REFUND_300000("退款成功","300000"),
	REFUND_300001("退款失败","300001"),
	REFUND_300002("已全额退款", "300002"),
	REFUND_300003("此交易已被撤销", "300003"),
	//支付类
	PAY_400000("支付成功","400000"),
	PAY_400001("支付失败","400001"),
	PAY_400002("计算限额失败","400002"),
	PAY_400003("收款人不存在","400003"),
	PAY_400004("代理商或者渠道不允许执行收款操作","400004"),
	PAY_400005("支付流水号重复","400005"),
	PAY_400006("不支持的支付类别","400006"),//payCode不在支持的范围内
	PAY_400007("支付处理中等待银行回调","400007"),//条码支付大额验证密码的情况下使用
	;
	
	
	
	
	// 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private ApiErrorCode(String desc, String code) {  
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
