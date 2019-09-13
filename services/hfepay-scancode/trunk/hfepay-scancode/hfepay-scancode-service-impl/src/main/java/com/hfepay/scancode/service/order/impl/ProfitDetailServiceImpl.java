/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.ProfitDetailCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.ProfitDetailDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.ProfitDetail;
import com.hfepay.scancode.commons.vo.ProfitDetailVO;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.order.ProfitDetailService;

@Service("profitDetailService")
public class ProfitDetailServiceImpl implements ProfitDetailService {
	public static final Logger log = LoggerFactory.getLogger(ProfitDetailServiceImpl.class);
	@Autowired
    private ProfitDetailDAO profitDetailDAO;
	@Autowired
	private MappingDicionService mappingDicionService;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param profitDetailCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
    @Override
	public PagingResult<ProfitDetail> findPagingResult(ProfitDetailCondition profitDetailCondition){
		CriteriaBuilder cb = Cnd.builder(ProfitDetail.class);
		if(!Strings.isEmpty(profitDetailCondition.getId())){
			cb.andEQ("id", profitDetailCondition.getId());
		}
		if(!Strings.isEmpty(profitDetailCondition.getIdentityNo())){
			cb.andEQ("identityNo", profitDetailCondition.getIdentityNo());
		}
		if(null != profitDetailCondition.getProfit()){
			cb.andEQ("profit", profitDetailCondition.getProfit());
		}
		if(!Strings.isEmpty(profitDetailCondition.getPayCode())){
			cb.andEQ("payCode", profitDetailCondition.getPayCode());
		}
		if(!Strings.isEmpty(profitDetailCondition.getTradeType())){
			cb.andEQ("tradeType", profitDetailCondition.getTradeType());
		}
		if(!Strings.isEmpty(profitDetailCondition.getMerchantNo())){
			cb.andEQ("merchantNo", profitDetailCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(profitDetailCondition.getProfitType())){
			cb.andEQ("profitType", profitDetailCondition.getProfitType());
		}
		if(null != profitDetailCondition.getProfitBase()){
			cb.andEQ("profitBase", profitDetailCondition.getProfitBase());
		}
		if(!Strings.isEmpty(profitDetailCondition.getChildLevelNo())){
			cb.andEQ("childLevelNo", profitDetailCondition.getChildLevelNo());
		}
		if(null != profitDetailCondition.getRate()){
			cb.andEQ("rate", profitDetailCondition.getRate());
		}
		if(null != profitDetailCondition.getRateDiff()){
			cb.andEQ("rateDiff", profitDetailCondition.getRateDiff());
		}
		if(null != profitDetailCondition.getCreateTime()){
			cb.andEQ("createTime", profitDetailCondition.getCreateTime());
		}
		cb.orderBy("createTime", Order.DESC);
		//结算开始日期
		if(!Strings.isEmpty(profitDetailCondition.getTransDate())){
			cb.andGE("createTime", profitDetailCondition.getTransDate());
		}
		//结算结束日期
		if(!Strings.isEmpty(profitDetailCondition.getTransDateEnd())){
			cb.andLE("createTime", profitDetailCondition.getTransDateEnd());
		}
		Map<String,String> mapPay = transPayCode();
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(profitDetailCondition.getFirst()), Long.valueOf(profitDetailCondition.getLast()));
		PagingResult<ProfitDetail> pager = profitDetailDAO.findPagingResult(buildCriteria);
		List<ProfitDetail> list = pager.getResult();
		for (ProfitDetail profitDetail : list) {
			try {
				ProfitDetailVO vo = (ProfitDetailVO)profitDetail;
				vo.setPayCode(mapPay.get(profitDetail.getPayCode()));//支付方式转换
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+vo.getIdentityNo()));
				if(agentBase != null){
					vo.setOrganName(agentBase.getAgentName());
				}
				if(Strings.isEmpty(vo.getOrganName())){
					ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+vo.getIdentityNo()));
					if(channelBase != null){
						vo.setOrganName(channelBase.getChannelName());
					}	
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+vo.getMerchantNo()));
				if(merchantInfo != null){
					vo.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("循环取名称失败..",e);
			}
		}
		return pager;
	}
	
	/**
	 * 列表
	 * @param profitDetail 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public List<ProfitDetail> findAll(ProfitDetailCondition profitDetailCondition){
		CriteriaBuilder cb = Cnd.builder(ProfitDetail.class);
		if(!Strings.isEmpty(profitDetailCondition.getId())){
			cb.andEQ("id", profitDetailCondition.getId());
		}
		if(!Strings.isEmpty(profitDetailCondition.getIdentityNo())){
			cb.andEQ("identityNo", profitDetailCondition.getIdentityNo());
		}
		if(null != profitDetailCondition.getProfit()){
			cb.andEQ("profit", profitDetailCondition.getProfit());
		}
		if(!Strings.isEmpty(profitDetailCondition.getPayCode())){
			cb.andEQ("payCode", profitDetailCondition.getPayCode());
		}
		if(!Strings.isEmpty(profitDetailCondition.getTradeType())){
			cb.andEQ("tradeType", profitDetailCondition.getTradeType());
		}
		if(!Strings.isEmpty(profitDetailCondition.getMerchantNo())){
			cb.andEQ("merchantNo", profitDetailCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(profitDetailCondition.getProfitType())){
			cb.andEQ("profitType", profitDetailCondition.getProfitType());
		}
		if(null != profitDetailCondition.getProfitBase()){
			cb.andEQ("profitBase", profitDetailCondition.getProfitBase());
		}
		if(!Strings.isEmpty(profitDetailCondition.getChildLevelNo())){
			cb.andEQ("childLevelNo", profitDetailCondition.getChildLevelNo());
		}
		if(null != profitDetailCondition.getRate()){
			cb.andEQ("rate", profitDetailCondition.getRate());
		}
		if(null != profitDetailCondition.getRateDiff()){
			cb.andEQ("rateDiff", profitDetailCondition.getRateDiff());
		}
		if(null != profitDetailCondition.getCreateTime()){
			cb.andEQ("createTime", profitDetailCondition.getCreateTime());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return profitDetailDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public ProfitDetail findById(String id){
		return profitDetailDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param profitDetailCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long insert(ProfitDetailCondition profitDetailCondition){
		ProfitDetail profitDetail = new ProfitDetail();
		BeanUtils.copyProperties(profitDetailCondition, profitDetail);
		if(Strings.isEmpty(profitDetailCondition.getId())){
			profitDetail.setId(Strings.getUUID());
		}
		return profitDetailDAO.insert(profitDetail);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long deleteById(String id){
		return profitDetailDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return profitDetailDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return profitDetailDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long update(ProfitDetailCondition profitDetailCondition){
		ProfitDetail profitDetail = new ProfitDetail();
		BeanUtils.copyProperties(profitDetailCondition, profitDetail);
		return profitDetailDAO.update(profitDetail);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public long updateByCriteria(ProfitDetailCondition profitDetailCondition,Criteria criteria){
		ProfitDetail profitDetail = new ProfitDetail();
		BeanUtils.copyProperties(profitDetailCondition, profitDetail);
		return profitDetailDAO.updateByCriteria(profitDetail,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param profitDetailCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	@Override
	public ProfitDetail findByCondition(ProfitDetailCondition profitDetailCondition){
		CriteriaBuilder cb = Cnd.builder(ProfitDetail.class);
		if(!Strings.isEmpty(profitDetailCondition.getId())){
			cb.andEQ("id", profitDetailCondition.getId());
		}
		if(!Strings.isEmpty(profitDetailCondition.getIdentityNo())){
			cb.andEQ("identityNo", profitDetailCondition.getIdentityNo());
		}
		if(null != profitDetailCondition.getProfit()){
			cb.andEQ("profit", profitDetailCondition.getProfit());
		}
		if(!Strings.isEmpty(profitDetailCondition.getPayCode())){
			cb.andEQ("payCode", profitDetailCondition.getPayCode());
		}
		if(!Strings.isEmpty(profitDetailCondition.getTradeType())){
			cb.andEQ("tradeType", profitDetailCondition.getTradeType());
		}
		if(!Strings.isEmpty(profitDetailCondition.getMerchantNo())){
			cb.andEQ("merchantNo", profitDetailCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(profitDetailCondition.getProfitType())){
			cb.andEQ("profitType", profitDetailCondition.getProfitType());
		}
		if(null != profitDetailCondition.getProfitBase()){
			cb.andEQ("profitBase", profitDetailCondition.getProfitBase());
		}
		if(!Strings.isEmpty(profitDetailCondition.getChildLevelNo())){
			cb.andEQ("childLevelNo", profitDetailCondition.getChildLevelNo());
		}
		if(null != profitDetailCondition.getRate()){
			cb.andEQ("rate", profitDetailCondition.getRate());
		}
		if(null != profitDetailCondition.getRateDiff()){
			cb.andEQ("rateDiff", profitDetailCondition.getRateDiff());
		}
		if(null != profitDetailCondition.getCreateTime()){
			cb.andEQ("createTime", profitDetailCondition.getCreateTime());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return profitDetailDAO.findOneByCriteria(buildCriteria);
	}
	@Override
	public void inserBatch(List<ProfitDetail> listDetail) {
		// TODO Auto-generated method stub
		profitDetailDAO.insertBatch(listDetail);
	}
	
	private Map<String,String> transPayCode(){
		Map<String,String> map = new HashMap<String, String>();
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("ZFTD");
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		for (MappingDicion mappingDicion : list) {
			map.put(mappingDicion.getParaVal(), mappingDicion.getParaName());
		}
		return map;
	}
}

