/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantStore;

@Generated("2016-10-21 10:21:58")
public interface MerchantStoreDAO extends EntityDAO<MerchantStore, String> {

	long updateByMerchantNo(MerchantStore store);

	List<MerchantStore> findAllMerchantStore(Criteria buildCriteria);

}
