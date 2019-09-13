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
import com.hfepay.scancode.commons.condition.QrcodeGoodsCondition;
import com.hfepay.scancode.commons.dao.QrcodeGoodsDAO;
import com.hfepay.scancode.commons.entity.QrcodeGoods;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.QrcodeGoodsService;

@Service("qrcodeGoodsService")
public class QrcodeGoodsServiceImpl implements QrcodeGoodsService {
	
	@Autowired
    private QrcodeGoodsDAO qrcodeGoodsDAO;
    
    /**
	 * 列表(分页)
	 * @param qrcodeGoodsCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
    @Override
	public PagingResult<QrcodeGoods> findPagingResult(QrcodeGoodsCondition qrcodeGoodsCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		if(!Strings.isEmpty(qrcodeGoodsCondition.getId())){
			cb.andEQ("id", qrcodeGoodsCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getQrCode())){
			cb.andLike("qrCode", qrcodeGoodsCondition.getQrCode());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", qrcodeGoodsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsName())){
			cb.andLike("goodsName", qrcodeGoodsCondition.getGoodsName());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsDesc())){
			cb.andEQ("goodsDesc", qrcodeGoodsCondition.getGoodsDesc());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getIsDefault())){
			cb.andEQ("isDefault", qrcodeGoodsCondition.getIsDefault());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", qrcodeGoodsCondition.getRecordStatus());
		}
		if(null != qrcodeGoodsCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeGoodsCondition.getCreateTime());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getOperator())){
			cb.andEQ("operator", qrcodeGoodsCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getRemark())){
			cb.andLike("remark", qrcodeGoodsCondition.getRemark());
		}
		if(Strings.isNotEmpty(qrcodeGoodsCondition.getNodeSeq())){
			cb.addParam("nodeSeq", qrcodeGoodsCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(qrcodeGoodsCondition.getOrderBy())){
			if(qrcodeGoodsCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = qrcodeGoodsCondition.getOrderBy().split(",");
				String[] orders = qrcodeGoodsCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(qrcodeGoodsCondition.getOrderBy(), Order.valueOf(qrcodeGoodsCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(qrcodeGoodsCondition.getFirst()), Long.valueOf(qrcodeGoodsCondition.getLast()));
		return qrcodeGoodsDAO.findPagingResult(buildCriteria);
	}

	/**
	 * 列表
	 * @param qrcodeGoods 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public List<QrcodeGoods> findAll(QrcodeGoodsCondition qrcodeGoodsCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		if(!Strings.isEmpty(qrcodeGoodsCondition.getId())){
			cb.andEQ("id", qrcodeGoodsCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getQrCode())){
			cb.andEQ("qrCode", qrcodeGoodsCondition.getQrCode());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", qrcodeGoodsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsName())){
			cb.andLike("goodsName", qrcodeGoodsCondition.getGoodsName());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsDesc())){
			cb.andEQ("goodsDesc", qrcodeGoodsCondition.getGoodsDesc());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getIsDefault())){
			cb.andEQ("isDefault", qrcodeGoodsCondition.getIsDefault());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", qrcodeGoodsCondition.getRecordStatus());
		}
		if(null != qrcodeGoodsCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeGoodsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getOperator())){
			cb.andEQ("operator", qrcodeGoodsCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getRemark())){
			cb.andLike("remark", qrcodeGoodsCondition.getRemark());
		}
		if(Strings.isNotEmpty(qrcodeGoodsCondition.getNodeSeq())){
			cb.addParam("nodeSeq", qrcodeGoodsCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return qrcodeGoodsDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public QrcodeGoods findById(String id){
		return qrcodeGoodsDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param qrcodeGoodsCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long insert(QrcodeGoodsCondition qrcodeGoodsCondition){
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.insert(qrcodeGoods);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long deleteById(String id){
		return qrcodeGoodsDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long deleteByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		if(!Strings.isEmpty(qrcodeGoodsCondition.getId())){
			cb.andEQ("id", qrcodeGoodsCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getQrCode())){
			cb.andEQ("qrCode", qrcodeGoodsCondition.getQrCode());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", qrcodeGoodsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsName())){
			cb.andEQ("goodsName", qrcodeGoodsCondition.getGoodsName());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsDesc())){
			cb.andEQ("goodsDesc", qrcodeGoodsCondition.getGoodsDesc());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getIsDefault())){
			cb.andEQ("isDefault", qrcodeGoodsCondition.getIsDefault());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", qrcodeGoodsCondition.getRecordStatus());
		}
		if(null != qrcodeGoodsCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeGoodsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getOperator())){
			cb.andEQ("operator", qrcodeGoodsCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getRemark())){
			cb.andLike("remark", qrcodeGoodsCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long countByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		if(!Strings.isEmpty(qrcodeGoodsCondition.getId())){
			cb.andEQ("id", qrcodeGoodsCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getQrCode())){
			cb.andEQ("qrCode", qrcodeGoodsCondition.getQrCode());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", qrcodeGoodsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsName())){
			cb.andEQ("goodsName", qrcodeGoodsCondition.getGoodsName());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsDesc())){
			cb.andEQ("goodsDesc", qrcodeGoodsCondition.getGoodsDesc());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getIsDefault())){
			cb.andEQ("isDefault", qrcodeGoodsCondition.getIsDefault());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", qrcodeGoodsCondition.getRecordStatus());
		}
		if(null != qrcodeGoodsCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeGoodsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getOperator())){
			cb.andEQ("operator", qrcodeGoodsCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getRemark())){
			cb.andLike("remark", qrcodeGoodsCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long update(QrcodeGoodsCondition qrcodeGoodsCondition){
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.update(qrcodeGoods);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long updateByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		if(!Strings.isEmpty(qrcodeGoodsCondition.getId())){
			cb.andEQ("id", qrcodeGoodsCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getQrCode())){
			cb.andEQ("qrCode", qrcodeGoodsCondition.getQrCode());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", qrcodeGoodsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsName())){
			cb.andEQ("goodsName", qrcodeGoodsCondition.getGoodsName());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getGoodsDesc())){
			cb.andEQ("goodsDesc", qrcodeGoodsCondition.getGoodsDesc());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getIsDefault())){
			cb.andEQ("isDefault", qrcodeGoodsCondition.getIsDefault());
		}
		if(!Strings.isEmpty(qrcodeGoodsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", qrcodeGoodsCondition.getRecordStatus());
		}
		if(null != qrcodeGoodsCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeGoodsCondition.getCreateTime());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getOperator())){
			cb.andEQ("operator", qrcodeGoodsCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeGoodsCondition.getRemark())){
			cb.andLike("remark", qrcodeGoodsCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.updateByCriteria(qrcodeGoods,buildCriteria);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	@Override
	public long updateByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition,Criteria buildCriteria){
		QrcodeGoods qrcodeGoods = new QrcodeGoods();
		BeanUtils.copyProperties(qrcodeGoodsCondition, qrcodeGoods);
		return qrcodeGoodsDAO.updateByCriteria(qrcodeGoods,buildCriteria);
	}

	@Override
	public QrcodeGoods findByQrCode(String qrCode) {
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		cb.andEQ("qrCode", qrCode);
		cb.andEQ("isDefault", "0");
		Criteria buildCriteria = cb.buildCriteria();
		return qrcodeGoodsDAO.findOneByCriteria(buildCriteria);
	}
	
}

