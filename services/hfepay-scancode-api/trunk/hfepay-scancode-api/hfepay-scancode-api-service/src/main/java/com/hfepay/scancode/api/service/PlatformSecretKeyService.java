/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.PlatformSecretKeyCondition;
import com.hfepay.scancode.api.entity.PlatformSecretKey;

public interface PlatformSecretKeyService {
	
	/**
	 * 列表(分页)
	 * @param platformSecretKeyCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	public PagingResult<PlatformSecretKey> findPagingResult(PlatformSecretKeyCondition platformSecretKeyCondition);
	
	/**
	 * 列表
	 * @param platformSecretKey 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	public List<PlatformSecretKey> findAll(PlatformSecretKeyCondition platformSecretKeyCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	PlatformSecretKey findById(String id);
	
	/**
	 * 新增
	 * @param platformSecretKeyCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long insert(PlatformSecretKeyCondition platformSecretKeyCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long update(PlatformSecretKeyCondition platformSecretKeyCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long updateByCriteria(PlatformSecretKeyCondition platformSecretKeyCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	long updateStatus(String id,String status);
	
	/**
	 * 根据方法名查询密钥
	 * @param payCode
	 * @return
	 */
	public PlatformSecretKey findByPayCode(String payCode);
	
}

