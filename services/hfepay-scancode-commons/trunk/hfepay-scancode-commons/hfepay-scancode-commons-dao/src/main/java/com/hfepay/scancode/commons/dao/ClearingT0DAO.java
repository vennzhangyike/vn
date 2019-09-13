/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ClearingT0;

@Generated("2017-03-20 19:07:10")
public interface ClearingT0DAO extends EntityDAO<ClearingT0, String> {

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT0> t0List);
}
