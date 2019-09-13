package com.hfepay.timer.service.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.ReconciliationService;

public class ReconciliationJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(ReconciliationJob.class);
	
	private static ReconciliationService reconciliationService = Springs.getBean("reconciliationService");

	/**
	 * 定时对当日对账数据检查
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("开始执行对账数据检查定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		try {
			reconciliationService.reconciliationJob();
		} catch (Exception e) {
			logger.info("执行对账数据检查定时任务失败："+e.getMessage());
		}
		
		logger.info("完成执行对账数据检查定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}
}
