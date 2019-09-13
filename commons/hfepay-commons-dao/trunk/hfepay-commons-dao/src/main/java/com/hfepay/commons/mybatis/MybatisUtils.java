package com.hfepay.commons.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Databases;
import com.hfepay.commons.utils.Databases.DBType;
/**
 * 操作Mybatis内部对象的工具类
 * @author Sam
 *
 */
public class MybatisUtils {

	  
	/**
	 * 复制一个BoundSql.
	 * @param ms
	 * @param boundSql
	 * @param sql
	 * @return
	 */
	public static BoundSql copyBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
		    String prop = mapping.getProperty();
		    if (boundSql.hasAdditionalParameter(prop)) {
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
		    }
		}
		return newBoundSql;
	}
	
	/**
	 * 对某个mybatis的sql进行参数填充，返回的sql可以直接在各种sql工具中直接运行
	 * @param ms mybatis中的某个映射sql
	 * @param parameterObject 传给sql的参数
	 * @return
	 */
	public static String getFilledSql(MappedStatement ms, Object parameterObject) {
		ObjectFactory defaultObjectFactory = new DefaultObjectFactory();
		ObjectWrapperFactory defaultObjectWrapperFactory = new DefaultObjectWrapperFactory();
		
		BoundSql boundSql = ms.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //根据IBatis3中配置的SQL和传进来的参数进行处理生成可在pl/sql中运行的SQL
        if (parameterMappings != null) {
            MetaObject metaObject = parameterObject == null ? null : MetaObject.forObject(parameterObject, defaultObjectFactory, defaultObjectWrapperFactory);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler( parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = MetaObject.forObject(value, defaultObjectFactory, defaultObjectWrapperFactory).getValue( propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (value != null) {
                    	boolean valueIsString = value instanceof String;
                    	if (valueIsString && StringUtils.containsAny(value.toString(), "$")) {
                    		value = ((String)value).replaceAll("\\$","\\\\\\$");
                    	}
                    	if(null != value && value instanceof Date){
                    		if(DBType.MYSQL == Databases.getDbType() ){
                    			value = Dates.format("yyyy-MM-dd HH:mm:ss", (Date)value);
	                        	value = "str_to_date('"+value+"','%Y-%m-%d %H:%i:%s')";
	                    	}else{
	                    		value = Dates.format("yyyy-MM-dd HH:mm:ss", (Date)value);
	                        	value = "to_date('"+value+"','yyyy-MM-dd HH24:mi:ss')";
	                    	}
                        	sql = sql.replaceFirst("\\?", value.toString());
                        }else{
                        	sql = sql.replaceFirst("\\?", valueIsString ? "'" + value + "'" : value.toString());
                        }
                    } else {
                        sql = sql.replaceFirst("\\?", "null");
                        
                    }
                }
            }
        }
        return sql;
	}
	
	/**
	 * 复制一个MappedStatement SQL
	 * @param ms
	 * @param newSqlSource
	 * @return
	 */
	public static MappedStatement copyMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if(ms.getKeyProperties() !=null && ms.getKeyProperties().length >0){
			builder.keyProperty(ms.getKeyProperties()[0]);
		}
		
		//setStatementTimeout()
		builder.timeout(ms.getTimeout());
		
		//setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		
		//setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
	    
		//setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder.build();
	}
	
}
