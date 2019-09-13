package com.hfepay.scancode.service.operator.impl;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

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
import com.hfepay.scancode.commons.condition.MerchantWalletCondition;
import com.hfepay.scancode.commons.dao.MerchantWalletDAO;
import com.hfepay.scancode.commons.entity.MerchantWallet;
import com.hfepay.scancode.service.operator.MerchantAccountsService;
import com.hfepay.scancode.service.operator.MerchantWalletService;

@Service("merchantWalletService")
public class MerchantWalletServiceImpl implements MerchantWalletService {
	
	@Autowired
    private MerchantWalletDAO merchantWalletDAO;
	@Autowired
	private MerchantAccountsService merchantAccountsService;
    
    /**
	 * 列表(分页)
	 * @param merchantWalletCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
    @Override
	public PagingResult<MerchantWallet> findPagingResult(MerchantWalletCondition merchantWalletCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantWallet.class);
		if(!Strings.isEmpty(merchantWalletCondition.getId())){
			cb.andEQ("id", merchantWalletCondition.getId());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantWalletCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getMerchantName())){
			cb.andLike("merchantName", merchantWalletCondition.getMerchantName());
		}
		if(null != merchantWalletCondition.getBalance()){
			cb.andEQ("balance", merchantWalletCondition.getBalance());
		}
		if(null != merchantWalletCondition.getFreezesAmt()){
			cb.andEQ("freezesAmt", merchantWalletCondition.getFreezesAmt());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getStatus())){
			cb.andEQ("status", merchantWalletCondition.getStatus());
		}
		if(null != merchantWalletCondition.getCreateTime()){
			cb.andEQ("createTime", merchantWalletCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantWalletCondition.getOperator())){
			cb.andEQ("operator", merchantWalletCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantWalletCondition.getRemark())){
			cb.andLike("remark", merchantWalletCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getTemp1())){
			cb.andEQ("temp1", merchantWalletCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getTemp2())){
			cb.andEQ("temp2", merchantWalletCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantWalletCondition.getOrderBy())){
			if(merchantWalletCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantWalletCondition.getOrderBy().split(",");
				String[] orders = merchantWalletCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantWalletCondition.getOrderBy(), Order.valueOf(merchantWalletCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantWalletCondition.getFirst()), Long.valueOf(merchantWalletCondition.getLast()));
		return merchantWalletDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantWallet 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public List<MerchantWallet> findAll(MerchantWalletCondition merchantWalletCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantWallet.class);
		if(!Strings.isEmpty(merchantWalletCondition.getId())){
			cb.andEQ("id", merchantWalletCondition.getId());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantWalletCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getMerchantName())){
			cb.andLike("merchantName", merchantWalletCondition.getMerchantName());
		}
		if(null != merchantWalletCondition.getBalance()){
			cb.andEQ("balance", merchantWalletCondition.getBalance());
		}
		if(null != merchantWalletCondition.getFreezesAmt()){
			cb.andEQ("freezesAmt", merchantWalletCondition.getFreezesAmt());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getStatus())){
			cb.andEQ("status", merchantWalletCondition.getStatus());
		}
		if(null != merchantWalletCondition.getCreateTime()){
			cb.andEQ("createTime", merchantWalletCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantWalletCondition.getOperator())){
			cb.andEQ("operator", merchantWalletCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantWalletCondition.getRemark())){
			cb.andLike("remark", merchantWalletCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getTemp1())){
			cb.andEQ("temp1", merchantWalletCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantWalletCondition.getTemp2())){
			cb.andEQ("temp2", merchantWalletCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantWalletDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public MerchantWallet findById(String id){
		return merchantWalletDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantWalletCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long insert(MerchantWalletCondition merchantWalletCondition){
		MerchantWallet merchantWallet = new MerchantWallet();
		BeanUtils.copyProperties(merchantWalletCondition, merchantWallet);
		return merchantWalletDAO.insert(merchantWallet);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long deleteById(String id){
		return merchantWalletDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantWalletDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantWalletDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long update(MerchantWalletCondition merchantWalletCondition){
		MerchantWallet merchantWallet = new MerchantWallet();
		BeanUtils.copyProperties(merchantWalletCondition, merchantWallet);
		return merchantWalletDAO.update(merchantWallet);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long updateByCriteria(MerchantWalletCondition merchantWalletCondition,Criteria criteria){
		MerchantWallet merchantWallet = new MerchantWallet();
		BeanUtils.copyProperties(merchantWalletCondition, merchantWallet);
		return merchantWalletDAO.updateByCriteria(merchantWallet,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantWalletDAO.updateStatus(id,status);
	}	
	
	@Override
	public MerchantWallet findByMerchantNo(String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantWallet.class);
		if(!Strings.isEmpty(merchantNo)){
			cb.andEQ("merchantNo", merchantNo);
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantWalletDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long updateByMerchantNo(MerchantWalletCondition merchantWalletCondition) {
		MerchantWallet merchantWallet = new MerchantWallet();
		BeanUtils.copyProperties(merchantWalletCondition, merchantWallet);
		return merchantWalletDAO.updateByMerchantNo(merchantWallet);
	}
	
	@Override
	public long updateBalanceByMerchantNo(MerchantWalletCondition merchantWalletCondition) {
		// TODO Auto-generated method stub
		long result = merchantAccountsService.updateBalance(merchantWalletCondition.getMerchantNo(),merchantWalletCondition.getBalance());
		MerchantWallet merchantWallet = new MerchantWallet();
		BeanUtils.copyProperties(merchantWalletCondition, merchantWallet);
		result = merchantWalletDAO.updateBalanceByMerchantNo(merchantWallet);
		return result;
	}
	
}

