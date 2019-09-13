/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.bo.AdviertismentBo;
import com.hfepay.scancode.commons.condition.AdviertisementInfoCondition;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;

public interface AdviertisementInfoService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: PagingResult<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	public PagingResult<AdviertisementInfo> findPagingResult(AdviertisementInfoCondition adviertisementInfoCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: List<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	public List<AdviertisementInfo> findAll(AdviertisementInfoCondition adviertisementInfoCondition);
	
	/**
	 * @Title: findByOrgan
	 * @Description: 列表
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: List<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	public List<AdviertisementInfo> findByOrgan(AdviertisementInfoCondition adviertisementInfoCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: AdviertisementInfo
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	AdviertisementInfo findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long insert(AdviertisementInfoCondition adviertisementInfoCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long update(AdviertisementInfoCondition adviertisementInfoCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long updateByCriteria(AdviertisementInfoCondition adviertisementInfoCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	long updateStatus(String id,String status);

	/** 获取机构列表 */
	List<AdviertisementInfo> getOrganList(Map<String, String> map);

	/**
	 * 生成编码
	 */
	String getAdviertNo();

	/** 广告信息审核 */
	public void auditPlatform(AdviertisementInfoCondition adviertisementInfoCondition,
			AuditLogCondition auditLogCondition) throws Exception;
	
	public List<AdviertisementInfo> getAdviertisInfoByUser(AdviertismentBo adviertismentBo);
	
}

