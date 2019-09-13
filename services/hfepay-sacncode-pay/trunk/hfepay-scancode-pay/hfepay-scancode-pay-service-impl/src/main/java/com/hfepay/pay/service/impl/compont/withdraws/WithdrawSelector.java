package com.hfepay.pay.service.impl.compont.withdraws;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hfepay.commons.utils.Springs;
import com.hfepay.pay.service.compont.WithdrawOperatorService;
import com.hfepay.pay.service.exception.PayException;
import com.hfepay.pay.service.exception.WithdrawException;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;

/**
 * 提现选择器，分为支付宝，QQ,微信公众号，微信扫码付(目前系统没有对接微信扫码付)
 * @author husain
 *
 */
@Component
public class WithdrawSelector {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static Map<String,WithdrawOperatorService> map = new HashMap<>();
	
	static{
		map.put(PayCode.PAYCODE_ZFBSMZF.getCode(), (WithdrawOperatorService)Springs.getBean("ZFBWithdrawService"));
		map.put(PayCode.PAYCODE_WXSMZF.getCode(), (WithdrawOperatorService)Springs.getBean("wechatSMWithdrawService"));
		map.put(PayCode.PAYCODE_WXGZHZF.getCode(), (WithdrawOperatorService)Springs.getBean("wechatGZHWithdrawService"));
		map.put(PayCode.PAYCODE_QQZF.getCode(), (WithdrawOperatorService)Springs.getBean("QQWithdrawService"));
		
	}
	
	public WithdrawOperatorService selectWithdraw(String strategy) throws PayException{
		logger.info("selectWithdraw get withdraw strategy is "+strategy);
		if(!PayCode.toMap().containsKey(strategy)){
			throw new WithdrawException(ScanCodeErrorCode.WITHDRAW_600000.getCode(), ScanCodeErrorCode.WITHDRAW_600000.getDesc());
		}
		return map.get(strategy);
	}
}
