package com.hfepay.commons.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hfepay.commons.base.collection.Collections;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.DefaultPagingResult;
import com.hfepay.commons.utils.PagingResult;

public class MybatisDaoSupport extends SqlSessionDaoSupport {	
	protected <T>PagingResult<T> pagingQuery(String countSqlId,String findSqlId,Criteria criteria) {
		if (!criteria.isHasLimit()) {
			List<T> allResult = getSqlSession().selectList(findSqlId, criteria);
			long size = Collections.isNotEmpty(allResult)?allResult.size():0;
			return new DefaultPagingResult<T>(size,allResult);
		}
		Long totalCount = new Long((Integer) getSqlSession().selectOne(countSqlId, criteria));
		RowBounds bounds = new RowBounds(criteria.getFirst().intValue(), criteria.getLast().intValue());
		List<T> result   = getSqlSession().selectList(findSqlId, criteria, bounds);
		return new DefaultPagingResult<T>(totalCount,result);
	}
}
