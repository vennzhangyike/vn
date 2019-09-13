/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.dao.PlatformQrcodeDAO;
import com.hfepay.scancode.commons.entity.PlatformQrcode;

@Repository(value="platformQrcodeDAO")
@Generated("2016-10-14 16:27:56")
public class PlatformQrcodeDAOImpl extends MybatisEntityDAO<PlatformQrcode, String> implements PlatformQrcodeDAO {

	@Override
	public PlatformQrcode findPlatformQrcode(String id) {
		return getSqlSession().selectOne(sqlId("findPlatformQrcode"),id);
	}
	
	@Override
	public void getQrcodeCode(Map<String, String> map) {
		getSqlSession().selectOne("PlatformQrcode.getQrcodeCode", map);
	}
	
	@Override
	public long updateByMerchantNo(String merchantNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		return getSqlSession().update(sqlId("updateByMerchantNo"),map);
	}
	
	/**查询重复二维码*/
	@Override
	public List<PlatformQrcode> findRepeatQrcode() {
		return getSqlSession().selectList("PlatformQrcode.findRepeatQrcode");
	}
	
	/**更新重复二维码*/
	@Override
	public void updateRepeatQrcode(PlatformQrcode platformQrcode) {
		getSqlSession().update("PlatformQrcode.updateRepeatQrcode", platformQrcode);
	}
	
	/**
	 * 批量新增
	 * @param qrList
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public void batchInsert(List<PlatformQrcodeCondition> qrList){
		 getSqlSession().insert(sqlId("batchInsert"), qrList);
	}
	
	
}