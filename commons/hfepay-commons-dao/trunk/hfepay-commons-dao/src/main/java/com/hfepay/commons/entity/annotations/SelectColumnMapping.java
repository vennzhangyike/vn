package com.hfepay.commons.entity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询列与实体类属性映射，提供在使用Criteria进行查询时，实体可以配置此项，然后根据此配置生成
 * 正确的WHERE条件所要用到的列名。
 * 
 * @author Sam
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SelectColumnMapping {
	/**
	 * 实体的属性
	 * @return
	 */
	String property();

	/**
	 * 实体属性类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class type() default String.class;

	/**
	 * 实体对应的表
	 * @return
	 */
	String table();

	/**
	 * 关联查询时表的别名或者全名
	 * @return
	 */
	String tableAlias();

	/**
	 * 实体属性对应的列
	 * @return
	 */
	String column(); 
}
