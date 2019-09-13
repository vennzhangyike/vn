/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantAccountsCondition;
import com.hfepay.scancode.commons.dao.MerchantAccountsDAO;
import com.hfepay.scancode.commons.entity.MerchantAccounts;
import com.hfepay.scancode.service.operator.MerchantAccountsService;

@Service("merchantAccountsService")
public class MerchantAccountsServiceImpl implements MerchantAccountsService {
	
	@Autowired
    private MerchantAccountsDAO merchantAccountsDAO;
    
    /**
	 * 列表(分页)
	 * @param merchantAccountsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
    @Override
	public PagingResult<MerchantAccounts> findPagingResult(MerchantAccountsCondition merchantAccountsCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantAccounts.class);
		if(!Strings.isEmpty(merchantAccountsCondition.getId())){
			cb.andEQ("id", merchantAccountsCondition.getId());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantAccountsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getChannelName())){
			cb.andLike("channelName", merchantAccountsCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantAccountsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMerchantName())){
			cb.andLike("merchantName", merchantAccountsCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIdCard())){
			cb.andEQ("idCard", merchantAccountsCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getName())){
			cb.andEQ("name", merchantAccountsCondition.getName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankCode())){
			cb.andEQ("bankCode", merchantAccountsCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankName())){
			cb.andEQ("bankName", merchantAccountsCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankCard())){
			cb.andEQ("bankCard", merchantAccountsCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankNo())){
			cb.andEQ("bankNo", merchantAccountsCondition.getBankNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMobile())){
			cb.andEQ("mobile", merchantAccountsCondition.getMobile());
		}
		if(null != merchantAccountsCondition.getBalance()){
			cb.andEQ("balance", merchantAccountsCondition.getBalance());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantAccountsCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIsFrozen())){
			cb.andEQ("isFrozen", merchantAccountsCondition.getIsFrozen());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getStatus())){
			cb.andEQ("status", merchantAccountsCondition.getStatus());
		}
		if(null != merchantAccountsCondition.getCreateTime()){
			cb.andEQ("createTime", merchantAccountsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantAccountsCondition.getOperater())){
			cb.andEQ("operater", merchantAccountsCondition.getOperater());
		}

		if(!Strings.isEmpty(merchantAccountsCondition.getRemark())){
			cb.andLike("remark", merchantAccountsCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp1())){
			cb.andEQ("temp1", merchantAccountsCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp2())){
			cb.andEQ("temp2", merchantAccountsCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp3())){
			cb.andEQ("temp3", merchantAccountsCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp4())){
			cb.andEQ("temp4", merchantAccountsCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantAccountsCondition.getFirst()), Long.valueOf(merchantAccountsCondition.getLast()));
		return merchantAccountsDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantAccounts 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public List<MerchantAccounts> findAll(MerchantAccountsCondition merchantAccountsCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantAccounts.class);
		if(!Strings.isEmpty(merchantAccountsCondition.getId())){
			cb.andEQ("id", merchantAccountsCondition.getId());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantAccountsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getChannelName())){
			cb.andLike("channelName", merchantAccountsCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantAccountsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMerchantName())){
			cb.andLike("merchantName", merchantAccountsCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIdCard())){
			cb.andEQ("idCard", merchantAccountsCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getName())){
			cb.andEQ("name", merchantAccountsCondition.getName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankCode())){
			cb.andEQ("bankCode", merchantAccountsCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankName())){
			cb.andEQ("bankName", merchantAccountsCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankCard())){
			cb.andEQ("bankCard", merchantAccountsCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getBankNo())){
			cb.andEQ("bankNo", merchantAccountsCondition.getBankNo());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getMobile())){
			cb.andEQ("mobile", merchantAccountsCondition.getMobile());
		}
		if(null != merchantAccountsCondition.getBalance()){
			cb.andEQ("balance", merchantAccountsCondition.getBalance());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantAccountsCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getIsFrozen())){
			cb.andEQ("isFrozen", merchantAccountsCondition.getIsFrozen());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getStatus())){
			cb.andEQ("status", merchantAccountsCondition.getStatus());
		}
		if(null != merchantAccountsCondition.getCreateTime()){
			cb.andEQ("createTime", merchantAccountsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantAccountsCondition.getOperater())){
			cb.andEQ("operater", merchantAccountsCondition.getOperater());
		}

		if(!Strings.isEmpty(merchantAccountsCondition.getRemark())){
			cb.andLike("remark", merchantAccountsCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp1())){
			cb.andEQ("temp1", merchantAccountsCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp2())){
			cb.andEQ("temp2", merchantAccountsCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp3())){
			cb.andEQ("temp3", merchantAccountsCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantAccountsCondition.getTemp4())){
			cb.andEQ("temp4", merchantAccountsCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantAccountsDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public MerchantAccounts findById(String id){
		return merchantAccountsDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantAccountsCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long insert(MerchantAccountsCondition merchantAccountsCondition){
		MerchantAccounts merchantAccounts = new MerchantAccounts();
		BeanUtils.copyProperties(merchantAccountsCondition, merchantAccounts);
		if(Strings.isEmpty(merchantAccountsCondition.getId())){
			merchantAccounts.setId(Strings.getUUID());
		}
		return merchantAccountsDAO.insert(merchantAccounts);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long deleteById(String id){
		return merchantAccountsDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantAccountsDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantAccountsDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long update(MerchantAccountsCondition merchantAccountsCondition){
		MerchantAccounts merchantAccounts = new MerchantAccounts();
		BeanUtils.copyProperties(merchantAccountsCondition, merchantAccounts);
		return merchantAccountsDAO.update(merchantAccounts);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long updateByCriteria(MerchantAccountsCondition merchantAccountsCondition,Criteria criteria){
		MerchantAccounts merchantAccounts = new MerchantAccounts();
		BeanUtils.copyProperties(merchantAccountsCondition, merchantAccounts);
		return merchantAccountsDAO.updateByCriteria(merchantAccounts,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantAccountsDAO.updateStatus(id,status);
	}	
	
	@Override
	public MerchantAccounts findByMerchantNo(String merchantNo) {
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantAccounts.class);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantAccountsDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long updateByMerchantNo(MerchantAccountsCondition condition) {
		MerchantAccounts merchantAccounts = new MerchantAccounts();
		BeanUtils.copyProperties(condition, merchantAccounts);
		return merchantAccountsDAO.updateByMerchantNo(merchantAccounts);
	}
	
	@Override
	public long updateBalance(String merchantNo, BigDecimal balance) {
		// TODO Auto-generated method stub
		return merchantAccountsDAO.updateBalance(merchantNo,balance);
	}
	
	@Override
	public long applyStoreStep3(MerchantAccountsCondition condition) {
		MerchantAccounts merchantAccounts = new MerchantAccounts();
		MerchantAccounts existBean = findByMerchantNo(condition.getMerchantNo());
		condition.setStatus("1");
		long result = 0L;
		if(existBean==null){//不存在插入
			BeanUtils.copyProperties(condition, merchantAccounts);
			merchantAccounts.setId(Strings.getUUID());
			merchantAccounts.setCreateTime(new Date());
			result = merchantAccountsDAO.insert(merchantAccounts);
		}else{
			BeanUtils.copyProperties(condition, merchantAccounts);
			merchantAccounts.setId(existBean.getId());
			merchantAccounts.setMerchantNo(condition.getMerchantNo());
			merchantAccounts.setUpdateTime(new Date());
			result = merchantAccountsDAO.update(merchantAccounts);
		}
		
		return result;
	}
}

