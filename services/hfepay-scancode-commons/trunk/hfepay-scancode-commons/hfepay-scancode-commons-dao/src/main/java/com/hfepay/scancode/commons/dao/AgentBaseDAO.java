/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.AgentBase;

@Generated("2016-10-19 16:04:33")
public interface AgentBaseDAO extends EntityDAO<AgentBase, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	/**
	 * 修改使用次数
	 * @Title: updateUsedTimes
	 * @Description: TODO
	 * @author: husain
	 * @param agentNo
	 * @return
	 * @return: long
	 */
	long updateUsedTimes(String agentNo);	
}
