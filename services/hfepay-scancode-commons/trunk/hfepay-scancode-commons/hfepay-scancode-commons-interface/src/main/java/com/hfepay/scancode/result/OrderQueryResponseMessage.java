package com.hfepay.scancode.result;

import com.hfepay.scancode.bean.ResponseBody;
import com.hfepay.scancode.bean.ResponseHeader;
import com.hfepay.scancode.bean.ResponseMessage;


/**
 *支付返回报文体
 */
public class OrderQueryResponseMessage extends ResponseMessage<OrderQueryResponseMessage.Body> {
	private static final long serialVersionUID = 238032038699187311L;
	private Body body;
	
	public OrderQueryResponseMessage(){
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
		private String cashierNo;
		private String tradeNo;
		private String orderStatus;
//		private String payPlatOrderNo;//微信或者支付宝返回的流水号
		private String userOrderNo;//上送的流水号
		public String getCashierNo() {
			return cashierNo;
		}
		public void setCashierNo(String cashierNo) {
			this.cashierNo = cashierNo;
		}
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
//		public String getPayPlatOrderNo() {
//			return payPlatOrderNo;
//		}
//		public void setPayPlatOrderNo(String payPlatOrderNo) {
//			this.payPlatOrderNo = payPlatOrderNo;
//		}
		public String getUserOrderNo() {
			return userOrderNo;
		}
		public void setUserOrderNo(String userOrderNo) {
			this.userOrderNo = userOrderNo;
		}
		@Override
		public String toString() {
			return "Body [cashierNo=" + cashierNo + ", tradeNo=" + tradeNo + ", orderStatus=" + orderStatus
					+ ", userOrderNo=" + userOrderNo + "]";
		}
		
	}
}
