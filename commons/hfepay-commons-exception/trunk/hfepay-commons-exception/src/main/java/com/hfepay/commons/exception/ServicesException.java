package com.hfepay.commons.exception;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.hfepay.commons.base.enums.PaymentErrorEnum;
import com.hfepay.commons.base.lang.Objects;

/**
 * 通用服务层异常对象
 * @author Sam
 *
 */
public class ServicesException extends UncheckedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5343530729791707570L;

	/**
	 * APP_ID不存在
	 */
	public static final ServicesException APPID_CODE_NOT_EXCEPTION = new ServicesException(PaymentErrorEnum.APPID_CODE.getValue(), PaymentErrorEnum.APPID_CODE.getDesc());

	/**
	 * 商户号不存在
	 */
	public static final ServicesException MECH_NOT_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.MECH_NOT_CODE.getValue(), PaymentErrorEnum.MECH_NOT_CODE.getDesc());

	/**
	 * 商户号与应用ID不匹配
	 */
	public static final ServicesException APPID_MCHID_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.APPID_MCHID_CODE.getValue(), PaymentErrorEnum.APPID_MCHID_CODE.getDesc());

	/**
	 * 缺少参数
	 */
	public static final ServicesException PARAMS_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.PARAMS_CODE.getValue(), PaymentErrorEnum.PARAMS_CODE.getDesc());

	/**
	 * 订单号重复
	 */
	public static final ServicesException ORDER_NO_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.ORDER_NO_CODE.getValue(), PaymentErrorEnum.ORDER_NO_CODE.getDesc());

	/**
	 * 订单已支付
	 */
	public static final ServicesException ORDERPAID_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.ORDERPAID.getValue(), PaymentErrorEnum.ORDERPAID.getDesc());
	
	/**
	 * 签名验证失败
	 */
	public static final ServicesException SIGNERROR_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.SIGNERROR_CODE.getValue(), PaymentErrorEnum.SIGNERROR_CODE.getDesc());
	

	/**
	 * 请求格式错误
	 */
	public static final ServicesException REQUIRE_POST_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.REQUIRE_POST_CODE.getValue(), PaymentErrorEnum.REQUIRE_POST_CODE.getDesc());
	
	/**
	 * 编码格式不正确
	 */
	public static final ServicesException UT_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.UT_CODE.getValue(), PaymentErrorEnum.UT_CODE.getDesc());

	/**
	 * 余额不足
	 */
	public static final ServicesException NOTENOUGH_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.NOTENOUGH_CODE.getValue(), PaymentErrorEnum.NOTENOUGH_CODE.getDesc());
	
	/**
	 * 系统错误
	 */
	public static final ServicesException SYSTEM_CHANNEL_CODE_EXCEPTION = new ServicesException(PaymentErrorEnum.SYSTEM_CHANNEL_CODE.getValue(), PaymentErrorEnum.SYSTEM_CHANNEL_CODE.getDesc());
	
	private Integer resultCode;
	
	
	
	public ServicesException(){}  
	 
    public ServicesException(Throwable e) {
        super(e);
    }    

	public ServicesException(Integer resultCode, String resultMsg) {
		super(resultMsg);
		this.resultCode = resultCode;
	}
    public ServicesException(String msg, Throwable e) {
        super(msg, e);
    }
    
    
    
    public ServicesException(String msg) {
        super(msg);
    }    
     
    public String toString() {
        return Objects.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);

    }

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}    
}
