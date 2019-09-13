package com.hfepay.scancode.service.timer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.timer.service.TimerService;

/**
 * 定时器：定时任务直接添加到：initJob 方法即可
 * @author Administrator
 *
 */
public class InitTasks {
	Logger logger = LoggerFactory.getLogger(InitTasks.class);
	private TimerService timerService;
		
	public InitTasks(TimerService timerService) {
		this.timerService = timerService;
	}

	public void initJob() throws Exception {
		logger.info("初始化定时开始任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
		/**
		 * 定时将当日商户费率修改后，从商户费率临时表更新到商户费率表，民生银行23:55-0:05不能交易，将修改费率调整到0点执行
		 * 每天凌晨0点0分执行一次
		 * Cron : 0 0 0 * * ?
		 */
		timerService.addCronJob("MerchantPaywayJob", "JOBGROUP", MerchantPaywayJob.class, "0 0 3,4,5 * * ?");//费率修改3点执行
		
		timerService.addCronJob("PaywayBackupJob", "JOBGROUP", PaywayBackupJob.class, "0 56 23 * * ?");//备份前一天的费率数据23:56执行
		
		//如"0 15 10 * * ?"表示 每天上午10:15触发
		timerService.addCronJob("ProfitDistributionJob", "JOBGROUP", ProfitDistributionJob.class, "0 15 1 * * ?");//收益计算1:15开始执行
		
		timerService.addCronJob("TradeQueryJob", "JOBGROUP", TradeQueryJob.class, "0 0/10 * * * ?");//订单轮询补偿机制
		timerService.addCronJob("WithdrawlsQueryJob", "JOBGROUP", WithdrawlsQueryJob.class, "0 0/8 * * * ?");//提现轮询补偿机制
		
		//清算补偿机制
		timerService.addCronJob("TradeQueryJobClear", "JOBGROUP", TradeQueryJobClear.class, "0 56 23 * * ?");//订单轮询补偿机制
		//清算提现轮询补偿机制
		timerService.addCronJob("WithdrawlsQueryJobClear", "JOBGROUP", WithdrawlsQueryJobClear.class, "0 56 23 * * ?");
		
		
		//更新钱包金额凌晨两点
		timerService.addCronJob("HierarchicalSettlementTotalJob", "JOBGROUP", HierarchicalSettlementTotalJob.class, "0 0 5 * * ?");//收益计算2点开始执行
		
		timerService.addCronJob("MessagePushRuleJob", "JOBGROUP", MessagePushRuleJob.class, "0 0/10 * * * ?");//消息推送每十分钟扫描一次

		logger.info("初始化定时任务完成："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
	}
}
