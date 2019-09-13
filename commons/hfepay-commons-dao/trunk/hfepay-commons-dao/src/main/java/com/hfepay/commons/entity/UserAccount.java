package com.hfepay.commons.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基于统一认证中心的用户抽象信息
 * @author Sam
 *
 */
public interface UserAccount extends IdEntity<Long>, Serializable {
	/**
	 * 用户的全名
	 * @return
	 */
	String getFullName();
	
	/**
	 * 用户的帐号名称，默认应该是根据规则生成的一串数字
	 * @return
	 */
	String getAccountName();
	
	/**
	 * 帐号绑定的手机号码
	 * @return
	 */
	String getMobile();
	
	/**
	 * 帐户绑定的邮箱
	 * @return
	 */
	String getEmail();
	
	/**
	 * 帐户状态
	 * @return
	 */
	String getStatus();
	
	/**
	 * 帐户开通的应用列表
	 * @return
	 */
	List<App> getOpenedApps();
	
	/**
	 * 
	 * getProperties:获取用户属性. <br/>
	 *
	 * @return
	 */
	Map<String,Object> getProperties();
}
