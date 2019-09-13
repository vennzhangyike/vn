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
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.pay.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dto.OrderPayStaticDTO;
import com.hfepay.scancode.commons.dto.OrderPayTotalDTO;
import com.hfepay.scancode.commons.entity.OrderPay;

@Repository(value="orderPayDAO")
@Generated("2016-10-17 11:37:40")
public class OrderPayDAOImpl extends MybatisEntityDAO<OrderPay, String> implements OrderPayDAO {

	@Override
	public BigDecimal getTotalMoney(String date,String cashierNo,String identityFlag) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		if(identityFlag.equals("4")){
			map.put("cashierNo", cashierNo);
		}else if(identityFlag.equals("3")){
			map.put("merchantNo", cashierNo);
		}
		
		return this.getSqlSession().selectOne(sqlId("getTotalMoney"), map);
	}

	@Override
	public List<OrderPayTotalDTO> getDealMoney(String date, String identityNo,String flag) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		if(flag.equals("4")){
			map.put("cashierNo", identityNo);
		}else if(flag.equals("3")){
			map.put("merchantNo", identityNo);
		}
		return this.getSqlSession().selectList(sqlId("getDealMoney"), map);
	}
	
	@Override
	public Map<String, BigDecimal> getDealMoneyTotal(String date, String identityNo,String flag) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		if(flag.equals("4")){
			map.put("cashierNo", identityNo);
		}else if(flag.equals("3")){
			map.put("merchantNo", identityNo);
		}
		return this.getSqlSession().selectOne(sqlId("getDealMoneyTotal"), map);
	}
	
	
	
	@Override
	public List<OrderPayStaticDTO> getSumOrderAmt(String date,String type) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		map.put("type", type);
		return this.getSqlSession().selectList(sqlId("getSumOrderAmt"), map);
	}
	
	@Override
	public List<OrderPayStaticDTO> getWithDrawTimes(String date, String type) {
		Map<String,String> map = new HashMap<>();
		map.put("date", date);
		map.put("type", type);
		return this.getSqlSession().selectList(sqlId("getWithDrawTimes"), map);
	}
	
}