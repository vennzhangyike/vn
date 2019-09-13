/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.TempProfitDAO;
import com.hfepay.scancode.commons.entity.TempProfit;

@Repository(value="tempProfitDAO")
@Generated("2016-12-01 15:05:27")
public class TempProfitDAOImpl extends MybatisEntityDAO<TempProfit, String> implements TempProfitDAO {

	@Override
	public void insertBatch(List<TempProfit> listTemp) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("insertBatch"),listTemp);
	}
	
	@Override
	public List<TempProfit> getTotalProfitByIdentity() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getTotalProfitByIdentity"));
	}
	
	@Override
	public List<TempProfit> getTotalProfit() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getTotalProfit"));
	}
	@Override
	public void clearTempData() {
		this.getSqlSession().update(sqlId("clearTempData"));
	}
	@Override
	public List<TempProfit> getWithDrawType() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getWithDrawType"));
	}
}