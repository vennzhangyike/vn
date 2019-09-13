package com.hfepay.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.DruidPasswordCallback;


public class JdbcPasswordCallback extends DruidPasswordCallback {
	
	private static final long serialVersionUID = -963477070869032008L;
	protected static Logger logger = LoggerFactory.getLogger(JdbcPasswordCallback.class);
	
	@Override
	public char[] getPassword() {
		char[] result =  super.getPassword();
		try {
			if(null != result){
				result = com.alibaba.druid.filter.config.ConfigTools.decrypt(new String(result)).toCharArray();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

}
