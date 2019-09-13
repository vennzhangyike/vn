/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ClearingT0Err;

@Generated("2017-03-20 19:49:25")
public interface ClearingT0ErrDAO extends EntityDAO<ClearingT0Err, String> {

	/** t0对账数据对比 */
	List<ClearingT0Err> checkT0Data(Map<String, Object> map);

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT0Err> t0ErrList);
}
