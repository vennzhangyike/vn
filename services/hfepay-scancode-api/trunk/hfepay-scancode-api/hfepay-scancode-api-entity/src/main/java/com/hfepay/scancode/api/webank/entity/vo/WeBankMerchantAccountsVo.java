package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 商户账户信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankMerchantAccountsVo implements Serializable{
	private String accountNo;//商户银行账号
	private String accountOpbankNo;//账户开户行号
	private String accountName;//开户户名
	private String accountOpbank;//开户行
	private String accountSubbranchOpbank;//开户支行
	private String accountOpbankAddr;//开户地址
	private String acctType;//账户类型
	private String settlementCycle;//清算周期
	
	public WeBankMerchantAccountsVo() {
		setSettlementCycle("1");
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}



	public String getAccountOpbankNo() {
		return accountOpbankNo;
	}



	public void setAccountOpbankNo(String accountOpbankNo) {
		this.accountOpbankNo = accountOpbankNo;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public String getAccountOpbank() {
		return accountOpbank;
	}



	public void setAccountOpbank(String accountOpbank) {
		this.accountOpbank = accountOpbank;
	}



	public String getAccountSubbranchOpbank() {
		return accountSubbranchOpbank;
	}



	public void setAccountSubbranchOpbank(String accountSubbranchOpbank) {
		this.accountSubbranchOpbank = accountSubbranchOpbank;
	}



	public String getAccountOpbankAddr() {
		return accountOpbankAddr;
	}



	public void setAccountOpbankAddr(String accountOpbankAddr) {
		this.accountOpbankAddr = accountOpbankAddr;
	}



	public String getAcctType() {
		return acctType;
	}



	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}



	public String getSettlementCycle() {
		return settlementCycle;
	}



	public void setSettlementCycle(String settlementCycle) {
		this.settlementCycle = settlementCycle;
	}



	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null && !"".equals(obj)){
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	
}
