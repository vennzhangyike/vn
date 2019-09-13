/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.entity.MerchantInfo;

import net.sf.json.JSON;

public interface MerchantInfoService {
	
	/**
	 * 列表(分页)
	 * @param merchantInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public PagingResult<MerchantInfo> findPagingResult(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 列表
	 * @param merchantInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public List<MerchantInfo> findAll(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 根据二维码查看商户是否唯一
	 * @param merchantInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public List<MerchantInfo> findMerchantInfoByQrCode(String qrCode);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	MerchantInfo findById(String id);
	
	/**
	 * 新增
	 * @param merchantInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long insert(MerchantInfoCondition merchantInfoCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	Map<String, String> deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long deleteByCriteria(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long countByCriteria(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long update(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long updateByAudit(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	long updateByCriteria(MerchantInfoCondition merchantInfoCondition);
	
	/**
	 * 状态更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-21 14:04:47
	 */
	public long updateStatus(String id,String status);
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public MerchantInfo findByMerchantNo(String MerchantNo);
	/**
	 * 根据商户编号录入商户信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long updateByMerchantNo(MerchantInfoCondition condition);
	
	/**
	 * 列表
	 * @param merchantInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public List<MerchantInfo> findAllByAgentNo(MerchantInfoCondition merchantInfoCondition,List<String> list);
	
	/**
	 * 从微信服务器下载图片
	 * @Title: downLoadImg
	 * @Description: TODO
	 * @author: husain
	 * @param mediaId
	 * @param filePath
	 * @return
	 * @return: String
	 */
	public String downLoadImg(String mediaId,String filePath,String channelNo);

	/**
	 * 
	 * @Title: freshImg
	 * @Description: 重新下载微信服务器是的图片
	 * @author: husain
	 * @param merchantNo
	 * @param type
	 * @return: void
	 */
	public String freshImg(String merchantNo, String type);

	/**设置商户redis
	 * @throws Exception */
	void setMerchantRedis() throws Exception;

	/**
	 * 商户入网信息变更
	 * @param info 商户基本信息
	 * @return
	 * @throws Exception
	 * @author lemon
	 */
	Map<String, String> merchantJoinChange(MerchantInfoCondition merchantInfoCondition) throws Exception;

	/**商户审核（平台审核）
	 * @throws Exception */
	JSON auditPlatform(MerchantInfoCondition merchantInfoCondition, String isRealAccount, String storeNo)
			throws Exception;
	
	/**
	 * 上级机构审核
	 * @param merchantInfoCondition
	 * @param isRealAccount
	 * @param storeNo
	 * @return
	 * @throws Exception
	 */
	JSON auditOrgan(MerchantInfoCondition merchantInfoCondition,String isRealAccount,String storeNo) throws Exception;

	/**
	 * 根据key删除redis数据支持*匹配
	 * @param redisKey
	 */
	public void delReiskey(String redisKey);

	/**
	 * 根据商户编号修改信息，不包含检查等
	 * @param merchantInfoCondition
	 */
	public long updateInfoByMerchantNo(MerchantInfoCondition merchantInfoCondition);

	/**
	 * 
	 * @author liushuyu
	 * Desc : 身份证二要素验证 
	 * DATE: 2017年6月16日
	 * @param name 商户名称
	 * @param idCard 商户身份证
	 * @return 
	 * @throws Exception
	 */
	Map<String, String> validateIdCard2(String name, String idCard) throws Exception;

}

