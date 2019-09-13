/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;

@Generated("2016-11-09 14:30:08")
public interface ApiChannelWxParamsDAO extends EntityDAO<ApiChannelWxParams, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	long updateStatus(String id,String status);

	/**
	 * @Title: getWechatConfigOrganNo
	 * @Description: 查询最近的配置有公众号的代理商
	 * @author: husain
	 * @param organNo
	 * @return
	 * @return: DataNodeVO
	 */
	DataNodeVO getWechatConfigEntity(List<String> parentsSeq);

	/**
	 * 获取当前节点的seq
	 * @param organNo
	 * @return
	 */
	String getSeq(String organNo);	
}
