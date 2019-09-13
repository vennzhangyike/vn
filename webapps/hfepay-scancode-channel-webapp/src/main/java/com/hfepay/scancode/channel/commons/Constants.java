package com.hfepay.scancode.channel.commons;

/**
 * web常量
 */
public interface Constants {
	/**
	 * 路径分隔符
	 */
	String SPT = "/";
	/**
	 * 默认模板
	 */
	String DEFAULT = "default";
	/**
	 * UTF-8编码
	 */
	String UTF8 = "UTF-8";
	/**
	 * 提示信息
	 */
	String MESSAGE = "message";

	String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * HTTP POST请求
	 */
	String POST = "POST";
	/**
	 * HTTP GET请求
	 */
	String GET = "GET";
	/**
	 * 授权票据request key
	 */
	String TOKEN_KEY = "token";
	/**
	 * 认证信息request key
	 */
	String AUTH_KEY = "auth";
	/**
	 * 当前登录user key
	 */
	String CURRENTUSER_KEY = "currentUser";
	/**
	 * 失败操作响应
	 */
	String FAIL_MSG = "操作失败，请联系开发人员！";
	/**
	 * 成功操作响应
	 */
	String SUCCESS_MSG = "操作成功！";
	/**
	 * 失败操作状态/停用状态
	 */
	String FAIL_STATE = "0";
	/**
	 * 成功操作状态/启用状态
	 */
	String SUCCESS_STATE = "1";
	/**
	 * 逻辑删除
	 */
	String RECORD_STATUS_NO = "N";
	/**
	 * 逻辑存在
	 */
	String RECORD_STATUS_YES = "Y";
	
	/**
	 * 处理状态    0初始
	 */
	String TREATMENT_STATUS_NEW = "0";
	/**
	 * 处理状态    1处理中
	 */
	String TREATMENT_STATUS_OPING = "1";
	/**
	 * 处理状态   2处理完成
	 */
	String TREATMENT_STATUS_SUCCESS = "2";
	/**
	 * 处理状态    3无需处理
	 */
	String TREATMENT_STATUS_NOOP = "3";
	
	
	/**
	 * 是否需要后续跟踪      0无需
	 */
	String TRACK_STATUS_NO = "0";
	/**
	 * 是否需要后续跟踪     1需要
	 */
	String TRACK_STATUS_YES = "1";
	/**
	 * 是否需要后续跟踪     2关闭
	 */
	String TRACK_STATUS_CLOSE = "2";
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
	 * 通用状态 -- Y
	 */
	public static String Y = "Y";
	
	/**
	 * 通用状态 -- N
	 */
	public static String N = "N";
	
	/**
	 * 状态--可用
	 */
	public static String STATUS_ACTIVE = "1";	
	
	/**
	 * 通道类型编码--支付通道
	 */
	public static String PAYWAY_CODE = "ZFTD";	
	
	/**
	 * 通道类型值--支付通道
	 */
	public static String PAYWAY_VALUE = "2";
	
	/**
	 * 使用状态--已使用
	 */
	public static String STATUS_USE = "1";
	
	/**
	 * 使用状态--未使用
	 */
	public static String STATUS_NOT_USE = "2";
	
	/**
	 * 操作人--系统
	 */
	public static String OPERATOR_SYSTEM = "system";
	
	/**
	 * 分配类型--主动分配
	 */
	public static String ASSIGNEDTYPE_ACTIVE = "0";
	/**
	 * 分配类型--申请审核后分配
	 */
	public static String ASSIGNEDTYPE_APPROVE = "1";
	
	/**
	 * 调用api的接口返回成功的状态
	 */
	public static String INTERFACE_SUCCESS_STATUS = "000000";
	
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
	 * 标识--商户
	 */
	public static String IDENTITYFLAG_CASHIER = "4";
	
	/**
	 * 角色类型--渠道
	 */
	public static String ROLE_TYPE_CHANNEL = "channel";
	
	/**
	 * 角色类型--代理商
	 */
	public static String ROLE_TYPE_AGENT = "agent";
	
	/**
	 * 二维码类型-二维码
	 */
	public static String QR_TYPE = "1";
	
	/**
	 * 二维码类型-子二维码
	 */
	public static String QR_TYPE_CHILD = "2";
	
	/**
	 * 经营类目层级--1
	 */
	public static String CATEGORY_LEVEL_ONE = "1";
	
	/**
	 * 经营类目层级--2
	 */
	public static String CATEGORY_LEVEL_TWO = "2";
	
	/**
	 * 经营类目层级--3
	 */
	public static String CATEGORY_LEVEL_THREE = "3";
	
	/**
	 * 代理商层级--1
	 */
	public static String AGENTLEVEL_ONE = "1";
	
	/**
	 * 代理商层级--2
	 */
	public static String AGENTLEVEL_TWO = "2";
	
	/**
	 * 代理商层级--3
	 */
	public static String AGENTLEVEL_THREE = "3";

	public static String WITHDRAWLS_AUDIT_INIT = "1";
	
	/**
	 * 广告 不限
	 */
	String UNLIMITED = "0";
}
