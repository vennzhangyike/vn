/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.AgentPaywayBak;

@Generated("2016-10-18 15:18:22")
public interface AgentPaywayBakDAO extends EntityDAO<AgentPaywayBak, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	/**
	 * 删除数据
	 * @Title: doTruncateTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void doTruncateTable();

	/**
	 * 插入当天最新数据
	 * @Title: doBackUpTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void doBackUpTable();	
}
