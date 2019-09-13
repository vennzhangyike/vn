/**
 * Project Name:full
 * File Name:ZookeeperModule.java
 * Package Name:com.kingnod.etraining.zookeeper
 * Date:2013-12-4下午02:29:57
 * Copyright (c) 2013, hfepay@hfepay.com All Rights Reserved.
 *
 */

package com.hfepay.commons.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ZookeeperModule <br/>
 * Function: 应用参数模块实体类包装器. <br/>
 * Date: 2013-12-4 下午02:29:57 <br/>
 * 
 * @author XieJunfeng
 * @version
 * @since JDK 1.6
 * @see
 */
public class ZookeeperModule {
	public static class Conf {
		private Module nameMappingModule = new Module();
		private List<App> apps = new ArrayList<App>();

		/**
		 * nameMappingModule.
		 * 
		 * @return the nameMappingModule
		 * @since JDK 1.6
		 */
		public Module getNameMappingModule() {
			return nameMappingModule;
		}

		/**
		 * nameMappingModule.
		 * 
		 * @param nameMappingModule
		 *            the nameMappingModule to set
		 * @since JDK 1.6
		 */
		public void setNameMappingModule(Module nameMappingModule) {
			this.nameMappingModule = nameMappingModule;
		}

		/**
		 * apps.
		 * 
		 * @return the apps
		 * @since JDK 1.6
		 */
		public List<App> getApps() {
			return apps;
		}

		/**
		 * apps.
		 * 
		 * @param apps
		 *            the apps to set
		 * @since JDK 1.6
		 */
		public void setApps(List<App> apps) {
			this.apps = apps;
		}
	}

	public static class App extends Name {
		private List<Module> modules = new ArrayList<Module>();

		/**
		 * modules.
		 * 
		 * @return the modules
		 * @since JDK 1.6
		 */
		public List<Module> getModules() {
			return modules;
		}

		/**
		 * modules.
		 * 
		 * @param modules
		 *            the modules to set
		 * @since JDK 1.6
		 */
		public void setModules(List<Module> modules) {
			this.modules = modules;
		}
	}

	public static class Module extends Name {
		private Map<String, String> details = new HashMap<String, String>();

		/**
		 * details.
		 * 
		 * @return the details
		 * @since JDK 1.6
		 */
		public Map<String, String> getDetails() {
			return details;
		}

		/**
		 * details.
		 * 
		 * @param details
		 *            the details to set
		 * @since JDK 1.6
		 */
		public void setDetails(Map<String, String> details) {
			this.details = details;
		}

	}

	public static abstract class Name {
		/**
		 * Module: module名称; App: zookeeper节点路径名
		 */
		private String name;

		/**
		 * name.
		 * 
		 * @return the name
		 * @since JDK 1.6
		 */
		public String getName() {
			return name;
		}

		/**
		 * name.
		 * 
		 * @param name
		 *            the name to set
		 * @since JDK 1.6
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
