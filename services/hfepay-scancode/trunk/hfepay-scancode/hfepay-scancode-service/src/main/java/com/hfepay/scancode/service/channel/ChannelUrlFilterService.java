package com.hfepay.scancode.service.channel;

import java.util.List;
import java.util.Set;

import com.hfepay.scancode.commons.entity.UrlFilter;

public interface ChannelUrlFilterService {
	/**
	 * 用户的所有角色
	 * @param name
	 * @return
	 */
	public Set<String> findRoles(String name);
	/**
	 * 角色对应的所有url
	 * @return
	 */
	public List<UrlFilter> findAll();
}
