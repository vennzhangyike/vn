/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.OrganProfitCondition;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.OrganProfit;

@Generated("2016-11-30 17:50:55")
public interface OrganProfitDAO extends EntityDAO<OrganProfit, String> {

	void insertBatch(List<OrganProfit> list);

	void updateMoney(OrganProfitCondition organProfitCondition);

	List<HierarchicalSettlementTotalDTO> queryTotalProfit(String date);
}
