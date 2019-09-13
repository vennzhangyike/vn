package com.hfepay.scancode.service.channel.impl;

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
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.dao.channel.ChannelRoleDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelRoleResourceDAO;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.commons.entity.ChannelRoleResource;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;

@Service("channelRoleService")
public class ChannelRoleServiceImpl implements ChannelRoleService {
	@Autowired
	private ChannelRoleDAO channelRoleDao;
	@Autowired
	private ChannelRoleResourceDAO channelRoleResourceDAO;

	/**
	 * 角色列表
	 */
	@Override
	public PagingResult<ChannelRole> findAll(ChannelRoleCondition condition) {
		CriteriaBuilder cb = Cnd.builder(ChannelRole.class);
		if(!Strings.isEmpty(condition.getDescription())){
			cb.andLike("description", condition.getDescription());
		}
		if(!Strings.isEmpty(condition.getRoleName())){
			cb.andLike("roleName", condition.getRoleName());
		}
		if(!Strings.isEmpty(condition.getChannelName())){
			cb.andEQ("channelName", condition.getChannelName());
		}
		if(!Strings.isEmpty(condition.getChannelCode())){
			cb.andEQ("channelCode", condition.getChannelCode());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(condition.getFirst()), Long.valueOf(condition.getLast()));
		
		return channelRoleDao.findPagingResult(buildCriteria);
	}
	
	/**
	 * 角色列表
	 */
	@Override
	public List<ChannelRole> findAllNoPage(ChannelRoleCondition condition) {
		CriteriaBuilder cb = Cnd.builder(ChannelRole.class);
		if(!Strings.isEmpty(condition.getDescription())){
			cb.andLike("description", condition.getDescription());
		}
		if(!Strings.isEmpty(condition.getRoleLevel())){
			cb.andEQ("roleLevel", condition.getRoleLevel());
		}
		List<String> list = new ArrayList<String>();
		list.add(ScanCodeConstants.ROLE_TYPE_CHANNEL);
		list.add(ScanCodeConstants.ROLE_TYPE_AGENT);
		list.add(ScanCodeConstants.ROLE_TYPE_MERCHANT);
		if(!Strings.isEmpty(condition.getChannelCode())){			
			list.add(condition.getChannelCode());
		}
		cb.andIn("channelCode", list);
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleDao.findByCriteria(buildCriteria);
	}


	/**
	 * 修改权限
	 */
	@Override
	@Transactional
	public String saveRoles(String ids, String roleId) {
		try {
			deleteChannelRoleResourceByRoleId(roleId);//roleResourceMapper.deleteByRoleId(roleId);
			List<ChannelRoleResource> list = new ArrayList<>();
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				ChannelRoleResource r = new ChannelRoleResource();
				r.setId(Strings.getUUID());//id由java类生成
				r.setResourceId(id);
				r.setRoleId(roleId);
				r.setCreateTime(new Date());
				list.add(r);
			}
			channelRoleResourceDAO.insert(list);
		} catch (Exception e) {
			return "";
		}
		return "success";
	}
	
	
	/**
	 * 新增
	 * @param ChannelRoleCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-06 10:35:30
	 */
	@Override
	public long saveRole(ChannelRoleCondition condition) {
		ChannelRole role = new ChannelRole();
		BeanUtils.copyProperties(condition,role);
		return channelRoleDao.insert(role);
	}


	/**
	 * 根据角色ID查询角色信息
	 */
	@Override
	public ChannelRole findRoleById(String id) {
		// TODO Auto-generated method stub
		return channelRoleDao.get(id);
	}


	/**
	 * 删除角色同时删除角色之源关系
	 */
	@Override
	public long deleteRole(String roleId) {
		// TODO Auto-generated method stub
		long result = channelRoleDao.deleteById(roleId);//删除角色
		if(result>0){
			deleteChannelRoleResourceByRoleId(roleId);//删除角色资源关系
		}else{
			throw new RuntimeException("删除失败");
		}
		return result;
	}
	
	private Long deleteChannelRoleResourceByRoleId(String roleId){
		long result = channelRoleResourceDAO.deleteChannelRoleResourceByRoleId(roleId);
		return result;
	}

	@Override
	public long update(ChannelRoleCondition condition) {
		ChannelRole  entity = new ChannelRole(); 
		BeanUtils.copyProperties(condition,entity);
		return channelRoleDao.update(entity);
	}
	
	@Override
	public int findExeits(String column, String value) {
		CriteriaBuilder cb = Cnd.builder(ChannelRole.class);
		cb.andEQ(column, value);
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleDao.countByCriteria(buildCriteria);
	}

	@Override
	public ChannelRole findRoleByChannelCode(String channelCode) {
		CriteriaBuilder cb = Cnd.builder(ChannelRole.class);
		cb.andEQ("channelCode", channelCode);
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleDao.findOneByCriteria(buildCriteria);
	}	
	
	
	@Override
	public ChannelRole findByCondition(ChannelRoleCondition condition) {
		CriteriaBuilder cb = Cnd.builder(ChannelRole.class);
		if(!Strings.isEmpty(condition.getDescription())){
			cb.andLike("description", condition.getDescription());
		}
		if(!Strings.isEmpty(condition.getRoleName())){
			cb.andLike("roleName", condition.getRoleName());
		}
		if(!Strings.isEmpty(condition.getChannelName())){
			cb.andEQ("channelName", condition.getChannelName());
		}
		if(!Strings.isEmpty(condition.getChannelCode())){
			cb.andEQ("channelCode", condition.getChannelCode());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleDao.findOneByCriteria(buildCriteria);
	}	
}
