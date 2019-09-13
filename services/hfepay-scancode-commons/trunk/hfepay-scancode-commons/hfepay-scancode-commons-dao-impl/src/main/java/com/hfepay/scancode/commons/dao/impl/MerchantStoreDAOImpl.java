/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantStoreDAO;
import com.hfepay.scancode.commons.entity.MerchantStore;

@Repository(value="merchantStoreDAO")
@Generated("2016-10-21 10:21:58")
public class MerchantStoreDAOImpl extends MybatisEntityDAO<MerchantStore, String> implements MerchantStoreDAO {

	@Override
	public long updateByMerchantNo(MerchantStore store) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(sqlId("updateByMerchantNo"), store);
	}

	@Override
	public List<MerchantStore> findAllMerchantStore(Criteria buildCriteria) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(sqlId("findAllMerchantStore"),buildCriteria);
	}
}