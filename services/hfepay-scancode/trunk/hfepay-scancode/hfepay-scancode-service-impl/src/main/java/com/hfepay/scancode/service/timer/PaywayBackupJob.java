package com.hfepay.scancode.service.timer;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.scancode.service.order.ProfitService;

/**
 * @ClassName: PaywayBackupJob
 * @Description: 费率备份计算
 * @author: husain
 * @date: 2016年12月2日 下午5:34:59
 */
public class PaywayBackupJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(PaywayBackupJob.class);
	
	private static ProfitService profitService = Springs.getBean("profitService");

	/**
	 * 提成分配计算
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始费率备份计算定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		profitService.saveBakupPayways();
		logger.info("结束费率备份计算定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}

}
