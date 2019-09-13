package com.hfepay.scancode.service.channel.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.commons.dao.channel.ChannelRoleUserDAO;
import com.hfepay.scancode.commons.entity.ChannelRoleUser;
import com.hfepay.scancode.commons.entity.SysRoleUser;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;

@Service("channelRoleUserService")
public class ChannelRoleUserServiceImpl implements ChannelRoleUserService {
	@Autowired
	private ChannelRoleUserDAO channelRoleUserDAO;

	@Override
	public List<ChannelRoleUser> selectRoleUserByRoleId(String roleId) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = Cnd.builder(ChannelRoleUser.class);
		cb.andGroup(Cnd.builder(SysRoleUser.class).andEQ("roleId", roleId));
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleUserDAO.findByCriteria(buildCriteria);//.selectRoleUserByRoleId(roleId);
	}

	@Override
	public ChannelRoleUser selectRoleUserByUserId(String userId) {
		CriteriaBuilder cb = Cnd.builder(ChannelRoleUser.class);
		cb.andGroup(Cnd.builder(ChannelRoleUser.class).andEQ("accountId", userId));
		Criteria buildCriteria = cb.buildCriteria();
		return channelRoleUserDAO.findOneByCriteria(buildCriteria);
	}

	@Override
	public long insert(ChannelRoleUser roleUser) {
		return channelRoleUserDAO.insert(roleUser);
	}

	@Override
	public long update(ChannelRoleUser admin) {
		return channelRoleUserDAO.update(admin);
	}

	
}
