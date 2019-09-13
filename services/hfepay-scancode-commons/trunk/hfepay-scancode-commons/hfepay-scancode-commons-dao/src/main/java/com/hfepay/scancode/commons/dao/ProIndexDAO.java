package com.hfepay.scancode.commons.dao;

import java.util.Map;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ProIndex;


    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
public interface ProIndexDAO extends EntityDAO<ProIndex, String> {
    	/**
    	 * 调用存储过程生成id
    	 * @param map
    	 */
    	void getOrderId(Map<String,Object> map);
}