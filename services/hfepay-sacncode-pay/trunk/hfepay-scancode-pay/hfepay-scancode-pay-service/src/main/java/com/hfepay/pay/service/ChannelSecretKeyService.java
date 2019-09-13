package com.hfepay.pay.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelSecretKeyCondition;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;

/** 
 * @author lemon
 * @Date 2016年9月13日 下午7:52:22 
 */
public interface ChannelSecretKeyService {

	public ChannelSecretKey query(ChannelSecretKeyCondition condition);
	

	/**
	 * 列表(分页)
	 * @param channelSecretKeyCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	public PagingResult<ChannelSecretKey> findPagingResult(ChannelSecretKeyCondition channelSecretKeyCondition);
	
	/**
	 * 列表
	 * @param channelSecretKey 
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	public List<ChannelSecretKey> findAll(ChannelSecretKeyCondition channelSecretKeyCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	ChannelSecretKey findById(String id);
	
	/**
	 * 新增
	 * @param channelSecretKeyCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long insert(ChannelSecretKeyCondition channelSecretKeyCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long update(ChannelSecretKeyCondition channelSecretKeyCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	long updateByCriteria(ChannelSecretKeyCondition channelSecretKeyCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-24 17:41:30
	 */
	long updateStatus(String id,String status);	
}
