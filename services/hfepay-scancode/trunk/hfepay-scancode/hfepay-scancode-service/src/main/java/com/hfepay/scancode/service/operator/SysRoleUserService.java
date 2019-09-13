package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.scancode.commons.entity.SysRoleUser;

public interface SysRoleUserService {
	/**
	 * 菜单
	 * @return
	 */
	public List<SysRoleUser> selectRoleUserByRoleId(String roleId);
	
	/**
	 * 通过用户编号获取权限
	 */
	public SysRoleUser selectRoleUserByUserId(String userId);
	
	
	/**
	 * 创建用户角色对应关系
	 */
	public long insert(SysRoleUser roleUser);

	public long update(SysRoleUser admin);
	
}
