/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.timer.service.impl;

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
import com.hfepay.pay.service.PayService;
import com.hfepay.scancode.api.entity.vo.TradeQueryVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.timer.service.TradeQueryService;
import com.hfepay.timer.service.utils.DateUtils;

import net.sf.json.JSONObject;

@Service("tradeQueryService")
public class TradeQueryServiceImpl implements TradeQueryService {
	public static final Logger log = LoggerFactory.getLogger(TradeQueryServiceImpl.class);

	@Autowired
    private OrderPaymentDAO orderPaymentDAO;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	
//	@Autowired
//	private PayCallBackService payCallBackService;
	
	@Autowired
	private PayService payService;
	
	@Override
	public void doTradeInfo(String flag) {
		try {
			//查询订单信息
			OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
			List<OrderPayment> paymentList = null;
			if (flag.equals("RJLX")) {
				log.info("#########本次交易轮询"+flag+"#########");
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_TREATE.getCode());
				String nowTime_10 = getNowBefor10Min();
				orderPaymentCondition.setBeginTime(DateUtils.parse(nowTime_10,"yyyy-MM-dd HH:mm:ss"));
				
				CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
				cb.andLT("beginTime", orderPaymentCondition.getBeginTime());
				cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
				
				Criteria buildCriteria = cb.buildCriteria();
				paymentList =  orderPaymentDAO.findByCriteria(buildCriteria);
				
			}else {
				log.info("#########本次交易轮询"+flag+"#########");
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
				Date beginTime = Dates.parse("yyyy-MM-dd", DateUtils.formatDateFmt(new Date(), "yyyy-MM-dd"));
				orderPaymentCondition.setBeginTime(beginTime);	
				String endTimeStr = DateUtils.formatDateFmt(new Date(), "yyyy-MM-dd");
				endTimeStr = endTimeStr + " 23:59:59";
				Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
				orderPaymentCondition.setEndTime(endTime);
				CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
				cb.andGT("beginTime", orderPaymentCondition.getBeginTime());
				cb.andLE("beginTime", orderPaymentCondition.getEndTime());
				cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
				
				Criteria buildCriteria = cb.buildCriteria();
				paymentList =  orderPaymentDAO.findByCriteria(buildCriteria);
			}
			if (null == paymentList || paymentList.size() < 1) {
				log.info("#########本次交易轮询暂无需要查询的交易，轮询退出#########");
				return ;
			}
			for (int i = 0; i < paymentList.size(); i++) {
				log.info("#########本次交易轮询总共:"+paymentList.size()+",目前处理第："+i+1+"条#########");
				OrderPayment orderPayment = paymentList.get(i);
				TradeQueryVo queryVo = new TradeQueryVo();
				queryVo.setOrderNo(orderPayment.getPayTradeNo());
				queryVo.setUserOrderNo(orderPayment.getTradeNo());
				
				CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
				cb.andEQ("merchantNo", orderPayment.getMerchantNo());
				Criteria buildCriteria = cb.buildCriteria();
				MerchantInfo merchantInfo =  merchantInfoDAO.findOneByCriteria(buildCriteria);
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				queryVo.setMerchantNo(merchantInfo.getPlatformMerchantNo());
			
				Map<String, String> map = merchantBusinessService.tradeQuery(queryVo);
				log.info("#########本次交易轮询map"+JSON.toJSONString(map)+"########");
				MerchantNotifyMessage queryMsg = new MerchantNotifyMessage();
				queryMsg.setPayStrategy(orderPayment.getTemp3());//指定策略
				//查询非正常返回数据
				if (map.get("respCode").equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {					
					String orderAmt = String.valueOf(map.get("orderAmt"));
					log.info("#########本次交易轮询orderAmt"+orderAmt+"########");
					
					queryMsg.setUserReqNo("");
					queryMsg.setOrderNo(map.get("orderNo"));		//平台交易订单号
					queryMsg.setUserOrderNo(map.get("userOrderNo"));//商户交易订单号
					queryMsg.setPayType(map.get("payCode"));		//支付渠道
					queryMsg.setChannelNo(map.get("channelNo"));	//渠道编号
					queryMsg.setMerchantNo(map.get("merchantNo"));	//商户编号
					queryMsg.setMerchantName(map.get(""));			//商户名称
					queryMsg.setOrderTitle(map.get("orderTitle"));	//订单标题
					queryMsg.setOrderDesc(map.get("orderDesc"));	//订单描述
					queryMsg.setOrderAmt(new BigDecimal(orderAmt.toString()));//订单金额
					queryMsg.setStatus(map.get("orderStatus"));		//订单状态
					queryMsg.setBeginTime(map.get("beginTime"));	//订单开始时间yyyy-MM-dd HH:mm:ss
					queryMsg.setEndTime(map.get("endTime"));		//订单交易结束时间yyyy-MM-dd HH:mm:ss
					queryMsg.setErrorMsg(map.get("原交易状态为初始，补偿机制轮询"));		//订单信息
					queryMsg.setAccountType(map.get("accountType"));
				}else {
					queryMsg.setOrderNo(orderPayment.getPayTradeNo());		//平台交易订单号
					queryMsg.setUserOrderNo(orderPayment.getTradeNo());		//商户交易订单号
					queryMsg.setStatus(PayStatus.PAYSTATUS_FAIL.getCode());		//订单状态
					queryMsg.setErrorMsg(map.get("原交易状态为初始，补偿机制轮询改为失败"));		//订单信息
				}
				log.info("#########本次交易轮询queryMsg"+JSON.toJSONString(queryMsg)+"########");
				payService.payCallBack(queryMsg);
				log.info("#########本次交易轮询总共:"+paymentList.size()+",处理完成第："+i+1+"条#########");
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
	
	public static void main(String[] args) {
		
		System.out.println(new BigDecimal(123.11));
	}
}

