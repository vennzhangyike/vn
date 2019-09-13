package com.hfepay.timer.service.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.MessagePushRuleService;

public class MessagePushRuleJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(MessagePushRuleJob.class);
	
	private static MessagePushRuleService messagePushRuleService = Springs.getBean("messagePushRuleService");

	/**
	 * 定时扫描消息推送规则并推送消息
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("开始执行扫描消息推送规则并推送消息任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		try {
			messagePushRuleService.messagePush();			
			logger.info("完成执行扫描消息推送规则并推送消息任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		} catch (Exception e) {
			logger.info("推送消息任务异常："+e.getMessage());
		}		
	}
	
}
