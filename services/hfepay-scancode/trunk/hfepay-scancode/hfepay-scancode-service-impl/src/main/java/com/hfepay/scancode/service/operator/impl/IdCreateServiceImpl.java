package com.hfepay.scancode.service.operator.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.utils.OrderIDUtils;


/**
 * 主键生成
* @author zy
* @date 2016-10-21 下午4:21:17
 */
@Service
public class IdCreateServiceImpl implements IdCreateService{

	@Autowired
	private OrderIDUtils orderIDUtils;
	
	@Override
	public String createParamNo(String param) {
		String agentNo = orderIDUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), param);
		return agentNo;
	}
	
}
