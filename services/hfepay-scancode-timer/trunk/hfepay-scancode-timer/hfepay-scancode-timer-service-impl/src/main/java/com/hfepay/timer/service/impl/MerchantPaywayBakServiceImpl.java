/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

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
import com.hfepay.scancode.commons.condition.MerchantPaywayBakCondition;
import com.hfepay.scancode.commons.dao.MerchantPaywayBakDAO;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;
import com.hfepay.timer.service.MerchantPaywayBakService;

@Service("merchantPaywayBakService")
public class MerchantPaywayBakServiceImpl implements MerchantPaywayBakService {
	
	@Autowired
    private MerchantPaywayBakDAO merchantPaywayBakDAO;
    
    /**
	 * 列表(分页)
	 * @param merchantPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
    @Override
	public PagingResult<MerchantPaywayBak> findPagingResult(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andLike("payName", merchantPaywayBakCondition.getPayName());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getAcceptStatus())){
			cb.andLike("acceptStatus", merchantPaywayBakCondition.getAcceptStatus());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayBakCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayBakCondition.getNodeSeq());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantPaywayBakCondition.getOrderBy())){
			if(merchantPaywayBakCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantPaywayBakCondition.getOrderBy().split(",");
				String[] orders = merchantPaywayBakCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantPaywayBakCondition.getOrderBy(), Order.valueOf(merchantPaywayBakCondition.getOrder()));
			}
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantPaywayBakCondition.getFirst()), Long.valueOf(merchantPaywayBakCondition.getLast()));
		return merchantPaywayBakDAO.findPagingResult(buildCriteria);
	}
	
    
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public List<MerchantPaywayBak> findAll(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andLike("payName", merchantPaywayBakCondition.getPayName());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayBakCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayBakCondition.getNodeSeq());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayBakDAO.findByCriteria(buildCriteria);
	}

	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long countByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andEQ("payName", merchantPaywayBakCondition.getPayName());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.countByCriteria(buildCriteria);
	}
	
	@Override
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		merchantPaywayBakDAO.doTruncateTable();
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		merchantPaywayBakDAO.doBackUpTable();
	}
	
}

