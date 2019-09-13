package com.hfepay.scancode.channel.commons;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.scancode.commons.entity.ChannelAdmin;

public class ScanBaseController extends BaseController{
	
	public static Map<String, String> MAP = new HashMap<String, String>();
	
	Logger logger = LoggerFactory.getLogger(ScanBaseController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 获取当前登录的账户
	 * @return
	 */
	protected ChannelAdmin getCurrentUser(){
		//获取渠道账户
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		return user;
	}
	
	/**
	 * 获取当前登录渠道账户编号
	 * @return
	 */
	protected String getCurrentUserId(){
		return getCurrentUser().getId();
	}
	
}
