/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.scancode.commons.condition.UserMessageCondition;
import com.hfepay.scancode.commons.dao.UserMessageDAO;
import com.hfepay.scancode.commons.entity.UserMessage;
import com.hfepay.scancode.service.operator.UserMessageService;

@Service("userMessageService")
public class UserMessageServiceImpl implements UserMessageService {
	
	@Autowired
    private UserMessageDAO userMessageDAO;
    
    /**
	 * 列表(分页)
	 * @param userMessageCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
    @Override
	public PagingResult<UserMessage> findPagingResult(UserMessageCondition userMessageCondition){
		CriteriaBuilder cb = Cnd.builder(UserMessage.class);
		if(!Strings.isEmpty(userMessageCondition.getId())){
			cb.andEQ("id", userMessageCondition.getId());
		}
		if(!Strings.isEmpty(userMessageCondition.getMessageId())){
			cb.andEQ("messageId", userMessageCondition.getMessageId());
		}
		if(!Strings.isEmpty(userMessageCondition.getUserNo())){
			cb.andEQ("userNo", userMessageCondition.getUserNo());
		}
		if(!Strings.isEmpty(userMessageCondition.getRecordStatus())){
			cb.andEQ("recordStatus", userMessageCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(userMessageCondition.getReadStatus())){
			cb.andEQ("readStatus", userMessageCondition.getReadStatus());
		}
		if(!Strings.isEmpty(userMessageCondition.getOperator())){
			cb.andEQ("operator", userMessageCondition.getOperator());
		}
		if(null != userMessageCondition.getCreateTime()){
			cb.andEQ("createTime", userMessageCondition.getCreateTime());
		}


		if(!Strings.isEmpty(userMessageCondition.getRemark())){
			cb.andLike("remark", userMessageCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(userMessageCondition.getOrderBy())){
			if(userMessageCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = userMessageCondition.getOrderBy().split(",");
				String[] orders = userMessageCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(userMessageCondition.getOrderBy(), Order.valueOf(userMessageCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(userMessageCondition.getFirst()), Long.valueOf(userMessageCondition.getLast()));
		return userMessageDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param userMessage 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public List<UserMessage> findAll(UserMessageCondition userMessageCondition){
		CriteriaBuilder cb = Cnd.builder(UserMessage.class);
		if(!Strings.isEmpty(userMessageCondition.getId())){
			cb.andEQ("id", userMessageCondition.getId());
		}
		if(!Strings.isEmpty(userMessageCondition.getMessageId())){
			cb.andEQ("messageId", userMessageCondition.getMessageId());
		}
		if(!Strings.isEmpty(userMessageCondition.getUserNo())){
			cb.andEQ("userNo", userMessageCondition.getUserNo());
		}
		if(!Strings.isEmpty(userMessageCondition.getReadStatus())){
			cb.andEQ("readStatus", userMessageCondition.getReadStatus());
		}
		if(!Strings.isEmpty(userMessageCondition.getRecordStatus())){
			cb.andEQ("recordStatus", userMessageCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(userMessageCondition.getOperator())){
			cb.andEQ("operator", userMessageCondition.getOperator());
		}
		if(null != userMessageCondition.getCreateTime()){
			cb.andEQ("createTime", userMessageCondition.getCreateTime());
		}


		if(!Strings.isEmpty(userMessageCondition.getRemark())){
			cb.andLike("remark", userMessageCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return userMessageDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public UserMessage findById(String id){
		return userMessageDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param userMessageCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long insert(UserMessageCondition userMessageCondition){
		UserMessage userMessage = new UserMessage();
		BeanUtils.copyProperties(userMessageCondition, userMessage);
		return userMessageDAO.insert(userMessage);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long deleteById(String id){
		return userMessageDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return userMessageDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 根据Id删除
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long batchDeleteById(List<String> list){
		CriteriaBuilder cb = Cnd.builder(UserMessage.class);
		if(list != null && list.size() > 0){
			cb.andIn("id", list);
		}
		Criteria buildCriteria = cb.buildCriteria();
		return userMessageDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return userMessageDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long update(UserMessageCondition userMessageCondition){
		UserMessage userMessage = new UserMessage();
		BeanUtils.copyProperties(userMessageCondition, userMessage);
		return userMessageDAO.update(userMessage);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	@Override
	public long updateByCriteria(UserMessageCondition userMessageCondition,Criteria criteria){
		UserMessage userMessage = new UserMessage();
		BeanUtils.copyProperties(userMessageCondition, userMessage);
		return userMessageDAO.updateByCriteria(userMessage,criteria);
	}

	@Override
	public UserMessage findByMessageId(String messageId) {
		return userMessageDAO.findByMessageId(messageId);
	}
	
	@Override
	public UserMessage findByMessageIdByUserNo(String messageId,String userName) {
		return userMessageDAO.findByMessageIdAndUserNo(messageId,userName);
	}

	@Override
	public PagingResult<UserMessage> findByUserType(UserMessageCondition userMessageCondition,List<String> list) {
		CriteriaBuilder cb = Cnd.builder(UserMessage.class);
		if(!Strings.isEmpty(userMessageCondition.getUserNo())){
			cb.andEQ("userNo", userMessageCondition.getUserNo());
		}
		if(!Strings.isEmpty(userMessageCondition.getReadStatus())){
			cb.andEQ("readStatus", userMessageCondition.getReadStatus());
		}
		if(!Strings.isEmpty(userMessageCondition.getRecordStatus())){
			cb.andEQ("recordStatus", userMessageCondition.getRecordStatus());
		}
		if(list != null && list.size() > 0){
			cb.andIn("userType", list);
		}
		Criteria buildCriteria = cb.buildCriteria();
		return userMessageDAO.findByUserType(buildCriteria);
	}

}

