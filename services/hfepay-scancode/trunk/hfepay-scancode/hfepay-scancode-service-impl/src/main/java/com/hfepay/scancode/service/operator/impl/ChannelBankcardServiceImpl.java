/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelBankcardCondition;
import com.hfepay.scancode.commons.dao.ChannelBankcardDAO;
import com.hfepay.scancode.commons.entity.ChannelBankcard;
import com.hfepay.scancode.service.operator.ChannelBankcardService;

@Service("channelBankcardService")
public class ChannelBankcardServiceImpl implements ChannelBankcardService {
	
	@Autowired
    private ChannelBankcardDAO channelBankcardDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: PagingResult<ChannelBankcard>
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
    @Override
	public PagingResult<ChannelBankcard> findPagingResult(ChannelBankcardCondition channelBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelBankcard.class);
		if(!Strings.isEmpty(channelBankcardCondition.getId())){
			cb.andEQ("id", channelBankcardCondition.getId());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getChannelNo())){
			cb.andEQ("channelNo", channelBankcardCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getChannelName())){
			cb.andLike("channelName", channelBankcardCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", channelBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankName())){
			cb.andEQ("bankName", channelBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankCard())){
			cb.andLike("bankCard", channelBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getIdCard())){
			cb.andLike("idCard", channelBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getName())){
			cb.andLike("name", channelBankcardCondition.getName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getMobile())){
			cb.andLike("mobile", channelBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", channelBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getAccountType())){
			cb.andEQ("accountType", channelBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getStatus())){
			cb.andEQ("status", channelBankcardCondition.getStatus());
		}
		if(null != channelBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", channelBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelBankcardCondition.getOperator())){
			cb.andEQ("operator", channelBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(channelBankcardCondition.getRemark())){
			cb.andLike("remark", channelBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp1())){
			cb.andEQ("temp1", channelBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp2())){
			cb.andEQ("temp2", channelBankcardCondition.getTemp2());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp3())){
			cb.andEQ("temp3", channelBankcardCondition.getTemp3());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp4())){
			cb.andEQ("temp4", channelBankcardCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelBankcardCondition.getFirst()), Long.valueOf(channelBankcardCondition.getLast()));
		return channelBankcardDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: List<ChannelBankcard>
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public List<ChannelBankcard> findAll(ChannelBankcardCondition channelBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelBankcard.class);
		if(!Strings.isEmpty(channelBankcardCondition.getId())){
			cb.andEQ("id", channelBankcardCondition.getId());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getChannelNo())){
			cb.andEQ("channelNo", channelBankcardCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getChannelName())){
			cb.andLike("channelName", channelBankcardCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", channelBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankName())){
			cb.andEQ("bankName", channelBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getBankCard())){
			cb.andEQ("bankCard", channelBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getIdCard())){
			cb.andEQ("idCard", channelBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getName())){
			cb.andEQ("name", channelBankcardCondition.getName());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getMobile())){
			cb.andEQ("mobile", channelBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", channelBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getAccountType())){
			cb.andEQ("accountType", channelBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getStatus())){
			cb.andEQ("status", channelBankcardCondition.getStatus());
		}
		if(null != channelBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", channelBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelBankcardCondition.getOperator())){
			cb.andEQ("operator", channelBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(channelBankcardCondition.getRemark())){
			cb.andLike("remark", channelBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp1())){
			cb.andEQ("temp1", channelBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp2())){
			cb.andEQ("temp2", channelBankcardCondition.getTemp2());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp3())){
			cb.andEQ("temp3", channelBankcardCondition.getTemp3());
		}
		if(!Strings.isEmpty(channelBankcardCondition.getTemp4())){
			cb.andEQ("temp4", channelBankcardCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelBankcardDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelBankcard
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public ChannelBankcard findById(String id){
		return channelBankcardDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long insert(ChannelBankcardCondition channelBankcardCondition){
		ChannelBankcard channelBankcard = new ChannelBankcard();
		BeanUtils.copyProperties(channelBankcardCondition, channelBankcard);
		return channelBankcardDAO.insert(channelBankcard);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long deleteById(String id){
		return channelBankcardDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelBankcardDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelBankcardDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long update(ChannelBankcardCondition channelBankcardCondition){
		ChannelBankcard channelBankcard = new ChannelBankcard();
		BeanUtils.copyProperties(channelBankcardCondition, channelBankcard);
		channelBankcard.setUpdateTime(new Date());
		return channelBankcardDAO.update(channelBankcard);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelBankcardCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long updateByCriteria(ChannelBankcardCondition channelBankcardCondition,Criteria criteria){
		ChannelBankcard channelBankcard = new ChannelBankcard();
		BeanUtils.copyProperties(channelBankcardCondition, channelBankcard);
		return channelBankcardDAO.updateByCriteria(channelBankcard,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelBankcardDAO.updateStatus(id,status);
	}	
	
	@Override
	public ChannelBankcard findByChannelNo(String channelNo) {
		CriteriaBuilder cb = Cnd.builder(ChannelBankcard.class);
		cb.andEQ("channelNo", channelNo);
		cb.andEQ("status", "1");
		Criteria buildCriteria = cb.buildCriteria();
		return channelBankcardDAO.findOneByCriteria(buildCriteria);
	}
}

