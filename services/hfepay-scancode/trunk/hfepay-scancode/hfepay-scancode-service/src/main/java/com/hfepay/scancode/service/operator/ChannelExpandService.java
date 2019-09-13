/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.entity.ChannelExpand;

public interface ChannelExpandService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: PagingResult<ChannelExpand>
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public PagingResult<ChannelExpand> findPagingResult(ChannelExpandCondition channelExpandCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: List<ChannelExpand>
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public List<ChannelExpand> findAll(ChannelExpandCondition channelExpandCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	ChannelExpand findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long insert(ChannelExpandCondition channelExpandCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long update(ChannelExpandCondition channelExpandCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long updateByCriteria(ChannelExpandCondition channelExpandCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * @Title: nextCode
	 * @Description: 生成渠道编码
	 * @author: Ricky
	 * @return: String
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public String nextCode();
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public ChannelExpand findByChannelNo(String channelNo);
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道二级域名查找
	 * @author: Ricky
	 * @param channelCode
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public ChannelExpand findByChannelCode(String channelCode);

	/**@Override
	 * @Title: save
	 * @Description: 保存
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: 
	 * @throws Exception 
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	void save(ChannelExpandCondition channelExpandCondition) throws Exception;

	/**
	 * 查询单个渠道信息
	 * @Title: findByCondition
	 * @Description: TODO
	 * @author: husain
	 * @param channelExpandCondition
	 * @return
	 * @return: ChannelExpand
	 */
	public ChannelExpand findByCondition(ChannelExpandCondition channelExpandCondition);

	/**设置渠道redis
	 * @throws Exception */
	void setChannelRedis() throws Exception;
}

