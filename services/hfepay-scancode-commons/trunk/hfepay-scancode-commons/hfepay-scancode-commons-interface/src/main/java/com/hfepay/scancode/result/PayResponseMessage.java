package com.hfepay.scancode.result;

import com.hfepay.scancode.bean.ResponseBody;
import com.hfepay.scancode.bean.ResponseHeader;
import com.hfepay.scancode.bean.ResponseMessage;


/**
 *支付返回报文体
 */
public class PayResponseMessage extends ResponseMessage<PayResponseMessage.Body> {
	private static final long serialVersionUID = 238032038699187311L;
	private Body body;
	
	public PayResponseMessage(){
		super.setHead(new ResponseHeader());
		this.body = new Body();
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	
	public class Body extends ResponseBody {

		private static final long serialVersionUID = 1355184358222866264L;
		private String payStr;
		//private String payInfo;
		private String merchantName;
		private String tradeNo;
		private String userOrderNo;//第三方上送的请求流水号
		//private String payPlatOrderNo;//微信或者支付宝给予的流水号,条码支付同步返回，扫码支付异步返回
		public String getPayStr() {
			return payStr;
		}
		public void setPayStr(String payStr) {
			this.payStr = payStr;
		}
//		public String getPayInfo() {
//			return payInfo;
//		}
//		public void setPayInfo(String payInfo) {
//			this.payInfo = payInfo;
//		}
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public String getUserOrderNo() {
			return userOrderNo;
		}
		public void setUserOrderNo(String userOrderNo) {
			this.userOrderNo = userOrderNo;
		}
//		public String getPayPlatOrderNo() {
//			return payPlatOrderNo;
//		}
//		public void setPayPlatOrderNo(String payPlatOrderNo) {
//			this.payPlatOrderNo = payPlatOrderNo;
//		}
		@Override
		public String toString() {
			return "Body [payStr=" + payStr + ", merchantName=" + merchantName + ", tradeNo=" + tradeNo
					+ ", userOrderNo=" + userOrderNo + "]";
		}
		
	
	}
}
