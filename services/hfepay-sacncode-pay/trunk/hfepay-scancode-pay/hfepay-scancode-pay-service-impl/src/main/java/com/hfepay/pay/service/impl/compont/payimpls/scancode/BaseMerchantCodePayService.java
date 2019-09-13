package com.hfepay.pay.service.impl.compont.payimpls.scancode;

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

/**
 * 商户二维码支付基类
 * @author husain
 *
 */
@Component
public class BaseMerchantCodePayService extends BasePayService {
	public static final Logger log = LoggerFactory.getLogger(BaseMerchantCodePayService.class);
	@Autowired
	private BasePayQueryBiz basePayQueryBiz;
	// 支付
	protected Map<String, String> toPay(OrderBo orderBo) {
		log.info("#######orderBo[" + orderBo.toString() + "]######");
		Map<String, String> returnMap = new HashMap<>();
		Map<String, String> wechatOffreturnMap = new HashMap<>();
		try {
			// 新增参数校验,收款人,门店编号,渠道代理商商户编号不能缺少，金额不能小于0
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
				log.info("#######qrcode[" + orderBo.getQrCode() + "]请求参数不合法######");
			} else {
				// 调用支付API
				MerchantPayInfoVo vo = getPayParams(merchantInfo, orderBo, orderPayment);
				wechatOffreturnMap = executePay(vo);//微信公众号支付的需要重写该方法，默认是直接调用扫码支付接口
				log.info("#########wechatOffreturnMap is:" + wechatOffreturnMap);
				// 支付返回值
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
	
	@Override
	protected void checkParams(OrderBo orderBo) throws PayException {
		// TODO Auto-generated method stub
		super.checkParams(orderBo);
		if(Strings.isEmpty(orderBo.getQrCode())){//二维码不能为空
			log.error("scancode pay qrcode is null");
			throw new PayException(ScanCodeErrorCode.VALIDAT_100003.getCode(), ScanCodeErrorCode.VALIDAT_100003.getDesc());
		}
	}
}
