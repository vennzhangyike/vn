/**
 * Project Name:hfepay-commons-codegen
 * File Name:ProjectManager.java
 * Package Name:com.hfepay.common.codegen.utils
 * Date:2014-2-8上午11:38:18
 * Copyright (c) 2014, hfepay All Rights Reserved.
 *
 */

package com.hfepay.common.codegen.manager;

import java.util.Properties;

import org.codehaus.plexus.util.StringUtils;

import com.hfepay.common.codegen.constants.Constants;

/**
 * ClassName:ProjectManager Date: 2014-2-8 上午11:38:18
 * 
 * @author hfepay
 * @version
 * @since JDK 1.6
 * @see
 */
public class ProjectManager {
	private String rootProject;
	private Properties properties;

	public ProjectManager(Properties properties, String rootProject) {
		this.properties = properties;
		this.rootProject = rootProject;
	}

	/**
	 * isMultiModule.
	 * 
	 * @return the isMultiModule
	 * @since JDK 1.6
	 */
	public boolean isMultiModule() {
		if (properties != null) {
			String property = properties.getProperty(Constants.PROPERTIES_MULTI_MODULE);
			if (!StringUtils.isEmpty(property)) {
				return Boolean.parseBoolean(property);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * getProperty <br/>
	 *
	 * @author hfepay
	 * @param key
	 * @return
	 * @since JDK 1.6
	 */
	public String getProperty(String key) {
		if (properties != null) {
			return properties.getProperty(key);
		}
		return null;
	}

	/**
	 * rootProject.
	 * 
	 * @return the rootProject
	 * @since JDK 1.6
	 */
	public String getRootProject() {
		return rootProject;
	}

	/**
	 * rootProject.
	 * 
	 * @param rootProject
	 *            the rootProject to set
	 * @since JDK 1.6
	 */
	public void setRootProject(String rootProject) {
		this.rootProject = rootProject;
	}

}
