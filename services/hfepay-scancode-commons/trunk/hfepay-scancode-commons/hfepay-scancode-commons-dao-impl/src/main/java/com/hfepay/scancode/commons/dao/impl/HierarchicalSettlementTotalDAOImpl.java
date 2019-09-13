/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.dao.HierarchicalSettlementTotalDAO;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;

@Repository(value="hierarchicalSettlementTotalDAO")
@Generated("2016-12-20 11:30:01")
public class HierarchicalSettlementTotalDAOImpl extends MybatisEntityDAO<HierarchicalSettlementTotal, String> implements HierarchicalSettlementTotalDAO {

	@Override
	public void inserBatch(List<HierarchicalSettlementTotal> list) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("inserBatch"), list);
	}
	
	@Override
	public PagingResult<HierarchicalStatic> findHierarchicalStatic(Criteria criteria){
		
		return pagingQuery(sqlId("countHierarchicalStatic"),sqlId("findHierarchicalStatic"),criteria);
	}
	
	@Override
	public HierarchicalSettlementTotal getAmtStatic(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) {
		return this.getSqlSession().selectOne(sqlId("getAmtStatic"), hierarchicalSettlementTotalCondition);
	}
	
	@Override
	public int getCountHierarchicalStatic(Criteria criteria){
		
		return this.getSqlSession().selectOne(sqlId("countHierarchicalStatic"),criteria);
	}
	
}