package com.hfepay.scancode.service.order;

public interface TradeQueryService {

	/**
	 * @Title: doTradeInfo
	 * @Description: 处理未正常回调的订单
	 * @author: husain
	 * @return: void
	 */
	void doTradeInfo(String flag);
}
