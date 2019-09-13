/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.bo.ProfitBo;
import com.hfepay.scancode.commons.condition.ChannelPaywayCondition;
import com.hfepay.scancode.commons.entity.ChannelPayway;

public interface ChannelPaywayService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: PagingResult<ChannelPayway>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	public PagingResult<ChannelPayway> findPagingResult(ChannelPaywayCondition channelPaywayCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: List<ChannelPayway>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	public List<ChannelPayway> findAll(ChannelPaywayCondition channelPaywayCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelPayway
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	ChannelPayway findById(String id);
	
	/**
	 * @Title: findByPayCode
	 * @Description: 支付通道编号
	 * @author: Ricky
	 * @param payCode
	 * @param channelNo
	 * @return: ChannelPayway
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	ChannelPayway findByPayCode(String payCode,String channelNo);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long insert(ChannelPaywayCondition channelPaywayCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long update(ChannelPaywayCondition channelPaywayCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long updateByCriteria(ChannelPaywayCondition channelPaywayCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long updateStatus(String id,String status);

	/**
	 * @Title: getChannelRateDiff
	 * @Description: 渠道费率差
	 * @author: husain
	 * @return
	 * @return: List<ProfitBo>
	 */
	public List<ProfitBo> getChannelRateDiff();	
	
}

