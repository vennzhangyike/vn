package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.scancode.commons.condition.BankcardNatureConfCondition;
import com.hfepay.scancode.commons.entity.BankcardNatureConf;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */


public interface BankcardNatureConfService {
	
	/**
	 * 银行卡属性计算实现
	 * @author sw
	 * @throws Exception 
	 * @since 2016-10-26
	 *
	 */
	public BankcardNatureConf invokeBankCardNature(String bankName,String bankCard,Map<Object, Object> confMap) throws Exception;
	
	/**
	 * 列表
	 * @param bankcardNatureConf 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 13:55:39
	 */
	public List<BankcardNatureConf> findAll(BankcardNatureConfCondition bankcardNatureConfCondition);
	
}

