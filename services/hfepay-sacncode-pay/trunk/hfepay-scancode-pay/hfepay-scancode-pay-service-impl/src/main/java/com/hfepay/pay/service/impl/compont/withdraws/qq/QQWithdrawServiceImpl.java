package com.hfepay.pay.service.impl.compont.withdraws.qq;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hfepay.pay.service.compont.WithdrawOperatorService;
import com.hfepay.pay.service.impl.compont.withdraws.BaseWithdrawService;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.entity.MerchantInfo;

@Service("QQWithdrawService")
public class QQWithdrawServiceImpl extends BaseWithdrawService implements WithdrawOperatorService{

	@Override
	public Map<String, String> doWithdraws(OrderBo orderBo) {
		return withdraws(orderBo);
	}

	@Override
	public void doWithdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception {
		withdrawsCallBack(bo);
	}
	
	@Override
	protected void setPayType(OrderPayCondition orderPayCondition) {
		orderPayCondition.setPayType(PayType.PAYTYPE_QQ.getCode());
	}
	
	@Override
	protected void setMessagePayCode(WithDrawsSuccessMessage message) {
		message.setPayCode("QQ钱包");
	}

}
