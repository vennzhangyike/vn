package com.hfepay.scancode.api.common;

public enum PushMessageErrorCode {
	//微信公众号推送消息错误码
	PUSH_CODE_1("系统繁忙","-1"),
	PUSH_CODE_0("请求成功","0"),
	PUSH_CODE_40001("验证失败","40001"),
	PUSH_CODE_40002("不合法的凭证类型","40002"),
	PUSH_CODE_40003("不合法的OpenID","40003"),
	PUSH_CODE_40004("不合法的媒体文件类型","40004"),
	PUSH_CODE_40005("不合法的文件类型","40005"),
	PUSH_CODE_40006("不合法的文件大小","40006"),
	PUSH_CODE_40007("不合法的媒体文件id","40007"),
	PUSH_CODE_40008("不合法的消息类型","40008"),
	PUSH_CODE_40009("不合法的图片文件大小","40009"),
	PUSH_CODE_40010("不合法的语音文件大小","40010"),
	PUSH_CODE_40011("不合法的媒体文件类型","40011"),
	PUSH_CODE_40012("不合法的文件类型","40012"),
	PUSH_CODE_40013("不合法的文件大小","40013"),
	PUSH_CODE_41001("不合法的媒体文件id","41001"),
	PUSH_CODE_41002("验证失败","41002"),
	PUSH_CODE_41003("不合法的凭证类型","41003"),
	PUSH_CODE_41004("不合法的OpenID","41004"),
	PUSH_CODE_41005("不合法的媒体文件类型","41005"),
	PUSH_CODE_41006("不合法的文件类型","41006"),
	PUSH_CODE_42001("不合法的文件大小","42001"),
	PUSH_CODE_43002("不合法的媒体文件id","43002"),
	PUSH_CODE_43003("需要HTTPS请求","43003"),
	PUSH_CODE_44001("多媒体文件为空","44001"),
	PUSH_CODE_44002("POST的数据包为空","44002"),
	PUSH_CODE_44003("图文消息内容为空","44003"),
	
	PUSH_CODE_45001("多媒体文件大小超过限制","45001"),
	PUSH_CODE_45002("消息内容超过限制","45002"),
	PUSH_CODE_45003("标题字段超过限制","45003"),
	PUSH_CODE_45004("描述字段超过限制","45004"),
	
	PUSH_CODE_45005("链接字段超过限制","45005"),
	PUSH_CODE_45006("图片链接字段超过限制","45006"),
	PUSH_CODE_45007("语音播放时间超过限制","45007"),
	PUSH_CODE_45008("图文消息超过限制","45008"),
	
	PUSH_CODE_45009("接口调用超过限制","45009"),
	PUSH_CODE_46001("不存在媒体数据","46001"),
	PUSH_CODE_47001("解析JSON/XML内容错误","47001");
    // 成员变量  
    private String desc;  
    private String code;  
    // 构造方法  
    private PushMessageErrorCode(String desc, String code) {  
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
