/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.UserSmsCondition;
import com.hfepay.scancode.commons.entity.UserSms;

public interface UserSmsService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: PagingResult<UserSms>
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	public PagingResult<UserSms> findPagingResult(UserSmsCondition userSmsCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: List<UserSms>
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	public List<UserSms> findAll(UserSmsCondition userSmsCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: UserSms
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	UserSms findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long insert(UserSmsCondition userSmsCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long update(UserSmsCondition userSmsCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param userSmsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	long updateByCriteria(UserSmsCondition userSmsCondition,Criteria criteria);

////	/** 商户发送短信并记录*/
////	boolean sendSms(String content, String mobile, String merchantNo) throws Exception;
//	
//	public Map<String, Integer> getSmsCountDetail(UserSmsCondition userSmsCondition);
//
//	/** 发送短信并记录*/
//	boolean sendSms(String content, String mobile, String channelNo, String agentNo, String merchantNo)
//			throws Exception;
}

