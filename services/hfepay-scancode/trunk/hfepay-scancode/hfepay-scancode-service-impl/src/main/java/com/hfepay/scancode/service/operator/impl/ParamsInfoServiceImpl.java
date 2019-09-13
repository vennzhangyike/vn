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

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.contants.CreditPayStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.dao.ParamsInfoDAO;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONObject;

@Service("paramsInfoService")
public class ParamsInfoServiceImpl implements ParamsInfoService {
	
	@Autowired
    private ParamsInfoDAO paramsInfoDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: PagingResult<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
    @Override
	public PagingResult<ParamsInfo> findPagingResult(ParamsInfoCondition paramsInfoCondition){
		CriteriaBuilder cb = Cnd.builder(ParamsInfo.class);
		if(!Strings.isEmpty(paramsInfoCondition.getId())){
			cb.andEQ("id", paramsInfoCondition.getId());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsKey())){
			cb.andEQ("paramsKey", paramsInfoCondition.getParamsKey());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsType())){
			cb.andEQ("paramsType", paramsInfoCondition.getParamsType());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsValue())){
			cb.andEQ("paramsValue", paramsInfoCondition.getParamsValue());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsStatus())){
			cb.andEQ("paramsStatus", paramsInfoCondition.getParamsStatus());
		}
		if(null != paramsInfoCondition.getCreateTime()){
			cb.andEQ("createTime", paramsInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(paramsInfoCondition.getOperator())){
			cb.andEQ("operator", paramsInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(paramsInfoCondition.getRemark())){
			cb.andLike("remark", paramsInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getTemp1())){
			cb.andEQ("temp1", paramsInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getTemp2())){
			cb.andEQ("temp2", paramsInfoCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(paramsInfoCondition.getFirst()), Long.valueOf(paramsInfoCondition.getLast()));
		return paramsInfoDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: List<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public List<ParamsInfo> findAll(ParamsInfoCondition paramsInfoCondition){
		CriteriaBuilder cb = Cnd.builder(ParamsInfo.class);
		if(!Strings.isEmpty(paramsInfoCondition.getId())){
			cb.andEQ("id", paramsInfoCondition.getId());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsKey())){
			cb.andEQ("paramsKey", paramsInfoCondition.getParamsKey());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsType())){
			cb.andEQ("paramsType", paramsInfoCondition.getParamsType());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsValue())){
			cb.andEQ("paramsValue", paramsInfoCondition.getParamsValue());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getParamsStatus())){
			cb.andEQ("paramsStatus", paramsInfoCondition.getParamsStatus());
		}
		if(null != paramsInfoCondition.getCreateTime()){
			cb.andEQ("createTime", paramsInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(paramsInfoCondition.getOperator())){
			cb.andEQ("operator", paramsInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(paramsInfoCondition.getRemark())){
			cb.andLike("remark", paramsInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getTemp1())){
			cb.andEQ("temp1", paramsInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(paramsInfoCondition.getTemp2())){
			cb.andEQ("temp2", paramsInfoCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return paramsInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: List<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public ParamsInfo findParamsKey(String paramsKey,String keyType){
		CriteriaBuilder cb = Cnd.builder(ParamsInfo.class);
		cb.andEQ("paramsKey", paramsKey);
		cb.andEQ("paramsType", keyType);
		Criteria buildCriteria = cb.buildCriteria();
		ParamsInfo info = paramsInfoDAO.findOneByCriteria(buildCriteria);
		return info;
	}
	
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ParamsInfo
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public ParamsInfo findById(String id){
		return paramsInfoDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long insert(ParamsInfoCondition paramsInfoCondition){
		ParamsInfo paramsInfo = new ParamsInfo();
		paramsInfoCondition.setId(Strings.getUUID());
		paramsInfoCondition.setParamsStatus(CreditPayStatus.CREDIT_PAY_STATUS_OPEN.getCode());
		paramsInfoCondition.setCreateTime(new Date());
		BeanUtils.copyProperties(paramsInfoCondition, paramsInfo);
		return paramsInfoDAO.insert(paramsInfo);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long deleteById(String id){
		return paramsInfoDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return paramsInfoDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return paramsInfoDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long update(ParamsInfoCondition paramsInfoCondition){
		ParamsInfo paramsInfo = new ParamsInfo();
		BeanUtils.copyProperties(paramsInfoCondition, paramsInfo);
		return paramsInfoDAO.update(paramsInfo);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public long updateByCriteria(ParamsInfoCondition paramsInfoCondition,Criteria criteria){
		ParamsInfo paramsInfo = new ParamsInfo();
		BeanUtils.copyProperties(paramsInfoCondition, paramsInfo);
		return paramsInfoDAO.updateByCriteria(paramsInfo,criteria);
	}
	
	/**
	 * @Title: getParamsInfoByDomain
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organNo
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public JSONObject getParamsInfoByDomain(String organNo) {
		JSONObject paramsInfoJson = null;
		ParamsInfo paramsInfo = this.findParamsKey(organNo,ParamsType.PARAMS_DOMAIN_INFO.getCode());
		if (null == paramsInfo) {
			paramsInfo = this.findParamsKey(ScanCodeConstants.SYSTEM,ParamsType.PARAMS_DOMAIN_INFO.getCode());
			paramsInfoJson = JSONObject.fromObject(paramsInfo.getParamsValue());
		}else {
			paramsInfoJson = JSONObject.fromObject(paramsInfo.getParamsValue());
		}
		return paramsInfoJson;
	}
	
}

