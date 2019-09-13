package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;


    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
public interface ChannelSecretKeyDAO extends EntityDAO<ChannelSecretKey, String> {

    	/**
    	 * 状态更新
    	 */
    	long updateStatus(String id,String status);	
}