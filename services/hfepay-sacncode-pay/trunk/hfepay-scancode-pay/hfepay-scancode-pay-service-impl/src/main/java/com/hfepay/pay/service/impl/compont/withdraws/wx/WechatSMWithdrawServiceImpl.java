package com.hfepay.pay.service.impl.compont.withdraws.wx;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hfepay.pay.service.compont.WithdrawOperatorService;
import com.hfepay.pay.service.impl.compont.withdraws.BaseWithdrawService;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.PayType;

@Service("wechatSMWithdrawService")
public class WechatSMWithdrawServiceImpl extends BaseWithdrawService implements WithdrawOperatorService {

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
		orderPayCondition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
	}
	
	@Override
	protected void setMessagePayCode(WithDrawsSuccessMessage message) {
		message.setPayCode("微信扫码付钱包");
	}
}
