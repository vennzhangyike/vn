package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

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
import com.hfepay.scancode.commons.condition.RemitBankInfoCondition;
import com.hfepay.scancode.commons.dao.RemitBankInfoDAO;
import com.hfepay.scancode.commons.entity.RemitBankInfo;
import com.hfepay.scancode.service.operator.RemitBankInfoService;

@Service("remitBankInfoService")
public class RemitBankInfoServiceImpl implements RemitBankInfoService {
	
	@Autowired
    private RemitBankInfoDAO remitBankInfoDAO;
    
    /**
	 * 列表(分页)
	 * @param remitBankInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
    @Override
	public PagingResult<RemitBankInfo> findPagingResult(RemitBankInfoCondition remitBankInfoCondition){
		CriteriaBuilder cb = Cnd.builder(RemitBankInfo.class);
		if(null != remitBankInfoCondition.getId()){
			cb.andEQ("id", remitBankInfoCondition.getId());
		}
		if(null != remitBankInfoCondition.getVersion()){
			cb.andEQ("version", remitBankInfoCondition.getVersion());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getBankChannelNo())){
			cb.andEQ("bankChannelNo", remitBankInfoCondition.getBankChannelNo());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getBankName())){
			cb.andEQ("bankName", remitBankInfoCondition.getBankName());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getProvince())){
			cb.andEQ("province", remitBankInfoCondition.getProvince());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getCity())){
			cb.andEQ("city", remitBankInfoCondition.getCity());
		}
		if(null != remitBankInfoCondition.getBankType()){
			cb.andEQ("bankType", remitBankInfoCondition.getBankType());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getClearBankChannelNo())){
			cb.andEQ("clearBankChannelNo", remitBankInfoCondition.getClearBankChannelNo());
		}
		if(null != remitBankInfoCondition.getIsInProvince()){
			cb.andEQ("isInProvince", remitBankInfoCondition.getIsInProvince());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(remitBankInfoCondition.getOrderBy())){
			if(remitBankInfoCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = remitBankInfoCondition.getOrderBy().split(",");
				String[] orders = remitBankInfoCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(remitBankInfoCondition.getOrderBy(), Order.valueOf(remitBankInfoCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(remitBankInfoCondition.getFirst()), Long.valueOf(remitBankInfoCondition.getLast()));
		return remitBankInfoDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param remitBankInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public List<RemitBankInfo> findAll(RemitBankInfoCondition remitBankInfoCondition){
		CriteriaBuilder cb = Cnd.builder(RemitBankInfo.class);
		if(null != remitBankInfoCondition.getId()){
			cb.andEQ("id", remitBankInfoCondition.getId());
		}
		if(null != remitBankInfoCondition.getVersion()){
			cb.andEQ("version", remitBankInfoCondition.getVersion());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getBankChannelNo())){
			cb.andEQ("bankChannelNo", remitBankInfoCondition.getBankChannelNo());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getBankName())){
			cb.andEQ("bankName", remitBankInfoCondition.getBankName());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getProvince())){
			cb.andEQ("province", remitBankInfoCondition.getProvince());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getCity())){
			cb.andEQ("city", remitBankInfoCondition.getCity());
		}
		if(null != remitBankInfoCondition.getBankType()){
			cb.andEQ("bankType", remitBankInfoCondition.getBankType());
		}
		if(!Strings.isEmpty(remitBankInfoCondition.getClearBankChannelNo())){
			cb.andEQ("clearBankChannelNo", remitBankInfoCondition.getClearBankChannelNo());
		}
		if(null != remitBankInfoCondition.getIsInProvince()){
			cb.andEQ("isInProvince", remitBankInfoCondition.getIsInProvince());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return remitBankInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public RemitBankInfo findById(Integer id){
		return remitBankInfoDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param remitBankInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long insert(RemitBankInfoCondition remitBankInfoCondition){
		RemitBankInfo remitBankInfo = new RemitBankInfo();
		BeanUtils.copyProperties(remitBankInfoCondition, remitBankInfo);
		return remitBankInfoDAO.insert(remitBankInfo);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long deleteById(Integer id){
		return remitBankInfoDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return remitBankInfoDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return remitBankInfoDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long update(RemitBankInfoCondition remitBankInfoCondition){
		RemitBankInfo remitBankInfo = new RemitBankInfo();
		BeanUtils.copyProperties(remitBankInfoCondition, remitBankInfo);
		return remitBankInfoDAO.update(remitBankInfo);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 14:40:32
	 */
	@Override
	public long updateByCriteria(RemitBankInfoCondition remitBankInfoCondition,Criteria criteria){
		RemitBankInfo remitBankInfo = new RemitBankInfo();
		BeanUtils.copyProperties(remitBankInfoCondition, remitBankInfo);
		return remitBankInfoDAO.updateByCriteria(remitBankInfo,criteria);
	}
	
	@Override
	public RemitBankInfo getInfoByCardNo(String bankcard) {
		List<String> cardListHead = new ArrayList<>();
		int length = bankcard.length()>=10?10:bankcard.length();//银行卡前2-10位标示
		bankcard = bankcard.substring(0, length);
		System.out.println(bankcard);
		for(int i=2;i<=bankcard.length();i++){
			cardListHead.add(bankcard.substring(0, i));
		}
		return remitBankInfoDAO.getInfoByCardNo(cardListHead);
	}
	
}

