package com.hfepay.commons.mybatis.interceptor;

import java.util.Date;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import com.hfepay.commons.context.InvocationContext;
import com.hfepay.commons.context.InvocationContextHolder;
import com.hfepay.commons.entity.RecordStatus;

/**
 * RecordStatus拦截处理器，检测要更新的实体是否有实现了RecordStatus接口，有的话要动态为要更新的实体 设置记录更新状态信息。
 * 
 * @author Sam
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class RecordStatusHandlerInterceptor extends AbstractInterceptor {

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	
	@SuppressWarnings("unchecked")
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] queryArgs = invocation.getArgs();
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object entity = queryArgs[PARAMETER_INDEX];
		if (entity instanceof RecordStatus) {
			RecordStatus<Long> recordStatus = (RecordStatus<Long>)entity;
			Date currentDate = new Date();
			Long currentUserId = -4L;		
			try{
				InvocationContext invocationContext = InvocationContextHolder.getInvocationContext();
				currentUserId = invocationContext.getUserAccout().getId();
			}catch(Exception e){				
			}

			if (null == recordStatus.getUpdateCount()) {
				recordStatus.setUpdateCount(0);
			} else {
				recordStatus.setUpdateCount(recordStatus.getUpdateCount() + 1);
			}
			
			recordStatus.setUpdaterId(currentUserId);
			recordStatus.setUpdateDate(currentDate);
			if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
				recordStatus.setRecordStatus(RecordStatus.ACTIVE);
				recordStatus.setCreatorId(currentUserId);
				recordStatus.setCreateDate(currentDate);
				recordStatus.setUpdateCount(0);
			}
		}
		return invocation.proceed();
	}
}
