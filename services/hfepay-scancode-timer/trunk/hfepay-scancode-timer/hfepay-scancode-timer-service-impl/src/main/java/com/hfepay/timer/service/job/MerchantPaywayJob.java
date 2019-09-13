package com.hfepay.timer.service.job;

import java.text.ParseException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.MerchantPaywayTmpService;

public class MerchantPaywayJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(MerchantPaywayJob.class);
	
	private static MerchantPaywayTmpService merchantPaywayTmpService = Springs.getBean("merchantPaywayTmpService");

	/**
	 * 定时将当日商户费率修改后，从商户费率临时表更新到商户费率表
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("开始执行商户费率修改定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		try {
			merchantPaywayTmpService.updatePaywayJob();			
			logger.info("完成执行商户费率修改定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		} catch (ParseException e) {
			logger.info("执行商户费率修改定时任务失败："+e.getMessage());
		}
		
	}
	
}
