/**
 * Project Name:full
 * File Name:ZookeeperPropertiesConfigurer.java
 * Package Name:com.kingnod.etraining.zookeeper
 * Date:2013-12-4上午10:32:57
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */

package com.hfepay.commons.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hfepay.commons.configuration.ZookeeperResource.OnConnectionFailed;
import com.hfepay.commons.configuration.ZookeeperResource.ReloadContext;


/**
 * ClassName:ZookeeperPropertiesConfigurer <br/>
 * Function: zookeeper客户端参数配置. <br/>
 * Date: 2013-12-4 上午10:32:57 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class ZookeeperPropertiesConfigurer {
	public boolean zkResouceEnable;
	public String connString;
	public String znodes;
	public boolean regression;
	public OnConnectionFailed onConnectionFailed;
	public ReloadContext reloadContext;
	
	public boolean need_initial;
	public String initial_data;

	private Log logger = LogFactory.getLog(this.getClass());

	public ZookeeperPropertiesConfigurer() throws Exception {
		/**
		 * 读取配置文件
		 */
		try {
			Properties zkCfg = getZkCfg();
			this.zkResouceEnable = Boolean.parseBoolean(zkCfg.get(ZookeeperConstants.ENABLE).toString());
			this.connString = zkCfg.getProperty(ZookeeperConstants.SERVER);
			this.znodes = zkCfg.getProperty(ZookeeperConstants.ZNODES);
			// this.chKCmd =
			// generateZkPingCmd(PingCmd.valueOf(zkCfg.getProperty(ZookeeperConstants.PING_CMD)));
			this.regression = Boolean.parseBoolean(zkCfg.get(ZookeeperConstants.REGRESSION).toString());
			this.onConnectionFailed = OnConnectionFailed.valueOf(zkCfg.get(ZookeeperConstants.ON_CONNECTION_FAILED).toString());
			this.reloadContext = ReloadContext.valueOf(zkCfg.get(ZookeeperConstants.RELOAD_CONTEXT).toString());
			this.need_initial = Boolean.parseBoolean(zkCfg.get(ZookeeperConstants.NEED_INITIAL).toString());
			this.initial_data = zkCfg.get(ZookeeperConstants.INITIAL_DATA).toString();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	private Properties getZkCfg() throws IOException {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream(ZookeeperConstants.CONFIG_ZK_PROPERTIES));
		return props;
	}
}
