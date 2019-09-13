package com.hfepay.scancode.api.webank.service;

import java.util.List;
import java.util.Map;

import com.hfepay.scancode.api.webank.entity.vo.WeBankGoodsDetailVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantAccountsVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantInfoBaseVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantRateVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankPreCreateTradeVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankRefundVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatJsPayInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatMerchantInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatNaoInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatQueryInfoVo;

public interface WeBankMerchantBusinessService{

//----------------------------------------以下为微众支付宝扫码接口-----------------------------------------------------------------	
	/**
	 * @Title: getAlipayTicket
	 * @Description: 获取支付宝Ticket
	 * @author: husain
	 * @throws Exception
	 * @return: String
	 */
	public String getWeBankTicket();
	
	/**
	 * @Title: getAlipayToken
	 * @Description: 获取支付宝getAlipayToken
	 * @author: husain
	 * @throws Exception
	 * @return: String
	 */
	public String getWeBankAccessToken();
	
	/**
	 * 商户入网
	 * @param info 商户基本信息
	 * @param accounts 商户账户信息
	 * @param payTypes 商户费率信息列表
	 * @return merchantID商户编号
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> merchantJoin(WeBankMerchantInfoBaseVo baseVo,WeBankMerchantInfoVo infoVo,WeBankMerchantAccountsVo accountsVo,List<WeBankMerchantRateVo> rateList);
	
	/**
	 * 支付宝扫码预订单
	 * @param info 商品基本信息
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> preCreateTrade(WeBankPreCreateTradeVo tradeVo,List<WeBankGoodsDetailVo> goodsList);
	
	/**
	 * 查询交易订单
	 * @param 商户编号，订单号
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> queryTrade(WeBankRefundVo vo);
	
	/**
	 * 退款
	 * @param 商户编号，订单号,退款请求号
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> refund(WeBankRefundVo vo);
	
	/**
	 * 退款查询
	 * @param 商户编号，订单号,退款请求号
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> queryRefund(WeBankRefundVo vo);
	
	/**
	 * 支付宝条码支付
	 * @param info 商品基本信息
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> scanPay(WeBankPreCreateTradeVo tradeVo,List<WeBankGoodsDetailVo> goodsList);
	
	/**
	 * 支付宝撤销
	 * @param 商户编号，订单号
	 * @return 
	 * @throws Exception
	 * @author zy
	 */
	public Map<String, String> cancel(WeBankRefundVo vo);
	
//----------------------------------------以下为微众微信扫码接口---------------------------------------------------------

	/**
	 * 微信扫码商户入网
	 * @param info 商户基本信息
	 * @throws Exception
	 * @author zy
	 */
	public String weChatMerchantJoin(WeBankWeChatMerchantInfoVo infoVo);
	
	/**
	 * 微信扫码商户基本信息查询
	 * @param info 商户基本信息
	 * @throws Exception
	 * @author zy
	 */
	public String weChatSelectMerchantInfo(WeBankWeChatMerchantInfoVo infoVo);
	
	/**
	 * 微信扫码商户基本信息变更
	 * @param info 商户基本信息
	 * @throws Exception
	 * @author zy
	 */
	public String weChatUpdateMerchantInfo(WeBankWeChatMerchantInfoVo infoVo);
	
	/**
	 * 微信扫码下单
	 * @param info 下单
	 * @throws Exception
	 * @author zy
	 */
	public String weChatNao(WeBankWeChatNaoInfoVo naoInfoVo);
	
	/**
	 * 微信扫码 查询订单
	 * @param info 查询
	 * @throws Exception
	 * @author zy
	 */
	public String weChatNgos(WeBankWeChatQueryInfoVo queryInfoVo);
	
	/**
	 * 微信扫码 刷卡支付
	 * @param info 下单
	 * @throws Exception
	 * @author zy
	 */
	public String weChatMao(WeBankWeChatNaoInfoVo naoInfoVo);
	
	/**
	 * 微信扫码 查询刷卡支付订单
	 * @param info 查询
	 * @throws Exception
	 * @author zy
	 */
	public String weChatMgos(WeBankWeChatQueryInfoVo queryInfoVo);
	
	
	/**
	 * 微信扫码 冲正撤销
	 * @param info 
	 * @throws Exception
	 * @author zy
	 */
	public String weChatRo(WeBankWeChatQueryInfoVo queryInfoVo);
	
	/**
	 * 微信扫码 退款
	 * @param info 
	 * @throws Exception
	 * @author zy
	 */
	public String weChatNro(WeBankWeChatQueryInfoVo queryInfoVo);
	
	/**
	 * 微信扫码 退款查询
	 * @param info 
	 * @throws Exception
	 * @author zy
	 */
	public String weChatNros(WeBankWeChatQueryInfoVo queryInfoVo);
	
//----------------------------------------以下为微众微信公众号扫码接口---------------------------------------------------------
	
	/**
	 * 微信公众号支付  下单
	 * @param info 
	 * @throws Exception
	 * @author zy
	 */
	public String weChatJsPayAddOrder(WeBankWeChatJsPayInfoVo payInfoVo);
	
	/**
	 * 微信公众号支付  查询订单信息
	 * @param info 
	 * @throws Exception
	 * @author zy
	 */
	public String weChatJsPayGetOrderStatus(WeBankWeChatJsPayInfoVo payInfoVo);
}
