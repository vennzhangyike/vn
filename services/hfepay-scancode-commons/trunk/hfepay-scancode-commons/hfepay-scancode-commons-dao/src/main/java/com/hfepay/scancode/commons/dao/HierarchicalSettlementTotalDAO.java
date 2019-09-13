/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;

@Generated("2016-12-20 11:30:01")
public interface HierarchicalSettlementTotalDAO extends EntityDAO<HierarchicalSettlementTotal, String> {

	void inserBatch(List<HierarchicalSettlementTotal> list);
	
	public PagingResult<HierarchicalStatic> findHierarchicalStatic(Criteria criteria);
	
	HierarchicalSettlementTotal getAmtStatic(HierarchicalSettlementTotalCondition hierarchicalSettlementTotals);
	
	public int getCountHierarchicalStatic(Criteria criteria);

}
