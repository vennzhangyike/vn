package com.hfepay.scancode.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.commons.base.lang.SnowflakeIdGenerator;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.common.PushMessageErrorCode;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.entity.PlatformSecretKey;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.message.RegistSuccessMessage;
import com.hfepay.scancode.api.entity.message.UnperfectMessage;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.api.entity.vo.BankCardAuthVo;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.entity.vo.DrawQueryVo;
import com.hfepay.scancode.api.entity.vo.HeaderMessage;
import com.hfepay.scancode.api.entity.vo.MerchantAccountsVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoChangeVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantPayInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.entity.vo.MerchantRefundVo;
import com.hfepay.scancode.api.entity.vo.MerchantUpdateCardVo;
import com.hfepay.scancode.api.entity.vo.MerchantWithdrawsInfoVo;
import com.hfepay.scancode.api.entity.vo.Message;
import com.hfepay.scancode.api.entity.vo.ParamsMessage;
import com.hfepay.scancode.api.entity.vo.TradeQueryVo;
import com.hfepay.scancode.api.exception.ValidatException;
import com.hfepay.scancode.api.httpClient.HttpProtocolHandler;
import com.hfepay.scancode.api.httpClient.HttpRequest;
import com.hfepay.scancode.api.httpClient.HttpResponse;
import com.hfepay.scancode.api.httpClient.HttpResultType;
import com.hfepay.scancode.api.httputil.Dates;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiLogService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.api.service.PlatformSecretKeyService;
import com.hfepay.scancode.api.service.commons.ScanCodeConstants;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.api.signature.EncrypterUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class MerchantBusinessServiceImpl implements MerchantBusinessService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PlatformSecretKeyService platformSecretKeyService;
	
	@Autowired
	SnowflakeIdGenerator idGenerator;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private ApiLogService apiLogService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	
	/**
	 * 商户入网
	 */
	@Override
	public Map<String, String> merchantJoin(MerchantInfoVo info, MerchantAccountsVo accounts, List<MerchantRateVo> payTypes) throws Exception{
		Map<String, String> respValue = null;
		try{
			accounts.setAccountType("0".equals(accounts.getAccountType())? ScanCodeConstants.N:ScanCodeConstants.Y );
			// 商户基本信息
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("info", info);
			logger.error("商户基本信息："+ JSON.toJSONString(info,filter));
			// 商户账号信息
			body.put("account", accounts);
			logger.error("商户账户信息："+JSON.toJSONString(accounts,filter));
			// 支付通道信息
			body.put("payType", payTypes);
			logger.error("商户支付通道信息："+JSON.toJSONString(payTypes,filter));
			Map<String, String> response = null;
			response = requestHandle(HfepayConfig.join_service_method, body,info.getMerchantNo());
			logger.error("响应报文（解密）, response="+JSON.toJSONString(response));
			//处理响应报文
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	/**
	 * 扫码付预订单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> merchantOrder(MerchantPayInfoVo payInfo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(payInfo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			Map<String,String> response = requestHandle(HfepayConfig.trade_order, body,payInfo.getMerchantNo());
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	/**
	 * 条码支付
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> merchantScanPay(MerchantPayInfoVo payInfo) throws Exception {
		Map<String,String> response= null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(payInfo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			response = requestHandle(HfepayConfig.trade_barcode, body,payInfo.getMerchantNo());
			//respValue = responseHandle(response);
		}catch(ValidatException e){
			response = Maps.newMap();
			response.put("respCode", "100010");
			response.put("respDesc", "密钥不存在");
		}
		return response;
	}
	
	/**
	 * 微信公众号支付
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> merchantWechatOfficial(MerchantPayInfoVo payInfo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(payInfo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			Map<String,String> response = requestHandle(HfepayConfig.trade_order_official, body,payInfo.getMerchantNo());
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	/**
	 * 退款
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> merchantRefund(MerchantRefundVo vo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(vo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			logger.info("-------------body is "+body+"....... vo is "+vo);
			Map<String,String> response = requestHandle(HfepayConfig.trade_refund, body,vo.getMerchantNo());
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	/**
	 * 银行卡要素校验
	 */
	@Override
	public Map<String, String> bankCardAuth(BankCardAuthVo bankCard,String type) throws Exception {
		logger.debug("============银行卡要素校验==============");
		String uri = HfepayConfig.bank_card_auth_uri_3;
		String appKey = HfepayConfig.appKey_three;
		//1、组装请求报文
		JSONObject headerJson = new JSONObject();
		JSONObject conditionJson = new JSONObject();
		headerJson.put("qryBatchNo", idGenerator.generate());  //验证批次号  用户生成的唯一编号
		headerJson.put("userCode", HfepayConfig.userCode);   //商户编号，即用户编号
		headerJson.put("sysCode", HfepayConfig.sysCode); //应用编号
		//必填
		conditionJson.put("realName", bankCard.getRealName());
		conditionJson.put("idCard", bankCard.getIdCard());
		conditionJson.put("bankCard", bankCard.getBankCard());
		if("4".equals(type)){
			conditionJson.put("mobile", bankCard.getMobile());
		}
		JSONObject allJson = new JSONObject();
		allJson.put("header", headerJson);
		allJson.put("condition", conditionJson);
		logger.debug(allJson.toString());
		String data = allJson.toString();
		logger.debug("请求报文："+data);
		
		ApiMappingDicionCondition apiDicCondition = new ApiMappingDicionCondition();
		apiDicCondition.setKeyNo(HfepayConfig.CHANNEL_REDIS_CHANNEL_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiDicCondition);
		
		ApiLogCondition condition = new ApiLogCondition();
		condition.setPayCode(HfepayConfig.BANK_AUTH_KEY);
		condition.setChannelNo(dic.getParaVal());
		condition.setRequestParams(data);
		/**
		 * 组装请求参数 调用实名认证接口
		 */
		//2、请求API对应的接口
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("condition", data));
		parameters.add(new BasicNameValuePair("userCode", HfepayConfig.userCode));
		if("4".equals(type)){
			uri = HfepayConfig.bank_card_auth_uri_4;
			appKey = HfepayConfig.appKey_four;
		}
		parameters.add(new BasicNameValuePair("appKey", appKey));
		condition.setCreateTime(new Date());
		String result = HttpRequestClient.invoke_http(uri,parameters,"UTF-8");
		condition.setResponseParams(result);
		condition.setUpdateTime(new Date());
		try{
			saveApiLog(condition);
		}catch(Exception e){
			logger.error("保存日志异常："+e);
		}
		logger.debug("返回报文："+result);
		logger.debug("============银行卡要素校验完成==============");
		JSONObject resValue = JSONObject.fromObject(result);
		Map<String, String> returnMap = Maps.newMap();
		if(resValue.get("msg")!=null){
			JSONObject msg = JSONObject.fromObject(resValue.get("msg"));
			returnMap.put("respCode", msg.getString("code"));
			returnMap.put("respDesc", msg.getString("codeDesc"));
		}else{
			JSONArray dataArray = JSONArray.fromObject(resValue.get("data"));
			JSONObject jsonObj = (JSONObject) dataArray.get(0);
			JSONArray recordArray = jsonObj.getJSONArray("record");
			JSONObject recordObj = (JSONObject)recordArray.get(0);
			String resCode = recordObj.getString("resCode");
			returnMap.put("respCode", resCode.equals("00")?"000000":resCode);
			returnMap.put("respDesc", recordObj.getString("resDesc"));
		}
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> merchantUpdateCard(MerchantUpdateCardVo updateCardVo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			updateCardVo.setAccountType("0".equals(updateCardVo.getAccountType())? ScanCodeConstants.N:ScanCodeConstants.Y );
			//将对象转为JSON字符串
			String json = JSON.toJSONString(updateCardVo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			Map<String,String> response = requestHandle(HfepayConfig.merchant_card_update_method, body,updateCardVo.getMerchantNo());
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> merchantUpdateRate(String merchantNo,
			MerchantRateVo merchantRateVo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(merchantRateVo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			body.put("merchantNo", merchantNo);
			Map<String,String> response = requestHandle(HfepayConfig.update_rate_service_method, body,merchantNo);
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	
	
	@Override
	public Map<String, String> merchantWallet(String merchantNo)
			throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("merchantNo", merchantNo);
			Map<String,String> response = requestHandle(HfepayConfig.merchant_wallet_query_method, body,merchantNo);
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}
	
	@Override
	public Map<String, String> getSettleFile(String billDate) throws Exception {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("billDate", billDate);
		Map<String,String> response = requestHandle(HfepayConfig.get_settle_file, body,"");
		Map<String,String> resValue = responseHandle(response);
		return resValue;
	}
	
	/**
	 * 推送注册成功消息
	 * @throws Exception
	 */
	@Override
	public Map<String, String> pushRegistSuccess(RegistSuccessMessage message) throws Exception{
		String access_token = getAccessToken(message.getOrganNo());
		ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
		condition.setKeyNo(HfepayConfig.WPUSH_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
		if(dic==null||Strings.isEmpty(dic.getParaVal())){
			throw new RuntimeException("获取消息推送url失败");
		}
		String uri= dic.getParaVal().replace("ACCESS_TOKEN", access_token);
		logger.info("=================pushRegistSuccess url is "+uri);
		
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo(message.getOrganNo());
		ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
		if(param==null||Strings.isEmpty(param.getWxParams())){
			throw new RuntimeException("获取消息推送模板失败");
		}
		
		JSONObject json = JSONObject.fromObject(param.getWxParams());
		String template_id = json.getString("registerTemplateId");
		logger.info("push message register get template_id is "+template_id);
		//String uri ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;//u
		Map<String, Object> request = Maps.newMap();
		request.put("touser", message.getTouser());
		request.put("template_id", template_id);//u
		request.put("url", message.getUrl());
		Map<String, Object> data = Maps.newMap();
		Map<String, String> first = Maps.newMap();
		first.put("value", message.getTitle());
		first.put("color", "#173177");
		data.put("first", first);
		Map<String, String> keyword1 = Maps.newMap(); 
		keyword1.put("value", message.getCompanyType());
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		Map<String, String> keyword2 = Maps.newMap();
		keyword2.put("value", message.getCompanyName());
		keyword2.put("color", "#173177");
		data.put("keyword2", keyword2);
		Map<String, String> keyword3 = Maps.newMap();
		keyword3.put("value", message.getName());
		keyword3.put("color", "#173177");
		data.put("keyword3", keyword3);
		Map<String, String> keyword4 = Maps.newMap();
		keyword4.put("value", message.getAddress());
		keyword4.put("color", "#173177");
		data.put("keyword4", keyword4);
		Map<String, String> keyword5 = Maps.newMap();
		keyword5.put("value", Dates.format("yyyy年MM月dd日 HH:mm:ss", message.getRegistTime()));
		keyword5.put("color", "#173177");
		data.put("keyword5", keyword5);
		Map<String, String> remark = Maps.newMap();
		remark.put("value", message.getRemark());
		remark.put("color", "#173177");
		data.put("remark", remark);
		request.put("data", data);
		Map<String, Object> map = this.registMessageData(message, template_id);
		logger.info("推送消息内容："+JSON.toJSON(map));
		String resultJSON = buildRequestJsonPost(uri,JSON.toJSONString(map));
		logger.info("微信返回结果,"+resultJSON);
		JSONObject jsonObj = JSONObject.fromObject(resultJSON);
		if(json!=null&&"40001".equals(jsonObj.getString("errcode"))){//tocken到期了，重新拿一次
			//重新触发消息发送
			return repush(message.getOrganNo(), dic.getParaVal(), map);
		}
		return pushMessageResponseHandle(jsonObj);
	}
	
	/**
	 *推送注册成功消息模版数据
	 */
	private Map<String, Object> registMessageData(RegistSuccessMessage message ,String template_id){
		Map<String, Object> request = Maps.newMap();
		request.put("touser", message.getTouser());
		request.put("template_id", template_id);//u
		request.put("url", message.getUrl());
		Map<String, Object> data = Maps.newMap();
		Map<String, String> first = Maps.newMap();
		first.put("value", message.getTitle());
		first.put("color", "#173177");
		data.put("first", first);
		Map<String, String> keyword1 = Maps.newMap(); 
		keyword1.put("value", message.getName());
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		Map<String, String> keyword2 = Maps.newMap();
		keyword2.put("value", message.getMobile());
		keyword2.put("color", "#173177");
		data.put("keyword2", keyword2);
		Map<String, String> remark = Maps.newMap();
		remark.put("value", message.getRemark());
		remark.put("color", "#173177");
		data.put("remark", remark);
		request.put("data", data);
		return request;
	}
	
	/**
	 * 推送支付成功消息
	 */
	@Override
	public Map<String, String> pushPaySuccess(PaySuccessMessage message) throws Exception{
		/*String access_token = getAccessToken();
		String uri ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;//u
*/		
		String access_token = getAccessToken(message.getOrganNo());
		ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
		condition.setKeyNo(HfepayConfig.WPUSH_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
		if(dic==null||Strings.isEmpty(dic.getParaVal())){
			throw new RuntimeException("获取消息推送url失败");
		}
		String uri= dic.getParaVal().replace("ACCESS_TOKEN", access_token);
		logger.info("=================pushPaySuccess url is "+uri);
		
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo(message.getOrganNo());
		ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
		if(param==null||Strings.isEmpty(param.getWxParams())){
			throw new RuntimeException("获取消息推送模板失败");
		}
		
		JSONObject json = JSONObject.fromObject(param.getWxParams());
		String template_id = json.getString("payTemplateId");
		logger.info("=================pushPaySuccess url is "+uri);
		
//		Map<String, Object> request = Maps.newMap();
//		request.put("touser", message.getTouser());
//		request.put("template_id", template_id);//u
//		request.put("url", message.getUrl());
//		Map<String, Object> data = Maps.newMap();
//		Map<String, String> first = Maps.newMap();
//		first.put("value", message.getTitle());
//		first.put("color", "#173177");
//		data.put("first", first);
//		Map<String, String> keyword1 = Maps.newMap(); 
//		keyword1.put("value", message.getOrderName());
//		keyword1.put("color", "#173177");
//		data.put("keyword1", keyword1);
//		Map<String, String> keyword2 = Maps.newMap();
//		keyword2.put("value", message.getOrderNo());
//		keyword2.put("color", "#173177");
//		data.put("keyword2", keyword2);
//		Map<String, String> keyword3 = Maps.newMap();
//		keyword3.put("value", message.getOrderAmt());
//		keyword3.put("color", "#173177");
//		data.put("keyword3", keyword3);
//		Map<String, String> remark = Maps.newMap();
//		remark.put("value", message.getRemark());
//		remark.put("color", "#173177");
//		data.put("remark", remark);
//		request.put("data", data);
		Map<String, Object> map = this.payMessageData(message, template_id);
		logger.debug("推送消息内容："+JSON.toJSON(map));
		String resultJSON = buildRequestJsonPost(uri,JSON.toJSONString(map));
		JSONObject jsonObj = JSONObject.fromObject(resultJSON);
		logger.info("微信返回结果,"+resultJSON);
		if(json!=null&&"40001".equals(jsonObj.getString("errcode"))){//tocken到期了，重新拿一次
			//重新触发消息发送
			return repush(message.getOrganNo(), dic.getParaVal(), map);
		}
		return pushMessageResponseHandle(jsonObj);
	}
	
	/**
	 * 推送支付成功消息模版数据
	 */
	private Map<String, Object> payMessageData(PaySuccessMessage message ,String template_id){
		Map<String, Object> request = Maps.newMap();
		request.put("touser", message.getTouser());
		request.put("template_id", template_id);//u
		request.put("url", message.getUrl());
		Map<String, Object> data = Maps.newMap();
		Map<String, String> first = Maps.newMap();
		first.put("value", message.getTitle());
		first.put("color", "#173177");
		data.put("first", first);
		
		Map<String, String> keyword1 = Maps.newMap(); 
		keyword1.put("value", message.getOrderAmt());
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		
		Map<String, String> keyword2 = Maps.newMap();
		keyword2.put("value", message.getOrderName());
		keyword2.put("color", "#173177");
		data.put("keyword2", keyword2);
		
		Map<String, String> keyword3 = Maps.newMap();
		keyword3.put("value", message.getPayCode());
		keyword3.put("color", "#173177");
		data.put("keyword3", keyword3);
		
		Map<String, String> keyword4 = Maps.newMap();
		keyword4.put("value", message.getOrderNo());
		keyword4.put("color", "#173177");
		data.put("keyword4", keyword4);
		
		Map<String, String> keyword5 = Maps.newMap();
		keyword5.put("value", Dates.format("yyyy年MM月dd日 HH:mm:ss", message.getBeginTime()));
		keyword5.put("color", "#173177");
		data.put("keyword5", keyword5);
		
		Map<String, String> remark = Maps.newMap();
		remark.put("value", message.getRemark());
		remark.put("color", "#173177");
		data.put("remark", remark);
		
		request.put("data", data);
		return request;
	}
	
	/**
	 * 推送商户资料待完善消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> pushUnperfect(UnperfectMessage message) throws Exception{
		
		String access_token = getAccessToken(message.getOrganNo());
		ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
		condition.setKeyNo(HfepayConfig.WPUSH_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
		if(dic==null||Strings.isEmpty(dic.getParaVal())){
			throw new RuntimeException("获取消息推送url失败");
		}
		String uri= dic.getParaVal().replace("ACCESS_TOKEN", access_token);
		logger.info("=================pushPaySuccess url is "+uri);
		
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo(message.getOrganNo());
		ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
		if(param==null||Strings.isEmpty(param.getWxParams())){
			throw new RuntimeException("获取消息推送模板失败");
		}
		
		JSONObject json = JSONObject.fromObject(param.getWxParams());
		String template_id = json.getString("unperfectTemplateId");
		logger.info("=================pushPaySuccess url is "+uri);
		
		Map<String, Object> map = this.nperfectMessageData(message, template_id);
		logger.debug("推送消息内容："+JSON.toJSON(map));
		String resultJSON = buildRequestJsonPost(uri,JSON.toJSONString(map));
		JSONObject jsonObj = JSONObject.fromObject(resultJSON);
		logger.info("微信返回结果,"+resultJSON);
		if(json!=null&&"40001".equals(jsonObj.getString("errcode"))){//tocken到期了，重新拿一次
			//重新触发消息发送
			return repush(message.getOrganNo(), dic.getParaVal(), map);
		}
		return pushMessageResponseHandle(jsonObj);
	}
	
	/**
	 * 推送支付成功消息模版数据
	 */
	private Map<String, Object> nperfectMessageData(UnperfectMessage message ,String template_id){
		Map<String, Object> request = Maps.newMap();
		request.put("touser", message.getTouser());
		request.put("template_id", template_id);//u
		request.put("url", message.getUrl());
		Map<String, Object> data = Maps.newMap();
		Map<String, String> first = Maps.newMap();
		first.put("value", message.getTitle());
		first.put("color", "#173177");
		data.put("first", first);
		
		Map<String, String> keyword1 = Maps.newMap(); 
		keyword1.put("value", message.getContent());
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		
		Map<String, String> keyword2 = Maps.newMap();
		keyword2.put("value", Dates.format("yyyy年MM月dd日 HH:mm:ss", message.getRegistTime()));
		keyword2.put("color", "#173177");
		data.put("keyword2", keyword2);
		
		Map<String, String> remark = Maps.newMap();
		remark.put("value", message.getRemark());
		remark.put("color", "#173177");
		data.put("remark", remark);
		
		request.put("data", data);
		return request;
	}
	
	/**
	 * 获取密钥
	 * @param payCode
	 * @return
	 */
	private PlatformSecretKey getSecretKey(String payCode) throws ValidatException, Exception{
		PlatformSecretKey platformSecretKey = platformSecretKeyService.findByPayCode(payCode);
		if(platformSecretKey == null){
			throw new ValidatException("100010","密钥不存在");
		}
		return platformSecretKey;
	}
	
	/**
	 * http请求处理方法
	 * @param method 服务名称
	 * @param body 报文体
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,String> requestHandle(String method,Map<String, Object> body,String merchantNo) throws ValidatException, Exception {
		ApiMappingDicionCondition apiDicCondition = new ApiMappingDicionCondition();
		apiDicCondition.setKeyNo(HfepayConfig.CHANNEL_REDIS_CHANNEL_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiDicCondition);
		
		//日志
		ApiLogCondition condition = new ApiLogCondition();
		condition.setPayCode(method);
		condition.setRequestParams(JSON.toJSONString(body));
		condition.setChannelNo(dic.getParaVal());
		condition.setMerchantNo(merchantNo);
		condition.setCreateTime(new Date());
		//获取密钥
		PlatformSecretKey secretKey = getSecretKey(method);
		Map<String, Object> message = Maps.newMap();
		logger.debug("请求body(未加密)："+JSON.toJSONString(body));
		message.put("body", body);
		//加密报文体
		Message data = EncrypterUtil.encode(JSON.toJSONString(message),secretKey.getJoinPublicKey(), secretKey.getJoinPrivateKey());
		//添加头部信息
		HeaderMessage head = new HeaderMessage();
		head.setVersion("V1.0.0");
		head.setReqTime(Dates.format("yyyyMMddHHmmss", new Date()));
		String userReqNo = Dates.format("yyyyMMddHHmmss", new Date());
		userReqNo = userReqNo+String.valueOf((int)((Math.random()*9+1)*100000));
		
		if (null != body.get("userOrderNo")) {
			condition.setTradeNo(body.get("userOrderNo").toString());
		}else {			
			condition.setTradeNo(userReqNo);
		}
		head.setUserReqNo(userReqNo);
		
		head.setChannelNo(dic.getParaVal());//渠道编号
		head.setMethod(method);
		head.setSign(data.getSign());
		head.setEncryptKey(data.getKey());
		//设置参数消息对象
		ParamsMessage params = new ParamsMessage();
		//设置渠道编号
		params.setHead(head);
		params.setBody(data.getContent());
		message.put("params", params);
		String str = JSON.toJSONString(params);
		JSONObject obj = JSONObject.fromObject(str);
		logger.debug("请求报文,request = "+obj.toString());
		//post请求服务器
		String resJson = buildRequestJsonPost(HfepayConfig.gateway_uri,obj.toString());
		logger.debug("响应报文（加密）,response = "+resJson);
		Map<String, String> response = JSON.parseObject(resJson, Map.class);
		if(response.get("body")!=null) {
			HeaderMessage header = JSON.parseObject(JSON.toJSONString(response.get("head")), HeaderMessage.class);
			String content = response.get("body");
			String sign = header.getSign();
			String encryptKey = header.getEncryptKey();
			if (content != null && sign != null && encryptKey != null) {
				//解密报文
				response.put("body", EncrypterUtil.decode(content, sign, encryptKey,secretKey.getJoinPublicKey(), secretKey.getJoinPrivateKey()));
				logger.debug("响应报文（解密）,response = "+JSON.toJSONString(response));
			}
		}else {
			response.put("body","");
		}
		condition.setResponseParams(JSON.toJSONString(response));
		condition.setUpdateTime(new Date());
		try{
			saveApiLog(condition);
		}catch(Exception e){
			logger.error("保存日志异常："+e);
		}
		return response;
	}
	
	/**
	 * POST 请求
	 * @param URL
	 * @param objectString
	 * @return
	 * @throws Exception
	 */
	private String buildRequestJsonPost(String URL,String objectString) throws Exception {
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setMethod("POST");
        request.setCharset(HfepayConfig.input_charset);
        request.setUrl(URL);
        HttpResponse response = httpProtocolHandler.execute(request,objectString,"","");
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }
	
	
	/**
	 * 响应报文处理
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> responseHandle(Map<String, String> response){
		Map<String, String> respValue = null;
		Map<String, String> headMap = JSON.parseObject(JSON.toJSONString(response.get("head")), Map.class);
		String respCode = headMap.get("respCode");
		if("000000".equals(respCode)){
			//如果返回码为交易成功
			respValue = JSON.parseObject(response.get("body"), Map.class);
			respValue.put("respCode", respCode);
			return respValue;
		}else{
			respValue = Maps.newMap();
			respValue.put("respCode", respCode);
			respValue.put("respDesc", headMap.get("respDesc"));
			return respValue;
		}
	}
	
	/**
	 * 响应报文处理（微信消息推送）
	 * @param response
	 * @return
	 */
	private Map<String, String> pushMessageResponseHandle(JSONObject jsonObj){
		Map<String, String> respValue = Maps.newMap();
		String respCode = jsonObj.getString("errcode");
		try{
			if("-1".equals(respCode)){
				respCode = "1";
			}
			respCode = "PUSH_CODE_"+respCode;
			respValue.put("respCode",PushMessageErrorCode.valueOf(respCode).getCode());
			respValue.put("respDesc",PushMessageErrorCode.valueOf(respCode).getDesc() );
		}catch(Exception e){//错误码不存在
			respCode = "PUSH_CODE_1";
			respValue.put("respCode",PushMessageErrorCode.valueOf(respCode).getCode());
			respValue.put("respDesc",PushMessageErrorCode.valueOf(respCode).getDesc() );
		}
		return respValue;
	}
	
	/**
	 * 获取access_token,由于有效期只有两个小时，同时一些API的tocken依赖于该tocken，因此在此处将所有的需要借助于
	 * access_token的其他APItocken也一并获取，保持数据和tocken一致
	 * @return
	 */
	public String getAccessToken(String organNo) throws Exception{
		String access_token = "";
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(organNo);
		if(vo==null){
			throw new Exception("根据当前节点获取父节点配置有公众号信息错误");
		}
		organNo=vo.getOrganNo();//获取上级配置有公众号的编号
		String redisKeyStr = HfepayConfig.ACCESS_TOKEN+organNo;  
		try{
			RedisKey redisKey = new RedisKey(redisKeyStr, redisKeyStr);
			access_token = redisSharedCache.get(redisKey);
			if(Strings.isNotBlank(access_token)){
				logger.info("从缓存中获取access_token成功,access_token:"+access_token);
				return access_token;
			}
			//1、先从缓存中获取,如果缓存中没有，则通过请求获取access_token //u
			//String access_token_uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+HfepayConfig.appid+"&secret="+HfepayConfig.secret;
			ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
			condition.setKeyNo(HfepayConfig.WTOKEN_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
			if(dic==null||Strings.isEmpty(dic.getParaVal())){
				throw new RuntimeException("获取 access_token url失败");
			}
			//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APP_ID&secret=SECRET
			String uri= dic.getParaVal();
			logger.info("=================getAccessToken url is "+uri);
			
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			if(param==null||Strings.isEmpty(param.getWxParams())){
				throw new RuntimeException("获取微信参数失败");
			}
			JSONObject json = JSONObject.fromObject(param.getWxParams());
			String appid = json.getString("appid");
			String secret = json.getString("secret");
			logger.info("=================getAccessToken appid is "+appid+" secret is "+secret);
			
			String access_token_uri = uri.replace("APP_ID", appid).replace("SECRET", secret);
			
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("appid", appid);
			sParaTemp.put("secret", secret);
			String resultJson = HttpRequestClient.invoke_http(access_token_uri, generatNameValuePair(sParaTemp), "utf-8");
			Map<String, String> result = JSON.parseObject(resultJson, Map.class);
			//将access_token保存至缓存中，有效时间7200秒（两小时）
			access_token = result.get("access_token");
			if(Strings.isEmpty(access_token)){
				throw new RuntimeException("access_token is null......");
			}
			
			
			//根据tocken获取jsapi并放入redis
			String tick = "";
			String redisKeyTicketStr = HfepayConfig.JSAPI_TICKET+organNo;
			RedisKey redisKeyTicket = new RedisKey(redisKeyTicketStr, redisKeyTicketStr);
			
			condition.setKeyNo(HfepayConfig.WAPITICKET_KEY);
			dic = apiMappingDicionService.getFromRedis(condition);
			if(dic==null||Strings.isEmpty(dic.getParaVal())){
				throw new RuntimeException("getJsApiTicket 获取 access_token url失败");
			}
			String tickUrl= dic.getParaVal();
			logger.info("=================getJsApiTicket url is "+tickUrl);
			tickUrl = tickUrl.replace("ACCESS_TOKEN", access_token);
			String tickJson = new String(HttpRequestClient.doGet(tickUrl), "utf-8");
			JSONObject jsTick = JSONObject.fromObject(tickJson);
			if(jsTick!=null){
				String errorCode = jsTick.getString("errcode");
				logger.info("get jsapi_ticket request errorCode is "+errorCode);
				if(errorCode!=null&&errorCode.equals("0")){
					tick = jsTick.getString("ticket");
				}
				logger.info("重新获取jsapi_ticket成功,jsapi_ticket:"+tick);
			}
			else{
				throw new RuntimeException("获取ticket失败");
			}
			//将jsapiticket保存至缓存中
			redisSharedCache.set(redisKeyTicket, tick,HfepayConfig.ACCESS_TOKEN_TIME);
			//将access_token保存至缓存中
			redisSharedCache.set(redisKey, access_token,HfepayConfig.ACCESS_TOKEN_TIME);
		}catch(Exception e){
			logger.error(e.toString());
			logger.error("获取access_token异常",e);
			throw new Exception();
		}
		logger.info("获取access_token成功:"+access_token);
		return access_token;
	}
	
	/**
	 * 获取jsapi_ticket
	 * @return
	 */
	public String getJsApiTicket(String organNo) throws Exception{
		getAccessToken(organNo);//tocken和jsapi的生命周期一致
		String tick = "";
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(organNo);
		if(vo==null){
			throw new Exception("根据当前节点获取父节点配置有公众号信息错误");
		}
		organNo=vo.getOrganNo();//获取上级配置有公众号的编号
		String redisKey = HfepayConfig.JSAPI_TICKET+organNo;
		RedisKey redisKeyTicket = new RedisKey(redisKey, redisKey);
		tick = redisSharedCache.get(redisKeyTicket);
		logger.info("从缓存中获取jsapi_ticket:"+tick);
		if(Strings.isNotBlank(tick)){
			logger.info("从缓存中获取jsapi_ticket成功,jsapi_ticket:"+tick);
			return tick;
		}else{
			throw new RuntimeException("获取jsapi失败");
		}
	}

	 private static List<NameValuePair> generatNameValuePair(Map<String, String> properties) {
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        for (Map.Entry<String, String> entry : properties.entrySet()) {
	        	NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(),entry.getValue());
	        	list.add(nameValuePair);
	        }
	        return list;
	    }
	 
	 @Async
	 public void saveApiLog(ApiLogCondition condition){
		 condition.setId(Strings.getUUID());
		 apiLogService.insert(condition);
	 }
	 
	 private static ValueFilter filter = new ValueFilter() {
	        @Override
	        public Object process(Object obj, String s, Object v) {
	            if (v == null)
	                return "";
	            return v;
	        }
	    };

    /**
     * 商户提现功能
     */
	@Override
	public Map<String, String> merchantWithdraws(MerchantWithdrawsInfoVo withdrawsInfo) throws Exception {
		Map<String, String> respValue = null;
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(withdrawsInfo,filter);
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			Map<String,String> response = requestHandle(HfepayConfig.get_withdraws, body,withdrawsInfo.getMerchantNo());
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}

	@Override
	public Map<String, String> pushWithdrawsSuccess(WithDrawsSuccessMessage message) throws Exception {
		String access_token = getAccessToken(message.getOrganNo());
		ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
		condition.setKeyNo(HfepayConfig.WPUSH_KEY);
		ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
		if(dic==null||Strings.isEmpty(dic.getParaVal())){
			throw new RuntimeException("获取消息推送url失败");
		}
		String uri= dic.getParaVal().replace("ACCESS_TOKEN", access_token);
		logger.info("=================pushWithdrawsSuccess url is "+uri);
		
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo(message.getOrganNo());
		ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
		if(param==null||Strings.isEmpty(param.getWxParams())){
			throw new RuntimeException("获取消息推送模板失败");
		}
		
		JSONObject json = JSONObject.fromObject(param.getWxParams());
		String templateId = json.getString("withDrawsTemplateId");
		logger.info("=================pushWithdrawsSuccess url is "+uri);
		
		Map<String, Object> map = this.withDrawsMessageData(message, templateId);
		
		logger.debug("推送消息内容："+JSON.toJSON(map));
		String resultJSON = buildRequestJsonPost(uri,JSON.toJSONString(map));
		JSONObject jsonObj = JSONObject.fromObject(resultJSON);
		logger.info("微信返回结果,"+resultJSON);
		if(json!=null&&"40001".equals(jsonObj.getString("errcode"))){//tocken到期了，重新拿一次
			//重新触发消息发送
			return repush(message.getOrganNo(), dic.getParaVal(), map);
		}
		return pushMessageResponseHandle(jsonObj);
	}
	
	//tocken过期重新触发消息推送
	private Map<String, String> repush(String organNo,String dicUrl,Map<String, Object> map) throws Exception{
		/*DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(organNo);
		String redisKeyStr = HfepayConfig.ACCESS_TOKEN+vo.getOrganNo();  
		RedisKey redisKey = new RedisKey(redisKeyStr, redisKeyStr);
		redisSharedCache.del(redisKey);//删除存放的tocken
*/		
		String access_token = getAccessToken(organNo);
		String uri= dicUrl.replace("ACCESS_TOKEN", access_token);
		logger.info("=================repush url is "+uri);
		String resultJSON = buildRequestJsonPost(uri,JSON.toJSONString(map));
		JSONObject jsonObj = JSONObject.fromObject(resultJSON);
		logger.info("repush 微信返回结果,"+resultJSON);
		return pushMessageResponseHandle(jsonObj);
	}
	
	/**
	 * 推送支付成功消息模版数据
	 */
	private Map<String, Object> withDrawsMessageData(WithDrawsSuccessMessage message ,String templateId){
		Map<String, Object> request = Maps.newMap();
		request.put("touser", message.getTouser());
		request.put("template_id", templateId);//u
		request.put("url", message.getUrl());
		Map<String, Object> data = Maps.newMap();
		Map<String, String> first = Maps.newMap();
		first.put("value", message.getTitle());
		first.put("color", "#173177");
		data.put("first", first);
		
		Map<String, String> keyword1 = Maps.newMap(); 
		keyword1.put("value", message.getMerchantName());
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		
		Map<String, String> keyword2 = Maps.newMap();
		keyword2.put("value", message.getOrderAmt());
		keyword2.put("color", "#173177");
		data.put("keyword2", keyword2);
		
		Map<String, String> keyword3 = Maps.newMap();
		keyword3.put("value", message.getPayCode());
		keyword3.put("color", "#173177");
		data.put("keyword3", keyword3);
		
		Map<String, String> keyword4 = Maps.newMap();
		keyword4.put("value", Dates.format("yyyy年MM月dd日 HH:mm:ss", message.getBeginTime()));
		keyword4.put("color", "#173177");
		data.put("keyword4", keyword4);
		
		Map<String, String> remark = Maps.newMap();
		remark.put("value", message.getRemark());
		remark.put("color", "#173177");
		data.put("remark", remark);
		
		request.put("data", data);
		return request;
	}

	@Override
	public Map<String, String> merchantJoinChange(MerchantInfoChangeVo info) throws Exception {
		Map<String, String> respValue = null;
		try{
			
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(info,filter);
			
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			logger.error("商户基本信息："+ JSON.toJSONString(body,filter));
			
			Map<String, String> response = null;
			response = requestHandle(HfepayConfig.get_join_change, body,info.getMerchantNo());
			logger.error("响应报文（解密）, response="+JSON.toJSONString(response));
			//处理响应报文
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}

	@Override
	public Map<String, String> tradeQuery(TradeQueryVo queryVo) throws Exception {
		Map<String, String> respValue = null;
		try{
			
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(queryVo,filter);
			
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			logger.error("商户基本信息："+ JSON.toJSONString(body,filter));
			
			Map<String, String> response = null;
			response = requestHandle(HfepayConfig.trade_query, body,queryVo.getMerchantNo());
			logger.error("响应报文（解密）, response="+JSON.toJSONString(response));
			//处理响应报文
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}

	@Override
	public Map<String, String> drawQuery(DrawQueryVo queryVo) throws Exception {
		Map<String, String> respValue = null;
		try{
			
			Map<String, Object> body = new HashMap<String, Object>();
			//将对象转为JSON字符串
			String json = JSON.toJSONString(queryVo,filter);
			
			//将json字符串转为map
			body = JSON.parseObject(json, Map.class);
			logger.error("商户基本信息："+ JSON.toJSONString(body,filter));
			
			Map<String, String> response = null;
			response = requestHandle(HfepayConfig.draw_query, body,queryVo.getMerchantNo());
			logger.error("响应报文（解密）, response="+JSON.toJSONString(response));
			//处理响应报文
			respValue = responseHandle(response);
		}catch(ValidatException e){
			respValue = Maps.newMap();
			respValue.put("respCode", "100010");
			respValue.put("respDesc", "密钥不存在");
		}
		return respValue;
	}

	@Override
	public Map<String, String> bankCardAuth2(String name, String idCard) throws Exception {
		logger.debug("============商户二要素校验==============");
		//1.商户编号，即用户编号,唯一编号
		String userCode=HfepayConfig.userCode;
		
		//2.应用编号,唯一编号
		String sysCode=HfepayConfig.sysCode;
		
//		//请求原因
//		String qryReason="信贷";
		
		//3.平台提供的加密秘钥appkey
		String appkey = HfepayConfig.appKey_two;
		
		//4.请求接口URL.
		
		String url=HfepayConfig.bank_card_auth_uri_2;//身份证二要素实名认证
	  
		
		//获取日期
		Date date = new Date();
		SimpleDateFormat datasf=new SimpleDateFormat("yyyyMMdd");
		String qryDate = datasf.format(date);  
		
		//获取时间
		SimpleDateFormat timesf=new SimpleDateFormat("HHmmss");
		String qryTime = timesf.format(date);
		
		//生成商户批次号：唯一，不超过20位（14位时间戳 + 6位随机数）
//	    String qryBatchNo;  
//	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
//	    String temp = sf.format(date);  
//	    int random=(int) (Math.random()*10000);  
//	    qryBatchNo=temp+random;
//	    System.out.println("商户批次号："+qryBatchNo);
	    
	    /** 生成请求报文**/
		//组装请求报文
		JSONObject headerJson = new JSONObject();
		JSONObject conditionJson = new JSONObject();
		headerJson.put("qryBatchNo", idGenerator.generate());//验证批次号  用户生成的唯一编号
//		headerJson.put("qryBatchNo", qryBatchNo);//验证批次号  用户生成的唯一编号
		headerJson.put("userCode", userCode);//商户编号，即用户编号
		headerJson.put("sysCode", sysCode);//应用编号
//		headerJson.put("qryReason", qryReason);//原因
		headerJson.put("qryDate", qryDate);//格式：yyyyMMdd
		headerJson.put("qryTime", qryTime);//格式：hhmmss
		
		conditionJson.put("realName", name);//姓名
		conditionJson.put("idCard", idCard);//身份证号码
		
		JSONObject allJson = new JSONObject();
		allJson.put("header", headerJson);
		allJson.put("condition", conditionJson);
		String data = allJson.toString();
		logger.info("###############请求报文：" + data);	

		/** 组装请求参数 调用实名认证接口**/
		//请求API对应的接口
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("condition", data));
		parameters.add(new BasicNameValuePair("userCode", userCode));
		parameters.add(new BasicNameValuePair("appKey", appkey));

		String result = HttpRequestClient.invoke_http(url, parameters, "UTF-8");

		logger.info("############返回报文： " + result);
		
		//报文解析
		JSONObject jsonObject = JSONObject.fromObject(result);
		Map<String,String> resultMap = new HashMap<>();
		if(jsonObject.has("data")){//正确报文
			JSONArray dataArray = jsonObject.getJSONArray("data");
			JSONObject dataObject = (JSONObject) dataArray.get(0);
			JSONArray recordArray = dataObject.getJSONArray("record");
			JSONObject recordObject = (JSONObject) recordArray.get(0);
			logger.info("######返回码：" + recordObject.getString("resCode"));//输出返回码
			logger.info("######返回码描述："+recordObject.getString("resDesc"));//输出返回码描述
			resultMap.put("resCode", String.valueOf(recordObject.getString("resCode")));
			resultMap.put("resDesc", recordObject.getString("resDesc"));
		}else{
			//错误报文
			JSONObject msgObject = jsonObject.getJSONObject("msg");
			logger.info("######返回码：" +msgObject.getString("code"));//输出返回码
			logger.info("######返回码描述："+msgObject.getString("codeDesc"));//输出返回码描述
			resultMap.put("resCode", "999999");
			resultMap.put("resDesc", "系统异常，请联系开发人员！");
		}
		return resultMap;
	}
}
