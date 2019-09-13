package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysRoleCondition;
import com.hfepay.scancode.commons.dao.SysRoleDAO;
import com.hfepay.scancode.commons.dao.SysRoleResourceDAO;
import com.hfepay.scancode.commons.entity.SysRole;
import com.hfepay.scancode.commons.entity.SysRoleResource;
import com.hfepay.scancode.service.operator.SysRoleService;

@Service("roleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDAO SysRoleDAO;
	@Autowired
	private SysRoleResourceDAO sysRoleResourceDAO;

	/**
	 * 角色列表
	 */
	@Override
	public PagingResult<SysRole> findAll(SysRoleCondition condition) {
		CriteriaBuilder cb = Cnd.builder(SysRole.class);
		if(!Strings.isEmpty(condition.getDescription())){
			cb.andLike("description", condition.getDescription());
		}
		if(!Strings.isEmpty(condition.getRoleName())){
			cb.andLike("roleName", condition.getRoleName());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(condition.getFirst()), Long.valueOf(condition.getLast()));
		
		return SysRoleDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 角色列表
	 */
	@Override
	public List<SysRole> findAllNoPage(SysRoleCondition condition) {
		CriteriaBuilder cb = Cnd.builder(SysRole.class);
		if(!Strings.isEmpty(condition.getDescription())){
			cb.andLike("description", condition.getDescription());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		return SysRoleDAO.findByCriteria(buildCriteria);
	}


	/**
	 * 修改权限
	 */
	@Override
	@Transactional
	public String saveRoles(String ids, String roleId) {
		try {
			deleteSysRoleResourceByRoleId(roleId);//roleResourceMapper.deleteByRoleId(roleId);
			List<SysRoleResource> list = new ArrayList<>();
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				SysRoleResource r = new SysRoleResource();
				r.setId(Strings.getUUID());//id由java类生成
				r.setResourceId(id);
				r.setRoleId(roleId);
				r.setCreateTime(new Date());
				list.add(r);
			}
			sysRoleResourceDAO.insert(list);
		} catch (Exception e) {
			return "";
		}
		return "success";
	}
	
	
	/**
	 * 新增
	 * @param SysRoleCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-06 10:35:30
	 */
	@Override
	public long saveRole(SysRoleCondition condition) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(condition,role);
		return SysRoleDAO.insert(role);
	}


	/**
	 * 根据角色ID查询角色信息
	 */
	@Override
	public SysRole findRoleById(String id) {
		// TODO Auto-generated method stub
		return SysRoleDAO.get(id);
	}


	/**
	 * 删除角色同时删除角色之源关系
	 */
	@Override
	public long deleteRole(String roleId) {
		// TODO Auto-generated method stub
		long result = SysRoleDAO.deleteById(roleId);//删除角色
		if(result>0){
			deleteSysRoleResourceByRoleId(roleId);//删除角色资源关系
		}
		else{
			throw new RuntimeException("删除失败");
		}
		return result;
	}
	
	private Long deleteSysRoleResourceByRoleId(String roleId){
		long result = sysRoleResourceDAO.deleteSysRoleResourceByRoleId(roleId);
		return result;
	}

	@Override
	public long update(SysRoleCondition condition) {
		SysRole  entity = new SysRole(); 
		BeanUtils.copyProperties(condition,entity);
		return SysRoleDAO.update(entity);
	}
	
	@Override
	public int findExeits(String column, String value) {
		CriteriaBuilder cb = Cnd.builder(SysRole.class);
		cb.andEQ(column, value);
		Criteria buildCriteria = cb.buildCriteria();
		return SysRoleDAO.countByCriteria(buildCriteria);
	}	
}
