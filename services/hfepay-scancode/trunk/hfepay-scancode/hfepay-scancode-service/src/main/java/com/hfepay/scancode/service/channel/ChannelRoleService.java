package com.hfepay.scancode.service.channel;


import java.util.List;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.entity.ChannelRole;

public interface ChannelRoleService {
	/**
	 * 角色列表
	 * @param role 
	 * @param page 
	 * @return
	 */
	public PagingResult<ChannelRole> findAll(ChannelRoleCondition condition);//(Page page, SysRole role);

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
	public ChannelRole findRoleById(String id);
	
	/**
	 * 根据渠道ID查找角色信息
	 * @param id
	 * @return
	 */
	public ChannelRole findRoleByChannelCode(String channelCode);

	/**
	 * 删除角色信息
	 * @param roleId
	 * @return
	 */
	public long deleteRole(String roleId);

	List<ChannelRole> findAllNoPage(ChannelRoleCondition condition);

	long saveRole(ChannelRoleCondition condition);
	
	long update(ChannelRoleCondition condition);
	
	/**
	 * 查看是否存在对应信息
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-20 15:35:32
	 */
	int findExeits(String column, String value);
	
	public ChannelRole findByCondition(ChannelRoleCondition condition);
}
