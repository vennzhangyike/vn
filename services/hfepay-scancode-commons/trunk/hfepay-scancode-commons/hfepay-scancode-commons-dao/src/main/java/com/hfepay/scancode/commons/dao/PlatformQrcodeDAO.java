/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.entity.PlatformQrcode;

@Generated("2016-10-14 16:27:56")
public interface PlatformQrcodeDAO extends EntityDAO<PlatformQrcode, String> {
	
	/**
	 * 条件取当前代理商没有用过的二维码
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	public PlatformQrcode findPlatformQrcode(String id);
	
	/**
	 * 生成编码
	 * @param map
	 */
	public void getQrcodeCode(Map<String, String> map);
	
	/**删除商户后更新二维码*/
	long updateByMerchantNo(String merchantNo);

	/**查询重复二维码*/
	List<PlatformQrcode> findRepeatQrcode();

	/**更新重复二维码*/
	void updateRepeatQrcode(PlatformQrcode platformQrcode);
	
	/**
	 * 批量新增
	 * @param qrList
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	void batchInsert(List<PlatformQrcodeCondition> qrList);
}
