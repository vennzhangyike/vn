package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Set;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.entity.PlatformQrcode;

public interface PlatformQrcodeService {
	
	/**
	 * 列表(分页)
	 * @param platformQrcodeCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	public PagingResult<PlatformQrcode> findPagingResult(PlatformQrcodeCondition platformQrcodeCondition);
	
	/**
	 * 列表
	 * @param platformQrcode 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	public List<PlatformQrcode> findAll(PlatformQrcodeCondition platformQrcodeCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	PlatformQrcode findById(String id);
	
	/**
	 * 新增
	 * @param platformQrcodeCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long insert(PlatformQrcodeCondition platformQrcodeCondition);
	
	/**
	 * 批量新增
	 * @param qrList
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	void batchInsert(List<PlatformQrcodeCondition> qrList);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long update(PlatformQrcodeCondition platformQrcodeCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long updateByCriteria(PlatformQrcodeCondition platformQrcodeCondition,Criteria criteria);
	
	/**
	 * 条件取当前代理商没有用过的二维码
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	PlatformQrcode findPlatformQrcode(String id);
	
	/**
	 * 生成编码
	 * @param map
	 */
	public String getQrcodeCode();
	
	/**
	 * 生成编码(保证每次生成的不会重复)
	 * @param map
	 */
	public String getQrcodeCode(Set<String> set,String qr);

	/**
	 * @Title: findByQrcode
	 * @Description: 根据二维码编号查找
	 * @author: husain
	 * @param qrCode
	 * @return
	 * @return: PlatformQrcode
	 */
	public PlatformQrcode findByQrcode(String qrCode);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	long update(PlatformQrcode platformQrcode);
	
	/**
	 * @Title: findByCondition
	 * @Description: 根据条件查询单个二维码
	 * @author: husain
	 * @param platformQrcodeCondition
	 * @return: PlatformQrcode
	 */
	public PlatformQrcode findByCondition(PlatformQrcodeCondition platformQrcodeCondition);
	
	/**
	 * 分页查询
	 * @param platformQrcodeCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	public PagingResult<PlatformQrcode> findPagingResultByChannelNoAndAgentNo(PlatformQrcodeCondition platformQrcodeCondition);

	/**删除商户后更新二维码*/
	long updateByMerchantNo(String merchantNo);

	/** 重置二维码 */
	void resetQrcode();
	
}

