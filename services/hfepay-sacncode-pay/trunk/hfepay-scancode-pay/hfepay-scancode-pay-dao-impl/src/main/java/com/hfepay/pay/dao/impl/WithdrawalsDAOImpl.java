/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.pay.dao.WithdrawalsDAO;
import com.hfepay.scancode.commons.dto.WithDrawsTotalDTO;
import com.hfepay.scancode.commons.entity.Withdrawals;

@Repository(value="withdrawalsDAO")
@Generated("2016-11-30 09:32:37")
public class WithdrawalsDAOImpl extends MybatisEntityDAO<Withdrawals, String> implements WithdrawalsDAO {
	@Override
	public List<WithDrawsTotalDTO> getWithDrawsMoneyTotal(String date,String merchantNo,String payCode) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		
		if (!Strings.isEmpty(payCode)) {
			map.put("payCode", payCode);
		}
		map.put("merchantNo", merchantNo);
		return this.getSqlSession().selectList(sqlId("getWithDrawsMoneyTotal"), map);
	}

	@Override
	public BigDecimal getTotalMoney(String date, String merchantNo) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		map.put("merchantNo", merchantNo);
		
		return this.getSqlSession().selectOne(sqlId("getTotalMoney"), map);
	}
}