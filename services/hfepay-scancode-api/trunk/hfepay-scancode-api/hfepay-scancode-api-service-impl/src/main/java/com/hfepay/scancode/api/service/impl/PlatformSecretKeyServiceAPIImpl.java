/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
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
import com.hfepay.scancode.api.condition.PlatformSecretKeyCondition;
import com.hfepay.scancode.api.dao.PlatformSecretKeyDAO;
import com.hfepay.scancode.api.entity.PlatformSecretKey;
import com.hfepay.scancode.api.service.PlatformSecretKeyService;

@Service
public class PlatformSecretKeyServiceAPIImpl implements PlatformSecretKeyService {
	
	@Autowired
    private PlatformSecretKeyDAO platformSecretKeyDAO;
    
    /**
	 * 列表(分页)
	 * @param platformSecretKeyCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
    @Override
	public PagingResult<PlatformSecretKey> findPagingResult(PlatformSecretKeyCondition platformSecretKeyCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformSecretKey.class);
		if(!Strings.isEmpty(platformSecretKeyCondition.getId())){
			cb.andEQ("id", platformSecretKeyCondition.getId());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getPayCode())){
			cb.andEQ("payCode", platformSecretKeyCondition.getPayCode());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getPayName())){
			cb.andEQ("payName", platformSecretKeyCondition.getPayName());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinUserPublicKey())){
			cb.andEQ("joinUserPublicKey", platformSecretKeyCondition.getJoinUserPublicKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinPublicKey())){
			cb.andEQ("joinPublicKey", platformSecretKeyCondition.getJoinPublicKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinPrivateKey())){
			cb.andEQ("joinPrivateKey", platformSecretKeyCondition.getJoinPrivateKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinKey())){
			cb.andEQ("joinKey", platformSecretKeyCondition.getJoinKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinType())){
			cb.andEQ("joinType", platformSecretKeyCondition.getJoinType());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getStatus())){
			cb.andEQ("status", platformSecretKeyCondition.getStatus());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformSecretKeyCondition.getRecordStatus());
		}
		if(null != platformSecretKeyCondition.getCreateTime()){
			cb.andEQ("createTime", platformSecretKeyCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformSecretKeyCondition.getOperatorId())){
			cb.andEQ("operatorId", platformSecretKeyCondition.getOperatorId());
		}

		if(!Strings.isEmpty(platformSecretKeyCondition.getRemark())){
			cb.andLike("remark", platformSecretKeyCondition.getRemark());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getTemp1())){
			cb.andEQ("temp1", platformSecretKeyCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getTemp2())){
			cb.andEQ("temp2", platformSecretKeyCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(platformSecretKeyCondition.getOrderBy())){
			if(platformSecretKeyCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = platformSecretKeyCondition.getOrderBy().split(",");
				String[] orders = platformSecretKeyCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(platformSecretKeyCondition.getOrderBy(), Order.valueOf(platformSecretKeyCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(platformSecretKeyCondition.getFirst()), Long.valueOf(platformSecretKeyCondition.getLast()));
		return platformSecretKeyDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param platformSecretKey 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public List<PlatformSecretKey> findAll(PlatformSecretKeyCondition platformSecretKeyCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformSecretKey.class);
		if(!Strings.isEmpty(platformSecretKeyCondition.getId())){
			cb.andEQ("id", platformSecretKeyCondition.getId());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getPayCode())){
			cb.andEQ("payCode", platformSecretKeyCondition.getPayCode());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getPayName())){
			cb.andEQ("payName", platformSecretKeyCondition.getPayName());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinUserPublicKey())){
			cb.andEQ("joinUserPublicKey", platformSecretKeyCondition.getJoinUserPublicKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinPublicKey())){
			cb.andEQ("joinPublicKey", platformSecretKeyCondition.getJoinPublicKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinPrivateKey())){
			cb.andEQ("joinPrivateKey", platformSecretKeyCondition.getJoinPrivateKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinKey())){
			cb.andEQ("joinKey", platformSecretKeyCondition.getJoinKey());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getJoinType())){
			cb.andEQ("joinType", platformSecretKeyCondition.getJoinType());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getStatus())){
			cb.andEQ("status", platformSecretKeyCondition.getStatus());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformSecretKeyCondition.getRecordStatus());
		}
		if(null != platformSecretKeyCondition.getCreateTime()){
			cb.andEQ("createTime", platformSecretKeyCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformSecretKeyCondition.getOperatorId())){
			cb.andEQ("operatorId", platformSecretKeyCondition.getOperatorId());
		}

		if(!Strings.isEmpty(platformSecretKeyCondition.getRemark())){
			cb.andLike("remark", platformSecretKeyCondition.getRemark());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getTemp1())){
			cb.andEQ("temp1", platformSecretKeyCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformSecretKeyCondition.getTemp2())){
			cb.andEQ("temp2", platformSecretKeyCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return platformSecretKeyDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public PlatformSecretKey findById(String id){
		return platformSecretKeyDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param platformSecretKeyCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long insert(PlatformSecretKeyCondition platformSecretKeyCondition){
		PlatformSecretKey platformSecretKey = new PlatformSecretKey();
		BeanUtils.copyProperties(platformSecretKeyCondition, platformSecretKey);
		return platformSecretKeyDAO.insert(platformSecretKey);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long deleteById(String id){
		return platformSecretKeyDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return platformSecretKeyDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return platformSecretKeyDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long update(PlatformSecretKeyCondition platformSecretKeyCondition){
		PlatformSecretKey platformSecretKey = new PlatformSecretKey();
		BeanUtils.copyProperties(platformSecretKeyCondition, platformSecretKey);
		return platformSecretKeyDAO.update(platformSecretKey);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long updateByCriteria(PlatformSecretKeyCondition platformSecretKeyCondition,Criteria criteria){
		PlatformSecretKey platformSecretKey = new PlatformSecretKey();
		BeanUtils.copyProperties(platformSecretKeyCondition, platformSecretKey);
		return platformSecretKeyDAO.updateByCriteria(platformSecretKey,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-11 18:56:33
	 */
	@Override
	public long updateStatus(String id,String status){
		return platformSecretKeyDAO.updateStatus(id,status);
	}

	/**
	 * 根据方法名获取密钥
	 */
	@Override
	public PlatformSecretKey findByPayCode(String payCode) {
		CriteriaBuilder criteriaBuilder = Cnd.builder(PlatformSecretKey.class);
		criteriaBuilder.andEQ("payCode", payCode);
		criteriaBuilder.andEQ("status", "1");
		return platformSecretKeyDAO.findOneByCriteria(criteriaBuilder.buildCriteria());
	}	
}

