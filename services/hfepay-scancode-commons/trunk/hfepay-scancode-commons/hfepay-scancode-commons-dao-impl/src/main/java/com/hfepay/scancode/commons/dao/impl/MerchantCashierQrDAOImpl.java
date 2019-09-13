/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.dao.MerchantCashierQrDAO;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;

@Repository(value="merchantCashierQrDAO")
@Generated("2016-11-10 16:13:31")
public class MerchantCashierQrDAOImpl extends MybatisEntityDAO<MerchantCashierQr, String> implements MerchantCashierQrDAO {
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
	public void bindCasiher(List<MerchantCashierQr> list) {
		// TODO Auto-generated method stub
		getSqlSession().insert(sqlId("bindCasiher"),list);
	}
	
	@Override
	public void deleteByCashier(String cashierNo) {
		// TODO Auto-generated method stub
		getSqlSession().update(sqlId("deleteByCashier"),cashierNo);
	}
	
	@Override
	public PagingResult<MerchantCashierQr> findPagingResultSelf(Criteria buildCriteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			buildCriteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return pagingQuery(sqlId("selfCountByCriteria"),sqlId("selfFindByCriteria"),buildCriteria);
	}
	
	@Override
	public long unbind(String cashierNo) {
		return getSqlSession().update(sqlId("unbind"),cashierNo);
	}
}