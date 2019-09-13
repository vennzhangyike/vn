/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.api.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;

public interface ApiChannelWxParamsService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: PagingResult<ApiChannelWxParams>
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	public PagingResult<ApiChannelWxParams> findPagingResult(ApiChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: List<ApiChannelWxParams>
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	public List<ApiChannelWxParams> findAll(ApiChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ApiChannelWxParams
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	ApiChannelWxParams findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long insert(ApiChannelWxParamsCondition channelWxParamsCondition);

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
	long update(ApiChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelWxParamsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long updateByCriteria(ApiChannelWxParamsCondition channelWxParamsCondition,Criteria criteria);
	
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
	public ApiChannelWxParams findByChannelNo(String channelNo);
	
	/**
	 * @Title: findByCondition
	 * @Description: 条件查找
	 * @author: husain
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public ApiChannelWxParams findByCondition(ApiChannelWxParamsCondition channelWxParamsCondition);
	
	/**
	 * @Title: getFromRedis
	 * @Description: 条件查找
	 * @author: husain
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public ApiChannelWxParams getFromRedis(ApiChannelWxParamsCondition channelWxParamsCondition) throws Exception;
	
	/**
	 * @Title: getWechatConfigOrganNo
	 * @Description: 根据机构编号查询最近的配置有公众号的微信配置
	 * @author: husain
	 * @param organNo
	 * @return
	 * @return: DataNodeVO
	 */
	public DataNodeVO getWechatConfigEntity(String organNo);
}

