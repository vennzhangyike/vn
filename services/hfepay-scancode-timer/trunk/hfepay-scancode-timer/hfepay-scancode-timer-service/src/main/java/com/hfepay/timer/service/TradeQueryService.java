package com.hfepay.timer.service;

public interface TradeQueryService {

	/**
	 * @Title: doTradeInfo
	 * @Description: 处理未正常回调的订单
	 * @author: husain
	 * @return: void
	 */
	void doTradeInfo(String flag);
}
