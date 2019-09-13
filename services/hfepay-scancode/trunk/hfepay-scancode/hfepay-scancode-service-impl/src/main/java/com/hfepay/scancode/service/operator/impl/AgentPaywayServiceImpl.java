package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.scancode.commons.condition.AgentPaywayCondition;
import com.hfepay.scancode.commons.dao.AgentPaywayDAO;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.service.operator.AgentPaywayService;

@Service("agentPaywayService")
public class AgentPaywayServiceImpl implements AgentPaywayService {
	
	@Autowired
    private AgentPaywayDAO agentPaywayDAO;
    
    /**
	 * 列表(分页)
	 * @param agentPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
    @Override
	public PagingResult<AgentPayway> findPagingResult(AgentPaywayCondition agentPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
		if(!Strings.isEmpty(agentPaywayCondition.getId())){
			cb.andEQ("id", agentPaywayCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayName())){
			cb.andLike("payName", agentPaywayCondition.getPayName());
		}
		if(null != agentPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayCondition.getT0Rate());
		}
		if(null != agentPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayCondition.getT1Rate());
		}
		if(null != agentPaywayCondition.getRate()){
			cb.andEQ("rate", agentPaywayCondition.getRate());
		}
		if(null != agentPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getStatus())){
			cb.andEQ("status", agentPaywayCondition.getStatus());
		}
		if(null != agentPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getOperator())){
			cb.andEQ("operator", agentPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getRemark())){
			cb.andLike("remark", agentPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentPaywayCondition.getOrderBy())){
			if(agentPaywayCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentPaywayCondition.getOrderBy().split(",");
				String[] orders = agentPaywayCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentPaywayCondition.getOrderBy(), Order.valueOf(agentPaywayCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentPaywayCondition.getFirst()), Long.valueOf(agentPaywayCondition.getLast()));
		return agentPaywayDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param agentPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public List<AgentPayway> findAll(AgentPaywayCondition agentPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
		if(!Strings.isEmpty(agentPaywayCondition.getId())){
			cb.andEQ("id", agentPaywayCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayName())){
			cb.andLike("payName", agentPaywayCondition.getPayName());
		}
		if(null != agentPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayCondition.getT0Rate());
		}
		if(null != agentPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayCondition.getT1Rate());
		}
		if(null != agentPaywayCondition.getRate()){
			cb.andEQ("rate", agentPaywayCondition.getRate());
		}
		if(null != agentPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getStatus())){
			cb.andEQ("status", agentPaywayCondition.getStatus());
		}
		if(null != agentPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getOperator())){
			cb.andEQ("operator", agentPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getRemark())){
			cb.andLike("remark", agentPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayCondition.getTemp2());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return agentPaywayDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public AgentPayway findById(String id){
		return agentPaywayDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param agentPaywayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long insert(AgentPaywayCondition agentPaywayCondition){
		AgentPayway agentPayway = new AgentPayway();
		BeanUtils.copyProperties(agentPaywayCondition, agentPayway);
		return agentPaywayDAO.insert(agentPayway);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long deleteById(String id){
		return agentPaywayDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return agentPaywayDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return agentPaywayDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long update(AgentPaywayCondition agentPaywayCondition){
		AgentPayway agentPayway = new AgentPayway();
		BeanUtils.copyProperties(agentPaywayCondition, agentPayway);
		return agentPaywayDAO.update(agentPayway);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long updateByCriteria(AgentPaywayCondition agentPaywayCondition,Criteria criteria){
		AgentPayway agentPayway = new AgentPayway();
		BeanUtils.copyProperties(agentPaywayCondition, agentPayway);
		return agentPaywayDAO.updateByCriteria(agentPayway,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long updateStatus(String id,String status){
		return agentPaywayDAO.updateStatus(id,status);
	}	
	
	/**
	 * 列表(分页)
	 * @param agentPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
    @Override
	public PagingResult<AgentPayway> findPagingResultByAgentNo(AgentPaywayCondition agentPaywayCondition,String nodeSeq){
		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
		if(!Strings.isEmpty(agentPaywayCondition.getId())){
			cb.andEQ("id", agentPaywayCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayCondition.getAgentNo());
		}
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("nodeSeq", nodeSeq);
		}
		if(!Strings.isEmpty(agentPaywayCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getPayName())){
			cb.andLike("payName", agentPaywayCondition.getPayName());
		}
		if(null != agentPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayCondition.getT0Rate());
		}
		if(null != agentPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayCondition.getT1Rate());
		}
		if(null != agentPaywayCondition.getRate()){
			cb.andEQ("rate", agentPaywayCondition.getRate());
		}
		if(null != agentPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getStatus())){
			cb.andEQ("status", agentPaywayCondition.getStatus());
		}
		if(null != agentPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getOperator())){
			cb.andEQ("operator", agentPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayCondition.getRemark())){
			cb.andLike("remark", agentPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentPaywayCondition.getOrderBy())){
			if(agentPaywayCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentPaywayCondition.getOrderBy().split(",");
				String[] orders = agentPaywayCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentPaywayCondition.getOrderBy(), Order.valueOf(agentPaywayCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentPaywayCondition.getFirst()), Long.valueOf(agentPaywayCondition.getLast()));
		return agentPaywayDAO.findPagingResult(buildCriteria);
	}

	@Override
	public AgentPayway findByPayCode(String payCode,String agentNo) {
		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("agentNo", agentNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return agentPaywayDAO.findOneByCriteria(buildCriteria);
	}
}

