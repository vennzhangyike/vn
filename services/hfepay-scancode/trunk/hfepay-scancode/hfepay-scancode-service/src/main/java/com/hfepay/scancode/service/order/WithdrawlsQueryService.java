package com.hfepay.scancode.service.order;

public interface WithdrawlsQueryService {

	/**
	 * @Title: doTradeInfo
	 * @Description: 处理未正常回调的提现
	 * @author: husain
	 * @return: void
	 */
	void doTradeInfo(String flag);
}
