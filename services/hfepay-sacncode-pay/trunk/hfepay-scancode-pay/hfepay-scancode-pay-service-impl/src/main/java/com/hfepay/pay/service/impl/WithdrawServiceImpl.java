package com.hfepay.pay.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.pay.service.WithdrawService;
import com.hfepay.pay.service.impl.compont.withdraws.WithdrawSelector;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.entity.MerchantInfo;
@Service
public class WithdrawServiceImpl implements WithdrawService {
	@Autowired
	private WithdrawSelector withdrawSelector;
	
	@Override
	public Map<String, String> withdraws(OrderBo orderBo) {
		return withdrawSelector.selectWithdraw(orderBo.getPayCode()).doWithdraws(orderBo);
	}

	@Override
	public void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception {
		
		withdrawSelector.selectWithdraw(bo.getPayCode()).doWithdrawsCallBack(bo);
	}

}
