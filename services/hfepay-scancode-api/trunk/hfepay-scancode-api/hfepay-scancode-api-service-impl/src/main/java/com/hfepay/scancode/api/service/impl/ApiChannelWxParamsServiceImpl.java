/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.dao.ApiChannelWxParamsDAO;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.config.HfepayConfig;


@Service("apiChannelWxParamsService")
public class ApiChannelWxParamsServiceImpl implements ApiChannelWxParamsService {
	public static final Logger log = LoggerFactory.getLogger(ApiChannelWxParamsServiceImpl.class);

	@Autowired
    private ApiChannelWxParamsDAO channelWxParamsDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param channelWxParamsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
    @Override
	public PagingResult<ApiChannelWxParams> findPagingResult(ApiChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ApiChannelWxParams.class);
		if(!Strings.isEmpty(channelWxParamsCondition.getId())){
			cb.andEQ("id", channelWxParamsCondition.getId());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getOrganNo())){
			cb.andEQ("organNo", channelWxParamsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getWxParams())){
			cb.andEQ("wxParams", channelWxParamsCondition.getWxParams());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getStatus())){
			cb.andEQ("status", channelWxParamsCondition.getStatus());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelWxParamsCondition.getRecordStatus());
		}
		if(null != channelWxParamsCondition.getCreateTime()){
			cb.andEQ("createTime", channelWxParamsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getOperator())){
			cb.andEQ("operator", channelWxParamsCondition.getOperator());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getRemark())){
			cb.andLike("remark", channelWxParamsCondition.getRemark());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp1())){
			cb.andEQ("temp1", channelWxParamsCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp2())){
			cb.andEQ("temp2", channelWxParamsCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelWxParamsCondition.getFirst()), Long.valueOf(channelWxParamsCondition.getLast()));
		return channelWxParamsDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param channelWxParams 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public List<ApiChannelWxParams> findAll(ApiChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ApiChannelWxParams.class);
		if(!Strings.isEmpty(channelWxParamsCondition.getId())){
			cb.andEQ("id", channelWxParamsCondition.getId());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getOrganNo())){
			cb.andEQ("organNo", channelWxParamsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getWxParams())){
			cb.andEQ("wxParams", channelWxParamsCondition.getWxParams());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getStatus())){
			cb.andEQ("status", channelWxParamsCondition.getStatus());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelWxParamsCondition.getRecordStatus());
		}
		if(null != channelWxParamsCondition.getCreateTime()){
			cb.andEQ("createTime", channelWxParamsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getOperator())){
			cb.andEQ("operator", channelWxParamsCondition.getOperator());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getRemark())){
			cb.andLike("remark", channelWxParamsCondition.getRemark());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp1())){
			cb.andEQ("temp1", channelWxParamsCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp2())){
			cb.andEQ("temp2", channelWxParamsCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelWxParamsDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public ApiChannelWxParams findById(String id){
		return channelWxParamsDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param channelWxParamsCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long insert(ApiChannelWxParamsCondition channelWxParamsCondition){
		ApiChannelWxParams channelWxParams = new ApiChannelWxParams();
		BeanUtils.copyProperties(channelWxParamsCondition, channelWxParams);
		if(Strings.isEmpty(channelWxParamsCondition.getId())){
			channelWxParams.setId(Strings.getUUID());
		}
		return channelWxParamsDAO.insert(channelWxParams);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long deleteById(String id){
		return channelWxParamsDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelWxParamsDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelWxParamsDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long update(ApiChannelWxParamsCondition channelWxParamsCondition){
		ApiChannelWxParams channelWxParams = new ApiChannelWxParams();
		BeanUtils.copyProperties(channelWxParamsCondition, channelWxParams);
		channelWxParams.setUpdateTime(new Date());
		return channelWxParamsDAO.update(channelWxParams);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public long updateByCriteria(ApiChannelWxParamsCondition channelWxParamsCondition,Criteria criteria){
		ApiChannelWxParams channelWxParams = new ApiChannelWxParams();
		BeanUtils.copyProperties(channelWxParamsCondition, channelWxParams);
		return channelWxParamsDAO.updateByCriteria(channelWxParams,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelWxParamsDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 机构编码查找
	 * @author: Ricky
	 * @param organNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public ApiChannelWxParams findByChannelNo(String organNo){
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();			
		channelWxParamsCondition.setOrganNo(organNo);			
		List<ApiChannelWxParams> list = this.findAll(channelWxParamsCondition);
		if(list.size() == 1){
			ApiChannelWxParams entity = list.get(0);
			return entity;
		}
		return null;
	}
	/**
	 * 单个实体对象
	 * @param channelWxParamsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public ApiChannelWxParams findByCondition(ApiChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ApiChannelWxParams.class);
		if(!Strings.isEmpty(channelWxParamsCondition.getId())){
			cb.andEQ("id", channelWxParamsCondition.getId());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getOrganNo())){
			cb.andEQ("organNo", channelWxParamsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getWxParams())){
			cb.andEQ("wxParams", channelWxParamsCondition.getWxParams());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getStatus())){
			cb.andEQ("status", channelWxParamsCondition.getStatus());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelWxParamsCondition.getRecordStatus());
		}
		if(null != channelWxParamsCondition.getCreateTime()){
			cb.andEQ("createTime", channelWxParamsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getOperator())){
			cb.andEQ("operator", channelWxParamsCondition.getOperator());
		}

		if(!Strings.isEmpty(channelWxParamsCondition.getRemark())){
			cb.andLike("remark", channelWxParamsCondition.getRemark());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp1())){
			cb.andEQ("temp1", channelWxParamsCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelWxParamsCondition.getTemp2())){
			cb.andEQ("temp2", channelWxParamsCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelWxParamsDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: getFromRedis
	 * @Description: 从redis获取数据
	 * @param channelWxParamsCondition
	 * @return
	 * @throws Exception 
	 * @see com.hfepay.scancode.api.service.ApiChannelWxParamsService#getFromRedis(com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition)
	 */
	@Override
	public ApiChannelWxParams getFromRedis(ApiChannelWxParamsCondition mappingDicionCondition) throws Exception {
		ApiChannelWxParams retEntity = null;
		if(Strings.isNotEmpty(mappingDicionCondition.getOrganNo())){
			RedisKey redisKey = new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY, HfepayConfig.CHANNEL_REDIS_WX_KEY+mappingDicionCondition.getOrganNo());
			retEntity = (ApiChannelWxParams)redisSharedCache.getObj(redisKey);
			if(retEntity==null){
				log.info("ApiChannelWxParams getFromRedis  from db.....");
				DataNodeVO vo = getWechatConfigEntity(mappingDicionCondition.getOrganNo());
				log.info("ApiChannelWxParams getFromRedis from db....."+vo.getOrganNo());
				mappingDicionCondition.setOrganNo(vo.getOrganNo());
				retEntity = findByCondition(mappingDicionCondition);
				redisSharedCache.setObj(redisKey, retEntity);//无时间戳，则永久有效
			}
		}
		return retEntity;
	}
	
	/**
	 * @Title: getWechatConfigOrganNo
	 * @Description: 根据编号查询配置有微信公众号信息的最近的父节点
	 * @param organNo
	 * @return
	 */
	@Override
	public DataNodeVO getWechatConfigEntity(String organNo) {
		DataNodeVO vo = null;
		if(Strings.isNotEmpty(organNo)){
			try {
				RedisKey redisKey = new RedisKey(HfepayConfig.MERCHANT_REDIS_AGENT_FAMILY, HfepayConfig.CHANNEL_REDIS_WX_KEY+organNo);
				vo = (DataNodeVO)redisSharedCache.getObj(redisKey);
				if(vo==null){
					List<String> parentsSeq = new ArrayList<>();
					log.info("to get entity from db.....");
					String seq = channelWxParamsDAO.getSeq(organNo);
					int length = seq.length();
					parentsSeq.add(seq);
					int level = length/4;
					for (int i = 1; i < level; i++) {
						parentsSeq.add(seq.substring(0,4*i));
					}
					vo = channelWxParamsDAO.getWechatConfigEntity(parentsSeq);//查询当前代理商最近的配置有公众号的父节点或者自己
					redisSharedCache.setObj(redisKey, vo);//无时间戳，则永久有效
				}
			} catch (Exception e) {
				log.error("getWechatConfigOrganNo error.",e);
			}
		}
		return vo;
	}
}

