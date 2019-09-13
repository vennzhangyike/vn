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
public class WeBankMerchantRateVo implements Serializable{
	private String paymentType;//支付类型，请参考数据字典
	private String settlementType;//结算方式（默认01）
	private String chargeType;//计费算法:01固定金额、02固定费率（默认填写02）
	private String commissionRate;//回拥费率（chargeType为02时必填）（0.6%代表千分之六）
	private String commissionAmount;//回佣金额（chargeType为01时必填）
	private String minAmount;//保底费用（默认不填）
	private String maxAmount;//封顶费用（默认不填）
	private String attachAmount;//附加金额（默认不填）
	private String attachRate;//附加费率（默认不填）
	private String marginDeposit;//保证金（默认不填）
	private String projectImplCost;//项目实施费（默认不填）
	private String sysUseCharge;//系统使用服务年费（默认不填）
	
	
	public WeBankMerchantRateVo() {
		setSettlementType("01");
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(String commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getAttachAmount() {
		return attachAmount;
	}

	public void setAttachAmount(String attachAmount) {
		this.attachAmount = attachAmount;
	}

	public String getAttachRate() {
		return attachRate;
	}

	public void setAttachRate(String attachRate) {
		this.attachRate = attachRate;
	}

	public String getMarginDeposit() {
		return marginDeposit;
	}

	public void setMarginDeposit(String marginDeposit) {
		this.marginDeposit = marginDeposit;
	}

	public String getProjectImplCost() {
		return projectImplCost;
	}

	public void setProjectImplCost(String projectImplCost) {
		this.projectImplCost = projectImplCost;
	}

	public String getSysUseCharge() {
		return sysUseCharge;
	}

	public void setSysUseCharge(String sysUseCharge) {
		this.sysUseCharge = sysUseCharge;
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
