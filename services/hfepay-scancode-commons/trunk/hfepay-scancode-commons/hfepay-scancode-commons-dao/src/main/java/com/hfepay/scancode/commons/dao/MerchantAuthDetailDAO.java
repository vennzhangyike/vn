/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.entity.MerchantAuthDetail;

@Generated("2016-11-21 17:19:45")
public interface MerchantAuthDetailDAO extends EntityDAO<MerchantAuthDetail, String> {

	List<MerchantAuthDetail> getAuthDetail(MerchantAuthDetailCondition merchantAuthDetailCondition);
}
