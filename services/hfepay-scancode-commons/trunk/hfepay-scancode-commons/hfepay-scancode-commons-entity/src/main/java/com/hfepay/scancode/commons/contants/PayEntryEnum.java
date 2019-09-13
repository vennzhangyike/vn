package com.hfepay.scancode.commons.contants;

import java.util.HashMap;
import java.util.Map;

/**
 *	支付入口 
 * @author husain
 *
 */
public enum PayEntryEnum {
	AMTCODE_PAY("AMTCODE", "扫码支付"),
	BARCODE_PAY("BARCODE", "条码支付"),
	MERCHANTCODE_PAY("MERCHANTCODE", "商户码支付");
	
	
	// 成员变量  
    private String entryCode;  
    private String entryName;  
    // 构造方法  
    private PayEntryEnum(String entryCode, String entryName) {  
        this.entryCode = entryCode;  
        this.entryName = entryName; 
    }
    
	

	public String getEntryCode() {
		return entryCode;
	}



	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}



	public String getEntryName() {
		return entryName;
	}



	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}



	@Override
	public String toString() {
		return this.entryCode+"_"+this.entryName;
	}
    
}
