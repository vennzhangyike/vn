/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelBankcardCondition;
import com.hfepay.scancode.commons.entity.ChannelBankcard;

public interface ChannelBankcardService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: PagingResult<ChannelBankcard>
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	public PagingResult<ChannelBankcard> findPagingResult(ChannelBankcardCondition channelBankcardCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: List<ChannelBankcard>
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	public List<ChannelBankcard> findAll(ChannelBankcardCondition channelBankcardCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelBankcard
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	ChannelBankcard findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long insert(ChannelBankcardCondition channelBankcardCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long update(ChannelBankcardCondition channelBankcardCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long updateByCriteria(ChannelBankcardCondition channelBankcardCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 状态更新
	 * @author: husain
	 * @param channelNo
	 * @return: ChannelBankcard
	 */
	ChannelBankcard findByChannelNo(String channelNo);
}

