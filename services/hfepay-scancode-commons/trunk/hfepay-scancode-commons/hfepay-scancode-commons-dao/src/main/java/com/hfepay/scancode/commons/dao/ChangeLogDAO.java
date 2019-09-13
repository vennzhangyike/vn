/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.entity.ChangeLog;

@Generated("2016-10-17 15:21:26")
public interface ChangeLogDAO extends EntityDAO<ChangeLog, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public PagingResult<ChangeLog> findAuditMerchantBankcard(Criteria criteria);
	
	/**
	 * @Title: findByTradeNo
	 * @Description: 商户银行账户主键查找(专用)
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	ChangeLog findByTradeNo(String id);
}
