package com.hfepay.pay.service.impl.compont.payimpls.bar;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hfepay.pay.service.compont.PayOperatorService;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStrategyEnum;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.entity.OrderPay;

/**
 * QQ条码支付
 * @author husain
 *
 */
@Service("QQBarCodePayService")
public class QQBarCodePayServiceImpl extends BaseBarCodePayService implements PayOperatorService{
	/**
	 * 支付
	 */
	@Override
	public Map<String, String> doPay(OrderBo orderBo) {
		return toPay(orderBo);
	}

	/**
	 * 回调
	 */
	@Override
	public void doPayCallBack(MerchantNotifyMessage bo) throws Exception {
		toPayCallBack(bo);
	}
	
	/**
	 * 自身策略接口
	 */
	@Override
	public String strategyCode() {
		return PayStrategyEnum.QQ_BARCODE_PAY.getStrategyCode();
	}

	@Override
	protected void saveOrderPay(OrderPay orderPay) {
		orderPay.setPayType(PayType.PAYTYPE_QQ.getCode());
		orderPay.setTemp3(strategyCode());//存放支付入口编码
		super.saveOrderPay(orderPay);
	}
	
	//消息推送内容payCode赋值
	protected void fixupPushMessageBo(PaySuccessMessage message){
		message.setPayCode(PayCode.PAYCODE_QQZF.getDesc());
	}
}
