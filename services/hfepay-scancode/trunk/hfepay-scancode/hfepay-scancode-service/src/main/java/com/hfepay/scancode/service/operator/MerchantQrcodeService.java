/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.entity.MerchantQrcode;

public interface MerchantQrcodeService {
	
	/**
	 * 列表(分页)
	 * @param merchantQrcodeCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	public PagingResult<MerchantQrcode> findPagingResult(MerchantQrcodeCondition merchantQrcodeCondition);
	
	/**
	 * 列表
	 * @param merchantQrcode 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	public List<MerchantQrcode> findAll(MerchantQrcodeCondition merchantQrcodeCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	MerchantQrcode findById(String id);
	
	/**
	 * 新增
	 * @param merchantQrcodeCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long insert(MerchantQrcodeCondition merchantQrcodeCondition, Map<String, String> map);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long deleteByCriteria(MerchantQrcodeCondition merchantQrcodeCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long countByCriteria(MerchantQrcodeCondition merchantQrcodeCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long update(MerchantQrcodeCondition merchantQrcodeCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	long updateByCriteria(MerchantQrcodeCondition merchantQrcodeCondition);

	/**
	 * 根据收银员编号查询二维码
	 * @Title: findQrcodeByIdentityNo
	 * @Description: TODO
	 * @author: husain
	 * @param identityNo
	 * @return
	 * @return: MerchantQrcode
	 */
	public MerchantQrcode findQrcodeByIdentityNo(String identityNo);

	/**
	 * 查询所有
	 * @Title: findAllMerchantQrcode
	 * @Description: TODO
	 * @author: husain
	 * @param merchantQrcodeCondition
	 * @return
	 * @return: List<MerchantQrcode>
	 */
	public List<MerchantQrcode> findAllMerchantQrcode(MerchantQrcodeCondition merchantQrcodeCondition);

	long updateByCriteria(MerchantQrcodeCondition merchantQrcodeCondition, Criteria buildCriteria);

	//删除商户二维码
	public void delMerchantQrcode(MerchantQrcodeCondition merchantQrcodeCondition);

	//分页显示商户二维码信息
	public PagingResult<MerchantQrcode> findPagingResultSelf(MerchantQrcodeCondition condition);


}

