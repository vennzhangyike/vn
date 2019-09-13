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
import com.hfepay.scancode.commons.dao.ClearingT1ErrDAO;
import com.hfepay.scancode.commons.entity.ClearingT1Err;

@Repository(value="clearingT1ErrDAO")
@Generated("2017-03-17 18:04:17")
public class ClearingT1ErrDAOImpl extends MybatisEntityDAO<ClearingT1Err, String> implements ClearingT1ErrDAO {
	
	/** t1对账数据对比 */
	public List<ClearingT1Err> checkT1Data(Map<String, Object> map){
		return getSqlSession().selectList(sqlId("checkT1Data"),map);
	}

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT1Err> t1ErrList) {
		getSqlSession().insert(sqlId("batchInsert"), t1ErrList);
	}
	
	
	
}