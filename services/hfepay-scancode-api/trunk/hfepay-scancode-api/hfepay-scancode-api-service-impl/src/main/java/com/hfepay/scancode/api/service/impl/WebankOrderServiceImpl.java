/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.service.impl;

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
import com.hfepay.scancode.api.condition.WebankOrderCondition;
import com.hfepay.scancode.api.dao.WebankOrderDAO;
import com.hfepay.scancode.api.entity.WebankOrder;
import com.hfepay.scancode.api.service.WebankOrderService;

@Service("webankOrderService")
public class WebankOrderServiceImpl implements WebankOrderService {
	
	@Autowired
    private WebankOrderDAO webankOrderDAO;
    
    /**
	 * 列表(分页)
	 * @param webankOrderCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
    @Override
	public PagingResult<WebankOrder> findPagingResult(WebankOrderCondition webankOrderCondition){
		CriteriaBuilder cb = Cnd.builder(WebankOrder.class);
		if(!Strings.isEmpty(webankOrderCondition.getId())){
			cb.andEQ("id", webankOrderCondition.getId());
		}
		if(!Strings.isEmpty(webankOrderCondition.getMerchantId())){
			cb.andEQ("merchantId", webankOrderCondition.getMerchantId());
		}
		if(!Strings.isEmpty(webankOrderCondition.getTradeNo())){
			cb.andEQ("tradeNo", webankOrderCondition.getTradeNo());
		}
		if(null != webankOrderCondition.getTradeAmt()){
			cb.andEQ("tradeAmt", webankOrderCondition.getTradeAmt());
		}
		if(null != webankOrderCondition.getCreateTime()){
			cb.andEQ("createTime", webankOrderCondition.getCreateTime());
		}
		if(!Strings.isEmpty(webankOrderCondition.getOperator())){
			cb.andEQ("operator", webankOrderCondition.getOperator());
		}
		if(!Strings.isEmpty(webankOrderCondition.getTradeStatus())){
			cb.andEQ("tradeStatus", webankOrderCondition.getTradeStatus());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(webankOrderCondition.getOrderBy())){
			if(webankOrderCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = webankOrderCondition.getOrderBy().split(",");
				String[] orders = webankOrderCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(webankOrderCondition.getOrderBy(), Order.valueOf(webankOrderCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(webankOrderCondition.getFirst()), Long.valueOf(webankOrderCondition.getLast()));
		return webankOrderDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param webankOrder 
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public List<WebankOrder> findAll(WebankOrderCondition webankOrderCondition){
		CriteriaBuilder cb = Cnd.builder(WebankOrder.class);
		if(!Strings.isEmpty(webankOrderCondition.getId())){
			cb.andEQ("id", webankOrderCondition.getId());
		}
		if(!Strings.isEmpty(webankOrderCondition.getMerchantId())){
			cb.andEQ("merchantId", webankOrderCondition.getMerchantId());
		}
		if(!Strings.isEmpty(webankOrderCondition.getTradeNo())){
			cb.andEQ("tradeNo", webankOrderCondition.getTradeNo());
		}
		if(null != webankOrderCondition.getTradeAmt()){
			cb.andEQ("tradeAmt", webankOrderCondition.getTradeAmt());
		}
		if(null != webankOrderCondition.getCreateTime()){
			cb.andEQ("createTime", webankOrderCondition.getCreateTime());
		}
		if(!Strings.isEmpty(webankOrderCondition.getOperator())){
			cb.andEQ("operator", webankOrderCondition.getOperator());
		}
		if(!Strings.isEmpty(webankOrderCondition.getTradeStatus())){
			cb.andEQ("tradeStatus", webankOrderCondition.getTradeStatus());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return webankOrderDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public WebankOrder findById(String id){
		return webankOrderDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param webankOrderCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long insert(WebankOrderCondition webankOrderCondition){
		WebankOrder webankOrder = new WebankOrder();
		BeanUtils.copyProperties(webankOrderCondition, webankOrder);
		return webankOrderDAO.insert(webankOrder);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long deleteById(String id){
		return webankOrderDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return webankOrderDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return webankOrderDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long update(WebankOrderCondition webankOrderCondition){
		WebankOrder webankOrder = new WebankOrder();
		BeanUtils.copyProperties(webankOrderCondition, webankOrder);
		return webankOrderDAO.update(webankOrder);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	@Override
	public long updateByCriteria(WebankOrderCondition webankOrderCondition,Criteria criteria){
		WebankOrder webankOrder = new WebankOrder();
		BeanUtils.copyProperties(webankOrderCondition, webankOrder);
		return webankOrderDAO.updateByCriteria(webankOrder,criteria);
	}

	@Override
	public WebankOrder findByTradeNo(String tradeNo) {
		return webankOrderDAO.findByTradeNo(tradeNo);
	}
	
}

