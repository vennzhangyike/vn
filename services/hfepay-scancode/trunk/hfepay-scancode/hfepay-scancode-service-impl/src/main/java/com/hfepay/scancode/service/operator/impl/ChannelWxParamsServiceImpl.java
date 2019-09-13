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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelWxParamsCondition;
import com.hfepay.scancode.commons.dao.ChannelWxParamsDAO;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;

@Service("channelWxParamsService")
public class ChannelWxParamsServiceImpl implements ChannelWxParamsService {
	
	@Autowired
    private ChannelWxParamsDAO channelWxParamsDAO;
    
    /**
	 * 列表(分页)
	 * @param channelWxParamsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
    @Override
	public PagingResult<ChannelWxParams> findPagingResult(ChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelWxParams.class);
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
	public List<ChannelWxParams> findAll(ChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelWxParams.class);
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
	public ChannelWxParams findById(String id){
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
	public long insert(ChannelWxParamsCondition channelWxParamsCondition){
		ChannelWxParams channelWxParams = new ChannelWxParams();
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
	public long update(ChannelWxParamsCondition channelWxParamsCondition){
		ChannelWxParams channelWxParams = new ChannelWxParams();
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
	public long updateByCriteria(ChannelWxParamsCondition channelWxParamsCondition,Criteria criteria){
		ChannelWxParams channelWxParams = new ChannelWxParams();
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
	 * @Description: organNo机构编号查找
	 * @author: Ricky
	 * @param organNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public ChannelWxParams findByChannelNo(String organNo){
		if(Strings.isEmpty(organNo)){
			throw new RuntimeException("机构编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(ChannelWxParams.class);
		cb.andEQ("organNo", organNo);
		cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
		Criteria buildCriteria = cb.buildCriteria();
		return channelWxParamsDAO.findOneByCriteria(buildCriteria);
	}
	/**
	 * 单个实体对象
	 * @param channelWxParamsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-09 14:30:08
	 */
	@Override
	public ChannelWxParams findByCondition(ChannelWxParamsCondition channelWxParamsCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelWxParams.class);
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
}

