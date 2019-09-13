/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantAccountsDAO;
import com.hfepay.scancode.commons.entity.MerchantAccounts;

@Repository(value="merchantAccountsDAO")
@Generated("2016-10-22 15:09:24")
public class MerchantAccountsDAOImpl extends MybatisEntityDAO<MerchantAccounts, String> implements MerchantAccountsDAO {
	/**
	 * 状态更新
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}

	@Override
	public long updateByMerchantNo(MerchantAccounts merchantAccounts) {
		// TODO Auto-generated method stub
		return getSqlSession().update(sqlId("updateByMerchantNo"),merchantAccounts);
	}

	@Override
	public long updateBalance(String merchantNo, BigDecimal balance) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("balance", balance);
		return getSqlSession().update(sqlId("updateBalance"),map);
	}	
	
	
}