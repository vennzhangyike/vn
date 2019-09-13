/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.QrcodeGoodsCondition;
import com.hfepay.scancode.commons.entity.QrcodeGoods;

public interface QrcodeGoodsService {
	
	/**
	 * 列表(分页)
	 * @param qrcodeGoodsCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	public PagingResult<QrcodeGoods> findPagingResult(QrcodeGoodsCondition qrcodeGoodsCondition);
	
	/**
	 * 列表
	 * @param qrcodeGoods 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	public List<QrcodeGoods> findAll(QrcodeGoodsCondition qrcodeGoodsCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	QrcodeGoods findById(String id);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	QrcodeGoods findByQrCode(String qrCode);
	
	/**
	 * 新增
	 * @param qrcodeGoodsCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long insert(QrcodeGoodsCondition qrcodeGoodsCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long deleteByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long countByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long update(QrcodeGoodsCondition qrcodeGoodsCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	long updateByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition);

	long updateByCriteria(QrcodeGoodsCondition qrcodeGoodsCondition, Criteria buildCriteria);
	
	
}

