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
import com.hfepay.scancode.commons.bo.ProfitBo;
import com.hfepay.scancode.commons.condition.ChannelPaywayCondition;
import com.hfepay.scancode.commons.dao.ChannelPaywayDAO;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.service.operator.ChannelPaywayService;

@Service("channelPaywayService")
public class ChannelPaywayServiceImpl implements ChannelPaywayService {
	
	@Autowired
    private ChannelPaywayDAO channelPaywayDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: PagingResult<ChannelPayway>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
    @Override
	public PagingResult<ChannelPayway> findPagingResult(ChannelPaywayCondition channelPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
		if(!Strings.isEmpty(channelPaywayCondition.getId())){
			cb.andEQ("id", channelPaywayCondition.getId());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getChannelNo())){
			cb.andEQ("channelNo", channelPaywayCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getChannelName())){
			cb.andLike("channelName", channelPaywayCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getPayCode())){
			cb.andEQ("payCode", channelPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getPayName())){
			cb.andLike("payName", channelPaywayCondition.getPayName());
		}
		if(null != channelPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", channelPaywayCondition.getT0Rate());
		}
		if(null != channelPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", channelPaywayCondition.getT1Rate());
		}
		if(null != channelPaywayCondition.getRate()){
			cb.andEQ("rate", channelPaywayCondition.getRate());
		}
		if(null != channelPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", channelPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getStatus())){
			cb.andEQ("status", channelPaywayCondition.getStatus());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelPaywayCondition.getRecordStatus());
		}
		if(null != channelPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", channelPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelPaywayCondition.getOperator())){
			cb.andEQ("operator", channelPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(channelPaywayCondition.getRemark())){
			cb.andLike("remark", channelPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getTemp1())){
			cb.andEQ("temp1", channelPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getTemp2())){
			cb.andEQ("temp2", channelPaywayCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelPaywayCondition.getFirst()), Long.valueOf(channelPaywayCondition.getLast()));
		return channelPaywayDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: List<ChannelPayway>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public List<ChannelPayway> findAll(ChannelPaywayCondition channelPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
		if(!Strings.isEmpty(channelPaywayCondition.getId())){
			cb.andEQ("id", channelPaywayCondition.getId());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getChannelNo())){
			cb.andEQ("channelNo", channelPaywayCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getChannelName())){
			cb.andLike("channelName", channelPaywayCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getPayCode())){
			cb.andEQ("payCode", channelPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getPayName())){
			cb.andLike("payName", channelPaywayCondition.getPayName());
		}
		if(null != channelPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", channelPaywayCondition.getT0Rate());
		}
		if(null != channelPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", channelPaywayCondition.getT1Rate());
		}
		if(null != channelPaywayCondition.getRate()){
			cb.andEQ("rate", channelPaywayCondition.getRate());
		}
		if(null != channelPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", channelPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getStatus())){
			cb.andEQ("status", channelPaywayCondition.getStatus());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelPaywayCondition.getRecordStatus());
		}
		if(null != channelPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", channelPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelPaywayCondition.getOperator())){
			cb.andEQ("operator", channelPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(channelPaywayCondition.getRemark())){
			cb.andLike("remark", channelPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getTemp1())){
			cb.andEQ("temp1", channelPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelPaywayCondition.getTemp2())){
			cb.andEQ("temp2", channelPaywayCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelPaywayDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelPayway
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public ChannelPayway findById(String id){
		return channelPaywayDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long insert(ChannelPaywayCondition channelPaywayCondition){
		ChannelPayway channelPayway = new ChannelPayway();
		BeanUtils.copyProperties(channelPaywayCondition, channelPayway);
		return channelPaywayDAO.insert(channelPayway);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long deleteById(String id){
		return channelPaywayDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelPaywayDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelPaywayDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long update(ChannelPaywayCondition channelPaywayCondition){
		ChannelPayway channelPayway = new ChannelPayway();
		BeanUtils.copyProperties(channelPaywayCondition, channelPayway);
		channelPayway.setUpdateTime(new Date());
		return channelPaywayDAO.update(channelPayway);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long updateByCriteria(ChannelPaywayCondition channelPaywayCondition,Criteria criteria){
		ChannelPayway channelPayway = new ChannelPayway();
		BeanUtils.copyProperties(channelPaywayCondition, channelPayway);
		return channelPaywayDAO.updateByCriteria(channelPayway,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelPaywayDAO.updateStatus(id,status);
	}

	@Override
	public ChannelPayway findByPayCode(String payCode,String channelNo) {
		CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("channelNo", channelNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return channelPaywayDAO.findOneByCriteria(buildCriteria);
	}	
	
	@Override
	public List<ProfitBo> getChannelRateDiff() {
		// TODO Auto-generated method stub
		return channelPaywayDAO.getChannelRateDiff();
	}
}

