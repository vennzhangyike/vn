package com.hfepay.common.web.controller;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.web.bind.annotation.RequestMapping;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingCondition;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.common.web.ExecuteStatus;

@RequestMapping("/router")
public class BaseController {
	public static final String EXECUTE_STATUS ="executeStatus";
	public static final int SUCCESS = 0;
	public static final int FAILED = 1;
	public static final String VALUES="values";
	
	/**
	 * 
	 * prepareSimpleSuccessJSONResult:(生成一个简单JSON对象表达执行成功返回). <br/>
	 *
	 * @return
	 * @deprecated 建议直接使用success()或者SUCCESS_RESULT更便捷
	 */
	protected JSON prepareSimpleSuccessJSONResult(){
		JSONObject obj = new JSONObject();
		obj.put(EXECUTE_STATUS, Integer.parseInt(ExecuteStatus.SUCCESS.code()));
		return obj;
	}
	
/*	*//**
	 * getCurrentUser:(获得当前用户). <br/>
	 * @return
	 *//*
	protected User getCurrentUser(){
		return UserAccountHolder.getCurrentUser();
	}
	
	*//**
	 * getCurrentCompanyId:(获得当前登录用户的公司id). <br/>
	 * @return
	 *//*
	protected Long getCurrentCompanyId(){
		return (Long) UserAccountHolder.getCurrentUser().getLongProperty(UserPropertiesKey.CMP_ID);
	}
	
	*//**
	 * getAdminStatus:(获得当前登录用户管理员状态[0-普通学员;1-管理员;2-超级管理员]). <br/>
	 * @return
	 *//*
	protected String getAdminStatus(){
		return UserAccountHolder.getCurrentUser().getStringProperty(UserPropertiesKey.ADMIN_FLAG);
	}
	
	*//**
	 * 获得用户当前角色 (MANAGER TRAINER LEARDER)
	 * @return
	 *//*
	protected String getUserRoleFlag() {
		return UserAccountHolder.getCurrentUser().getStringProperty(UserPropertiesKey.USER_ROLE);
	}
	
	*//**
	 * 获得当前用户当前部门id
	 * @return
	 *//*
	protected Long getCurrentUserDepId() {
		return (Long) UserAccountHolder.getCurrentUser().getLongProperty(UserPropertiesKey.DEP_ID);
	}
	
	protected String getCurrentUserStringProperty(String userPropertiesKey){
		return UserAccountHolder.getCurrentUser().getStringProperty(userPropertiesKey);
	}
	
	protected Long getCurrentUserLongProperty(String userPropertiesKey){
		return (Long)UserAccountHolder.getCurrentUser().getLongProperty(userPropertiesKey);
	}*/
	
	protected JSON success() {
		return JSONSerializer.toJSON(Maps.mapByAarray(EXECUTE_STATUS,0));
	}
	
	protected <E> JSON success(PagingCondition paging,PagingResult<E> page,JsonConfig jsonconf) {
		Pager<E> pager = new Pager<E>();
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		pager.setPageNo(paging.getPageNo());
		pager.setPageSize(paging.getPageSize());
		if (pager.getOrder() == null) {
			pager.setOrder("");
		}
		return customJsonResult(Maps.mapByAarray(EXECUTE_STATUS, 0, VALUES, pager),jsonconf);		
	}
	
	protected JSON customJsonResult(Map<Object,Object> map,JsonConfig jsonconf) {
		if (jsonconf == null)
			return JSONSerializer.toJSON(map);
		else 
			return JSONSerializer.toJSON(map,jsonconf);
	}
	
	protected static final JSON SUCCESS_RESULT = JSONSerializer.toJSON(Maps.mapByAarray(EXECUTE_STATUS,0));
}