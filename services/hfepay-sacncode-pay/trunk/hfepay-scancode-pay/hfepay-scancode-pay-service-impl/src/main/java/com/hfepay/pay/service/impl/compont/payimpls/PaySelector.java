package com.hfepay.pay.service.impl.compont.payimpls;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hfepay.commons.utils.Springs;
import com.hfepay.pay.service.compont.PayOperatorService;
import com.hfepay.pay.service.exception.PayException;
import com.hfepay.scancode.commons.contants.PayStrategyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;

/**
 * 支付方式的选择器，通过支付类型（条码|扫码）和支付方式(QQ|支付宝|微信)选择指定的支付
 * 
 * @author husain
 *
 */
@Component
public class PaySelector {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static Map<String,PayOperatorService> map = new HashMap<>();
	
	static{
		map.put(PayStrategyEnum.ZFB_AMTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("ZFBAmtCodePayService"));
		map.put(PayStrategyEnum.ZFB_BARCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("ZFBBarCodePayService"));
		map.put(PayStrategyEnum.ZFB_MERCHANTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("ZFBMerchantCodePayService"));
		map.put(PayStrategyEnum.WX_AMTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("wechatAmtCodePayService"));
		map.put(PayStrategyEnum.WX_BARCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("wechatBarCodePayService"));
		map.put(PayStrategyEnum.WX_MERCHANTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("wechatMerchantCodePayService"));
		map.put(PayStrategyEnum.QQ_AMTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("QQAmtCodePayService"));
		map.put(PayStrategyEnum.QQ_BARCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("QQBarCodePayService"));
		map.put(PayStrategyEnum.QQ_MERCHANTCODE_PAY.getStrategyCode(), (PayOperatorService)Springs.getBean("QQMerchantCodePayService"));
	}
	
	public PayOperatorService selectPay(String strategy) throws PayException{
		logger.info("selector get pay strategy is "+strategy);
		if(!PayStrategyEnum.toMap().containsKey(strategy)){
			throw new PayException(ScanCodeErrorCode.PAY_500003.getCode(), ScanCodeErrorCode.PAY_500003.getDesc());
		}
		return map.get(strategy);
	}

}
