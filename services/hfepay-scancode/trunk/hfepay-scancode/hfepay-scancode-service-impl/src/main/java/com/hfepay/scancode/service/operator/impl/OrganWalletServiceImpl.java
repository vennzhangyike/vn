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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.dao.OrganWalletDAO;
import com.hfepay.scancode.commons.entity.OrganWallet;
import com.hfepay.scancode.service.operator.OrganWalletService;

@Service("organWalletService")
public class OrganWalletServiceImpl implements OrganWalletService {
	@Autowired
    private OrganWalletDAO organWalletDAO;
    
    /**
	 * 列表(分页)
	 * @param organWalletCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
    @Override
	public PagingResult<OrganWallet> findPagingResult(OrganWalletCondition organWalletCondition){
		CriteriaBuilder cb = Cnd.builder(OrganWallet.class);
		if(!Strings.isEmpty(organWalletCondition.getId())){
			cb.andEQ("id", organWalletCondition.getId());
		}
		if(!Strings.isEmpty(organWalletCondition.getOrganNo())){
			cb.andEQ("organNo", organWalletCondition.getOrganNo());
		}
		if(null != organWalletCondition.getBalance()){
			cb.andEQ("balance", organWalletCondition.getBalance());
		}
		if(null != organWalletCondition.getFreezesAmt()){
			cb.andEQ("freezesAmt", organWalletCondition.getFreezesAmt());
		}
		if(!Strings.isEmpty(organWalletCondition.getStatus())){
			cb.andEQ("status", organWalletCondition.getStatus());
		}
		if(null != organWalletCondition.getCreateTime()){
			cb.andEQ("createTime", organWalletCondition.getCreateTime());
		}

		if(!Strings.isEmpty(organWalletCondition.getOperator())){
			cb.andEQ("operator", organWalletCondition.getOperator());
		}

		if(!Strings.isEmpty(organWalletCondition.getRemark())){
			cb.andLike("remark", organWalletCondition.getRemark());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp1())){
			cb.andEQ("temp1", organWalletCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp2())){
			cb.andEQ("temp2", organWalletCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(organWalletCondition.getFirst()), Long.valueOf(organWalletCondition.getLast()));
		return organWalletDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param organWallet 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public List<OrganWallet> findAll(OrganWalletCondition organWalletCondition){
		CriteriaBuilder cb = Cnd.builder(OrganWallet.class);
		if(!Strings.isEmpty(organWalletCondition.getId())){
			cb.andEQ("id", organWalletCondition.getId());
		}
		if(!Strings.isEmpty(organWalletCondition.getOrganNo())){
			cb.andEQ("organNo", organWalletCondition.getOrganNo());
		}
		if(null != organWalletCondition.getBalance()){
			cb.andEQ("balance", organWalletCondition.getBalance());
		}
		if(null != organWalletCondition.getFreezesAmt()){
			cb.andEQ("freezesAmt", organWalletCondition.getFreezesAmt());
		}
		if(!Strings.isEmpty(organWalletCondition.getStatus())){
			cb.andEQ("status", organWalletCondition.getStatus());
		}
		if(null != organWalletCondition.getCreateTime()){
			cb.andEQ("createTime", organWalletCondition.getCreateTime());
		}

		if(!Strings.isEmpty(organWalletCondition.getOperator())){
			cb.andEQ("operator", organWalletCondition.getOperator());
		}

		if(!Strings.isEmpty(organWalletCondition.getRemark())){
			cb.andLike("remark", organWalletCondition.getRemark());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp1())){
			cb.andEQ("temp1", organWalletCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp2())){
			cb.andEQ("temp2", organWalletCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organWalletDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public OrganWallet findById(String id){
		return organWalletDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param organWalletCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long insert(OrganWalletCondition organWalletCondition){
		OrganWallet organWallet = new OrganWallet();
		BeanUtils.copyProperties(organWalletCondition, organWallet);
		if(Strings.isEmpty(organWalletCondition.getId())){
			organWallet.setId(Strings.getUUID());
		}
		return organWalletDAO.insert(organWallet);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long deleteById(String id){
		return organWalletDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return organWalletDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return organWalletDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long update(OrganWalletCondition organWalletCondition){
		OrganWallet organWallet = new OrganWallet();
		BeanUtils.copyProperties(organWalletCondition, organWallet);
		return organWalletDAO.update(organWallet);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long updateByCriteria(OrganWalletCondition organWalletCondition,Criteria criteria){
		OrganWallet organWallet = new OrganWallet();
		BeanUtils.copyProperties(organWalletCondition, organWallet);
		return organWalletDAO.updateByCriteria(organWallet,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param organWalletCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public OrganWallet findByCondition(OrganWalletCondition organWalletCondition){
		CriteriaBuilder cb = Cnd.builder(OrganWallet.class);
		if(!Strings.isEmpty(organWalletCondition.getId())){
			cb.andEQ("id", organWalletCondition.getId());
		}
		if(!Strings.isEmpty(organWalletCondition.getOrganNo())){
			cb.andEQ("organNo", organWalletCondition.getOrganNo());
		}
		if(null != organWalletCondition.getBalance()){
			cb.andEQ("balance", organWalletCondition.getBalance());
		}
		if(null != organWalletCondition.getFreezesAmt()){
			cb.andEQ("freezesAmt", organWalletCondition.getFreezesAmt());
		}
		if(!Strings.isEmpty(organWalletCondition.getStatus())){
			cb.andEQ("status", organWalletCondition.getStatus());
		}
		if(null != organWalletCondition.getCreateTime()){
			cb.andEQ("createTime", organWalletCondition.getCreateTime());
		}

		if(!Strings.isEmpty(organWalletCondition.getOperator())){
			cb.andEQ("operator", organWalletCondition.getOperator());
		}

		if(!Strings.isEmpty(organWalletCondition.getRemark())){
			cb.andLike("remark", organWalletCondition.getRemark());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp1())){
			cb.andEQ("temp1", organWalletCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWalletCondition.getTemp2())){
			cb.andEQ("temp2", organWalletCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organWalletDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long updateBalance(OrganWalletCondition organWalletCondition) {
		return organWalletDAO.updateBalance(organWalletCondition);
	}
	
	@Override
	public long updateBalanceToWallet(String date) {
		return organWalletDAO.updateBalanceToWallet(date);
		
	}
	
	@Override
	public long updateBalanceToChannel(String date) {
		return organWalletDAO.updateBalanceToChannel(date);
	}
}

