package com.hfepay.pay.service.impl;

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
import com.hfepay.pay.service.ChannelSecretKeyService;
import com.hfepay.scancode.commons.condition.ChannelSecretKeyCondition;
import com.hfepay.scancode.commons.dao.ChannelSecretKeyDAO;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;

/** 
 * @author lemon
 * @Date 2016年9月13日 下午8:01:50 
 */
@Service("channelSecretKeyService")
public class ChannelSecretKeyServiceImpl implements ChannelSecretKeyService {
	
	@Autowired
	ChannelSecretKeyDAO channelSecretKeyDAO;

	@Override
	public ChannelSecretKey query(ChannelSecretKeyCondition condition) {
		CriteriaBuilder cb = Cnd.builder(ChannelSecretKey.class);
		if(!Strings.isEmpty(condition.getChannelNo())){
			cb.andEQ("channelNo", condition.getChannelNo());
		}
		if(!Strings.isEmpty(condition.getPayCode())){
			cb.andEQ("payCode", condition.getPayCode());
		}
		if(!Strings.isEmpty(condition.getBoundIp())){
			cb.andLike("boundIp", condition.getBoundIp());
		}
		Criteria criteria = cb.buildCriteria();
		return channelSecretKeyDAO.findOneByCriteria(criteria);
	}

	/**
	 * 列表(分页)
	 * @param channelSecretKeyCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
    @Override
	public PagingResult<ChannelSecretKey> findPagingResult(ChannelSecretKeyCondition channelSecretKeyCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelSecretKey.class);
		if(!Strings.isEmpty(channelSecretKeyCondition.getId())){
			cb.andEQ("id", channelSecretKeyCondition.getId());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getChannelNo())){
			cb.andEQ("channelNo", channelSecretKeyCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getCompanyName())){
			cb.andEQ("companyName", channelSecretKeyCondition.getCompanyName());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getChannelType())){
			cb.andEQ("channelType", channelSecretKeyCondition.getChannelType());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getFirstPayobj())){
			cb.andEQ("firstPayobj", channelSecretKeyCondition.getFirstPayobj());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getPayCode())){
			cb.andEQ("payCode", channelSecretKeyCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getPayName())){
			cb.andEQ("payName", channelSecretKeyCondition.getPayName());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getBoundIp())){
			cb.andEQ("boundIp", channelSecretKeyCondition.getBoundIp());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinUserPublicKey())){
			cb.andEQ("joinUserPublicKey", channelSecretKeyCondition.getJoinUserPublicKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinPublicKey())){
			cb.andEQ("joinPublicKey", channelSecretKeyCondition.getJoinPublicKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinPrivateKey())){
			cb.andEQ("joinPrivateKey", channelSecretKeyCondition.getJoinPrivateKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinKey())){
			cb.andEQ("joinKey", channelSecretKeyCondition.getJoinKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinType())){
			cb.andEQ("joinType", channelSecretKeyCondition.getJoinType());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getStatus())){
			cb.andEQ("status", channelSecretKeyCondition.getStatus());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelSecretKeyCondition.getRecordStatus());
		}
		if(null != channelSecretKeyCondition.getCreateTime()){
			cb.andEQ("createTime", channelSecretKeyCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelSecretKeyCondition.getOperatorId())){
			cb.andEQ("operatorId", channelSecretKeyCondition.getOperatorId());
		}

		if(!Strings.isEmpty(channelSecretKeyCondition.getRemark())){
			cb.andLike("remark", channelSecretKeyCondition.getRemark());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getTemp1())){
			cb.andEQ("temp1", channelSecretKeyCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getTemp2())){
			cb.andEQ("temp2", channelSecretKeyCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(channelSecretKeyCondition.getOrderBy())){
			if(channelSecretKeyCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = channelSecretKeyCondition.getOrderBy().split(",");
				String[] orders = channelSecretKeyCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(channelSecretKeyCondition.getOrderBy(), Order.valueOf(channelSecretKeyCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelSecretKeyCondition.getFirst()), Long.valueOf(channelSecretKeyCondition.getLast()));
		return channelSecretKeyDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param channelSecretKey 
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public List<ChannelSecretKey> findAll(ChannelSecretKeyCondition channelSecretKeyCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelSecretKey.class);
		if(!Strings.isEmpty(channelSecretKeyCondition.getId())){
			cb.andEQ("id", channelSecretKeyCondition.getId());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getChannelNo())){
			cb.andEQ("channelNo", channelSecretKeyCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getCompanyName())){
			cb.andEQ("companyName", channelSecretKeyCondition.getCompanyName());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getChannelType())){
			cb.andEQ("channelType", channelSecretKeyCondition.getChannelType());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getFirstPayobj())){
			cb.andEQ("firstPayobj", channelSecretKeyCondition.getFirstPayobj());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getPayCode())){
			cb.andEQ("payCode", channelSecretKeyCondition.getPayCode());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getPayName())){
			cb.andEQ("payName", channelSecretKeyCondition.getPayName());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getBoundIp())){
			cb.andEQ("boundIp", channelSecretKeyCondition.getBoundIp());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinUserPublicKey())){
			cb.andEQ("joinUserPublicKey", channelSecretKeyCondition.getJoinUserPublicKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinPublicKey())){
			cb.andEQ("joinPublicKey", channelSecretKeyCondition.getJoinPublicKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinPrivateKey())){
			cb.andEQ("joinPrivateKey", channelSecretKeyCondition.getJoinPrivateKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinKey())){
			cb.andEQ("joinKey", channelSecretKeyCondition.getJoinKey());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getJoinType())){
			cb.andEQ("joinType", channelSecretKeyCondition.getJoinType());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getStatus())){
			cb.andEQ("status", channelSecretKeyCondition.getStatus());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelSecretKeyCondition.getRecordStatus());
		}
		if(null != channelSecretKeyCondition.getCreateTime()){
			cb.andEQ("createTime", channelSecretKeyCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelSecretKeyCondition.getOperatorId())){
			cb.andEQ("operatorId", channelSecretKeyCondition.getOperatorId());
		}

		if(!Strings.isEmpty(channelSecretKeyCondition.getRemark())){
			cb.andLike("remark", channelSecretKeyCondition.getRemark());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getTemp1())){
			cb.andEQ("temp1", channelSecretKeyCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelSecretKeyCondition.getTemp2())){
			cb.andEQ("temp2", channelSecretKeyCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelSecretKeyDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public ChannelSecretKey findById(String id){
		return channelSecretKeyDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param channelSecretKeyCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long insert(ChannelSecretKeyCondition channelSecretKeyCondition){
		ChannelSecretKey channelSecretKey = new ChannelSecretKey();
		BeanUtils.copyProperties(channelSecretKeyCondition, channelSecretKey);
		return channelSecretKeyDAO.insert(channelSecretKey);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long deleteById(String id){
		return channelSecretKeyDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelSecretKeyDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelSecretKeyDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long update(ChannelSecretKeyCondition channelSecretKeyCondition){
		ChannelSecretKey channelSecretKey = new ChannelSecretKey();
		BeanUtils.copyProperties(channelSecretKeyCondition, channelSecretKey);
		return channelSecretKeyDAO.update(channelSecretKey);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-14 10:05:58
	 */
	@Override
	public long updateByCriteria(ChannelSecretKeyCondition channelSecretKeyCondition,Criteria criteria){
		ChannelSecretKey channelSecretKey = new ChannelSecretKey();
		BeanUtils.copyProperties(channelSecretKeyCondition, channelSecretKey);
		return channelSecretKeyDAO.updateByCriteria(channelSecretKey,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-09-24 17:41:30
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelSecretKeyDAO.updateStatus(id,status);
	}
}
