package com.hfepay.commons.context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.entity.AppName;
import com.hfepay.commons.entity.UserAccount;
/**
 * InvocationContext的一个简易实现
 * 
 * @author Sam
 *
 */
public class SimpleInvocationContext implements InvocationContext,Serializable {
	
	private static final long serialVersionUID = -5066658909073717994L;

	@SuppressWarnings("rawtypes")
	protected Map values = Maps.newMap();
	
	protected UserAccount useraccount;
	
	protected AppName appname;

	@SuppressWarnings("unchecked")
	public void set(String key, Object value) {
		values.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) values.get(key);
	}

	public Map<?, ?> getValuesMap() {
		return values;
	}

	public List<?> getValues() {
		// TODO 这里还不想写，估计还有一段比较长的时间用不到
		return null;
	}

	public Iterator<?> valuesIterator() {
		return getValues().iterator();
	}

	public List<?> findValuesByREGEX(String regex) {
		// TODO 这里还不想写，估计还有一段比较长的时间用不到
		return null;
	}

	public UserAccount getUserAccout() {
		return useraccount;
	}

	public AppName getAppName() {
		return appname;
	}
	
	public static SimpleInvocationContext create(UserAccount ua,AppName appName) {
		SimpleInvocationContext sc = new SimpleInvocationContext();
		sc.appname = appName;
		sc.useraccount = ua;
		InvocationContextHolder.setInvocationContext(sc);
		return sc;
	}

}
