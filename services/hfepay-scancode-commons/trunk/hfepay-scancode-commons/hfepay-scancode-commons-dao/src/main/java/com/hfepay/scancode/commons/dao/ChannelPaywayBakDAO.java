/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.bo.ProfitBo;
import com.hfepay.scancode.commons.entity.ChannelPaywayBak;

@Generated("2016-10-18 15:28:16")
public interface ChannelPaywayBakDAO extends EntityDAO<ChannelPaywayBak, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long updateStatus(String id,String status);

	/**
	 * 渠道费率差
	 * @Title: getChannelRateDiff
	 * @author: husain
	 * @return
	 * @return: List<ProfitBo>
	 */
	List<ProfitBo> getChannelRateDiff();

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
