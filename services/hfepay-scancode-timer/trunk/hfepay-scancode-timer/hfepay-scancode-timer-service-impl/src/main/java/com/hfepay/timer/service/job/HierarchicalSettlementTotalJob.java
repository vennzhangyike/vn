package com.hfepay.timer.service.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Springs;
import com.hfepay.timer.service.HierarchicalSettlementTotalService;

/**
 * @ClassName: HierarchicalSettlementTotalJob
 * @Description: 分级利润和钱包金额更新
 * @author: husain
 * @date: 2016年12月2日 下午5:34:59
 */
public class HierarchicalSettlementTotalJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(HierarchicalSettlementTotalJob.class);
	
	private static HierarchicalSettlementTotalService hierarchicalSettlementTotalService = Springs.getBean("hierarchicalSettlementTotalService");

	/**
	 * 提成分配计算
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始分级利润和钱包金额更新定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		hierarchicalSettlementTotalService.saveSummaryProfit(Dates.format(Dates.DF.yyyy_MM_dd, new Date()));//结算日期为当天
		logger.info("结束分级利润和钱包金额更新定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
	}

}
