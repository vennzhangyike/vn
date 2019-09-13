/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.api.dao.ApiChannelWxParamsDAO;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;

@Repository(value="apiChannelWxParamsDAO")
@Generated("2016-11-07 14:47:12")
public class ApiChannelWxParamsDAOImpl extends MybatisEntityDAO<ApiChannelWxParams, String> implements ApiChannelWxParamsDAO {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}	
	
	@Override
	public DataNodeVO getWechatConfigEntity(List<String> parentsSeq) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("parentsSeq", parentsSeq);
		return this.getSqlSession().selectOne(sqlId("getWechatConfigEntity"),map);
	}

	@Override
	public String getSeq(String organNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(sqlId("getSeq"),organNo);
	}
	
}