/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.dao.AgreementInfoDAO;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.service.operator.AgreementInfoService;

@Service("agreementInfoService")
public class AgreementInfoServiceImpl implements AgreementInfoService {
	
	@Autowired
    private AgreementInfoDAO agreementInfoDAO;
    
    /**
	 * 列表(分页)
	 * @param agreementInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
    @Override
	public PagingResult<AgreementInfo> findPagingResult(AgreementInfoCondition agreementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AgreementInfo.class);
		if(!Strings.isEmpty(agreementInfoCondition.getId())){
			cb.andEQ("id", agreementInfoCondition.getId());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementNo())){
			cb.andEQ("agreementNo", agreementInfoCondition.getAgreementNo());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", agreementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementtype())){
			cb.andEQ("agreementtype", agreementInfoCondition.getAgreementtype());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementcontent())){
			cb.andEQ("agreementcontent", agreementInfoCondition.getAgreementcontent());
		}
		if(null != agreementInfoCondition.getStartDate()){
			cb.andEQ("startDate", agreementInfoCondition.getStartDate());
		}
		if(null != agreementInfoCondition.getEndDate()){
			cb.andEQ("endDate", agreementInfoCondition.getEndDate());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getStatus())){
			cb.andEQ("status", agreementInfoCondition.getStatus());
		}
		if(null != agreementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", agreementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agreementInfoCondition.getOperater())){
			cb.andEQ("operater", agreementInfoCondition.getOperater());
		}

		if(!Strings.isEmpty(agreementInfoCondition.getRemark())){
			cb.andLike("remark", agreementInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp1())){
			cb.andEQ("temp1", agreementInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp2())){
			cb.andEQ("temp2", agreementInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp3())){
			cb.andEQ("temp3", agreementInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp4())){
			cb.andEQ("temp4", agreementInfoCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agreementInfoCondition.getOrderBy())){
			if(agreementInfoCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agreementInfoCondition.getOrderBy().split(",");
				String[] orders = agreementInfoCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agreementInfoCondition.getOrderBy(), Order.valueOf(agreementInfoCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agreementInfoCondition.getFirst()), Long.valueOf(agreementInfoCondition.getLast()));
		return agreementInfoDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param agreementInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public List<AgreementInfo> findAll(AgreementInfoCondition agreementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AgreementInfo.class);
		if(!Strings.isEmpty(agreementInfoCondition.getId())){
			cb.andEQ("id", agreementInfoCondition.getId());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementNo())){
			cb.andEQ("agreementNo", agreementInfoCondition.getAgreementNo());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", agreementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementtype())){
			cb.andEQ("agreementtype", agreementInfoCondition.getAgreementtype());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getAgreementcontent())){
			cb.andEQ("agreementcontent", agreementInfoCondition.getAgreementcontent());
		}
		if(null != agreementInfoCondition.getStartDate()){
			cb.andEQ("startDate", agreementInfoCondition.getStartDate());
		}
		if(null != agreementInfoCondition.getEndDate()){
			cb.andEQ("endDate", agreementInfoCondition.getEndDate());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getStatus())){
			cb.andEQ("status", agreementInfoCondition.getStatus());
		}
		if(null != agreementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", agreementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agreementInfoCondition.getOperater())){
			cb.andEQ("operater", agreementInfoCondition.getOperater());
		}

		if(!Strings.isEmpty(agreementInfoCondition.getRemark())){
			cb.andLike("remark", agreementInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp1())){
			cb.andEQ("temp1", agreementInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp2())){
			cb.andEQ("temp2", agreementInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp3())){
			cb.andEQ("temp3", agreementInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(agreementInfoCondition.getTemp4())){
			cb.andEQ("temp4", agreementInfoCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agreementInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public AgreementInfo findById(String id){
		return agreementInfoDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param agreementInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long insert(AgreementInfoCondition agreementInfoCondition){
		AgreementInfo agreementInfo = new AgreementInfo();
		BeanUtils.copyProperties(agreementInfoCondition, agreementInfo);
		return agreementInfoDAO.insert(agreementInfo);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long deleteById(String id){
		return agreementInfoDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return agreementInfoDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return agreementInfoDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long update(AgreementInfoCondition agreementInfoCondition){
		AgreementInfo agreementInfo = new AgreementInfo();
		BeanUtils.copyProperties(agreementInfoCondition, agreementInfo);
		return agreementInfoDAO.update(agreementInfo);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long updateByCriteria(AgreementInfoCondition agreementInfoCondition){
		AgreementInfo agreementInfo = new AgreementInfo();
		BeanUtils.copyProperties(agreementInfoCondition, agreementInfo);
		CriteriaBuilder cb = Cnd.builder(AgreementInfo.class);
		if(!Strings.isEmpty(agreementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", agreementInfoCondition.getOrganNo());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agreementInfoDAO.updateByCriteria(agreementInfo,buildCriteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	@Override
	public long updateStatus(String id,String status){
		return agreementInfoDAO.updateStatus(id,status);
	}	
}

