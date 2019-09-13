package com.hfepay.scancode.service.channel;

import java.util.List;

import com.hfepay.scancode.commons.entity.ChannelRoleUser;

public interface ChannelRoleUserService {
	/**
	 * 菜单
	 * @return
	 */
	public List<ChannelRoleUser> selectRoleUserByRoleId(String roleId);
	
	/**
	 * 通过用户编号获取权限
	 */
	public ChannelRoleUser selectRoleUserByUserId(String userId);
	
	
	/**
	 * 创建用户角色对应关系
	 */
	public long insert(ChannelRoleUser roleUser);

	public long update(ChannelRoleUser admin);
	
}
