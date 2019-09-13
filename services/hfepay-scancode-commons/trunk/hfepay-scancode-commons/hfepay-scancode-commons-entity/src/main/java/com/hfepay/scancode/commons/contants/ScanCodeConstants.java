package com.hfepay.scancode.commons.contants;

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
	 * 广告编号抬头
	 */
	public static String ADVIERT_PRE_NAME = "ADXX";
	
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
	/**
	 * 失败操作响应
	 */
	String FAIL_MSG = "操作失败，请联系开发人员！";
	/**
	 * 成功操作响应
	 */
	String SUCCESS_MSG = "操作成功！";
	/**
	 * 是否对公对私-对公
	 */
	public static String ACCOUNTTYPE_PUBLIC  = "1";
	
	/**
	 * 是否对公对私-对私
	 */
	public static String ACCOUNTTYPE_PRIVATE  = "0";
	/**
	 * 审核状态    0初始
	 */
	String APPROVE_STATUS_NEW = "0";
	/**
	 * 审核状态    1审核通过
	 */
	String APPROVE_STATUS_SUCCESS = "1";
	/**
	 * 审核状态   2审核失败
	 */
	String APPROVE_STATUS_FAIL = "2";
	/**
	 * 审核状态   3审核中
	 */
	String APPROVE_STATUSING = "3";
	
	/**
	 * 广告状态    1初始
	 */
	String AD_STATUS_NEW = "1";
	/**
	 * 广告状态    2审核通过
	 */
	String AD_STATUS_SUCCESS = "2";
	/**
	 * 广告状态   3审核失败
	 */
	String AD_STATUS_FAIL = "3";
	/**
	 * 广告 不限
	 */
	String SYSTEM_UNLIMITED = "不限";
	
	public static String SYSTEM = "system";
	
	public static String UNLIMITED = "0";
	
	/**
	 * 请求源头，1为接口 0为网页
	 */
	public static String SOURCE_INTERFACE="1";
	public static String SOURCE_WEB="0";
}
