package com.hfepay.scancode.api.service;

import java.util.List;
import java.util.Map;

import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.message.RegistSuccessMessage;
import com.hfepay.scancode.api.entity.message.UnperfectMessage;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.api.entity.vo.BankCardAuthVo;
import com.hfepay.scancode.api.entity.vo.DrawQueryVo;
import com.hfepay.scancode.api.entity.vo.MerchantAccountsVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoChangeVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantPayInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.entity.vo.MerchantRefundVo;
import com.hfepay.scancode.api.entity.vo.MerchantUpdateCardVo;
import com.hfepay.scancode.api.entity.vo.MerchantWithdrawsInfoVo;
import com.hfepay.scancode.api.entity.vo.TradeQueryVo;

public interface MerchantBusinessService{

	
	/**
	 * 商户入网
	 * @param info 商户基本信息
	 * @param accounts 商户账户信息
	 * @param payTypes 商户费率信息列表
	 * @return
	 * @throws Exception
	 * @author lemon
	 */
	public Map<String, String> merchantJoin(MerchantInfoVo info, MerchantAccountsVo accounts, List<MerchantRateVo> payTypes) throws Exception;
	
	/**
	 * 商户入网信息变更
	 * @param info 商户基本信息
	 * @return
	 * @throws Exception
	 * @author lemon
	 */
	public Map<String, String> merchantJoinChange(MerchantInfoChangeVo info) throws Exception;
	
	/**
	 * 预订单（扫码付）
	 * @param payInfo 订单信息
	 * @return
	 * @throws Exception
	 * @author lemons
	 */
	public Map<String,String> merchantOrder(MerchantPayInfoVo payInfo) throws Exception;
	
	/**
	 * 查询订单信息
	 * @param TradeQueryVo 订单信息
	 * @return
	 * @throws Exception
	 * @author lemons
	 */
	public Map<String,String> tradeQuery(TradeQueryVo queryVo) throws Exception;
	
	
	/**
	 * 查询订单信息
	 * @param TradeQueryVo 订单信息
	 * @return
	 * @throws Exception
	 * @author lemons
	 */
	public Map<String,String> drawQuery(DrawQueryVo queryVo) throws Exception;
	
	
	/**
	 * 提现
	 * @param payInfo 订单信息
	 * @return
	 * @throws Exception
	 * @author lemons
	 */
	public Map<String,String> merchantWithdraws(MerchantWithdrawsInfoVo withdrawsInfo) throws Exception;
	
	/**
	 * 微信公众号支付
	 * @param payInfo 订单信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> merchantWechatOfficial(MerchantPayInfoVo payInfo) throws Exception;
	
	/**
	 * 银行卡四要素
	 * @param bankCard
	 * @param type 3:三要素；4:四要素；
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> bankCardAuth(BankCardAuthVo bankCard, String type) throws Exception;
	
	/**
	 * 
	 * @author liushuyu
	 * Desc : 商户二要素校验 
	 * DATE: 2017年6月13日
	 * @param auth
	 * @return 
	 */
	public Map<String, String> bankCardAuth2(String name, String idCard)throws Exception;
	
	/**
	 * 商户结算账户信息变更
	 * @param updateCardVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> merchantUpdateCard(MerchantUpdateCardVo updateCardVo) throws Exception;
	
	/**
	 * 商户费率变更
	 * @param merchantNo 商户支付编号
	 * @param merchantRateVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> merchantUpdateRate(String merchantNo, MerchantRateVo merchantRateVo) throws Exception;
	
	/**
	 * 商户钱包查询
	 * @param merchantNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> merchantWallet(String merchantNo) throws Exception;
	
	/**
	 * 获取对账文件
	 * @param billDate 账单日期 yyyy-MM-dd 各式
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getSettleFile(String billDate) throws Exception;
	
	/**
	 * 推送注册成功消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pushRegistSuccess(RegistSuccessMessage message) throws Exception;
	
	/**
	 * 推送支付成功消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pushPaySuccess(PaySuccessMessage message) throws Exception;
	
	/**
	 * 推送提现成功消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pushWithdrawsSuccess(WithDrawsSuccessMessage message) throws Exception;
	
	/**
	 * 推送商户资料待完善消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pushUnperfect(UnperfectMessage message) throws Exception;
	
	/**
	 * @Title: getJsApiTicket
	 * @Description: 获取微信jsapiTicket
	 * @author: husain
	 * @throws Exception
	 * @return: String
	 */
	public String getJsApiTicket(String channelNo) throws Exception;
	
	/**
	 * @Title: getJsApiTicket
	 * @Description: 获取微信jsapiTicket
	 * @author: husain
	 * @throws Exception
	 * @return: String
	 */
	public String getAccessToken(String channelNo) throws Exception;

	/**
	 * 条形码支付
	 * @Title: merchantScanPay
	 * @Description: TODO
	 * @author: husain
	 * @param vo
	 * @return
	 * @return: Map<String,String>
	 */
	public Map<String, String> merchantScanPay(MerchantPayInfoVo vo) throws Exception;
	
	/**
	 * 退款
	 * @Title: merchantRefund
	 * @Description: TODO
	 * @author: husain
	 * @param vo
	 * @return
	 * @return: Map<String,String>
	 */
	public Map<String, String> merchantRefund(MerchantRefundVo vo) throws Exception;
}
