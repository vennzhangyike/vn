package com.hfepay.scancode.service.operator;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.RemitBankInfoCondition;
import com.hfepay.scancode.commons.entity.RemitBankInfo;

public interface RemitBankInfoService {
	
	/**
	 * 列表(分页)
	 * @param remitBankInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	public PagingResult<RemitBankInfo> findPagingResult(RemitBankInfoCondition remitBankInfoCondition);
	
	/**
	 * 列表
	 * @param remitBankInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	public List<RemitBankInfo> findAll(RemitBankInfoCondition remitBankInfoCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	RemitBankInfo findById(Integer id);
	
	/**
	 * 新增
	 * @param remitBankInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long insert(RemitBankInfoCondition remitBankInfoCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long deleteById(Integer id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long update(RemitBankInfoCondition remitBankInfoCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	long updateByCriteria(RemitBankInfoCondition remitBankInfoCondition,Criteria criteria);

	/**
	 * 根据银行卡获取联行号等
	 * @Title: getInfoByCardNo
	 * @Description: TODO
	 * @author: husain
	 * @param bankCard
	 * @return
	 * @return: RemitBankInfo
	 */
	public RemitBankInfo getInfoByCardNo(String bankCard);
	
	
}

