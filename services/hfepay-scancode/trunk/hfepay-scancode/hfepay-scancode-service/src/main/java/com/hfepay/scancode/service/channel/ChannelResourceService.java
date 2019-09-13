package com.hfepay.scancode.service.channel;

import java.util.List;
import java.util.Map;

import com.hfepay.scancode.commons.entity.ChannelResource;

public interface ChannelResourceService {
	/**
	 * 菜单
	 * @return
	 */
	public List<ChannelResource> selectActiveMenu(String userId);
	
	/**
	 * 角色资源
	 */
	public List<ChannelResource> selectActiveResource(Map<String, Object> params);
}
