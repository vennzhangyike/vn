/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ClearingT0ErrDAO;
import com.hfepay.scancode.commons.entity.ClearingT0Err;

@Repository(value="clearingT0ErrDAO")
@Generated("2017-03-20 19:49:25")
public class ClearingT0ErrDAOImpl extends MybatisEntityDAO<ClearingT0Err, String> implements ClearingT0ErrDAO {

	/** t0对账数据对比 */
	@Override
	public List<ClearingT0Err> checkT0Data(Map<String, Object> map) {
		return getSqlSession().selectList(sqlId("checkT0Data"),map);
	}

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT0Err> t0ErrList) {
		getSqlSession().insert(sqlId("batchInsert"), t0ErrList);
		
	}
}