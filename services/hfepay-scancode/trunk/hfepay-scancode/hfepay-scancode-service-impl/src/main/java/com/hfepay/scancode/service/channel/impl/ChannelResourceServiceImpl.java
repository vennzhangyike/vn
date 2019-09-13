package com.hfepay.scancode.service.channel.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.scancode.commons.dao.channel.ChannelResourceDAO;
import com.hfepay.scancode.commons.entity.ChannelResource;
import com.hfepay.scancode.service.channel.ChannelResourceService;


@Service("channelResourceService")
public class ChannelResourceServiceImpl implements ChannelResourceService{
	@Autowired
	private ChannelResourceDAO channelResourceDAO;
	
	@Override
	public List<ChannelResource> selectActiveMenu(String userId){
		List<ChannelResource> menuList= channelResourceDAO.selectActiveMenu(userId);
		return menuList;
	}
	
	@Override
	public List<ChannelResource> selectActiveResource(Map<String, Object> params) {
		return channelResourceDAO.selectActiveResource(params);
	}
}
