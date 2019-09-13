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
import com.hfepay.scancode.commons.BatchNoUtil;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.AgentBankcardCondition;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.dao.AgentBankcardDAO;
import com.hfepay.scancode.commons.entity.AgentBankcard;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AgentBankcardService;
import com.hfepay.scancode.service.operator.ChangeLogService;

import net.sf.json.JSONSerializer;

@Service("agentBankcardService")
public class AgentBankcardServiceImpl implements AgentBankcardService {
	
	@Autowired
    private AgentBankcardDAO agentBankcardDAO;
	
	@Autowired
	private ChangeLogService changeLogService;
	
    /**
	 * 列表(分页)
	 * @param agentBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
    @Override
	public PagingResult<AgentBankcard> findPagingResult(AgentBankcardCondition agentBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(AgentBankcard.class);
		if(!Strings.isEmpty(agentBankcardCondition.getId())){
			cb.andEQ("id", agentBankcardCondition.getId());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentNo())){
			cb.andEQ("agentNo", agentBankcardCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentName())){
			cb.andLike("agentName", agentBankcardCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", agentBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankName())){
			cb.andEQ("bankName", agentBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCard())){
			cb.andLike("bankCard", agentBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIdCard())){
			cb.andLike("idCard", agentBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getName())){
			cb.andLike("name", agentBankcardCondition.getName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getMobile())){
			cb.andLike("mobile", agentBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", agentBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAccountType())){
			cb.andEQ("accountType", agentBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getStatus())){
			cb.andEQ("status", agentBankcardCondition.getStatus());
		}
		if(null != agentBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", agentBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getOperator())){
			cb.andEQ("operator", agentBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getRemark())){
			cb.andLike("remark", agentBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp1())){
			cb.andEQ("temp1", agentBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp2())){
			cb.andEQ("temp2", agentBankcardCondition.getTemp2());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp3())){
			cb.andEQ("temp3", agentBankcardCondition.getTemp3());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp4())){
			cb.andEQ("temp4", agentBankcardCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentBankcardCondition.getOrderBy())){
			if(agentBankcardCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentBankcardCondition.getOrderBy().split(",");
				String[] orders = agentBankcardCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentBankcardCondition.getOrderBy(), Order.valueOf(agentBankcardCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentBankcardCondition.getFirst()), Long.valueOf(agentBankcardCondition.getLast()));
		return agentBankcardDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param agentBankcard 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public List<AgentBankcard> findAll(AgentBankcardCondition agentBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(AgentBankcard.class);
		if(!Strings.isEmpty(agentBankcardCondition.getId())){
			cb.andEQ("id", agentBankcardCondition.getId());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentNo())){
			cb.andEQ("agentNo", agentBankcardCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentName())){
			cb.andLike("agentName", agentBankcardCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", agentBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankName())){
			cb.andEQ("bankName", agentBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCard())){
			cb.andEQ("bankCard", agentBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIdCard())){
			cb.andEQ("idCard", agentBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getName())){
			cb.andEQ("name", agentBankcardCondition.getName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getMobile())){
			cb.andEQ("mobile", agentBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", agentBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAccountType())){
			cb.andEQ("accountType", agentBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getStatus())){
			cb.andEQ("status", agentBankcardCondition.getStatus());
		}
		if(null != agentBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", agentBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getOperator())){
			cb.andEQ("operator", agentBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getRemark())){
			cb.andLike("remark", agentBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp1())){
			cb.andEQ("temp1", agentBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp2())){
			cb.andEQ("temp2", agentBankcardCondition.getTemp2());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp3())){
			cb.andEQ("temp3", agentBankcardCondition.getTemp3());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp4())){
			cb.andEQ("temp4", agentBankcardCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentBankcardDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public AgentBankcard findById(String id){
		return agentBankcardDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param agentBankcardCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long insert(AgentBankcardCondition agentBankcardCondition){
		AgentBankcard agentBankcard = new AgentBankcard();
		BeanUtils.copyProperties(agentBankcardCondition, agentBankcard);
		return agentBankcardDAO.insert(agentBankcard);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long deleteById(String id){
		return agentBankcardDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return agentBankcardDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return agentBankcardDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long update(AgentBankcardCondition agentBankcardCondition){
		AgentBankcard agentBankcard = new AgentBankcard();
		BeanUtils.copyProperties(agentBankcardCondition, agentBankcard);
		
		AgentBankcard entity = findById(agentBankcardCondition.getId());
		if(entity != null && 
				!(Strings.equals(entity.getBankCode(), agentBankcardCondition.getBankCode())
						&&Strings.equals(entity.getBankName(), agentBankcardCondition.getBankName())
						&&Strings.equals(entity.getBankCard(), agentBankcardCondition.getBankCard())
						&&Strings.equals(entity.getIdCard(), agentBankcardCondition.getIdCard())
						&&Strings.equals(entity.getName(), agentBankcardCondition.getName())
						&&Strings.equals(entity.getMobile(), agentBankcardCondition.getMobile())
						&&Strings.equals(entity.getAccountType(), agentBankcardCondition.getAccountType()))){
			
			ChangeLogCondition changeLogCondition = new ChangeLogCondition();
			String tradeNo = agentBankcardCondition.getId();//t_agent_base表Id关联
			String batchNo = BatchNoUtil.getBatchNo();
			changeLogCondition.setTradeNo(tradeNo);
			changeLogCondition.setBatchNo(batchNo);
			changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.AGENT_BANKCARD_CODE.getValue()));
			changeLogCondition.setTransName(TransCodeEnum.AGENT_BANKCARD_CODE.getDesc());
			changeLogCondition.setOwnersNo(agentBankcardCondition.getAgentNo());
			changeLogCondition.setBefore(JSONSerializer.toJSON(entity).toString());
			changeLogCondition.setStatus(ScanCodeConstants.APPROVE_STATUS_NEW);
			changeLogCondition.setOperator(agentBankcardCondition.getOperator());
			changeLogCondition.setCreateTime(new Date());
			changeLogService.insert(changeLogCondition);
		}
		
		return agentBankcardDAO.update(agentBankcard);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long updateByCriteria(AgentBankcardCondition agentBankcardCondition,Criteria criteria){
		AgentBankcard agentBankcard = new AgentBankcard();
		BeanUtils.copyProperties(agentBankcardCondition, agentBankcard);
		return agentBankcardDAO.updateByCriteria(agentBankcard,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	@Override
	public long updateStatus(String id,String status){
		return agentBankcardDAO.updateStatus(id,status);
	}	
	
	
	/**
	 * 列表(分页)
	 * @param agentBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
    @Override
	public PagingResult<AgentBankcard> findPagingResultByAgentNo(AgentBankcardCondition agentBankcardCondition,String nodeSeq){
		CriteriaBuilder cb = Cnd.builder(AgentBankcard.class);
		if(!Strings.isEmpty(agentBankcardCondition.getId())){
			cb.andEQ("id", agentBankcardCondition.getId());
		}
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("identityFlag", IdentityType.Identity_Agent.getCode());
			cb.addParam("nodeSeq", nodeSeq);
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentNo())){
			cb.andEQ("agentNo", agentBankcardCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAgentName())){
			cb.andLike("agentName", agentBankcardCondition.getAgentName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", agentBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankName())){
			cb.andEQ("bankName", agentBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getBankCard())){
			cb.andEQ("bankCard", agentBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIdCard())){
			cb.andEQ("idCard", agentBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getName())){
			cb.andEQ("name", agentBankcardCondition.getName());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getMobile())){
			cb.andEQ("mobile", agentBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", agentBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getAccountType())){
			cb.andEQ("accountType", agentBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getStatus())){
			cb.andEQ("status", agentBankcardCondition.getStatus());
		}
		if(null != agentBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", agentBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getOperator())){
			cb.andEQ("operator", agentBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(agentBankcardCondition.getRemark())){
			cb.andLike("remark", agentBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp1())){
			cb.andEQ("temp1", agentBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp2())){
			cb.andEQ("temp2", agentBankcardCondition.getTemp2());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp3())){
			cb.andEQ("temp3", agentBankcardCondition.getTemp3());
		}
		if(!Strings.isEmpty(agentBankcardCondition.getTemp4())){
			cb.andEQ("temp4", agentBankcardCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(agentBankcardCondition.getOrderBy())){
			if(agentBankcardCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = agentBankcardCondition.getOrderBy().split(",");
				String[] orders = agentBankcardCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(agentBankcardCondition.getOrderBy(), Order.valueOf(agentBankcardCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentBankcardCondition.getFirst()), Long.valueOf(agentBankcardCondition.getLast()));
		return agentBankcardDAO.findPagingResult(buildCriteria);
	}
    
    @Override
    public AgentBankcard findByAgentNo(String agentNo) {
    	CriteriaBuilder cb = Cnd.builder(AgentBankcard.class);
    	cb.andEQ("agentNo", agentNo);
    	cb.andEQ("status", "1");
    	Criteria buildCriteria = cb.buildCriteria();
    	return agentBankcardDAO.findOneByCriteria(buildCriteria);
    }
}

