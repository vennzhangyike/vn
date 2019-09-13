/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.dao.ApiMappingDicionDAO;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.config.HfepayConfig;

@Service("apiMappingDicionService")
public class ApiMappingDicionServiceImpl implements ApiMappingDicionService {
	public static final Logger log = LoggerFactory.getLogger(ApiChannelWxParamsServiceImpl.class);

	@Autowired
    private ApiMappingDicionDAO mappingDicionDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: PagingResult<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
    @Override
	public PagingResult<ApiMappingDicion> findPagingResult(ApiMappingDicionCondition mappingDicionCondition){
		CriteriaBuilder cb = Cnd.builder(ApiMappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andLike("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andLike("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(mappingDicionCondition.getFirst()), Long.valueOf(mappingDicionCondition.getLast()));
		return mappingDicionDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: List<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public List<ApiMappingDicion> findAll(ApiMappingDicionCondition mappingDicionCondition){
		CriteriaBuilder cb = Cnd.builder(ApiMappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andEQ("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andEQ("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return mappingDicionDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ApiMappingDicion
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public ApiMappingDicion findById(String id){
		return mappingDicionDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long insert(ApiMappingDicionCondition mappingDicionCondition){
		ApiMappingDicion mappingDicion = new ApiMappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		return mappingDicionDAO.insert(mappingDicion);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long deleteById(String id){
		return mappingDicionDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return mappingDicionDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return mappingDicionDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long update(ApiMappingDicionCondition mappingDicionCondition){
		ApiMappingDicion mappingDicion = new ApiMappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		mappingDicion.setUpdateTime(new Date());
		return mappingDicionDAO.update(mappingDicion);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long updateByCriteria(ApiMappingDicionCondition mappingDicionCondition,Criteria criteria){
		ApiMappingDicion mappingDicion = new ApiMappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		return mappingDicionDAO.updateByCriteria(mappingDicion,criteria);
	}

	/**
	 * @Title: findByCondition
	 * @Description: 查询单个字典信息
	 * @param mappingDicionCondition
	 * @return
	 * @see com.hfepay.scancode.ApiMappingDicionService.operator.MappingDicionService#findByCondition(com.hfepay.scancode.ApiMappingDicionCondition.MappingDicionCondition)
	 */
	@Override
	public ApiMappingDicion findByCondition(ApiMappingDicionCondition mappingDicionCondition) {
		CriteriaBuilder cb = Cnd.builder(ApiMappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andEQ("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andEQ("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return mappingDicionDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: getFromRedis
	 * @Description: 从redis中获取实体
	 * @param mappingDicionCondition
	 * @return
	 * @throws Exception 
	 * @see com.hfepay.scancode.api.service.ApiMappingDicionService#getFromRedis(com.hfepay.scancode.api.condition.ApiMappingDicionCondition)
	 */
	@Override
	public ApiMappingDicion getFromRedis(ApiMappingDicionCondition mappingDicionCondition) throws Exception {
		// TODO Auto-generated method stub
		List<String> redisEntityKey = Arrays.asList(HfepayConfig.DIC_KEY);
		ApiMappingDicion retEntity = null;
		if(Strings.isNotEmpty(mappingDicionCondition.getKeyNo()) && redisEntityKey.contains(mappingDicionCondition.getKeyNo())){
//			RedisKey redisKey = new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY, HfepayConfig.CHANNEL_REDIS_DC_KEY+mappingDicionCondition.getKeyNo());
//			retEntity = (ApiMappingDicion)redisSharedCache.getObj(redisKey);
//			if(retEntity==null){
				log.info("to get entity from db.....");
				retEntity = findByCondition(mappingDicionCondition);
//				redisSharedCache.setObj(redisKey, retEntity);//无时间戳，则永久有效
//			}
		}
		return retEntity;
	}
}

