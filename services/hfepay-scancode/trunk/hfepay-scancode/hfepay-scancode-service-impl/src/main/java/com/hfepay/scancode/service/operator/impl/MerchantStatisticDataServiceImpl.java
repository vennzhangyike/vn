package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.scancode.commons.condition.MerchantOrderStatisticCondition;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.dao.MerchantStatisticDAO;
import com.hfepay.scancode.commons.dto.MerchantOrderStatisticDTO;
import com.hfepay.scancode.commons.vo.MerchantOrderStatisticVo;
import com.hfepay.scancode.service.operator.MerchantStatisticDataService;
import com.hfepay.scancode.service.utils.DateUtils;
import com.hfepay.scancode.service.utils.InitStatisticList;

/**
 * 统计类
 * @author tanbiao
 *
 */
@Service("merchantStatisticDataService")
public class MerchantStatisticDataServiceImpl implements MerchantStatisticDataService {
	@Autowired
	private MerchantStatisticDAO merchantStatisticDAO;
	
	/**
	 * 订单统计
	 * @param condition
	 * @author tanbiao
	 * @return
	 */
	@Override
	public MerchantOrderStatisticVo orderStatistic(MerchantOrderStatisticCondition condition) {
		// TODO Auto-generated method stub
		List<String> xList = new ArrayList<>();//横坐标数据一个，纵坐标数据不确定
		Integer capcity = wrapConditionXYData(xList, condition);//初始化横坐标纵坐标查询的条件
		
		List<String> yOrderPaySuccessAliList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPaySuccessWechatList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPaySuccessQQList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailAliList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailWechatList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailQQList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		//查询条件进行包装
		
		
		//支付订单成功 支付宝
		condition.setStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
		condition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessAliList = merchantStatisticDAO.orderPayStatistic(condition);
		//支付订单成功 微信
		condition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessWechantList = merchantStatisticDAO.orderPayStatistic(condition);
		//支付订单成功 QQ
		condition.setPayType(PayType.PAYTYPE_QQ.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessQQList = merchantStatisticDAO.orderPayStatistic(condition);
		//支付订单失败 支付宝
		condition.setStatus(PayStatus.PAYSTATUS_FAIL.getCode());
		condition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailAliList = merchantStatisticDAO.orderPayStatistic(condition);
		//支付订单失败 微信
		condition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailWechatList = merchantStatisticDAO.orderPayStatistic(condition);
		//支付订单失败 qq
		condition.setPayType(PayType.PAYTYPE_QQ.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailQQList = merchantStatisticDAO.orderPayStatistic(condition);
		
		//退款订单成功
		/*condition.setStatus("REFUND_SUCCESS");
		List<MerchantOrderStatisticDTO> orderRefundSuccesslist = merchantStatisticDAO.orderRefundStatistic(condition);*/
		//退款订单失败
		/*condition.setStatus("REFUND_FAILURE");
		List<MerchantOrderStatisticDTO> orderRefundFaillist = merchantStatisticDAO.orderRefundStatistic(condition);*/
		
		//对数据进行非零填充
		wrapList(orderPaySuccessAliList, yOrderPaySuccessAliList,xList);
		wrapList(orderPaySuccessWechantList, yOrderPaySuccessWechatList,xList);
		wrapList(orderPaySuccessQQList, yOrderPaySuccessQQList,xList);
		wrapList(orderPayFailAliList, yOrderPayFailAliList,xList);
		wrapList(orderPayFailWechatList, yOrderPayFailWechatList,xList);
		wrapList(orderPayFailQQList, yOrderPayFailQQList,xList);
		
		MerchantOrderStatisticVo vo = new MerchantOrderStatisticVo();
		vo.setOrderPaySuccessAliData(yOrderPaySuccessAliList);
		vo.setOrderPaySuccessWechatData(yOrderPaySuccessWechatList);
		vo.setOrderPaySuccessQQData(yOrderPaySuccessQQList);
		vo.setOrderPayFailAliData(yOrderPayFailAliList);
		vo.setOrderPayFailWechatData(yOrderPayFailWechatList);
		vo.setOrderPayFailQQData(yOrderPayFailQQList);
		vo.sethCoordinateList(xList);//横坐标数据
		vo.setTitle(getTitle(condition));
		return vo;
	}
	
	//修改结果集
	private void wrapList(List<MerchantOrderStatisticDTO> dtoList,List<String> list,List<String> xList){
		if(dtoList.size()==0){//数据集为空，则全为0
			return;
		}
		//对非0的时间段赋值
		for (MerchantOrderStatisticDTO dto : dtoList) {
			if(xList.size()==24){
				list.set(dto.getTime(), dto.getCount());
			}else{
				list.set(dto.getTime()!=0?dto.getTime()-1:0, dto.getCount());
			}
		}
	}
	
	/**
	 * 初始化横坐标纵坐标数据,同时初始化查询条件
	 * @param xList
	 * @param yList
	 * @param type
	 */
	private Integer wrapConditionXYData(List<String> xList,MerchantOrderStatisticCondition condition){
		Integer length = 0;
		String fmt = "yyyy-MM-dd";
		//如果是月获取有多少天
		if(condition.getType().equals("M")){
			int day = DateUtils.getMonthDays(condition.getStartTime(), fmt);
			xList.addAll(InitStatisticList.monthList.subList(0, day));
			condition.setStartTime(DateUtils.formatDateFmt(DateUtils.getDateBystr(condition.getStartTime(), fmt), "yyyy-MM"));
			length = day;
		}
		else if(condition.getType().equals("Y")){
			xList.addAll(InitStatisticList.yearList);
			condition.setStartTime(DateUtils.formatDateFmt(DateUtils.getDateBystr(condition.getStartTime(), fmt), "yyyy"));
			length = 12;
		}
		else if(condition.getType().equals("D")){
			xList.addAll(InitStatisticList.dayList);
			length = 24;
		}
		else if(condition.getType().equals("W")){
			xList.addAll(InitStatisticList.weekList);
			List<String> list = DateUtils.getWeekDate(condition.getStartTime(), fmt);
			condition.setStartTime(list.get(0));
			condition.setEndTime(list.get(1));
			length = 7;
		}
		return length;
	}
	
	private String getTitle(MerchantOrderStatisticCondition condition){
		if("W".equals(condition.getType())){
			return condition.getStartTime()+"~"+condition.getEndTime();
		}else{
			return condition.getStartTime();
		}
	}

	@Override
	public MerchantOrderStatisticVo orderAmtStatistic(MerchantOrderStatisticCondition condition) {
		List<String> xList = new ArrayList<>();//横坐标数据一个，纵坐标数据不确定
		Integer capcity = wrapConditionXYData(xList, condition);//初始化横坐标纵坐标查询的条件
		
		List<String> yOrderPaySuccessAliList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPaySuccessWechatList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPaySuccessQQList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailAliList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailWechatList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		List<String> yOrderPayFailQQList = new ArrayList<>(InitStatisticList.initDataList.subList(0, capcity));//纵坐标初始化容量初始值为0
		//查询条件进行包装
		
		
		//支付订单成功 支付宝
		condition.setStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
		condition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessAliList = merchantStatisticDAO.orderPayAmtStatistic(condition);
		//支付订单成功 微信
		condition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessWechantList = merchantStatisticDAO.orderPayAmtStatistic(condition);
		//支付订单成功 QQ
		condition.setPayType(PayType.PAYTYPE_QQ.getCode());
		List<MerchantOrderStatisticDTO> orderPaySuccessQQList = merchantStatisticDAO.orderPayAmtStatistic(condition);
		//支付订单失败 支付宝
		condition.setStatus(PayStatus.PAYSTATUS_FAIL.getCode());
		condition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailAliList = merchantStatisticDAO.orderPayAmtStatistic(condition);
		//支付订单失败 微信
		condition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailWechatList = merchantStatisticDAO.orderPayAmtStatistic(condition);
		//支付订单失败 qq
		condition.setPayType(PayType.PAYTYPE_QQ.getCode());
		List<MerchantOrderStatisticDTO> orderPayFailQQList = merchantStatisticDAO.orderPayAmtStatistic(condition);		
		//退款订单成功
		/*condition.setStatus("REFUND_SUCCESS");
		List<MerchantOrderStatisticDTO> orderRefundSuccesslist = merchantStatisticDAO.orderRefundAmtStatistic(condition);
		//退款订单失败
		condition.setStatus("REFUND_FAILURE");
		List<MerchantOrderStatisticDTO> orderRefundFaillist = merchantStatisticDAO.orderRefundAmtStatistic(condition);*/
		
		//对数据进行非零填充
		wrapList(orderPaySuccessAliList, yOrderPaySuccessAliList,xList);
		wrapList(orderPaySuccessWechantList, yOrderPaySuccessWechatList,xList);
		wrapList(orderPaySuccessQQList, yOrderPaySuccessQQList,xList);
		wrapList(orderPayFailAliList, yOrderPayFailAliList,xList);
		wrapList(orderPayFailWechatList, yOrderPayFailWechatList,xList);
		wrapList(orderPayFailQQList, yOrderPayFailQQList,xList);
		
		MerchantOrderStatisticVo vo = new MerchantOrderStatisticVo();
		vo.setOrderPaySuccessAliData(yOrderPaySuccessAliList);
		vo.setOrderPaySuccessWechatData(yOrderPaySuccessWechatList);
		vo.setOrderPaySuccessQQData(yOrderPaySuccessQQList);
		vo.setOrderPayFailAliData(yOrderPayFailAliList);
		vo.setOrderPayFailWechatData(yOrderPayFailWechatList);
		vo.setOrderPayFailQQData(yOrderPayFailQQList);
		vo.sethCoordinateList(xList);//横坐标数据
		vo.setTitle(getTitle(condition));
		return vo;
	}
	
}
