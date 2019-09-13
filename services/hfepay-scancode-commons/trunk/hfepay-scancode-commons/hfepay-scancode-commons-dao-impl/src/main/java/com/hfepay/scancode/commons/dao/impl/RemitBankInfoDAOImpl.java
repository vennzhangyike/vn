/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.RemitBankInfoDAO;
import com.hfepay.scancode.commons.entity.RemitBankInfo;

@Repository(value="remitBankInfoDAO")
@Generated("2016-10-26 14:40:32")
public class RemitBankInfoDAOImpl extends MybatisEntityDAO<RemitBankInfo, Integer> implements RemitBankInfoDAO {

	@Override
	public RemitBankInfo getInfoByCardNo(List<String> cardListHead) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(sqlId("getInfoByCardNo"),cardListHead);
	}
}