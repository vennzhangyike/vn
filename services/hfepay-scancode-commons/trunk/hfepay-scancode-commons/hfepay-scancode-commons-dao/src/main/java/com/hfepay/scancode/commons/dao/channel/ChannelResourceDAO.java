package com.hfepay.scancode.commons.dao.channel;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ChannelResource;


    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public interface ChannelResourceDAO extends EntityDAO<ChannelResource, String> {
    	/**
    	 * 菜单
    	 * @return
    	 */
		List<ChannelResource> selectActiveMenu(String userId);
		/**
		 * 角色资源
		 */
		List<ChannelResource> selectActiveResource(Map<String, Object> params);

}