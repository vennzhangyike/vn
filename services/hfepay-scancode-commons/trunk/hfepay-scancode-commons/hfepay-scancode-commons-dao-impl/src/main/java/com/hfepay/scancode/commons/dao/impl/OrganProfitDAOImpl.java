/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.OrganProfitCondition;
import com.hfepay.scancode.commons.dao.OrganProfitDAO;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.OrganProfit;

@Repository(value="organProfitDAO")
@Generated("2016-11-30 17:50:55")
public class OrganProfitDAOImpl extends MybatisEntityDAO<OrganProfit, String> implements OrganProfitDAO {

	@Override
	public void insertBatch(List<OrganProfit> list) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("insertBatch"),list);
	}
	
	@Override
	public void updateMoney(OrganProfitCondition organProfitCondition) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(sqlId("updateMoney"),organProfitCondition);
	}
	
	@Override
	public List<HierarchicalSettlementTotalDTO> queryTotalProfit(String date) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("queryTotalProfit"),date);
	}
}