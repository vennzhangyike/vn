package com.hfepay.commons.configuration;

/**
 * Project Name:hfepay-common-zkclient
 * File Name:ZookeeperClient.java
 * Package Name:com.hfepay.common.zookeeper
 * Date:2013-12-4上午09:40:34
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:ZookeeperClient <br/>
 * Function: zookeeper客戶端封裝. <br/>
 * Date: 2013-12-4 上午09:40:34 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class ZookeeperClient implements Watcher {

	private final static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class); 

	private ZooKeeper zk;

	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	/**
	 * 
	 * createConnection:创建ZK连接. <br/>
	 * 
	 * 
	 * @author XieJunfeng
	 * @since JDK 1.6
	 */
	public void createConnection(String connString, int connSessionTimeout) {
		this.releaseConnection();
		try {
			zk = new ZooKeeper(connString, connSessionTimeout, this);
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
	 * isNodeExist:节点是否存在. <br/>
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @return
	 * @since JDK 1.6
	 */
	public boolean isNodeExist(String path) {
		try {
			Stat exists = this.zk.exists(path, null);
			if (exists != null) {
				logger.debug("节点存在: " + path);
				return true;
			} else {
				logger.debug("节点不存在: " + path);
			}
		} catch (KeeperException e) {
			logger.debug("节点状态读取失败，发生KeeperException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.debug("节点状态读取失败，发生 InterruptedException");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * createNode:创建节点. <br/>
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            初始数据内容
	 * @param mode
	 *            节点创建类型
	 * 
	 * @author XieJunfeng
	 * @param path
	 * @param data
	 * @return
	 * @since JDK 1.6
	 */
	public boolean createNode(String path, String data, CreateMode mode) {
		try {
			logger.debug("节点创建成功, Path: " + this.zk.create(path, //
					data.getBytes(), //
					Ids.OPEN_ACL_UNSAFE, //
					mode) + ", content: " + data);
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
	
	public ZooKeeper getZK(){
		return this.zk;
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
		ZookeeperClient zookeeperClient = new ZookeeperClient();
		zookeeperClient.createConnection("192.168.3.101:4180", 3000);
		/**
		 *  打印 /conf 节点
		 */
		String confData = zookeeperClient.readData("/conf");
		System.out.println("/conf="+confData);
		
		ZooKeeper zk = zookeeperClient.getZK();
		try {
			List<String> children = zk.getChildren("/conf", false);
			for(String child : children){
				System.out.println("/conf/" + child + "=" + new String(zk.getData("/conf/" + child, false, null)));
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		zookeeperClient.releaseConnection();
	}
}
