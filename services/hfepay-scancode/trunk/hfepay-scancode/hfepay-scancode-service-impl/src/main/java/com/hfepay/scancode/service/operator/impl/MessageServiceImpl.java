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
import com.hfepay.scancode.commons.condition.MessageCondition;
import com.hfepay.scancode.commons.dao.MessageDAO;
import com.hfepay.scancode.commons.entity.Message;
import com.hfepay.scancode.service.operator.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
    private MessageDAO messageDAO;
    
    /**
	 * 列表(分页)
	 * @param messageCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
    @Override
	public PagingResult<Message> findPagingResult(MessageCondition messageCondition){
		CriteriaBuilder cb = Cnd.builder(Message.class);
		if(!Strings.isEmpty(messageCondition.getId())){
			cb.andEQ("id", messageCondition.getId());
		}
		if(!Strings.isEmpty(messageCondition.getChannelNo())){
			cb.andEQ("channelNo", messageCondition.getChannelNo());
		}
		if(!Strings.isEmpty(messageCondition.getUserType())){
			cb.andEQ("userType", messageCondition.getUserType());
		}
		if(!Strings.isEmpty(messageCondition.getTitle())){
			cb.andEQ("title", messageCondition.getTitle());
		}
		if(!Strings.isEmpty(messageCondition.getContent())){
			cb.andEQ("content", messageCondition.getContent());
		}
		if(!Strings.isEmpty(messageCondition.getMessageType())){
			cb.andEQ("messageType", messageCondition.getMessageType());
		}
		if(!Strings.isEmpty(messageCondition.getStatus())){
			cb.andEQ("status", messageCondition.getStatus());
		}
		if(!Strings.isEmpty(messageCondition.getOperator())){
			cb.andEQ("operator", messageCondition.getOperator());
		}
		if(null != messageCondition.getCreateTime()){
			cb.andEQ("createTime", messageCondition.getCreateTime());
		}


		if(!Strings.isEmpty(messageCondition.getRemark())){
			cb.andLike("remark", messageCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(messageCondition.getOrderBy())){
			if(messageCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = messageCondition.getOrderBy().split(",");
				String[] orders = messageCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(messageCondition.getOrderBy(), Order.valueOf(messageCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(messageCondition.getFirst()), Long.valueOf(messageCondition.getLast()));
		return messageDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param message 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public List<Message> findAll(MessageCondition messageCondition){
		CriteriaBuilder cb = Cnd.builder(Message.class);
		if(!Strings.isEmpty(messageCondition.getId())){
			cb.andEQ("id", messageCondition.getId());
		}
		if(!Strings.isEmpty(messageCondition.getChannelNo())){
			cb.andEQ("channelNo", messageCondition.getChannelNo());
		}
		if(!Strings.isEmpty(messageCondition.getUserType())){
			cb.andEQ("userType", messageCondition.getUserType());
		}
		if(!Strings.isEmpty(messageCondition.getTitle())){
			cb.andEQ("title", messageCondition.getTitle());
		}
		if(!Strings.isEmpty(messageCondition.getContent())){
			cb.andEQ("content", messageCondition.getContent());
		}
		if(!Strings.isEmpty(messageCondition.getMessageType())){
			cb.andEQ("messageType", messageCondition.getMessageType());
		}
		if(!Strings.isEmpty(messageCondition.getStatus())){
			cb.andEQ("status", messageCondition.getStatus());
		}
		if(!Strings.isEmpty(messageCondition.getOperator())){
			cb.andEQ("operator", messageCondition.getOperator());
		}
		if(null != messageCondition.getCreateTime()){
			cb.andEQ("createTime", messageCondition.getCreateTime());
		}


		if(!Strings.isEmpty(messageCondition.getRemark())){
			cb.andLike("remark", messageCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return messageDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param message 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public List<Message> findAllByUserType(List<String> list){
		return messageDAO.findAllByUserType(list);
	}
	
	public List<Message> findAllByUserTypeAndUserNo(List<String> list,String userNo){
		return messageDAO.findAllByUserTypeAndUserNo(list,userNo);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public Message findById(String id){
		return messageDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param messageCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long insert(MessageCondition messageCondition){
		Message message = new Message();
		BeanUtils.copyProperties(messageCondition, message);
		return messageDAO.insert(message);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long deleteById(String id){
		return messageDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return messageDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return messageDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long update(MessageCondition messageCondition){
		Message message = new Message();
		BeanUtils.copyProperties(messageCondition, message);
		return messageDAO.update(message);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long updateByCriteria(MessageCondition messageCondition,Criteria criteria){
		Message message = new Message();
		BeanUtils.copyProperties(messageCondition, message);
		return messageDAO.updateByCriteria(message,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	@Override
	public long updateStatus(String id,String status){
		return messageDAO.updateStatus(id,status);
	}	
	
	/**
	 * 列表(分页)
	 * @param messageCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
    @Override
	public PagingResult<Message> findPagingResultByUserType(MessageCondition messageCondition,List<String> list){
		CriteriaBuilder cb = Cnd.builder(Message.class);
		if(!Strings.isEmpty(messageCondition.getId())){
			cb.andEQ("id", messageCondition.getId());
		}
		if(!Strings.isEmpty(messageCondition.getChannelNo())){
			cb.andEQ("channelNo", messageCondition.getChannelNo());
		}
		if(list != null && list.size() > 0){
			cb.andIn("userType", list);
		}
		/*if(!Strings.isEmpty(messageCondition.getUserType())){
			cb.andEQ("userType", messageCondition.getUserType());
		}*/
		if(!Strings.isEmpty(messageCondition.getTitle())){
			cb.andEQ("title", messageCondition.getTitle());
		}
		if(!Strings.isEmpty(messageCondition.getContent())){
			cb.andEQ("content", messageCondition.getContent());
		}
		if(!Strings.isEmpty(messageCondition.getMessageType())){
			cb.andEQ("messageType", messageCondition.getMessageType());
		}
		if(!Strings.isEmpty(messageCondition.getStatus())){
			cb.andEQ("status", messageCondition.getStatus());
		}
		if(!Strings.isEmpty(messageCondition.getOperator())){
			cb.andEQ("operator", messageCondition.getOperator());
		}
		if(null != messageCondition.getCreateTime()){
			cb.andEQ("createTime", messageCondition.getCreateTime());
		}


		if(!Strings.isEmpty(messageCondition.getRemark())){
			cb.andLike("remark", messageCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(messageCondition.getOrderBy())){
			if(messageCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = messageCondition.getOrderBy().split(",");
				String[] orders = messageCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(messageCondition.getOrderBy(), Order.valueOf(messageCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(messageCondition.getFirst()), Long.valueOf(messageCondition.getLast()));
		return messageDAO.findPagingResult(buildCriteria);
	}
}

