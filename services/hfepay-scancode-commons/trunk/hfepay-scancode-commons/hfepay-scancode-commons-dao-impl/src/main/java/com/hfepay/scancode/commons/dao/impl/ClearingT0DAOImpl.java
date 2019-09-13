/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ClearingT0DAO;
import com.hfepay.scancode.commons.entity.ClearingT0;

@Repository(value="clearingT0DAO")
@Generated("2017-03-20 19:07:10")
public class ClearingT0DAOImpl extends MybatisEntityDAO<ClearingT0, String> implements ClearingT0DAO {

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT0> t0List) {
		getSqlSession().insert(sqlId("batchInsert"), t0List);
	}
}