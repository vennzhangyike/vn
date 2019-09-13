/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.dto.WithDrawsTotalDTO;
import com.hfepay.scancode.commons.entity.Withdrawals;

public interface WithdrawalsService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: PagingResult<Withdrawals>
	 * @throws Exception 
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public PagingResult<Withdrawals> findPagingResult(WithdrawalsCondition withdrawalsCondition) throws Exception;
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: List<Withdrawals>
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public List<Withdrawals> findAll(WithdrawalsCondition withdrawalsCondition);
	
	/**
	 * 查询所有初始化的交易
	 * @param withdrawalsCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	public List<Withdrawals> findAllByInit(WithdrawalsCondition withdrawalsCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: Withdrawals
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	Withdrawals findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long insert(WithdrawalsCondition withdrawalsCondition);
	
	/**
	 * @Title: createWithdraws
	 * @Description: 新增
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public Withdrawals createWithdraws(WithdrawalsCondition withdrawalsCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long update(WithdrawalsCondition withdrawalsCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long updateByCriteria(WithdrawalsCondition withdrawalsCondition,Criteria criteria);
	
	/**
	 * @Title: getWalleInfo
	 * @Description: 查询钱包
	 * @author: Ricky
	 * @param merchantNo
	 * @return: Map<String, String>
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	Map<String, String> getWalleInfo(String merchantNo) throws Exception;

	/**
	 * 商户提现金额
	 * @Title: getDealMoney
	 * @Description: TODO
	 * @author: husain
	 * @param identityNo
	 * @return
	 * @return: List<WithDrawsTotalDTO>
	 */
	public List<WithDrawsTotalDTO> getWithDrawsMoneyTotal(String merchantNo,String payCode);
	
	/**
	 * @Title: getTotalMoney
	 * @Description: 统计提现金额
	 * @author: husain
	 * @param type:D天，M月，Y年
	 * @param date：YYYY-MM-DD
	 * @return: BigDecimal
	 */
	public BigDecimal getTotalMoney(String date,String type,String merchantNo);

	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatistics(WithdrawalsCondition withdrawalsCondition);
     
	
	/**
	 * 
	 * @author liushuyu
	 * Desc 按条件查询 
	 * @param withdrawals
	 * @return
	 */
	public Withdrawals queryWithdrawals(Withdrawals withdrawals);
	
}

