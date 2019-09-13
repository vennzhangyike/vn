package com.hfepay.scancode.service.timer;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.scancode.service.order.WithdrawlsQueryService;

/**
 * @ClassName: TradeQueryJob
 * @Description: 订单补偿机制，定时扫描未正常支付的订单
 * @author: husain
 * @date: 2016年12月2日 下午5:34:59
 */
public class WithdrawlsQueryJobClear implements Job {
	
	Logger logger = LoggerFactory.getLogger(WithdrawlsQueryJobClear.class);
	
	private static WithdrawlsQueryService withdrawlsQueryService = Springs.getBean("withdrawlsQueryService");

	/**
	 * 处理未正常回调的订单信息
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行订单信息定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		String flag = "QSTXLX";
		withdrawlsQueryService.doTradeInfo(flag);
		logger.info("开始执行订单信息定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}

}
