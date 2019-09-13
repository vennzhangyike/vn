/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentPromotionCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.AgentPromotionDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgentPromotion;
import com.hfepay.scancode.service.commons.FileUploadConfig;
import com.hfepay.scancode.service.operator.AgentPromotionService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONObject;

@Service("agentPromotionService")
public class AgentPromotionServiceImpl implements AgentPromotionService {
	
	@Autowired
    private AgentPromotionDAO agentPromotionDAO;
	
	@Autowired
	private FileUploadConfig fileUploadConfig;
	
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
    
//	private static String qrFileType = ".png";//二维码文件类型
	
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: PagingResult<AgentPromotion>
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
    @Override
	public PagingResult<AgentPromotion> findPagingResult(AgentPromotionCondition agentPromotionCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPromotion.class);
		if(!Strings.isEmpty(agentPromotionCondition.getId())){
			cb.andEQ("id", agentPromotionCondition.getId());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPromotionCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTitle())){
			cb.andEQ("title", agentPromotionCondition.getTitle());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getContent())){
			cb.andEQ("content", agentPromotionCondition.getContent());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getAddress())){
			cb.andEQ("address", agentPromotionCondition.getAddress());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getQrAddress())){
			cb.andEQ("qrAddress", agentPromotionCondition.getQrAddress());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getQrImgAddress())){
			cb.andEQ("qrImgAddress", agentPromotionCondition.getQrImgAddress());
		}
		if(null != agentPromotionCondition.getCreateTime()){
			cb.andEQ("createTime", agentPromotionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPromotionCondition.getOperator())){
			cb.andEQ("operator", agentPromotionCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPromotionCondition.getRemark())){
			cb.andLike("remark", agentPromotionCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp1())){
			cb.andEQ("temp1", agentPromotionCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp2())){
			cb.andEQ("temp2", agentPromotionCondition.getTemp2());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp3())){
			cb.andEQ("temp3", agentPromotionCondition.getTemp3());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp4())){
			cb.andEQ("temp4", agentPromotionCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentPromotionCondition.getFirst()), Long.valueOf(agentPromotionCondition.getLast()));
		return agentPromotionDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: List<AgentPromotion>
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public List<AgentPromotion> findAll(AgentPromotionCondition agentPromotionCondition){
		CriteriaBuilder cb = Cnd.builder(AgentPromotion.class);
		if(!Strings.isEmpty(agentPromotionCondition.getId())){
			cb.andEQ("id", agentPromotionCondition.getId());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getAgentNo())){
			cb.andEQ("agentNo", agentPromotionCondition.getAgentNo());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTitle())){
			cb.andEQ("title", agentPromotionCondition.getTitle());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getContent())){
			cb.andEQ("content", agentPromotionCondition.getContent());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getAddress())){
			cb.andEQ("address", agentPromotionCondition.getAddress());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getQrAddress())){
			cb.andEQ("qrAddress", agentPromotionCondition.getQrAddress());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getQrImgAddress())){
			cb.andEQ("qrImgAddress", agentPromotionCondition.getQrImgAddress());
		}
		if(null != agentPromotionCondition.getCreateTime()){
			cb.andEQ("createTime", agentPromotionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(agentPromotionCondition.getOperator())){
			cb.andEQ("operator", agentPromotionCondition.getOperator());
		}

		if(!Strings.isEmpty(agentPromotionCondition.getRemark())){
			cb.andLike("remark", agentPromotionCondition.getRemark());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp1())){
			cb.andEQ("temp1", agentPromotionCondition.getTemp1());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp2())){
			cb.andEQ("temp2", agentPromotionCondition.getTemp2());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp3())){
			cb.andEQ("temp3", agentPromotionCondition.getTemp3());
		}
		if(!Strings.isEmpty(agentPromotionCondition.getTemp4())){
			cb.andEQ("temp4", agentPromotionCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentPromotionDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: AgentPromotion
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public AgentPromotion findById(String id){
		return agentPromotionDAO.get(id);
	}
	
	/**
	 * @Title: findByAgentNo
	 * @Description: 代理商编号查找
	 * @author: Ricky
	 * @param agentNo
	 * @return: AgentPromotion
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public AgentPromotion findByAgentNo(String agentNo){
		CriteriaBuilder cb = Cnd.builder(AgentPromotion.class);
		cb.andEQ("agentNo", agentNo);
		Criteria buildCriteria = cb.buildCriteria();
		AgentPromotion agentPromotion = agentPromotionDAO.findOneByCriteria(buildCriteria);
		try {
			if (null == agentPromotion) {
				AgentPromotionCondition agentPromotionCondition = new AgentPromotionCondition();
				agentPromotionCondition.setId(Strings.getUUID());
				agentPromotionCondition.setAgentNo(agentNo);
				
				//获取代理商信息
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentNo));
				//获取地址参数信息
				JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(agentBase.getChannelNo());
				
				StringBuffer content = new StringBuffer();//二维码扫码地址+参数(代理商编码+二维码编码)
				content.append(paramsInfoJson.getString("hfepayAgentQrPath"));
				content.append("?agentNo=");
				content.append(agentNo);
				
				agentPromotionCondition.setQrAddress(content.toString());
				agentPromotionCondition.setQrImgAddress(content.toString());
				agentPromotionCondition.setCreateTime(new Date());
				
				this.insert(agentPromotionCondition);
				
				agentPromotion = agentPromotionDAO.findOneByCriteria(buildCriteria);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return agentPromotion;
	}
	
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long insert(AgentPromotionCondition agentPromotionCondition){
		AgentPromotion agentPromotion = new AgentPromotion();
		BeanUtils.copyProperties(agentPromotionCondition, agentPromotion);
		return agentPromotionDAO.insert(agentPromotion);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long deleteById(String id){
		return agentPromotionDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return agentPromotionDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return agentPromotionDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long update(AgentPromotionCondition agentPromotionCondition){
		AgentPromotion agentPromotion = new AgentPromotion();
		BeanUtils.copyProperties(agentPromotionCondition, agentPromotion);
		return agentPromotionDAO.update(agentPromotion);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	@Override
	public long updateByCriteria(AgentPromotionCondition agentPromotionCondition,Criteria criteria){
		AgentPromotion agentPromotion = new AgentPromotion();
		BeanUtils.copyProperties(agentPromotionCondition, agentPromotion);
		return agentPromotionDAO.updateByCriteria(agentPromotion,criteria);
	}
	
}

