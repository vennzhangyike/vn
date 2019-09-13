package com.hfepay.pay.service.impl.compont;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.impl.compont.payimpls.BasePayService;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.dao.MerchantCostDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.MerchantQrcodeDAO;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.dao.PlatformQrcodeDAO;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
/**
 * 查询工具类
 * @author husain
 *
 */
@Component
public class BasePayQueryBiz {
	public static final Logger log = LoggerFactory.getLogger(BasePayQueryBiz.class);
	@Autowired
	private PlatformQrcodeDAO platformQrcodeDAO;
	@Autowired
	private MerchantQrcodeDAO merchantQrcodeDAO;
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	@Autowired
	private OrderPayDAO orderPayDAO;
	@Autowired
	private MerchantCostDAO merchantCostDAO;
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
	public PlatformQrcode findByQrCode(String qrCode) {
		log.info("#########qrCode["+qrCode+"]#########");
		if (Strings.isEmpty(qrCode)) {
			new RuntimeException("二维码编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		cb.andEQ("qrCode", qrCode);
		Criteria criteria = cb.buildCriteria();
		return platformQrcodeDAO.findOneByCriteria(criteria);
	}
	
	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
	public MerchantQrcode findByMerchantQrCode(String qrCode) {
		log.info("#########qrCode["+qrCode+"]#########");
		if (Strings.isEmpty(qrCode)) {
			new RuntimeException("二维码不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		cb.andEQ("qrCode", qrCode);
		Criteria criteria = cb.buildCriteria();
		return merchantQrcodeDAO.findOneByCriteria(criteria);
	}
	
	/**
	 * 根据订单编号查询订单明细
	 * @param tradeNo
	 * @return OrderPayment
	 */
	public OrderPayment findOrderPaymentByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * 根据订单编号查询资金流水信息
	 * @param tradeNo
	 * @return OrderPay
	 */
	public OrderPay findOrderPayByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(OrderPay.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return orderPayDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * 根据订单号查询商户成本信息
	 * @param tradeNo
	 * @return MerchantCost
	 */
	public MerchantCost findMerchantCostByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantCost.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCostDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * 商户费率创建
	 * @param merchantCostCondition
	 * @return
	 */
	public long insert(MerchantCostCondition merchantCostCondition){
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		return merchantCostDAO.insert(merchantCost);
	}
	
	/**
	 * 商户基本信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantInfo findByMerchantNo(String merchantNo){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		MerchantInfo merchantInfo = merchantInfoDAO.findOneByCriteria(buildCriteria);
		return merchantInfo;
	}
	
	public OrderPay createWithdrawsOrder(OrderPayCondition orderPayCondition) {
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		orderPayDAO.insert(orderPay);
		return orderPay;
	}
	
	public long update(OrderPayCondition orderPayCondition){
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		return orderPayDAO.update(orderPay);
	}
}
