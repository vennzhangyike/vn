/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.RemitBankInfo;

@Generated("2016-10-26 14:40:32")
public interface RemitBankInfoDAO extends EntityDAO<RemitBankInfo, Integer> {

	RemitBankInfo getInfoByCardNo(List<String> cardListHead);
}
