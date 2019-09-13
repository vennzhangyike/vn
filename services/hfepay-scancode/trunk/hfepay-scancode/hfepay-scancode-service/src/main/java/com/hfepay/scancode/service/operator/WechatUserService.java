/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.WechatUserCondition;
import com.hfepay.scancode.commons.entity.WechatUser;

public interface WechatUserService {
	
	/**
	 * 列表(分页)
	 * @param wechatUserCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	public PagingResult<WechatUser> findPagingResult(WechatUserCondition wechatUserCondition);
	
	/**
	 * 列表
	 * @param wechatUser 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	public List<WechatUser> findAll(WechatUserCondition wechatUserCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	WechatUser findById(String id);
	
	/**
	 * 新增
	 * @param wechatUserCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long insert(WechatUserCondition wechatUserCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long update(WechatUserCondition wechatUserCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	long updateByCriteria(WechatUserCondition wechatUserCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param wechatUserCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	public WechatUser findByCondition(WechatUserCondition wechatUserCondition);
	
}

