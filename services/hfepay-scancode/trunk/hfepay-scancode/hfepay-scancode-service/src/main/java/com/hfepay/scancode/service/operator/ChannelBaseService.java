/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelBase;

public interface ChannelBaseService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: PagingResult<ChannelBase>
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public PagingResult<ChannelBase> findPagingResult(ChannelBaseCondition channelBaseCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: List<ChannelBase>
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public List<ChannelBase> findAll(ChannelBaseCondition channelBaseCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	ChannelBase findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long insert(ChannelBaseCondition channelBaseCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long update(ChannelBaseCondition channelBaseCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long updateByCriteria(ChannelBaseCondition channelBaseCondition);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public ChannelBase findByChannelNo(String channelNo);
	
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public void encode(String content, String imgPath, String destPath,
			boolean needCompress,String qrName) throws Exception;
	
	/** 添加二维码 */
	public void addQrcode(String channelNo ,String qrcodeNum,Admin user);
}

