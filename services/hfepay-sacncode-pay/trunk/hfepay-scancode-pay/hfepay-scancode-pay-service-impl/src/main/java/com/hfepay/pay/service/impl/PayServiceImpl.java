package com.hfepay.pay.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.pay.service.PayService;
import com.hfepay.pay.service.impl.compont.payimpls.PaySelector;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
@Service
public class PayServiceImpl implements PayService {
	@Autowired
	private PaySelector paySelector;

	@Override
	public Map<String, String> pay(OrderBo orderBo) {
		return paySelector.selectPay(orderBo.getPayStrategy()).doPay(orderBo);
	}

	@Override
	public void payCallBack(MerchantNotifyMessage bo) throws Exception {
		paySelector.selectPay(bo.getPayStrategy()).doPayCallBack(bo);
	}

}
