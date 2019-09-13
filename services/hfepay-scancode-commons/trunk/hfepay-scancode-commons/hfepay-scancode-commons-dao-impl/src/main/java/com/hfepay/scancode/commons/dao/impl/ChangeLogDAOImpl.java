/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.dao.ChangeLogDAO;
import com.hfepay.scancode.commons.entity.ChangeLog;

@Repository(value="changeLogDAO")
@Generated("2016-10-17 15:21:26")
public class ChangeLogDAOImpl extends MybatisEntityDAO<ChangeLog, String> implements ChangeLogDAO {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}
	
	/**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public PagingResult<ChangeLog> findAuditMerchantBankcard(Criteria criteria){
		return pagingQuery(sqlId("countAuditMerchantBankcard"),sqlId("findAuditMerchantBankcard"),criteria);
	}	
	
	/**
	 * @Title: findByTradeNo
	 * @Description: 商户银行账户主键查找(专用)
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public ChangeLog findByTradeNo(String id){
		return getSqlSession().selectOne(sqlId("findByTradeNo"),id);
	}
}