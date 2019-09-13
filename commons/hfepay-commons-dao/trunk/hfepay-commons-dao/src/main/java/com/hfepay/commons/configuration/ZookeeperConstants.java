/**
 * Project Name:full
 * File Name:ZookeeperConstants.java
 * Package Name:com.kingnod.etraining.zookeeper
 * Date:2013-12-3下午01:49:47
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */

package com.hfepay.commons.configuration;

/**
 * ClassName:ZookeeperConstants <br/>
 * Function: 定义常量. <br/>
 * Date: 2013-12-3 下午01:49:47 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class ZookeeperConstants {
	/**
	 * Session超时时间
	 */
	public static final int SESSION_TIMEOUT = 3000;
	/**
	 * zookeeper URL头
	 */
	public static final String URL_HEADER = "zk://";
	/**
	 * zookeeper配置参数
	 */
	public static final String CONFIG_ZK_PROPERTIES = "/config/zk.properties";
	public static final String RELOAD_CONTEXT = "reload_context";
	public static final String ON_CONNECTION_FAILED = "on_connection_failed";
	public static final String REGRESSION = "regression";
	public static final String PING_CMD = "ping_cmd";
	public static final String ZNODES = "znodes";
	public static final String SERVER = "server";
	public static final String ENABLE = "enable";
	
	public static final String NEED_INITIAL = "need_initial";
	public static final String INITIAL_DATA = "initial_data";
	
	/**
	 * 全局配置参数模块名称
	 */
	public static final String GLOBAL = "global";
	/**
	 * 模块名称key
	 */
	public static final String NAME = "name";
	/**
	 * 模块对应的键值对key
	 */
	public static final String DETAILS = "details";
	/**
	 * 服务器模块名
	 */
	public static final String $_CONF_SERVER_NAME = "${conf.server.name}";

	public static final String NULL = "null";
	public static final String REGEX = ",";
	public static final String STR = "\n";
	public static final String SUFFIX = "/";
	public static final String EQUAL = "=";
}
