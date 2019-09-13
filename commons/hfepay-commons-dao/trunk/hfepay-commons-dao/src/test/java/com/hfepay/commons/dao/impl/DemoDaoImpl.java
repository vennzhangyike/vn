package com.hfepay.commons.dao.impl;

import org.apache.ibatis.session.SqlSession;

import com.hfepay.commons.dao.DemoDao;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.entity.DemoEntity;

@org.springframework.stereotype.Repository

@com.hfepay.commons.base.annotation.Generated("2014-09-26 10:12")
public class DemoDaoImpl extends MybatisEntityDAO<DemoEntity, Long> implements DemoDao {

	public void getSessionDemo(DemoEntity entity) {
		SqlSession session = this.getSqlSession();
		this.insert(entity);
		System.out.println("session Object = "+session);
	}
}
