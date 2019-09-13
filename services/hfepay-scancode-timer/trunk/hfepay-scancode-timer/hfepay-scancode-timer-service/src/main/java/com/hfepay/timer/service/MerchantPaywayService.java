/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service;

public interface MerchantPaywayService {
	
	
	
	/**
	 * 设置清算手续费
	 * @param channelNo
	 * @param liquidationFee
	 * @throws Exception
	 */
	void setLiquidationFee(String channelNo, String liquidationFee) throws Exception;

	/**
	 * 清算手续费job
	 */
	void liquidationFeeJob();

}

