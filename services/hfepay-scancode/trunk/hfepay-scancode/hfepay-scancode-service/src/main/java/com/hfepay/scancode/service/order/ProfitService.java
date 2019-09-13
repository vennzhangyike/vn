package com.hfepay.scancode.service.order;

public interface ProfitService {

	/**
	 * @Title: doSaveProfit
	 * @Description: 交易利润
	 * @author: husain
	 * @return: void
	 */
	void doSaveProfit();
	
	/**
	 * @Title: showRedisRateDiff
	 * @Description: 显示利率差,作为调试用
	 * @author: husain
	 * @return: void
	 */
	void showRedisRateDiff();
	
	/**
	 * 在修改费率定时任务开启之前备份前一天的费率数据，包括商户费率，代理商费率，渠道费率
	 * @Title: bakupPayways
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void saveBakupPayways();
	
}
