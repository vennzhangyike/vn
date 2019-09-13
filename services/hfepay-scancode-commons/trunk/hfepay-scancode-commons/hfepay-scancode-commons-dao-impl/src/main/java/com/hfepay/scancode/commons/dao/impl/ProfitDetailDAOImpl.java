/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ProfitDetailDAO;
import com.hfepay.scancode.commons.entity.ProfitDetail;

@Repository(value="profitDetailDAO")
@Generated("2017-01-11 14:21:58")
public class ProfitDetailDAOImpl extends MybatisEntityDAO<ProfitDetail, String> implements ProfitDetailDAO {

	@Override
	public void insertBatch(List<ProfitDetail> listDetail) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("insertBatch"),listDetail);
	}
}