package com.hfepay.scancode.api.service.commons;

public interface ScanCodeConstants {
	/**
	 * 状态--可用
	 */
	public static String STATUS_ACTIVE = "1";
	
	/**
	 * 状态--禁用
	 */
	public static String STATUS_DISABLE = "2";
	
	/**
	 * 渠道编号抬头
	 */
	public static String CHANNEL_CODE_PRE_NAME = "QDXX";
	
	/**
	 * 二维码编号抬头
	 */
	public static String QRCODE_PRE_NAME = "QRXX";
	
	/**
	 * redis 缓存key 项目前缀
	 */
	public static String REDIS_KEY_MODULE = "superplat";
	
	/**
	 * h5注册默认商户角色
	 */
	public static String DEFAULE_ROLE = "merchant";
	
	/**
	 * h5注册默认代理商编号
	 */
	public static String DEFAULE_AGENTNO = "HFJK20161028000017";
	
	public static String DEFAULT_AGENTNAME="小二买单默认代理商";
	
	public static String DEFAULT_CHANNELNO = "QDXX20161028000005";
	
	public static String DEFAULT_CHANNELNAME="华付小二买单";
	
	/**
	 * 
	 * 路径分隔符
	 */
	public static final String  SPT = "/";
	
	/**
	 * 上传文件路径
	 */
	public static final String FILE_UPLOAD_ROOT_PATH = "/data/files";
	
	public static final String IMG_EXTEND=".png";
	
	public static final String WECHAT_IMGUPLOAD_TYPE="business";
	
	/**
	 * 通用状态 -- Y
	 */
	public static String Y = "Y";
	
	/**
	 * 通用状态 -- N
	 */
	public static String N = "N";
	
	/**
	 * 角色类型--渠道
	 */
	public static String ROLE_TYPE_CHANNEL = "channel";
	
	/**
	 * 角色类型--代理商
	 */
	public static String ROLE_TYPE_AGENT = "agent";
	
	/**
	 * 角色类型--商户
	 */
	public static String ROLE_TYPE_MERCHANT = "merchant";
	
	/**
	 * 标识--渠道
	 */
	public static String IDENTITYFLAG_CHANNEL = "1";
	
	/**
	 * 标识--代理商
	 */
	public static String IDENTITYFLAG_AGENT = "2";
	
	/**
	 * 标识--商户
	 */
	public static String IDENTITYFLAG_MERCHANT = "3";
	
	/**
	 * 短信默认值
	 */
	public static String INIT_SMS_SYSTEM = "system";
	
	/**
	 * 逻辑删除
	 */
	String RECORD_STATUS_NO = "N";
	/**
	 * 逻辑存在
	 */
	String RECORD_STATUS_YES = "Y";
	
	/**
	 * 失败操作状态/停用状态
	 */
	String FAIL_STATE = "0";
	/**
	 * 成功操作状态/启用状态
	 */
	String SUCCESS_STATE = "1";
	
	/**
	 * 使用状态--初始化
	 */
	public static String STATUS_INIT = "0";
	
	/**
	 * 使用状态--已使用
	 */
	public static String STATUS_USE = "1";
	
	/**
	 * 使用状态--未使用
	 */
	public static String STATUS_NOT_USE = "2";
	
	/**
	 * 调用api的接口返回成功的状态
	 */
	public static String INTERFACE_SUCCESS_STATUS = "000000";
	
	//页面接收参数
	public static final String EXECUTE_STATUS ="executeStatus";
	public static final int SUCCESS = 0;
	public static final int FAILED = 1;
	public static final String VALUES="values";
}
