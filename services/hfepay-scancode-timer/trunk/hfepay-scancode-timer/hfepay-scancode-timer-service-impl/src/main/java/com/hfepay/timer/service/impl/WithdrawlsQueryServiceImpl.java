/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.pay.service.PayCallBackService;
import com.hfepay.pay.service.WithdrawService;
import com.hfepay.scancode.api.entity.vo.TradeQueryVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.WithdrawalsDAO;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.timer.service.WithdrawlsQueryService;
import com.hfepay.timer.service.utils.DateUtils;

import net.sf.json.JSONObject;

@Service("withdrawlsQueryService")
public class WithdrawlsQueryServiceImpl implements WithdrawlsQueryService {
	public static final Logger log = LoggerFactory.getLogger(WithdrawlsQueryServiceImpl.class);

	@Autowired
    private WithdrawalsDAO withdrawalsDAO;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	
//	@Autowired
//	private PayCallBackService payCallBackService;
	
	@Autowired
	private WithdrawService withdrawService;
	
	@Override
	public void doTradeInfo(String flag) {
		List<Withdrawals> withdrawalsList = null;
		try {
			//查询订单信息
			WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
			if (flag.equals("RJQXLX")) {
				log.info("#########本次提现轮询"+flag+"#########");
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_CREATE.getCode());
				String nowTime_10 = getNowBefor10Min();
				withdrawalsCondition.setBeginTime(DateUtils.parse(nowTime_10,"yyyy-MM-dd HH:mm:ss"));
				
				CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
				cb.andLT("beginTime", withdrawalsCondition.getBeginTime());
				cb.andEQ("resultCode", withdrawalsCondition.getResultCode());
				Criteria buildCriteria = cb.buildCriteria();
				withdrawalsList =  withdrawalsDAO.findByCriteria(buildCriteria);
			}else {
				log.info("#########本次提现轮询"+flag+"#########");
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
				
				Date beginTime = Dates.parse("yyyy-MM-dd", DateUtils.formatDateFmt(new Date(), "yyyy-MM-dd"));
				withdrawalsCondition.setBeginTime(beginTime);	
				String endTimeStr = DateUtils.formatDateFmt(new Date(), "yyyy-MM-dd");
				endTimeStr = endTimeStr + " 23:59:59";
				Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
				withdrawalsCondition.setEndTime(endTime);
				
				CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
				cb.andGT("beginTime", withdrawalsCondition.getBeginTime());
				cb.andLE("beginTime", withdrawalsCondition.getEndTime());
				cb.andEQ("orderStatus", withdrawalsCondition.getResultCode());
				
				Criteria buildCriteria = cb.buildCriteria();
				withdrawalsList = withdrawalsDAO.findByCriteria(buildCriteria);
			}
			
			if (null == withdrawalsList || withdrawalsList.size() < 1) {
				log.info("#########本次提现轮询暂无需要查询的交易，轮询退出#########");
				return ;
			}
			for (int i = 0; i < withdrawalsList.size(); i++) {
				log.info("#########本次提现轮询总共:"+withdrawalsList.size()+",目前处理第："+i+"条#########");
				Withdrawals withdrawals = withdrawalsList.get(i);
				TradeQueryVo queryVo = new TradeQueryVo();
				queryVo.setOrderNo(withdrawals.getPayTradeNo());
				queryVo.setUserOrderNo(withdrawals.getTradeNo());
				
				CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
				cb.andEQ("merchantNo", withdrawals.getMerchantNo());
				Criteria buildCriteria = cb.buildCriteria();
				MerchantInfo merchantInfo =  merchantInfoDAO.findOneByCriteria(buildCriteria);
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				queryVo.setMerchantNo(merchantInfo.getPlatformMerchantNo());
			
				Map<String, String> map = merchantBusinessService.tradeQuery(queryVo);
				log.info("#########本次提现轮询map"+JSON.toJSONString(map)+"########");
				
				MerchantWithdrowNotifyMessage queryMsg = new MerchantWithdrowNotifyMessage();
				//查询非正常返回数据
				if (map.get("respCode").equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {
					queryMsg.setUserReqNo(map.get(""));
					queryMsg.setOrderNo(map.get("orderNo"));
					queryMsg.setUserOrderNo(map.get("userOrderNo"));
					queryMsg.setPayCode(map.get("payCode"));
					queryMsg.setStatus(map.get("status"));
					queryMsg.setDrawAmount(new BigDecimal(map.get("drawAmount")));
					queryMsg.setDrawFee(new BigDecimal(map.get("drawFee")));
					queryMsg.setTradeFee(new BigDecimal(map.get("tradeFee")));
					queryMsg.setSettleDate(map.get("settleDate"));
					queryMsg.setCreateDate(map.get("createDate"));
				}else {
					queryMsg.setOrderNo(withdrawals.getPayTradeNo());
					queryMsg.setUserOrderNo(withdrawals.getTradeNo());
					queryMsg.setStatus(WithdrawsStatus.WD_STATUS_FAIL.getCode());
				}
				log.info("#########本次提现轮询queryMsg"+JSON.toJSONString(queryMsg)+"########");
				withdrawService.withdrawsCallBack(queryMsg);
				log.info("#########本次提现轮询总共:"+withdrawalsList.size()+",处理完成第："+i+"条#########");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当前日期的前10分钟
	 * @return
	 */
	private static String getNowBefor10Min() {
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 600000); //10分钟前的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String nowTime_10 = dateFormat.format(now_10);
		return nowTime_10;
	}
	
	/**
	 * 当前日期的前10分钟
	 * @return
	 */
	private static String getNowBefor60Min() {
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 3600000); //10分钟前的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String nowTime_10 = dateFormat.format(now_10);
		return nowTime_10;
	}
}

