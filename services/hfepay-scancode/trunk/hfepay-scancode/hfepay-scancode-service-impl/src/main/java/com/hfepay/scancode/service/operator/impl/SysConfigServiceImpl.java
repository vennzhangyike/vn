package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.scancode.commons.condition.SysConfigCondition;
import com.hfepay.scancode.commons.dao.SysConfigDAO;
import com.hfepay.scancode.commons.entity.SysConfig;
import com.hfepay.scancode.service.operator.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	SysConfigDAO sysConfigDAO;

	@Override
	public List<SysConfig> getSysConfig(String keyNo) throws Exception {
		CriteriaBuilder cb = Cnd.builder(SysConfig.class);
		cb.andEQ("keyNo", keyNo);
		Criteria criteria = cb.buildCriteria();
		return sysConfigDAO.findByCriteria(criteria);
	}

	@Override
	public SysConfig getSyconfigByKeyNoAndValue(String keyNo, String paraVal) throws Exception {
		CriteriaBuilder cb = Cnd.builder(SysConfig.class);
		cb.andEQ("keyNo", keyNo);
		cb.andEQ("paraVal", paraVal);
		Criteria criteria = cb.buildCriteria();
		return sysConfigDAO.findOneByCriteria(criteria);
	}

	/**
	 * 列表(分页)
	 * @param sysConfigCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
    @Override
	public PagingResult<SysConfig> findPagingResult(SysConfigCondition sysConfigCondition){
		CriteriaBuilder cb = Cnd.builder(SysConfig.class);
		if(!Strings.isEmpty(sysConfigCondition.getId())){
			cb.andEQ("id", sysConfigCondition.getId());
		}
		if(!Strings.isEmpty(sysConfigCondition.getName())){
			cb.andLike("name", sysConfigCondition.getName());
		}
		if(!Strings.isEmpty(sysConfigCondition.getChannelCode())){
			cb.andEQ("channelCode", sysConfigCondition.getChannelCode());
		}
		if(!Strings.isEmpty(sysConfigCondition.getKeyNo())){
			cb.andLike("keyNo", sysConfigCondition.getKeyNo());
		}
		if(!Strings.isEmpty(sysConfigCondition.getParaName())){
			cb.andLike("paraName", sysConfigCondition.getParaName());
		}
		if(!Strings.isEmpty(sysConfigCondition.getParaVal())){
			cb.andLike("paraVal", sysConfigCondition.getParaVal());
		}
		if(!Strings.isEmpty(sysConfigCondition.getRecordStatus())){
			cb.andEQ("recordStatus", sysConfigCondition.getRecordStatus());
		}
		if(null != sysConfigCondition.getCreateTime()){
			cb.andEQ("createTime", sysConfigCondition.getCreateTime());
		}

		if(!Strings.isEmpty(sysConfigCondition.getOperatorId())){
			cb.andEQ("operatorId", sysConfigCondition.getOperatorId());
		}

		if(!Strings.isEmpty(sysConfigCondition.getRemark())){
			cb.andLike("remark", sysConfigCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(sysConfigCondition.getOrderBy())){
			if(sysConfigCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = sysConfigCondition.getOrderBy().split(",");
				String[] orders = sysConfigCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(sysConfigCondition.getOrderBy(), Order.valueOf(sysConfigCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(sysConfigCondition.getFirst()), Long.valueOf(sysConfigCondition.getLast()));
		return sysConfigDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param sysConfig 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public List<SysConfig> findAll(SysConfigCondition sysConfigCondition){
		CriteriaBuilder cb = Cnd.builder(SysConfig.class);
		if(!Strings.isEmpty(sysConfigCondition.getId())){
			cb.andEQ("id", sysConfigCondition.getId());
		}
		if(!Strings.isEmpty(sysConfigCondition.getName())){
			cb.andLike("name", sysConfigCondition.getName());
		}
		if(!Strings.isEmpty(sysConfigCondition.getKeyNo())){
			cb.andLike("keyNo", sysConfigCondition.getKeyNo());
		}
		if(!Strings.isEmpty(sysConfigCondition.getChannelCode())){
			cb.andEQ("channelCode", sysConfigCondition.getChannelCode());
		}
		if(!Strings.isEmpty(sysConfigCondition.getParaName())){
			cb.andLike("paraName", sysConfigCondition.getParaName());
		}
		if(!Strings.isEmpty(sysConfigCondition.getParaVal())){
			cb.andLike("paraVal", sysConfigCondition.getParaVal());
		}
		if(!Strings.isEmpty(sysConfigCondition.getRecordStatus())){
			cb.andEQ("recordStatus", sysConfigCondition.getRecordStatus());
		}
		if(null != sysConfigCondition.getCreateTime()){
			cb.andEQ("createTime", sysConfigCondition.getCreateTime());
		}

		if(!Strings.isEmpty(sysConfigCondition.getOperatorId())){
			cb.andEQ("operatorId", sysConfigCondition.getOperatorId());
		}

		if(!Strings.isEmpty(sysConfigCondition.getRemark())){
			cb.andLike("remark", sysConfigCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return sysConfigDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public SysConfig findById(String id){
		return sysConfigDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param sysConfigCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long insert(SysConfigCondition sysConfigCondition){
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(sysConfigCondition, sysConfig);
		return sysConfigDAO.insert(sysConfig);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long deleteById(String id){
		return sysConfigDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return sysConfigDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return sysConfigDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long update(SysConfigCondition sysConfigCondition){
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(sysConfigCondition, sysConfig);
		return sysConfigDAO.update(sysConfig);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	@Override
	public long updateByCriteria(SysConfigCondition sysConfigCondition,Criteria criteria){
		SysConfig sysConfig = new SysConfig();
		BeanUtils.copyProperties(sysConfigCondition, sysConfig);
		return sysConfigDAO.updateByCriteria(sysConfig,criteria);
	}
	
}
