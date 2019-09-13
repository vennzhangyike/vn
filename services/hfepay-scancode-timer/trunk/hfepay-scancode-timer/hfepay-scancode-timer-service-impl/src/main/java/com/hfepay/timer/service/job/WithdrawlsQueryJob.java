package com.hfepay.timer.service.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.WithdrawlsQueryService;

/**
 * @ClassName: TradeQueryJob
 * @Description: 订单补偿机制，定时扫描未正常支付的订单
 * @author: husain
 * @date: 2016年12月2日 下午5:34:59
 */
public class WithdrawlsQueryJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(WithdrawlsQueryJob.class);
	
	private static WithdrawlsQueryService withdrawlsQueryService = Springs.getBean("withdrawlsQueryService");

	/**
	 * 处理未正常回调的订单信息
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行订单信息定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		String flag = "RJQXLX";
		withdrawlsQueryService.doTradeInfo(flag);
		logger.info("开始执行订单信息定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}

}
