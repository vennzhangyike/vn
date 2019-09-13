package com.hfepay.scancode.commons.dao.channel;

import java.util.List;

import com.hfepay.scancode.commons.entity.UrlFilter;


public interface ChannelUrlFilterDAO {
	List<UrlFilter> findRoles(String name);
	
	List<UrlFilter> findAllUrl();
}
