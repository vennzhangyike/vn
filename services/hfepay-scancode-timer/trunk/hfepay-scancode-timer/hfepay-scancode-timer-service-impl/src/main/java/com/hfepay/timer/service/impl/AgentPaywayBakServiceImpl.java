package com.hfepay.timer.service.impl;

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
import com.hfepay.scancode.commons.condition.AgentPaywayBakCondition;
import com.hfepay.scancode.commons.dao.AgentPaywayBakDAO;
import com.hfepay.scancode.commons.entity.AgentPaywayBak;
import com.hfepay.timer.service.AgentPaywayBakService;

@Service("agentPaywayBakService")
public class AgentPaywayBakServiceImpl implements AgentPaywayBakService {
	
	@Autowired
    private AgentPaywayBakDAO agentPaywayBakDAO;
    
    /**
	 * 列表(分页)
	 * @param agentPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
    @Override
	public PagingResult<AgentPaywayBak> findPagingResult(AgentPaywayBakCondition agentPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPaywayBak.class);
		if(!Strings.isEmpty(agentPaywayBakCondition.getId())){
			cb.andEQ("id", agentPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayBakCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayBakCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayName())){
			cb.andLike("payName", agentPaywayBakCondition.getPayName());
		}
		if(null != agentPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayBakCondition.getT0Rate());
		}
		if(null != agentPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayBakCondition.getT1Rate());
		}
		if(null != agentPaywayBakCondition.getRate()){
			cb.andEQ("rate", agentPaywayBakCondition.getRate());
		}
		if(null != agentPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getStatus())){
			cb.andEQ("status", agentPaywayBakCondition.getStatus());
		}
		if(null != agentPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getOperator())){
			cb.andEQ("operator", agentPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getRemark())){
			cb.andLike("remark", agentPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayBakCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentPaywayBakCondition.getOrderBy())){
			if(agentPaywayBakCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentPaywayBakCondition.getOrderBy().split(",");
				String[] orders = agentPaywayBakCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentPaywayBakCondition.getOrderBy(), Order.valueOf(agentPaywayBakCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentPaywayBakCondition.getFirst()), Long.valueOf(agentPaywayBakCondition.getLast()));
		return agentPaywayBakDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param agentPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public List<AgentPaywayBak> findAll(AgentPaywayBakCondition agentPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPaywayBak.class);
		if(!Strings.isEmpty(agentPaywayBakCondition.getId())){
			cb.andEQ("id", agentPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayBakCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayBakCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayName())){
			cb.andLike("payName", agentPaywayBakCondition.getPayName());
		}
		if(null != agentPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayBakCondition.getT0Rate());
		}
		if(null != agentPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayBakCondition.getT1Rate());
		}
		if(null != agentPaywayBakCondition.getRate()){
			cb.andEQ("rate", agentPaywayBakCondition.getRate());
		}
		if(null != agentPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getStatus())){
			cb.andEQ("status", agentPaywayBakCondition.getStatus());
		}
		if(null != agentPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getOperator())){
			cb.andEQ("operator", agentPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getRemark())){
			cb.andLike("remark", agentPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayBakCondition.getTemp2());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return agentPaywayBakDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public AgentPaywayBak findById(String id){
		return agentPaywayBakDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param agentPaywayBakCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long insert(AgentPaywayBakCondition agentPaywayBakCondition){
		AgentPaywayBak agentPayway = new AgentPaywayBak();
		BeanUtils.copyProperties(agentPaywayBakCondition, agentPayway);
		return agentPaywayBakDAO.insert(agentPayway);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long deleteById(String id){
		return agentPaywayBakDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return agentPaywayBakDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return agentPaywayBakDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long update(AgentPaywayBakCondition agentPaywayBakCondition){
		AgentPaywayBak agentPayway = new AgentPaywayBak();
		BeanUtils.copyProperties(agentPaywayBakCondition, agentPayway);
		return agentPaywayBakDAO.update(agentPayway);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long updateByCriteria(AgentPaywayBakCondition agentPaywayBakCondition,Criteria criteria){
		AgentPaywayBak agentPayway = new AgentPaywayBak();
		BeanUtils.copyProperties(agentPaywayBakCondition, agentPayway);
		return agentPaywayBakDAO.updateByCriteria(agentPayway,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	@Override
	public long updateStatus(String id,String status){
		return agentPaywayBakDAO.updateStatus(id,status);
	}	
	
	/**
	 * 列表(分页)
	 * @param agentPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
    @Override
	public PagingResult<AgentPaywayBak> findPagingResultByAgentNo(AgentPaywayBakCondition agentPaywayBakCondition,List<String> list){
		CriteriaBuilder cb = Cnd.builder(AgentPaywayBak.class);
		if(!Strings.isEmpty(agentPaywayBakCondition.getId())){
			cb.andEQ("id", agentPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPaywayBakCondition.getAgentNo());
		}
		if(list != null && list.size() > 0){
			cb.andIn("agentNo", list);
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getAgentName())){
			cb.andLike("agentName", agentPaywayBakCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", agentPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getPayName())){
			cb.andLike("payName", agentPaywayBakCondition.getPayName());
		}
		if(null != agentPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", agentPaywayBakCondition.getT0Rate());
		}
		if(null != agentPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", agentPaywayBakCondition.getT1Rate());
		}
		if(null != agentPaywayBakCondition.getRate()){
			cb.andEQ("rate", agentPaywayBakCondition.getRate());
		}
		if(null != agentPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", agentPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", agentPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getStatus())){
			cb.andEQ("status", agentPaywayBakCondition.getStatus());
		}
		if(null != agentPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", agentPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getOperator())){
			cb.andEQ("operator", agentPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPaywayBakCondition.getRemark())){
			cb.andLike("remark", agentPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", agentPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", agentPaywayBakCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentPaywayBakCondition.getOrderBy())){
			if(agentPaywayBakCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentPaywayBakCondition.getOrderBy().split(",");
				String[] orders = agentPaywayBakCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentPaywayBakCondition.getOrderBy(), Order.valueOf(agentPaywayBakCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentPaywayBakCondition.getFirst()), Long.valueOf(agentPaywayBakCondition.getLast()));
		return agentPaywayBakDAO.findPagingResult(buildCriteria);
	}

	@Override
	public AgentPaywayBak findByPayCode(String payCode,String agentNo) {
		CriteriaBuilder cb = Cnd.builder(AgentPaywayBak.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("agentNo", agentNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return agentPaywayBakDAO.findOneByCriteria(buildCriteria);
	}

	@Override
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		agentPaywayBakDAO.doTruncateTable();
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		agentPaywayBakDAO.doBackUpTable();
	}
}

