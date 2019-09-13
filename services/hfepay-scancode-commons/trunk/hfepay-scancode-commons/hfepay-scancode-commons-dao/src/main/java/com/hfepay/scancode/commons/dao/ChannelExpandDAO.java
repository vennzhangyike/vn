/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ChannelExpand;

@Generated("2016-10-13 15:29:52")
public interface ChannelExpandDAO extends EntityDAO<ChannelExpand, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * 生成编码
	 * @param map
	 */
	public void getChannelCode(Map<String, String> map);
}
