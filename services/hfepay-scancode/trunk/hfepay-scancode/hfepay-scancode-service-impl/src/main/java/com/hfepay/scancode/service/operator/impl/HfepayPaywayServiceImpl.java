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
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.dao.HfepayPaywayDAO;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.service.operator.HfepayPaywayService;

@Service("hfepayPaywayService")
public class HfepayPaywayServiceImpl implements HfepayPaywayService {
	
	@Autowired
    private HfepayPaywayDAO hfepayPaywayDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: PagingResult<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
    @Override
	public PagingResult<HfepayPayway> findPagingResult(HfepayPaywayCondition hfepayPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(HfepayPayway.class);
		if(!Strings.isEmpty(hfepayPaywayCondition.getId())){
			cb.andEQ("id", hfepayPaywayCondition.getId());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerNo())){
			cb.andEQ("serverNo", hfepayPaywayCondition.getServerNo());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerName())){
			cb.andLike("serverName", hfepayPaywayCondition.getServerName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayCode())){
			cb.andEQ("payCode", hfepayPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayName())){
			cb.andLike("payName", hfepayPaywayCondition.getPayName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayType())){
			cb.andEQ("payType", hfepayPaywayCondition.getPayType());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayDesc())){
			cb.andEQ("payDesc", hfepayPaywayCondition.getPayDesc());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPaySeq())){
			cb.andEQ("paySeq", hfepayPaywayCondition.getPaySeq());
		}
		if(null != hfepayPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", hfepayPaywayCondition.getT0Rate());
		}
		if(null != hfepayPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", hfepayPaywayCondition.getT1Rate());
		}
		if(null != hfepayPaywayCondition.getRate()){
			cb.andEQ("rate", hfepayPaywayCondition.getRate());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", hfepayPaywayCondition.getRecordStatus());
		}
		if(null != hfepayPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", hfepayPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getOperator())){
			cb.andEQ("operator", hfepayPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getRemark())){
			cb.andLike("remark", hfepayPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp1())){
			cb.andEQ("temp1", hfepayPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp2())){
			cb.andEQ("temp2", hfepayPaywayCondition.getTemp2());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp3())){
			cb.andEQ("temp3", hfepayPaywayCondition.getTemp3());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp4())){
			cb.andEQ("temp4", hfepayPaywayCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(hfepayPaywayCondition.getFirst()), Long.valueOf(hfepayPaywayCondition.getLast()));
		return hfepayPaywayDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: List<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public List<HfepayPayway> findAll(HfepayPaywayCondition hfepayPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(HfepayPayway.class);
		if(!Strings.isEmpty(hfepayPaywayCondition.getId())){
			cb.andEQ("id", hfepayPaywayCondition.getId());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerNo())){
			cb.andEQ("serverNo", hfepayPaywayCondition.getServerNo());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerName())){
			cb.andLike("serverName", hfepayPaywayCondition.getServerName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayCode())){
			cb.andEQ("payCode", hfepayPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayName())){
			cb.andLike("payName", hfepayPaywayCondition.getPayName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayType())){
			cb.andEQ("payType", hfepayPaywayCondition.getPayType());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayDesc())){
			cb.andEQ("payDesc", hfepayPaywayCondition.getPayDesc());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPaySeq())){
			cb.andEQ("paySeq", hfepayPaywayCondition.getPaySeq());
		}
		if(null != hfepayPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", hfepayPaywayCondition.getT0Rate());
		}
		if(null != hfepayPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", hfepayPaywayCondition.getT1Rate());
		}
		if(null != hfepayPaywayCondition.getRate()){
			cb.andEQ("rate", hfepayPaywayCondition.getRate());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", hfepayPaywayCondition.getRecordStatus());
		}
		if(null != hfepayPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", hfepayPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getOperator())){
			cb.andEQ("operator", hfepayPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getRemark())){
			cb.andLike("remark", hfepayPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp1())){
			cb.andEQ("temp1", hfepayPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp2())){
			cb.andEQ("temp2", hfepayPaywayCondition.getTemp2());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp3())){
			cb.andEQ("temp3", hfepayPaywayCondition.getTemp3());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp4())){
			cb.andEQ("temp4", hfepayPaywayCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return hfepayPaywayDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: HfepayPayway
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public HfepayPayway findById(String id){
		return hfepayPaywayDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long insert(HfepayPaywayCondition hfepayPaywayCondition){
		HfepayPayway hfepayPayway = new HfepayPayway();
		BeanUtils.copyProperties(hfepayPaywayCondition, hfepayPayway);
		return hfepayPaywayDAO.insert(hfepayPayway);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long deleteById(String id){
		return hfepayPaywayDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return hfepayPaywayDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return hfepayPaywayDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long update(HfepayPaywayCondition hfepayPaywayCondition){
		HfepayPayway hfepayPayway = new HfepayPayway();
		BeanUtils.copyProperties(hfepayPaywayCondition, hfepayPayway);
		hfepayPayway.setUpdateTime(new Date());
		return hfepayPaywayDAO.update(hfepayPayway);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public long updateByCriteria(HfepayPaywayCondition hfepayPaywayCondition,Criteria criteria){
		HfepayPayway hfepayPayway = new HfepayPayway();
		BeanUtils.copyProperties(hfepayPaywayCondition, hfepayPayway);
		return hfepayPaywayDAO.updateByCriteria(hfepayPayway,criteria);
	}

	@Override
	public HfepayPayway findByPayCode(String payCode) {
		CriteriaBuilder cb = Cnd.builder(HfepayPayway.class);
		cb.andEQ("payCode", payCode);
		Criteria buildCriteria = cb.buildCriteria();
		return hfepayPaywayDAO.findOneByCriteria(buildCriteria);
	}
	
}

