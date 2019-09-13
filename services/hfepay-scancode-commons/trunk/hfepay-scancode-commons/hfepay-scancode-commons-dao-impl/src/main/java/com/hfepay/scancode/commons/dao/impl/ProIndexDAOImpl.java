package com.hfepay.scancode.commons.dao.impl;

import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ProIndexDAO;
import com.hfepay.scancode.commons.entity.ProIndex;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
public class ProIndexDAOImpl extends MybatisEntityDAO<ProIndex, String> implements ProIndexDAO {

	public void getOrderId(Map<String, Object> map) {
		map.put("outIndex32", -1);
		map.put("outIndex64",-1);
		this.getSqlSession().selectOne("ProIndex.getOrderId", map);
	}


}