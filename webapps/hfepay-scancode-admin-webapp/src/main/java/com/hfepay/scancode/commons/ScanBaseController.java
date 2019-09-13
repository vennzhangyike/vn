package com.hfepay.scancode.commons;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.scancode.commons.entity.Admin;

public class ScanBaseController extends BaseController{
	
	public static Map<String, String> MAP = new HashMap<String, String>();
	
	Logger logger = LoggerFactory.getLogger(ScanBaseController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	
	protected String getCurrentUserId(){
		//获取渠道
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		return user.getId();
	}
	
	protected String getCurrentUserName(){
		//获取渠道
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		return user.getUserName();
	}
	
	
}
