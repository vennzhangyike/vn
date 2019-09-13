package com.hfepay.timer.service.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.MerchantPaywayService;

public class LiquidationFeeJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(LiquidationFeeJob.class);
	
	private static MerchantPaywayService merchantPaywayService = Springs.getBean("merchantPaywayService");

	/**
	 * 定时扫描次日清算手续费
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("开始执行清算手续费任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		try {
			merchantPaywayService.liquidationFeeJob();
		} catch (Exception e) {
			logger.error("执行清算手续费定时任务失败："+e.getMessage());
		}
		
		logger.info("完成执行清算手续费定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}
}
