package com.hfepay.scancode.service.timer;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.Springs;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantPaywayTmp;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantPaywayTmpService;

import net.sf.json.JSONSerializer;

public class MerchantPaywayJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(MerchantPaywayJob.class);
	
	private static MerchantPaywayTmpService merchantPaywayTmpService = Springs.getBean("merchantPaywayTmpService");
	private static MerchantPaywayService merchantPaywayService = Springs.getBean("merchantPaywayService");
	private static MerchantBusinessService merchantBusinessService = Springs.getBean("merchantBusinessService");

	/**
	 * 定时将当日商户费率修改后，从商户费率临时表更新到商户费率表
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("开始执行商户费率修改定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String yesterday = Dates.format(Dates.DF.yyyy_MM_dd, cal.getTime());
		logger.info("更新商户费率的时间为："+yesterday);
		MerchantPaywayTmpCondition merchantPaywayTmpCondition = new MerchantPaywayTmpCondition();
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		try {
			merchantPaywayTmpCondition.setUpdateTime(Dates.parse("yyyy-MM-dd", yesterday));
			List<MerchantPaywayTmp> merchantPaywayTmps = merchantPaywayTmpService.findAll(merchantPaywayTmpCondition);
			logger.info("共更新商户费率："+merchantPaywayTmps.size() + "条");
			for (MerchantPaywayTmp merchantPaywayTmp : merchantPaywayTmps) {
				MerchantPayway merchantPayway = merchantPaywayService.findByPayCode(merchantPaywayTmp.getPayCode(), merchantPaywayTmp.getMerchantNo());
				if(merchantPayway != null && !Strings.equals(merchantPaywayTmp.getAcceptStatus(), ScanCodeConstants.SUCCESS_STATE)){
					MerchantPaywayTmpCondition tmp = new MerchantPaywayTmpCondition();
					tmp.setId(merchantPaywayTmp.getId());
					merchantPaywayTmp.setId(merchantPayway.getId());
					merchantPaywayTmp.setCreateTime(null);
					BeanUtils.copyProperties(merchantPaywayTmp, merchantPaywayCondition);
					merchantPaywayCondition.setRateAmount(merchantPayway.getRateAmount());
					logger.info("更新商户费率入参："+JSONSerializer.toJSON(merchantPaywayCondition));
					
					MerchantRateVo payway = new MerchantRateVo();
					payway.setPayCode(merchantPaywayCondition.getPayCode());
					payway.setTradeRate(this.objToString(String.valueOf(merchantPaywayCondition.getT1Rate())));
					payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRate())));
					payway.setWithdrawRate(this.objToString(String.valueOf(merchantPaywayCondition.getT0Rate())));
					payway.setSettleAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRateAmount())));
					//调用商户费率变更接口
					try {
						Map<String, String> map = merchantBusinessService.merchantUpdateRate(merchantPaywayCondition.getMerchantNo(), payway);
						logger.info("调用商户费率变更接口成功："+map);
						if(Strings.equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS, map.get("respCode"))){							
							merchantPaywayService.update(merchantPaywayCondition);
							logger.info("更新商户费率成功："+JSONSerializer.toJSON(merchantPaywayCondition));
							CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
							cb.andEQ("id", tmp.getId());
							Criteria buildCriteria = cb.buildCriteria();
							tmp.setAcceptStatus(ScanCodeConstants.SUCCESS_STATE);
							merchantPaywayTmpService.updateByCriteria(tmp, buildCriteria);
							logger.info("更新商户费率临时表成功："+JSONSerializer.toJSON(tmp));
						}
						
					} catch (Exception e) {
						logger.info("调用商户费率变更接口失败："+e.getMessage());
					}
				}else{
					logger.info("更新商户费率失败："+JSONSerializer.toJSON(merchantPaywayTmp));
				}
			}
			
			logger.info("完成执行商户费率修改定时任务："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		} catch (ParseException e) {
			logger.info("执行商户费率修改定时任务失败："+e.getMessage());
		}
		
	}
	
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}

}
