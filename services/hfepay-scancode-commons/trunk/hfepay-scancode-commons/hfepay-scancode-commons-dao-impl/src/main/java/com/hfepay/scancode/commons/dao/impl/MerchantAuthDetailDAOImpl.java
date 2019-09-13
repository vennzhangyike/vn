/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.dao.MerchantAuthDetailDAO;
import com.hfepay.scancode.commons.entity.MerchantAuthDetail;

@Repository(value="merchantAuthDetailDAO")
@Generated("2016-11-21 17:19:45")
public class MerchantAuthDetailDAOImpl extends MybatisEntityDAO<MerchantAuthDetail, String> implements MerchantAuthDetailDAO {

	@Override
	public List<MerchantAuthDetail> getAuthDetail(MerchantAuthDetailCondition merchantAuthDetailCondition) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(sqlId("getAuthDetail"),merchantAuthDetailCondition);
	}
}