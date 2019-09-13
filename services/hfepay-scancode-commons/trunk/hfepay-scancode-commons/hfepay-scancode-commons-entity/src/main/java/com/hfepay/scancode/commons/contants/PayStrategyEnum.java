package com.hfepay.scancode.commons.contants;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付类型和支付入口组成的支付方式选中类型（如支付宝扫码支付，支付宝条码支付等）
 * @author husain
 *
 */
public enum PayStrategyEnum {
	ZFB_AMTCODE_PAY("ZFBSMZF_AMTCODE", "支付宝金额扫码支付"),
	ZFB_BARCODE_PAY("ZFBSMZF_BARCODE", "支付宝条码支付"),
	ZFB_MERCHANTCODE_PAY("ZFBSMZF_MERCHANTCODE", "支付宝商户码支付"),
	
	WX_AMTCODE_PAY("WXGZHZF_AMTCODE", "微信金额扫码支付"),
	WX_BARCODE_PAY("WXGZHZF_BARCODE", "微信条码支付"),
	WX_MERCHANTCODE_PAY("WXGZHZF_MERCHANTCODE", "微信商户码支付"),
	
	QQ_AMTCODE_PAY("QQZF_AMTCODE", "QQ金额扫码支付"),
	QQ_BARCODE_PAY("QQZF_BARCODE", "QQ条码支付"),
	QQ_MERCHANTCODE_PAY("QQZF_MERCHANTCODE", "QQ商户码支付");
	
	
	// 成员变量  
    private String strategyCode;  
    private String strategyName;  
    // 构造方法  
    private PayStrategyEnum(String strategyCode, String strategyName) {  
        this.strategyCode = strategyCode;  
        this.strategyName = strategyName; 
    }
    
	public String getStrategyCode() {
		return strategyCode;
	}

	public void setStrategyCode(String strategyCode) {
		this.strategyCode = strategyCode;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	
	public static Map<String,String> toMap(){
		Map<String,String> map =new HashMap<>();
		PayStrategyEnum[] code = PayStrategyEnum.values();
		for (PayStrategyEnum payCode : code) {
			map.put(payCode.getStrategyCode(), payCode.getStrategyName());
		}
		return map;
	}

	@Override
	public String toString() {
		return this.strategyCode+"_"+this.strategyName;
	}
    
}
