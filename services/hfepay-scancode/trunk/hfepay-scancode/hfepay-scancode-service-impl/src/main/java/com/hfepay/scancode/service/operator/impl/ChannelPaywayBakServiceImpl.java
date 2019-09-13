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
import com.hfepay.scancode.commons.condition.ChannelPaywayBakCondition;
import com.hfepay.scancode.commons.dao.ChannelPaywayBakDAO;
import com.hfepay.scancode.commons.entity.ChannelPaywayBak;
import com.hfepay.scancode.service.operator.ChannelPaywayBakService;

@Service("channelPaywayBakService")
public class ChannelPaywayBakServiceImpl implements ChannelPaywayBakService {
	
	@Autowired
    private ChannelPaywayBakDAO channelPaywayBakDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: PagingResult<ChannelPaywayBak>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
    @Override
	public PagingResult<ChannelPaywayBak> findPagingResult(ChannelPaywayBakCondition channelPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelPaywayBak.class);
		if(!Strings.isEmpty(channelPaywayBakCondition.getId())){
			cb.andEQ("id", channelPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getChannelNo())){
			cb.andEQ("channelNo", channelPaywayBakCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getChannelName())){
			cb.andLike("channelName", channelPaywayBakCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", channelPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getPayName())){
			cb.andLike("payName", channelPaywayBakCondition.getPayName());
		}
		if(null != channelPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", channelPaywayBakCondition.getT0Rate());
		}
		if(null != channelPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", channelPaywayBakCondition.getT1Rate());
		}
		if(null != channelPaywayBakCondition.getRate()){
			cb.andEQ("rate", channelPaywayBakCondition.getRate());
		}
		if(null != channelPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", channelPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getStatus())){
			cb.andEQ("status", channelPaywayBakCondition.getStatus());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelPaywayBakCondition.getRecordStatus());
		}
		if(null != channelPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", channelPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelPaywayBakCondition.getOperator())){
			cb.andEQ("operator", channelPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(channelPaywayBakCondition.getRemark())){
			cb.andLike("remark", channelPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", channelPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", channelPaywayBakCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelPaywayBakCondition.getFirst()), Long.valueOf(channelPaywayBakCondition.getLast()));
		return channelPaywayBakDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: List<ChannelPaywayBak>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public List<ChannelPaywayBak> findAll(ChannelPaywayBakCondition channelPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelPaywayBak.class);
		if(!Strings.isEmpty(channelPaywayBakCondition.getId())){
			cb.andEQ("id", channelPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getChannelNo())){
			cb.andEQ("channelNo", channelPaywayBakCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getChannelName())){
			cb.andLike("channelName", channelPaywayBakCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", channelPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getPayName())){
			cb.andLike("payName", channelPaywayBakCondition.getPayName());
		}
		if(null != channelPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", channelPaywayBakCondition.getT0Rate());
		}
		if(null != channelPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", channelPaywayBakCondition.getT1Rate());
		}
		if(null != channelPaywayBakCondition.getRate()){
			cb.andEQ("rate", channelPaywayBakCondition.getRate());
		}
		if(null != channelPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", channelPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getStatus())){
			cb.andEQ("status", channelPaywayBakCondition.getStatus());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelPaywayBakCondition.getRecordStatus());
		}
		if(null != channelPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", channelPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelPaywayBakCondition.getOperator())){
			cb.andEQ("operator", channelPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(channelPaywayBakCondition.getRemark())){
			cb.andLike("remark", channelPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", channelPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", channelPaywayBakCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelPaywayBakDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelPaywayBak
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public ChannelPaywayBak findById(String id){
		return channelPaywayBakDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long insert(ChannelPaywayBakCondition channelPaywayBakCondition){
		ChannelPaywayBak channelPayway = new ChannelPaywayBak();
		BeanUtils.copyProperties(channelPaywayBakCondition, channelPayway);
		return channelPaywayBakDAO.insert(channelPayway);
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
		return channelPaywayBakDAO.deleteById(id);
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
		return channelPaywayBakDAO.deleteByCriteria(criteria);
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
		return channelPaywayBakDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long update(ChannelPaywayBakCondition channelPaywayBakCondition){
		ChannelPaywayBak channelPayway = new ChannelPaywayBak();
		BeanUtils.copyProperties(channelPaywayBakCondition, channelPayway);
		channelPayway.setUpdateTime(new Date());
		return channelPaywayBakDAO.update(channelPayway);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelPaywayBakCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@Override
	public long updateByCriteria(ChannelPaywayBakCondition channelPaywayBakCondition,Criteria criteria){
		ChannelPaywayBak channelPayway = new ChannelPaywayBak();
		BeanUtils.copyProperties(channelPaywayBakCondition, channelPayway);
		return channelPaywayBakDAO.updateByCriteria(channelPayway,criteria);
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
		return channelPaywayBakDAO.updateStatus(id,status);
	}

	@Override
	public ChannelPaywayBak findByPayCode(String payCode,String channelNo) {
		CriteriaBuilder cb = Cnd.builder(ChannelPaywayBak.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("channelNo", channelNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return channelPaywayBakDAO.findOneByCriteria(buildCriteria);
	}	
	
	@Override
	public List<ProfitBo> getChannelRateDiff() {
		// TODO Auto-generated method stub
		return channelPaywayBakDAO.getChannelRateDiff();
	}

	@Override
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		channelPaywayBakDAO.doTruncateTable();
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		channelPaywayBakDAO.doBackUpTable();
	}
}

