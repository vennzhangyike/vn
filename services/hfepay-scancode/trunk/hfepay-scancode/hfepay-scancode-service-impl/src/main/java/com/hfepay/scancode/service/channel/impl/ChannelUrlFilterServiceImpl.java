package com.hfepay.scancode.service.channel.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.scancode.commons.dao.channel.ChannelUrlFilterDAO;
import com.hfepay.scancode.commons.entity.UrlFilter;
import com.hfepay.scancode.service.channel.ChannelUrlFilterService;

@Service("channelUrlFilterService")
public class ChannelUrlFilterServiceImpl implements ChannelUrlFilterService {
	@Autowired
	private ChannelUrlFilterDAO channelUrlFilterDAO;
	
	public Set<String> findRoles(String name){
		List<UrlFilter> roleList= channelUrlFilterDAO.findRoles(name);
		Set<String> set=new HashSet<String>();
		for (UrlFilter filter : roleList) {
			set.add(filter.getRoles());
		}
		return set;
	}

	public List<UrlFilter> findAll() {
		// TODO Auto-generated method stub
		return channelUrlFilterDAO.findAllUrl();
	}
}
