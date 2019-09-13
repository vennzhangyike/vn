/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.dao.MerchantQrcodeDAO;
import com.hfepay.scancode.commons.entity.MerchantQrcode;

@Repository(value="merchantQrcodeDAO")
@Generated("2016-10-24 10:47:29")
public class MerchantQrcodeDAOImpl extends MybatisEntityDAO<MerchantQrcode, String> implements MerchantQrcodeDAO {

	@Override
	public MerchantQrcode findQrcodeByIdentityNo(String identityNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(sqlId("findQrcodeByIdentityNo"),identityNo);
	}

	@Override
	public List<MerchantQrcode> findAllMerchantQrcode(Criteria buildCriteria) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("findAllMerchantQrcode"),buildCriteria);
	}
	
	@Override
	public PagingResult<MerchantQrcode> findPagingResultSelf(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return pagingQuery(sqlId("countPagingResultSelf"),sqlId("findPagingResultSelf"),criteria);
	}
}