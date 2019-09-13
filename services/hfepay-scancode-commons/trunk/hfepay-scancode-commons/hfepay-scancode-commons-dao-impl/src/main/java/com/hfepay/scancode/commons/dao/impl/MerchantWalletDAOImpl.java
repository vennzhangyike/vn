/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantWalletDAO;
import com.hfepay.scancode.commons.entity.MerchantWallet;

@Repository(value="merchantWalletDAO")
@Generated("2016-10-18 18:02:16")
public class MerchantWalletDAOImpl extends MybatisEntityDAO<MerchantWallet, String> implements MerchantWalletDAO {
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
	public long updateByMerchantNo(MerchantWallet merchantWallet) {
		// TODO Auto-generated method stub
		return getSqlSession().update(sqlId("updateByMerchantNo"),merchantWallet);
	}	
	
	@Override
	public long updateBalanceByMerchantNo(MerchantWallet merchantWalletCondition) {
		return getSqlSession().update(sqlId("updateBalanceByMerchantNo"),merchantWalletCondition);
	}
}