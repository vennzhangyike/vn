/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.AdviertisementInfoDAO;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;

@Repository(value="adviertisementInfoDAO")
@Generated("2017-01-06 10:31:38")
public class AdviertisementInfoDAOImpl extends MybatisEntityDAO<AdviertisementInfo, String> implements AdviertisementInfoDAO {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}	
	
	/** 获取机构列表 */
	@Override
	public List<AdviertisementInfo> getOrganList(Map<String, String> map) {
		return this.getSqlSession().selectList(sqlId("getOrganList"),map);
	}	
}