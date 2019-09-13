package com.hfepay.pay.service.impl.compont.payimpls.bar;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.exception.PayException;
import com.hfepay.pay.service.impl.compont.BasePayQueryBiz;
import com.hfepay.pay.service.impl.compont.payimpls.BasePayService;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.vo.MerchantPayInfoVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;

import net.sf.json.JSONObject;

/**
 * 条码支付基类
 * @author husain
 *
 */
@Component
public class BaseBarCodePayService extends BasePayService {
	public static final Logger log = LoggerFactory.getLogger(BaseBarCodePayService.class);
	@Autowired
	private BasePayQueryBiz basePayQueryBiz;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	// 支付
	protected Map<String, String> toPay(OrderBo orderBo) {
		log.info("#######orderBo[" + orderBo.toString() + "]######");
		Map<String, String> returnMap = new HashMap<>();
		Map<String, String> wechatOffreturnMap = new HashMap<>();
		
		try {
			// 新增参数校验,收款人,门店编号,渠道代理商商户编号不能缺少，金额不能小于0,authCode,scene
			checkParams(orderBo);
			// 限额处理
			limitCash(orderBo);

			MerchantInfo merchantInfo = basePayQueryBiz.findByMerchantNo(orderBo.getMerchantNo());
			// 设置默认的产品名称，若存在二维码，则取二维码产品名称
			orderBo.setProductName(merchantInfo.getMerchantName());
			// 保存支付订单
			OrderPayment orderPayment = saveOrder(orderBo);

			// 支付参数未配置，直接失败
			if (orderPayment.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
				returnMap.put("returnCode", ScanCodeErrorCode.VALIDAT_100003.getCode());
				returnMap.put("returnMsg", ScanCodeErrorCode.VALIDAT_100003.getDesc());
				if(Strings.isNotEmpty(orderPayment.getResultInfo())){
					returnMap.put("returnMsg", orderPayment.getResultInfo());
				}
				log.info("#######orderBo[" + orderBo + "]请求参数不合法######");
			} else {
				returnMap.put("discountAmt", orderBo.getDiscountAmt()+"");//打折信息返回
				returnMap.put("price", orderBo.getPrice()+"");//实际支付金额返回
				// 调用支付API
				MerchantPayInfoVo vo = getPayParams(merchantInfo, orderBo, orderPayment);
				wechatOffreturnMap = executePay(vo);
				log.info("#########wechatOffreturnMap is:" + wechatOffreturnMap);
				// 支付返回值
				returnMap.put("userOrderNo", orderPayment.getTradeNo());
				handleReturnMap(wechatOffreturnMap, returnMap);
			}
			returnMap.put("tradeNo", orderPayment.getTradeNo());
			returnMap.put("merchantName", merchantInfo.getMerchantName());
			log.info("########wechatOfficialMap[" + JSON.toJSONString(returnMap) + "]#######");
		}catch (PayException e) {//参数校验等支付过程中自定义的异常
			e.printStackTrace();
			log.error("#########pay error:" + e);
			returnMap.put("returnCode", e.getCode());
			returnMap.put("returnMsg", e.getMessage());
			return returnMap;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("#########pay error:" + e);
			returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999999.getCode());
			returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_999999.getDesc());
			return returnMap;
		}
		return returnMap;
	}
	
	//支付调用的接口差异，复写之类方法
	@Override
	protected Map<String, String> executePay(MerchantPayInfoVo vo) throws Exception {
		// TODO Auto-generated method stub
		return merchantBusinessService.merchantScanPay(vo);
	}
	
	// 回调
	protected void toPayCallBack(MerchantNotifyMessage bo) throws Exception {
		log.info("###########原交易[" + bo + "]数据#############");
		// 1.查询订单信息
		OrderPayment orderPayment = basePayQueryBiz.findOrderPaymentByTradeNo(bo.getUserOrderNo());
		OrderPay orderPay = basePayQueryBiz.findOrderPayByTradeNo(bo.getUserOrderNo());
		if (null != orderPayment) {
			if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
				MerchantCost merchantCost = basePayQueryBiz.findMerchantCostByTradeNo(bo.getUserOrderNo());
				if (merchantCost != null) {
					log.info("###########重复的支付回调订单：" + bo.getOrderNo() + "，不给与处理#############");
					return;
				}
				// 2.创建商户成本
				MerchantCostCondition merchantCostCondition = createMerchantCost(orderPay);
				orderPayment.setMerchantCost(merchantCostCondition.getMerchantCost());
				orderPay.setMerchantCost(merchantCostCondition.getMerchantCost());
				// 3.推送消息
				pushMessage(bo, orderPayment);
			}
			// 4.根据状态修改订单信息
			updateOrderPay(bo, orderPay);
			updateOrderPayment(bo, orderPayment);
		} else {
			log.error("###########原交易[" + bo.getOrderNo() + "]数据不存在#############");
		}
	}
	
	/**
	 * 条码支付需要新增授权码和scene校验
	 */
	@Override
	protected void checkParams(OrderBo orderBo) throws PayException {
		super.checkParams(orderBo);
		if(Strings.isEmpty(orderBo.getScene())||Strings.isEmpty(orderBo.getAuthCode())){
			log.error("bar pay scene,authcode is null");
			throw new PayException(ScanCodeErrorCode.VALIDAT_100003.getCode(), ScanCodeErrorCode.VALIDAT_100003.getDesc());
		}
	}
	
	//产品名称已经给定了初始值，并且支付没有二维码无需更改产品名称
	@Override
	protected void setProduct(OrderPaymentCondition orderPaymentCondition, OrderBo orderBo) {
		
	}
	
	/**
	 * 支付参数组装
	 */
	@Override
	protected MerchantPayInfoVo getPayParams(MerchantInfo merchantInfo, OrderBo orderBo, OrderPayment orderPayment) {
		// TODO Auto-generated method stub
		MerchantPayInfoVo vo = super.getPayParams(merchantInfo, orderBo, orderPayment);
		vo.setScene(orderBo.getScene());
		vo.setAuthCode(orderBo.getAuthCode());
		return vo;
	}
	
	//针对结果集进行处理
	@Override
	protected void handleReturnMap(Map<String, String> wechatOffreturnMap, Map<String, String> returnMap) {
		Map<String,String> resMap = JSON.parseObject(JSON.toJSONString(wechatOffreturnMap.get("head")), Map.class);//responseHandle(wechatOffreturnMap);
		log.info("response msg map is:"+wechatOffreturnMap);
		String userOrderNo = returnMap.get("userOrderNo");
		if("200002".equals(resMap.get("respCode"))){//交易状态未知，异步回调，存在报文体
			log.info("in 200002 case wait for callback");
			returnMap.put("returnCode", resMap.get("respCode"));
			returnMap.put("returnMsg", "支付处理中，等待银行回调");
			returnMap.put("userOrderNo",userOrderNo);
		}
		else{
			log.info("==========同步返回=====================");
			//修改日志
			updateApiLog(userOrderNo, wechatOffreturnMap.toString());
			MerchantNotifyMessage merchantNotifyMessage = null;
			//支付返回值
			if (ScanCodeErrorCode.SYSTEM_000000.getCode().equals(resMap.get("respCode"))) {//返回000000一定有报文体body
				//处理订单信息：和回调类似的处理
				//String body = (String) responseMap.get("body");
				JSONObject object = JSONObject.fromObject(wechatOffreturnMap);
				String body = object.getString("body");
				JSONObject jsObject = JSONObject.fromObject(body);
				merchantNotifyMessage = JSON.parseObject(body, MerchantNotifyMessage.class);
				
				merchantNotifyMessage.setUserReqNo(resMap.get("userReqNo").toString());
				merchantNotifyMessage.setStatus(jsObject.getString("orderStatus"));//订单状态
				try {
					//校验参数
					Map<String,String> returnInfo = checkCallBackOrder(merchantNotifyMessage);
					if (null != returnInfo) {
						returnMap.putAll(returnInfo);
						log.error("#########支付系统错误:"+JSON.toJSONString(returnInfo)+"########");
						return;
					}
					
					returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
					returnMap.put("userOrderNo",userOrderNo);
				} catch (Exception e) {
					log.error("#########handle success situation error:"+e);
				}
			}else {//非成功状态直接返回，如果涉及到异步的情况也无需再做业务处理，在回调总处理，支付失败
				merchantNotifyMessage = new MerchantNotifyMessage();
				merchantNotifyMessage.setUserOrderNo(userOrderNo);
				merchantNotifyMessage.setErrorMsg(resMap.get("respDesc"));
				merchantNotifyMessage.setErrorCode(resMap.get("respCode"));
				merchantNotifyMessage.setOrderNo("");//不存在
				merchantNotifyMessage.setStatus(PayStatus.PAYSTATUS_FAIL.getCode());
				//toPayCallBack(merchantNotifyMessage);
				returnMap.put("returnCode", resMap.get("respCode"));
				returnMap.put("returnMsg", resMap.get("respDesc"));
			}
			//回调逻辑
			try {
				toPayCallBack(merchantNotifyMessage);
			} catch (Exception e) {
				log.error("callback error..",e);
			}
		}
		
	}
	
	/**
	 * 支付回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackOrder(MerchantNotifyMessage bo) {
		if (Strings.isEmpty(bo.getOrderNo())) {
			return returnInfo("100002","订单编号不能为空");
		}
		if (Strings.isEmpty(bo.getUserOrderNo())) {
			return returnInfo("100002","商户订单号不能为空");
		}
		if (Strings.isEmpty(bo.getStatus())) {
			return returnInfo("100002","支付状态不能为空");
		}
		return null;
	}
	
	private Map<String,String> returnInfo(String returnCode,String returnMsg) {
		Map<String,String> returnInfo = new HashMap<String,String>();
		returnInfo.put("returnCode", returnCode);
		returnInfo.put("returnMsg", returnMsg);
		return returnInfo;
	}
	
	/**
	 * 回调地址不同在此处更新
	 */
	@Override
	protected void updateNotifyUrl(OrderPaymentCondition orderPaymentCondition, JSONObject callbackJson) {
		orderPaymentCondition.setNotifyUrl(callbackJson.getString("scanPayNotifyUrl"));
		orderPaymentCondition.setReturnUrl(callbackJson.getString("returnUrl"));
	}
}
