package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.entity.MerchantBankcard;

public interface MerchantBankcardService {
	
	/**
	 * 列表(分页)
	 * @param merchantBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	public PagingResult<MerchantBankcard> findPagingResult(MerchantBankcardCondition merchantBankcardCondition);
	
	/**
	 * 列表
	 * @param merchantBankcard 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	public List<MerchantBankcard> findAll(MerchantBankcardCondition merchantBankcardCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	MerchantBankcard findById(String id);
	
	/**
	 * 新增
	 * @param merchantBankcardCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long insert(MerchantBankcardCondition merchantBankcardCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long update(MerchantBankcardCondition merchantBankcardCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long updateByCriteria(MerchantBankcardCondition merchantBankcardCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	long updateStatus(String id,String status);

	
	/**
	 * 根据商户号查询
	 * @Title: findByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @return
	 * @return: MerchantAccounts
	 */
	public MerchantBankcard findByMerchantNo(String merchantNo);

	/**
	 * 根据商户编号变更信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long updateByMerchantNo(MerchantBankcardCondition condition);

	/**
	 * 用户余额修改
	 * @Title: updateBalance
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @param balance
	 * @return
	 * @return: long
	 */
	public long updateBalance(String merchantNo, BigDecimal balance);

	/**
	 * 商户入驻第三部，
	 * @Title: applyStoreStep3
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long applyStoreStep3(MerchantBankcardCondition condition);

	/** 保存银行卡 
	 * @return */
	Map<String, String> save(MerchantBankcardCondition merchantBankcardCondition) throws Exception;

	/**
	 * 手机号码是否唯一
	 * @Title: findByPhone
	 * @Description: TODO
	 * @author: husain
	 * @param mobile
	 * @return
	 * @return: MerchantBankcard
	 */
	public List<MerchantBankcard> findByPhone(String mobile);	
	
}

