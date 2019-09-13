package com.hfepay.commons.mybatis.interceptor;



import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.jdbc.Dialect;
import com.hfepay.commons.mybatis.MybatisUtils;

/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 
 * @author badqiu
 * @author Sam
 */

@Intercepts({
	@Signature( type= Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PagingHandlerInterceptor extends AbstractInterceptor{
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	
	Dialect dialect;
	
	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(final Object[] queryArgs) {
		//queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		
		if(parameter instanceof Criteria && 
				dialect.supportsLimit() && 
				(offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			Criteria criteria = (Criteria)parameter;
			if (criteria.isHasLimit()) {
				BoundSql boundSql = ms.getBoundSql(parameter);
				String sql = boundSql.getSql().trim();
				if (dialect.supportsLimitOffset()) {
					sql = dialect.getLimitString(sql, offset, limit);
					offset = RowBounds.NO_ROW_OFFSET;
				} else {
					sql = dialect.getLimitString(sql, 0, limit);
				}
				limit = RowBounds.NO_ROW_LIMIT;
				
				queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset,limit);
				
				BoundSql newBoundSql = MybatisUtils.copyBoundSql(ms, boundSql, sql);
				
				MappedStatement newMs = MybatisUtils.copyMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
				queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			}
		}
	}

	public void afterSetProperties() {
		String dialectClass = properties.getProperty("dialectClass");
		try {
			dialect = (Dialect)Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:"+dialectClass,e);
		} 
	}
	
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
	
}
