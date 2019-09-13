package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.commons.dao.SysRoleUserDAO;
import com.hfepay.scancode.commons.entity.SysRoleUser;
import com.hfepay.scancode.service.operator.SysRoleUserService;

@Service("roleUserService")
public class SysRoleUserServiceImpl implements SysRoleUserService {
	@Autowired
	private SysRoleUserDAO sysRoleUserDAO;

	@Override
	public List<SysRoleUser> selectRoleUserByRoleId(String roleId) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = Cnd.builder(SysRoleUser.class);
		cb.andGroup(Cnd.builder(SysRoleUser.class).andEQ("roleId", roleId));
		Criteria buildCriteria = cb.buildCriteria();
		return sysRoleUserDAO.findByCriteria(buildCriteria);//.selectRoleUserByRoleId(roleId);
	}

	@Override
	public SysRoleUser selectRoleUserByUserId(String userId) {
		CriteriaBuilder cb = Cnd.builder(SysRoleUser.class);
		cb.andGroup(Cnd.builder(SysRoleUser.class).andEQ("accountId", userId));
		Criteria buildCriteria = cb.buildCriteria();
		return sysRoleUserDAO.findOneByCriteria(buildCriteria);
	}

	@Override
	public long insert(SysRoleUser roleUser) {
		return sysRoleUserDAO.insert(roleUser);
	}

	@Override
	public long update(SysRoleUser admin) {
		return sysRoleUserDAO.update(admin);
	}

	
}
