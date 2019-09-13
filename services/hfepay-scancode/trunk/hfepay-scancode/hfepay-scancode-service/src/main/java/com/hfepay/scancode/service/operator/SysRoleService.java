package com.hfepay.scancode.service.operator;


import java.util.List;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysRoleCondition;
import com.hfepay.scancode.commons.entity.SysRole;

public interface SysRoleService {
	/**
	 * 角色列表
	 * @param role 
	 * @param page 
	 * @return
	 */
	public PagingResult<SysRole> findAll(SysRoleCondition condition);//(Page page, SysRole role);

	/**
	 * 修改角色权限
	 * @param ids
	 * @param roleId
	 * @return
	 */
	public String saveRoles(String ids, String roleId);
	/**
	 * 根据角色ID查找角色信息
	 * @param id
	 * @return
	 */
	public SysRole findRoleById(String id);

	/**
	 * 删除角色信息
	 * @param roleId
	 * @return
	 */
	public long deleteRole(String roleId);

	List<SysRole> findAllNoPage(SysRoleCondition condition);

	long saveRole(SysRoleCondition condition);
	
	long update(SysRoleCondition condition);
	
	/**
	 * 查看是否存在对应信息
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-20 15:35:32
	 */
	int findExeits(String column, String value);
}
