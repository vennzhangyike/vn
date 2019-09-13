package com.hfepay.pay.service.impl.compont.payimpls.sweep;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hfepay.pay.service.compont.PayOperatorService;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.vo.MerchantPayInfoVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStrategyEnum;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;

/**
 * 微信扫码支付(金额二维码)
 * @author husain
 *
 */
@Service("wechatAmtCodePayService")
public class WechatAmtCodePayServiceImpl extends BaseAmtCodePayService implements PayOperatorService{
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	/**
	 * 支付
	 */
	@Override
	public Map<String, String> doPay(OrderBo orderBo) {
		return toPay(orderBo);
	}
	
	@Override
	protected Map<String, String> executePay(MerchantPayInfoVo vo) throws Exception {
		// TODO Auto-generated method stub
		return merchantBusinessService.merchantWechatOfficial(vo);
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
		return PayStrategyEnum.WX_AMTCODE_PAY.getStrategyCode();
	}

	@Override
	protected void saveOrderPay(OrderPay orderPay) {
		orderPay.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		orderPay.setTemp3(strategyCode());//存放支付入口编码
		super.saveOrderPay(orderPay);
	}
	
	//消息推送内容payCode赋值
	protected void fixupPushMessageBo(PaySuccessMessage message){
		message.setPayCode(PayCode.PAYCODE_WXGZHZF.getDesc());
	}

	//扫码支付公众号支付需要制定returntype
	@Override
	protected MerchantPayInfoVo getPayParams(MerchantInfo merchantInfo, OrderBo orderBo, OrderPayment orderPayment) {
		// TODO Auto-generated method stub
		MerchantPayInfoVo vo = super.getPayParams(merchantInfo, orderBo, orderPayment);
		vo.setReturnType("02");
		return vo;
	}
}
