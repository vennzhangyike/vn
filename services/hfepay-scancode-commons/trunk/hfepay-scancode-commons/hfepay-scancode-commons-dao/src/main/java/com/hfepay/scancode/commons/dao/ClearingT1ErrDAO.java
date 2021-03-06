/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ClearingT1Err;

@Generated("2017-03-17 18:04:17")
public interface ClearingT1ErrDAO extends EntityDAO<ClearingT1Err, String> {

	/** t1对账数据对比 */
	List<ClearingT1Err> checkT1Data(Map<String, Object> map);

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT1Err> t1ErrList);
}
