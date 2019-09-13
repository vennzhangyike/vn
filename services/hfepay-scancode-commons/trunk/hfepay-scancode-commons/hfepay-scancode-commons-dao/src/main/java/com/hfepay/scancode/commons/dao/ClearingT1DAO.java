/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ClearingT1;

@Generated("2017-02-09 16:20:29")
public interface ClearingT1DAO extends EntityDAO<ClearingT1, String> {

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT1> t1List);
}
