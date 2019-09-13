/**
 * Project Name:full
 * File Name:ZookeeperInitial.java
 * Package Name:com.kingnod.etraining.zookeeper
 * Date:2013-12-4上午09:40:34
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */

package com.hfepay.commons.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;


/**
 * ClassName:AppInitial <br/>
 * Function: zookeeper参数管理器. <br/>
 * Date: 2013-12-4 上午09:40:34 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class AppInitial {
	public static void main(String[] args) {
		String appPath = PathUtil.getAppPath(AppInitial.class);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(appPath
					+ "/data.zk"));
			
			readAll(args, bw);

			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Export zk properties success.....................");
		// initial(args);
	}

	public static void readAll(String[] args, BufferedWriter bw) throws IOException {
		if (args.length < 1) {
			System.out.println("Please specify the zk addr!\r\n"
					+ "like:192.168.3.101:4180");
			System.exit(1);
		}
		String zkAddr = args[0];
		ZookeeperClient zookeeperClient = new ZookeeperClient();
		zookeeperClient.createConnection(zkAddr, 10000);
		/**
		 * 打印 /conf 节点
		 */
		String confData = zookeeperClient.readData("/conf");
		bw.write("/conf=" + confData);
		bw.write("\r\n");

		ZooKeeper zk = zookeeperClient.getZK();
		try {
			List<String> children = zk.getChildren("/conf", false);
			for (String child : children) {
				bw.write("/conf/" + child + "="
						+ new String(zk.getData("/conf/" + child, false, null)));
				bw.write("\r\n");
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		zookeeperClient.releaseConnection();
	}

	public static void initial(String[] args) {
		if (args.length < 2) {
			System.out
					.println("Please specify the zk addr and a valid data file path for app properties initializing!\r\n"
							+ "192.168.3.101:4180 /root/initial-data.properties");
			System.exit(1);
		}

		String zkAddr = args[0];
		String dataFile = args[1];

		File initialData = new File(dataFile);
		if (!initialData.exists()) {
			System.out
					.println("Please specify the zk addr and a valid data file path for app properties initializing!");
			System.exit(1);
		}
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(initialData);
			props.load(fis);

			ZookeeperClient zookeeperClient = new ZookeeperClient();
			zookeeperClient.createConnection(zkAddr, 10000);

			Enumeration<Object> keys = props.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String data = (String) props.get(key);
				if (zookeeperClient.isNodeExist(key)) {
					zookeeperClient.deleteNode(key);
				}
				zookeeperClient.createNode(key, data, CreateMode.PERSISTENT);
			}

			zookeeperClient.releaseConnection();
		} catch (FileNotFoundException e) {
			System.out
					.println("Please specify the zk addr and a valid data file path for app properties initializing!");
			System.exit(1);
		} catch (IOException e) {
			System.out
					.println("Please specify the zk addr and a valid data file path for app properties initializing!");
			System.exit(1);
		}

	}
}
