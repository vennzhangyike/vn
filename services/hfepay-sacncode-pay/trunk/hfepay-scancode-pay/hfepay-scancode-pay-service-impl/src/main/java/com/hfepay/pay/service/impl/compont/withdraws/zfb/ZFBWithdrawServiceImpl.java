package com.hfepay.pay.service.impl.compont.withdraws.zfb;

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

@Service("ZFBWithdrawService")
public class ZFBWithdrawServiceImpl extends BaseWithdrawService implements WithdrawOperatorService{

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
		orderPayCondition.setPayType(PayType.PAYTYPE_ZFB.getCode());
	}
	
	@Override
	protected void setMessagePayCode(WithDrawsSuccessMessage message) {
		message.setPayCode("支付宝钱包");
	}
}
