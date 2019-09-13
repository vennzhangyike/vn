/**
 * Project Name:hfepay-commons-codegen
 * File Name:Constants.java
 * Package Name:com.hfepay.common.codegen.constants
 * Date:2014-2-8下午06:53:34
 * Copyright (c) 2014, hfepay All Rights Reserved.
 *
 */

package com.hfepay.common.codegen.constants;

/**
 * ClassName:Constants Date: 2014-2-8 下午06:53:34
 * 
 * @author hfepay
 * @version
 * @since JDK 1.6
 * @see
 */
public class Constants {
	/**
	 * generatorConfig.xml文件属性参数配置
	 */
	public static final String PROPERTIES_MULTI_MODULE = "isMultiModule";
	public static final String PROPERTIES_DAO_IMPL_PACKAGE = "daoImplPackage";
	public static final String PROPERTIES_DAO_IMPL_PROJECT = "daoImplProject";
	
	/**
	 * EntityDAO/MybatisEntityDAO路径
	 */
	//单模块
	public static final String ENTITY_DAO_SM = "com.kingnod.core.dao.EntityDAO";
	public static final String MYBATIS_ENTITY_DAO_SM = "com.kingnod.core.dao.MybatisEntityDAO";
	//多模块
	public static final String ENTITY_DAO_MM = "com.hfepay.commons.dao.EntityDAO";
	public static final String MYBATIS_ENTITY_DAO_MM = "com.hfepay.commons.dao.impl.MybatisEntityDAO";
	
	/**
	 * SelectColumnMapping/SelectColumnMappings路径
	 */
	//单模块
	public static final String SELECT_COLUMN_MAPPING_SM = "com.kingnod.core.criteria.annotations.SelectColumnMapping";
	public static final String SELECT_COLUMN_MAPPINGS_SM = "com.kingnod.core.criteria.annotations.SelectColumnMappings";
	//多模块
	public static final String SELECT_COLUMN_MAPPING_MM = "com.hfepay.commons.entity.annotations.SelectColumnMapping";
	public static final String SELECT_COLUMN_MAPPINGS_MM = "com.hfepay.commons.entity.annotations.SelectColumnMappings";
	
	
	/**
	 * IdEntity/RecordStatus路径
	 */
	//单模块
	public static final String ID_ENTITY_SM = "com.kingnod.core.entity.IdEntity";
	public static final String RECORD_STATUS_SM = "com.kingnod.core.entity.RecordStatus";
	//多模块
	public static final String ID_ENTITY_MM = "com.hfepay.commons.entity.IdEntity";
	public static final String RECORD_STATUS_MM = "com.hfepay.commons.entity.RecordStatus";
	
	/**
	 * com.kingnod.core.criteria.Criteria路径
	 */
	//单模块
	public static final String CRITERIA_SM = "com.kingnod.core.criteria.Criteria";
	//多模块
	public static final String CRITERIA_MM = "com.hfepay.commons.criteria.Criteria";
}
