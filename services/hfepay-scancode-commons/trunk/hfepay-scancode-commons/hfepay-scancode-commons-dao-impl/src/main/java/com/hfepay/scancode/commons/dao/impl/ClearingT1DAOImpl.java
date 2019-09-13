/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ClearingT1DAO;
import com.hfepay.scancode.commons.entity.ClearingT1;

@Repository(value="clearingT1DAO")
@Generated("2017-02-09 16:20:29")
public class ClearingT1DAOImpl extends MybatisEntityDAO<ClearingT1, String> implements ClearingT1DAO {
	
	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	public void batchInsert(List<ClearingT1> t1List){
		 getSqlSession().insert(sqlId("batchInsert"), t1List);
	}
}