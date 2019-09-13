/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.entity.MerchantQrcode;

@Generated("2016-10-24 10:47:29")
public interface MerchantQrcodeDAO extends EntityDAO<MerchantQrcode, String> {

	MerchantQrcode findQrcodeByIdentityNo(String identityNo);

	List<MerchantQrcode> findAllMerchantQrcode(Criteria buildCriteria);

	PagingResult<MerchantQrcode> findPagingResultSelf(Criteria buildCriteria);

}
