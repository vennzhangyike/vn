/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelWxParamsCondition;
import com.hfepay.scancode.commons.entity.ChannelWxParams;

public interface ChannelWxParamsService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: PagingResult<ChannelWxParams>
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	public PagingResult<ChannelWxParams> findPagingResult(ChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: List<ChannelWxParams>
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	public List<ChannelWxParams> findAll(ChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelWxParams
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	ChannelWxParams findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long insert(ChannelWxParamsCondition channelWxParamsCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long update(ChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long updateByCriteria(ChannelWxParamsCondition channelWxParamsCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long updateStatus(String id,String status);
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public ChannelWxParams findByChannelNo(String channelNo);
	
	/**
	 * @Title: findByCondition
	 * @Description: 条件查找
	 * @author: husain
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public ChannelWxParams findByCondition(ChannelWxParamsCondition channelWxParamsCondition);
}

