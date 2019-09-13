/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;

@Generated("2017-01-06 10:31:38")
public interface AdviertisementInfoDAO extends EntityDAO<AdviertisementInfo, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long updateStatus(String id,String status);

	/** 获取机构列表 */
	List<AdviertisementInfo> getOrganList(Map<String, String> map);	
}
