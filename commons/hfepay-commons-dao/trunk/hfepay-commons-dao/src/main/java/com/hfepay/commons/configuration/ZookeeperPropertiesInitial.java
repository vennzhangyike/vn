/**
 * Project Name:full
 * File Name:ZookeeperInitial.java
 * Package Name:com.kingnod.etraining.zookeeper
 * Date:2013-12-4上午09:40:34
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */

package com.hfepay.commons.configuration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.hfepay.commons.configuration.ZookeeperModule.App;
import com.hfepay.commons.configuration.ZookeeperModule.Conf;
import com.hfepay.commons.configuration.ZookeeperModule.Module;
import com.hfepay.commons.configuration.ZookeeperResource.OnConnectionFailed;


/**
 * ClassName:ZookeeperPropertiesInitial <br/>
 * Function: zookeeper参数管理器. <br/>
 * Date: 2013-12-4 上午09:40:34 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class ZookeeperPropertiesInitial implements Watcher {
	private static final String NAME_MAPPING_MODULE = "nameMappingModule";

	private static final String CONTENTS = "contents";

	private static final String NAME = "name";

	private static final String APPS = "apps";

	private static final String CONF = "/conf";

	private Log logger = LogFactory.getLog(this.getClass());

	private ZookeeperPropertiesConfigurer config;
	private ZooKeeper zk;

	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public ZookeeperPropertiesInitial() {
		try {
			/**
			 * 读取配置
			 */
			config = new ZookeeperPropertiesConfigurer();
		} catch (Exception e) {
			if (config.onConnectionFailed == OnConnectionFailed.THROW_EXCEPTION) {
				throw new org.springframework.context.ApplicationContextException("Failed to acess /config/zk.properties", e);
			} else {
				logger.error("Failed to acess /config/zk.properties", e);
			}
		}
	}

	/**
	 * 
	 * doInitial:初始化应用参数到zookeeper. <br/>
	 * 
	 * 执行流程：
	 * 			0：判断是否需要执行数据初始化
	 * 			1：从配置文件读取初始化数据，并写入VO
	 * 			2：按模块解析VO,转换成json,存入zookeeper节点
	 * 
	 * @author XieJunfeng
	 * @since JDK 1.6
	 */
	public void doInitial() {
		if(config.need_initial){
			logger.info("开始执行应用参数初始化操作");
			update(initProperties());
			logger.info("应用参数初始化操作执行结束");
		}else{
			logger.info("不需要初始化应用参数");
		}
	}

	/**
	 * 
	 * initProperties:初始应用参数收集. <br/>
	 * 
	 * @author XieJunfeng
	 * @return
	 * @since JDK 1.6
	 */
	private Conf initProperties() {
		Conf conf = new Conf();
		String initial_data = config.initial_data;
		JSONObject confObj = JSONObject.fromObject(initial_data);
		JSONObject o1 = confObj.getJSONObject(NAME_MAPPING_MODULE);
		Module bean = (Module) JSONObject.toBean(o1, Module.class);
		conf.setNameMappingModule(bean);
		JSONArray o2 = confObj.getJSONArray(APPS);
		for (int i = 0; i < o2.size(); i++) {
			App app = new App();
			JSONObject o3 = o2.getJSONObject(i);
			String path = o3.getString(NAME);
			app.setName(path);
			JSONArray o4 = o3.getJSONArray(CONTENTS);
			for (int j = 0; j < o4.size(); j++) {
				JSONObject o5 = o4.getJSONObject(j);
				bean = (Module) JSONObject.toBean(o5, Module.class);
				app.getModules().add(bean);
			}
			conf.getApps().add(app);
		}
		return conf;
	}

	/**
	 * 
	 * update:更新应用参数到zookeeper服务器. <br/>
	 * 
	 * @author XieJunfeng
	 * @param conf
	 * @since JDK 1.6
	 */
	private void update(Conf conf) {
		/**
		 * 1:更新应用名称映射模块
		 */
		Module nameMappingModule = conf.getNameMappingModule();
		JSONObject nameMappingModuleObj = JSONObject.fromObject(nameMappingModule);
		JSONArray confApp = new JSONArray();
		confApp.add(nameMappingModuleObj);
		writeData(CONF, confApp.toString());
		/**
		 * 2:更新应用参数到对应节点
		 */
		List<App> apps = conf.getApps();
		for (App app : apps) {
			List<Module> modules = app.getModules();
			JSONArray details = JSONArray.fromObject(modules);
			String detailsStr = details.toString();
			writeData(app.getName(), detailsStr);
		}
	}

	/**
	 * 
	 * createConnection:创建ZK连接. <br/>
	 * 
	 * @param connectString
	 *            ZK服务器地址列表
	 * @param sessionTimeout
	 *            Session超时时间
	 * 
	 * @author XieJunfeng
	 * @since JDK 1.6
	 */
	public void createConnection() {
		this.releaseConnection();
		try {
			zk = new ZooKeeper(config.connString, ZookeeperConstants.SESSION_TIMEOUT, this);
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			logger.debug("连接创建失败，发生 InterruptedException");
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("连接创建失败，发生 IOException");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * createPath:创建节点. <br/>
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            初始数据内容
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @param data
	 * @return
	 * @since JDK 1.6
	 */
	public boolean createPath(String path, String data) {
		try {
			logger.debug("节点创建成功, Path: " + this.zk.create(path, //
					data.getBytes(), //
					Ids.OPEN_ACL_UNSAFE, //
					CreateMode.EPHEMERAL) + ", content: " + data);
		} catch (KeeperException e) {
			logger.debug("节点创建失败，发生KeeperException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.debug("节点创建失败，发生 InterruptedException");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * readData:读取指定节点数据内容. <br/>
	 * 
	 * @param path
	 *            节点path
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @return
	 * @since JDK 1.6
	 */
	public String readData(String path) {
		try {
			logger.debug("获取数据成功，path：" + path);
			return new String(this.zk.getData(path, false, null));
		} catch (KeeperException e) {
			logger.debug("读取数据失败，发生KeeperException，path: " + path);
			e.printStackTrace();
			return "";
		} catch (InterruptedException e) {
			logger.debug("读取数据失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * writeData:更新指定节点数据内容. <br/>
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            数据内容
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @param data
	 * @return
	 * @since JDK 1.6
	 */
	public boolean writeData(String path, String data) {
		try {
			logger.debug("更新数据成功，path：" + path + ", stat: " + this.zk.setData(path, data.getBytes(), -1));
		} catch (KeeperException e) {
			logger.debug("更新数据失败，发生KeeperException，path: " + path);
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.debug("更新数据失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * deleteNode:删除指定节点. <br/>
	 * 
	 * @param path
	 *            节点path
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @since JDK 1.6
	 */
	public void deleteNode(String path) {
		try {
			this.zk.delete(path, -1);
			logger.debug("删除节点成功，path：" + path);
		} catch (KeeperException e) {
			logger.debug("删除节点失败，发生KeeperException，path: " + path);
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.debug("删除节点失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 收到来自Server的Watcher通知后的处理。
	 * 
	 * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	public void process(WatchedEvent event) {
		logger.debug("收到事件通知：" + event.getState() + "\n");
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}

	/**
	 * 
	 * releaseConnection:释放zookeeper连接. <br/>
	 * 
	 * @author XieJunfeng
	 * @since JDK 1.6
	 */
	public void releaseConnection() {
		if (this.zk != null) {
			try {
				this.zk.close();
			} catch (Exception e) {
				logger.error("关闭zookeeper客户端失败: ", e);
			}
		}
	}

	public static void main(String[] args) {
		ZookeeperPropertiesInitial zookeeperPropertiesInitial = new ZookeeperPropertiesInitial();
		zookeeperPropertiesInitial.createConnection();
		zookeeperPropertiesInitial.doInitial();
		zookeeperPropertiesInitial.releaseConnection();
	}
}
