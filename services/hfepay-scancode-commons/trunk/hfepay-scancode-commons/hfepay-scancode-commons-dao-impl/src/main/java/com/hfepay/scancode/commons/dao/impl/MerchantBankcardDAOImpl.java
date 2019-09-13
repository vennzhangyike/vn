/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantBankcardDAO;
import com.hfepay.scancode.commons.entity.MerchantBankcard;

@Repository(value="merchantBankcardDAO")
@Generated("2016-10-17 13:50:11")
public class MerchantBankcardDAOImpl extends MybatisEntityDAO<MerchantBankcard, String> implements MerchantBankcardDAO {
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
	public long updateByMerchantNo(MerchantBankcard merchantAccounts) {
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
	
	@Override
	public List<MerchantBankcard> findByPhone(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		return getSqlSession().selectList(sqlId("findByPhone"),map);
	}
}