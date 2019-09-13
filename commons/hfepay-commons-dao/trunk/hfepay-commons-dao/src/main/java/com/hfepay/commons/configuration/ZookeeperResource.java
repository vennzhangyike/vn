package com.hfepay.commons.configuration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.hfepay.commons.base.lang.Strings;

/**
 * ClassName: ZookeeperResource <br/>
 * Reason: 资源文件实现类（文件内容从zookeeper读取）. <br/>
 * date: 2013-11-25 上午09:10:48 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 */
public class ZookeeperResource extends AbstractResource implements ApplicationContextAware, DisposableBean {
	private static Logger log = LoggerFactory.getLogger(ZookeeperResource.class);
	private boolean printInputStream = true;

	public static enum ReloadContext {
		AUTO, HOLD
	};

	public static enum OnConnectionFailed {
		IGNORE, THROW_EXCEPTION
	};

	public static enum PingCmd {
		get, ls
	}

	AbstractApplicationContext ctx;

	private String znodes;
	private String connString;
	// private String chKCmd;
	private boolean connectFailed = false;

	private boolean zkResouceEnable = true;
	private boolean regression;
	private OnConnectionFailed onConnectionFailed;
	private ReloadContext reloadContext;
	private RefreshContextWatcher watcher;
	private ZkExecutor executor;
	private Resource[] locations;

	public ZookeeperResource() {
		init();
	}
	
	public ZookeeperResource(Resource[] locations) {
		setLocations(locations);
		init();
	}

	private void init() {
		try {
			/**
			 * 读取配置文件
			 */
			Properties zkCfg = getZkCfg();
			this.zkResouceEnable = Boolean.parseBoolean(zkCfg.get(ZookeeperConstants.ENABLE).toString());
			this.connString = zkCfg.getProperty(ZookeeperConstants.SERVER);
			this.znodes = zkCfg.getProperty(ZookeeperConstants.ZNODES);
			// this.chKCmd =
			// generateZkPingCmd(PingCmd.valueOf(zkCfg.getProperty(ZookeeperConstants.PING_CMD)));
			this.regression = Boolean.parseBoolean(zkCfg.get(ZookeeperConstants.REGRESSION).toString());
			this.onConnectionFailed = OnConnectionFailed.valueOf(zkCfg.get(ZookeeperConstants.ON_CONNECTION_FAILED).toString());
			this.reloadContext = ReloadContext.valueOf(zkCfg.get(ZookeeperConstants.RELOAD_CONTEXT).toString());
		} catch (IOException e) {
			if (onConnectionFailed == OnConnectionFailed.THROW_EXCEPTION) {
				throw new org.springframework.context.ApplicationContextException("Failed to acess /zk properties", e);
			} else {
				log.error("Failed to acess /zk properties", e);
			}
			connectFailed = true;
		}
	}

	public ZookeeperResource(String connString, String znodes, PingCmd chkCmd, boolean regression, OnConnectionFailed onConnectionFailed, ReloadContext reloadContext) {
		this.connString = connString;
		this.znodes = znodes;
		// this.chKCmd = generateZkPingCmd(chkCmd);
		this.regression = regression;
		this.reloadContext = reloadContext;
		this.onConnectionFailed = onConnectionFailed;
	}

	private void setLocations(Resource[] locations) {
		String tomcatLocation = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		if(!Strings.isEmpty(tomcatLocation) && tomcatLocation.indexOf("work")>0){
			tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("work"));
		}else if(!Strings.isEmpty(tomcatLocation) && tomcatLocation.indexOf("webapps")>0){
			tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("webapps"));
		}
		Resource[] newLocations = new Resource[locations.length];
		for (int i = 0; i < locations.length; i ++) {
			Resource location =  locations[i];
			try {
				if("file:./application.properties".equals(location.getURL().toString())){
					location = new UrlResource(tomcatLocation + "./application.properties");
				}
			} catch (Exception e) {
			}
			newLocations[i] = location;
		}
		this.locations = newLocations;
	}
	
	private Properties getZkCfg() throws IOException {
		return loadFromApplicationProperties();
		//return loadFromLocalClasspath();
	}

	/**
	 * 
	 * loadFromApplicationProperties:将zk属性迁移到application.properties文件中. <br/>
	 *
	 * @author hfepay
	 * @return
	 * @since JDK 1.6
	 */
	private Properties loadFromApplicationProperties() {
		Properties props = new Properties();
		if(locations == null){
			PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
			Resource resource = null;
			// 1: load from class path
			try {
				Resource[] resources = resolover.getResources("classpath*:/application.properties");
				if (resources != null && resources.length>0) {
					for(Resource res : resources){
						if(res.exists()){
							resource = res;
							break;
						}
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			// 2: load from c:/
			if (resource == null || !resource.exists()) {
				resource = resolover.getResource("file:c:/application.properties");
			}
			
			if (resource == null || !resource.exists()) {
				// 3: load from tomcat
				String tomcatLocation = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
				if (!Strings.isEmpty(tomcatLocation) && tomcatLocation.indexOf("work") > 0) {
					tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("work"));
				} else if (!Strings.isEmpty(tomcatLocation) && tomcatLocation.indexOf("webapps") > 0) {
					tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("webapps"));
				}
				log.info(tomcatLocation);
				try {
					resource = new UrlResource(tomcatLocation + "./application.properties");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			if (resource != null && resource.exists()) {
				try {
					props.load(resource.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			for(int i = 0;i<locations.length;i++){
				Resource resource = locations[i];
				if (resource != null && resource.exists()) {
					try {
						props.load(resource.getInputStream());
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return props;
	}

	@SuppressWarnings("unused")
	private Properties loadFromLocalClasspath() throws IOException {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream(ZookeeperConstants.CONFIG_ZK_PROPERTIES));
		return props;
	}

	// private String generateZkPingCmd(PingCmd pingCmd) {
	// return "zkCli -server " + connString + " " + pingCmd.toString() + " " +
	// znodes.split(ZookeeperConstants.REGEX)[0];
	// }

	private ZkExecutor startZkClientThread() throws IOException, InterruptedException {
		log.info("Start connecting to zookeeper server: " + this.connString + ", znodes:" + znodes + " regression: " + regression);
		this.watcher = new RefreshContextWatcher(ctx, this.regression, this.reloadContext);
		ZkExecutor zkExecutor = new ZkExecutor(this);
		synchronized (this) {
			new Thread(zkExecutor).start();
			this.wait();
		}
		log.info("Zookeeper server connected");
		return zkExecutor;

	}

	static class StreamWriter extends Thread {
		Log log;

		BufferedReader br;

		StreamWriter(InputStream inputStream, Log log) {
			this.br = new BufferedReader(new InputStreamReader(inputStream));
			this.log = log;
			start();
		}

		public void run() {
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					log.info(line);
				}
			} catch (IOException e) {
				log.error("failed to log ZK process.", e);
			}
		}
	}

	private static class ZkExecutor implements Runnable, Watcher, DataMonitorListener {
		private static Log log = LogFactory.getLog(ZkExecutor.class);
		// private String cmd;
		// private Process child;
		private ZooKeeper zk;
		private DataMonitor dm;
		private String znodes;
		private ZookeeperResource zkRes;

		private boolean zkResStarted = false;

		public ZkExecutor(ZookeeperResource zkRes) throws IOException {

			this.znodes = zkRes.znodes;
			// this.cmd = zkRes.chKCmd;
			this.zk = new ZooKeeper(zkRes.connString, 3000, this);
			this.dm = new DataMonitor(zk, znodes, zkRes.watcher, this);
			this.zkRes = zkRes;
		}

		public void process(WatchedEvent event) {
			dm.process(event);
			// at the first time ZK message send back, unlock zk resource object
			// to finish the init.
			if (!zkResStarted) {
				synchronized (zkRes) {
					zkRes.notify();
					zkResStarted = true;
				}
			}
		}

		public void run() {
			try {
				synchronized (this) {
					while (!dm.dead) {
						wait();
					}
				}
			} catch (InterruptedException e) {
				log.error(e);
			}

		}

		public void exists(byte[] data) {
			// 本地检测
			/*
			 * if (data == null) { if (child != null) {
			 * log.info("Stopping ZK process."); child.destroy(); try {
			 * child.waitFor(); } catch (InterruptedException e) {
			 * log.error("Error found when waiting for ZK process stopping", e);
			 * } } child = null; } else { if (child != null) {
			 * log.info("Stopping ZK process."); child.destroy(); try {
			 * child.waitFor(); } catch (InterruptedException e) {
			 * log.error("Error found when waiting for ZK process stopping", e);
			 * } } log.info("Load config from zookeeper:\n" + new String(data));
			 * try { log.info("Starting ZK process."); String _cmd = cmd; String
			 * osName = System.getProperty("os.name"); if (osName != null &&
			 * osName.toUpperCase().startsWith("WIN")) { _cmd = "cmd.exe " +
			 * cmd; } log.info("executing " + _cmd);
			 * 
			 * child = Runtime.getRuntime().exec(_cmd);
			 * 
			 * new StreamWriter(child.getInputStream(), log); new
			 * StreamWriter(child.getErrorStream(), log); } catch (IOException
			 * e) { log.error("Failed to start ZK process.", e); } }
			 */

		}

		public void closing(int rc) {
			synchronized (this) {
				notifyAll();
			}

		}

		public ZooKeeper getZk() {
			return this.zk;
		}
	}

	public interface DataMonitorListener {
		void exists(byte[] data);

		void closing(int rc);
	}

	private static class DataMonitor implements Watcher, StatCallback {

		public boolean dead;
		private DataMonitorListener listener;
		private ZooKeeper zk;
		private Watcher chainedWatcher;
		private byte prevData[];

		public DataMonitor(ZooKeeper zk, String znodes, Watcher watcher, DataMonitorListener listener) {
			this.zk = zk;
			this.chainedWatcher = watcher;
			this.listener = listener;
			for (String znode : znodes.split(ZookeeperConstants.REGEX)) {
				zk.exists(znode, true, this, null);
			}
		}

		public void process(WatchedEvent event) {
			String path = event.getPath();
			if (event.getType() == Event.EventType.None) {
				// We are are being told that the state of the
				// connection has changed
				switch (event.getState()) {
				case SyncConnected:
					// In this particular example we don't need to do anything
					// here - watches are automatically re-registered with
					// server and any watches triggered while the client was
					// disconnected will be delivered (in order of course)
					break;
				case Expired:
					// It's all over
					dead = true;
					listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
					break;
				default:
					log.info("Recevied zk change with unknow status:" + event.getState() + ", skip.");
					break;
				}
			} else {
				if (path != null && path.equals(path)) {
					// Something has changed on the node, let's find out
					zk.exists(path, true, this, null);
				}
			}
			if (chainedWatcher != null) {
				chainedWatcher.process(event);
			}
		}

		public void processResult(int rc, String path, Object ctx, Stat stat) {
			boolean exists;
			Code code = Code.get(rc);
			switch (code) {
			case OK:
				exists = true;
				break;
			case NONODE:
				exists = false;
				break;
			case SESSIONEXPIRED:
			case NOAUTH:
				dead = true;
				listener.closing(rc);
				return;
			default:
				// Retry errors
				zk.exists(path, true, this, null);
				return;
			}

			byte b[] = null;
			if (exists) {
				try {
					b = zk.getData(path, false, null);
				} catch (KeeperException e) {
					// We don't need to worry about recovering now. The watch
					// callbacks will kick off any exception handling
					e.printStackTrace();
				} catch (InterruptedException e) {
					return;
				}
			}
			if ((b == null && b != prevData) || (b != null && !Arrays.equals(prevData, b))) {
				listener.exists(b);
				prevData = b;
			}
		}

	}

	public ZooKeeper getZk() {
		return executor.getZk();
	}

	public boolean exists() {
		try {
			Stat stat = getZk().exists(znodes, false);
			return null != stat;
		} catch (Exception e) {
			log.error("Falied to detect the config in zoo keeper.", e);
			return false;
		}
	}

	public boolean isOpen() {
		return false;
	}

	public URL getURL() throws IOException {
		return new URL(ZookeeperConstants.URL_HEADER + connString + znodes);
	}

	public String getFilename() throws IllegalStateException {
		return znodes;
	}

	public String getDescription() {
		return "Zookeeper resouce at '" + ZookeeperConstants.URL_HEADER + connString + ", zonode: '" + znodes + "'. Enabled: " + zkResouceEnable;
	}

	public InputStream getInputStream() throws IOException {
		if (executor == null || !executor.zkResStarted) {
			try {
				this.executor = startZkClientThread();
			} catch (Exception e) {
				if (onConnectionFailed == OnConnectionFailed.THROW_EXCEPTION) {
					throw new org.springframework.context.ApplicationContextException("Failed to connect to zk server" + this.connString, e);
				} else {
					log.error("Failed to connect to zk server:" + this.connString, e);
				}
				connectFailed = true;
			}
		}

		if (!zkResouceEnable) {
			// disabled tools, return nothing;
			log.info("Zookeeper resource disbaled, skip loading resource from Zookeeper server.");
			closeZk();
			return new ByteArrayInputStream(new byte[0]);
		}

		if (connectFailed) {
			// init failed, but set to continue, return nothing;
			closeZk();
			return new ByteArrayInputStream(new byte[0]);
		} else {
			try {
				SequenceInputStream pis = generateSequenceInputStream(znodes, regression);
				if (printInputStream) {
					String ouput = getString(pis);
					log.info(ouput);
					return new ByteArrayInputStream(ouput.getBytes());
				} else {
					return pis;
				}
			} catch (Exception e) {
				throw new IOException("Fail to get inputstream from zookeeper", e);
			} finally {
				closeZk();
			}
		}
	}

	private void closeZk() {
		if (executor != null && executor.zkResStarted) {
			try {
				executor.getZk().close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getString(SequenceInputStream pis) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(pis));
		String line = null;
		StringBuilder sb = new StringBuilder();
		sb.append(ZookeeperConstants.STR);
		while ((line = br.readLine()) != null) {
			sb.append(line).append(ZookeeperConstants.STR);
		}
		return sb.toString();
	}

	/**
	 * 
	 * generateSequenceInputStream:生成最终返回给spring的数据流，包含应用参数的键值对. <br/>
	 * 
	 * @author XieJunfeng
	 * @param znodes
	 * @param regressionZnodes
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @since JDK 1.6
	 */
	private SequenceInputStream generateSequenceInputStream(String znodes, boolean regressionZnodes) throws KeeperException, InterruptedException {
		List<InputStream> seqenceInputStreamCollector = new ArrayList<InputStream>();
		for (String znode : znodes.split(ZookeeperConstants.REGEX)) {
			getDataInputStream(znode, seqenceInputStreamCollector, regressionZnodes);
		}
	    List<InputStream> result = constructPropertiesStream(seqenceInputStreamCollector);
		return new SequenceInputStream(Collections.enumeration(result));
	}

	/**
	 * 
	 * constructPropertiesStream:按模块从数据流中读取应用参数键值对，并处理全局参数的覆盖. <br/>
	 * 
	 * @author XieJunfeng
	 * @param seqenceInputStreamCollector
	 * @return
	 * @since JDK 1.6
	 */
	private List<InputStream> constructPropertiesStream(List<InputStream> seqenceInputStreamCollector) {
		//String confModuleName = ctx.getBeanFactory().resolveEmbeddedValue(ZookeeperConstants.$_CONF_SERVER_NAME);
		/**
		 * 1：读取应用服务器模块的配置，用于应用参数的替换
		 */
		Map<String, String> map = constructModuleParameters("", seqenceInputStreamCollector);
		List<InputStream> result = new ArrayList<InputStream>();
		try {
			Properties globalProperties = new Properties();
			Properties appProperties = new Properties();
			/**
			 * 2：读取全局模块和应用模块参数
			 */
			calculateAppProperties(seqenceInputStreamCollector, map, globalProperties, appProperties);
			/**
			 *:3：合并全局参数，创建应用参数数据流
			 */
			computePropertiesResult(result, globalProperties, appProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * computePropertiesResult:合并全局参数，创建应用参数数据流. <br/>
	 * @author XieJunfeng
	 * @param result
	 * @param globalProperties
	 * @param appProperties
	 * @since JDK 1.6
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void computePropertiesResult(List<InputStream> result, Properties globalProperties, Properties appProperties) {
		Set keySet = globalProperties.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			String val = globalProperties.getProperty(next);
			if (appProperties.getProperty(next) == null) {
				appProperties.put(next, val);
			}
		}
		Set keySet2 = appProperties.keySet();
		Iterator<String> iterator2 = keySet2.iterator();
		while (iterator2.hasNext()) {
			String next = iterator2.next();
			String val = appProperties.getProperty(next);
			String prop = next + ZookeeperConstants.EQUAL + val;
			result.add(new ByteArrayInputStream(prop.getBytes()));
			result.add(new ByteArrayInputStream(ZookeeperConstants.STR.getBytes()));
		}
	}

	/**
	 * 
	 * calculateAppProperties:取全局模块和应用模块参数. <br/>
	 *
	 * @author XieJunfeng
	 * @param seqenceInputStreamCollector
	 * @param map
	 * @param globalProperties
	 * @param appProperties
	 * @throws IOException
	 * @since JDK 1.6
	 */
	@SuppressWarnings("rawtypes")
	private void calculateAppProperties(List<InputStream> seqenceInputStreamCollector, Map<String, String> map, Properties globalProperties, Properties appProperties) throws IOException {
		for (int i = 0; i < seqenceInputStreamCollector.size(); i++) {
			InputStream in = seqenceInputStreamCollector.get(i);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String all = sb.toString();
			if (all.length() > 0 && !all.equals(ZookeeperConstants.NULL)) {
				all = replaceGlobalParameters(map, all);
				JSONArray fromObject = JSONArray.fromObject(all);
				for (int j = 0; j < fromObject.size(); j++) {
					JSONObject object = (JSONObject) fromObject.get(j);
					if (object.get(ZookeeperConstants.NAME).equals(ZookeeperConstants.GLOBAL)) {
						if (object.get(ZookeeperConstants.DETAILS) != null && !object.get(ZookeeperConstants.DETAILS).toString().equals(ZookeeperConstants.NULL)) {
							JSONObject detail = (JSONObject) object.get(ZookeeperConstants.DETAILS);
							Iterator keys = detail.keys();
							while (keys.hasNext()) {
								String key = (String) keys.next();
								String val = detail.getString(key);
								globalProperties.put(key, val);
							}
						}
					} else {
						if (object.get(ZookeeperConstants.DETAILS) != null && !object.get(ZookeeperConstants.DETAILS).toString().equals(ZookeeperConstants.NULL)) {
							JSONObject detail = (JSONObject) object.get(ZookeeperConstants.DETAILS);
							Iterator keys = detail.keys();
							while (keys.hasNext()) {
								String key = (String) keys.next();
								String val = detail.getString(key);
								appProperties.put(key, val);
							}
						}
					}
				}
			}
		}
	}

	private String replaceGlobalParameters(Map<String, String> map, String all) {
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String val = map.get(key);
			all = all.replaceAll("\\$\\{" + key + "\\}", val);
		}
		return all;
	}

	/**
	 * 
	 * constructModuleParameters:读取应用服务器模块的配置，后续用于应用参数的替换. <br/>
	 * 
	 * 例如： server_1_param=server_1_val server_2_param=server_2_val
	 * app_1_param=${server_1_param}
	 * 
	 * @author XieJunfeng
	 * @param confModuleName
	 * @param seqenceInputStreamCollector
	 * @return
	 * @since JDK 1.6
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> constructModuleParameters(String confModuleName, List<InputStream> seqenceInputStreamCollector) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (int i = 0; i < seqenceInputStreamCollector.size(); i++) {
				InputStream in = seqenceInputStreamCollector.get(i);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				String all = sb.toString();
				if (all.length() > 0 && !all.equals(ZookeeperConstants.NULL)) {
					try {
						JSONArray fromObject = JSONArray.fromObject(sb.toString());
						for (int j = 0; j < fromObject.size(); j++) {
							JSONObject object = (JSONObject) fromObject.get(j);
							if (object.get(ZookeeperConstants.NAME) != null && object.get(ZookeeperConstants.NAME).equals(confModuleName)) {
								if (object.get(ZookeeperConstants.DETAILS) != null && !object.get(ZookeeperConstants.DETAILS).toString().equals(ZookeeperConstants.NULL)) {
									JSONObject detail = (JSONObject) object.get(ZookeeperConstants.DETAILS);
									Iterator keys = detail.keys();
									while (keys.hasNext()) {
										String key = (String) keys.next();
										String val = detail.getString(key);
										map.put(key, val);
									}
								}
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				in.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * getDataInputStream:迭代读取节点数据放入,将读取到的字节流放入List<InputStream>中. <br/>
	 * 
	 * @author XieJunfeng
	 * @param currentZnode
	 * @param seqIsCollector
	 * @param regressionZnodes
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @since JDK 1.6
	 */
	private void getDataInputStream(String currentZnode, List<InputStream> seqIsCollector, boolean regressionZnodes) throws KeeperException, InterruptedException {
		ZooKeeper zk = getZk();
		try {
			seqIsCollector.add(new ByteArrayInputStream(zk.getData(currentZnode, true, null)));
			// need add return between every stream otherwise top/last line will
			// be
			// join to one line.
			seqIsCollector.add(new ByteArrayInputStream(ZookeeperConstants.STR.getBytes()));
			if (regressionZnodes) {
				List<String> children = zk.getChildren(currentZnode, true);
				if (children != null) {
					for (String child : children) {
						String childZnode = "";
						if (currentZnode.endsWith(ZookeeperConstants.SUFFIX)) {
							childZnode = currentZnode + child;
						} else {
							childZnode = currentZnode + ZookeeperConstants.SUFFIX + child;
						}
						getDataInputStream(childZnode, seqIsCollector, regressionZnodes);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = (AbstractApplicationContext) ctx;
	}

	public void destroy() throws Exception {
		log.info("Destory Zookeeper Resouce.");
		if (executor != null) {
			log.info("Close connection to Zookeeper Server.");
			try {
				executor.getZk().close();
				log.info("Connection to Zookeeper Server closed.");
			} catch (Exception e) {
				log.error("Error found when close zookeeper connection.", e);
			}
		}

	}

}
