/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ProfitDetail;

@Generated("2017-01-11 14:21:58")
public interface ProfitDetailDAO extends EntityDAO<ProfitDetail, String> {

	void insertBatch(List<ProfitDetail> listDetail);
}
