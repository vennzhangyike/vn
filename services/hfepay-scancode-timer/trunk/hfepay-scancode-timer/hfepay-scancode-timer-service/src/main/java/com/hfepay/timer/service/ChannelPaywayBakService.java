/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.bo.ProfitBo;
import com.hfepay.scancode.commons.condition.ChannelPaywayBakCondition;
import com.hfepay.scancode.commons.entity.ChannelPaywayBak;

public interface ChannelPaywayBakService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: PagingResult<ChannelPaywayBak>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	public PagingResult<ChannelPaywayBak> findPagingResult(ChannelPaywayBakCondition channelPaywayCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: List<ChannelPaywayBak>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	public List<ChannelPaywayBak> findAll(ChannelPaywayBakCondition channelPaywayCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelPaywayBak
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	ChannelPaywayBak findById(String id);
	
	/**
	 * @Title: findByPayCode
	 * @Description: 支付通道编号
	 * @author: Ricky
	 * @param payCode
	 * @param channelNo
	 * @return: ChannelPaywayBak
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	ChannelPaywayBak findByPayCode(String payCode,String channelNo);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long insert(ChannelPaywayBakCondition channelPaywayCondition);

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
	 * @param channelPaywayBakCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long update(ChannelPaywayBakCondition channelPaywayCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	long updateByCriteria(ChannelPaywayBakCondition channelPaywayBakCondition,Criteria criteria);
	
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

	/**
	 * 删除数据
	 * @Title: doTruncateTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doTruncateTable();

	/**
	 * 备份数据
	 * @Title: doBackUpTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doBackUpTable();	
	
}

