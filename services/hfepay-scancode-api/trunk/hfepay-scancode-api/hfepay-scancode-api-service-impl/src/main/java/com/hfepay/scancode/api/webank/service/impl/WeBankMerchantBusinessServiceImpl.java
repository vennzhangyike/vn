package com.hfepay.scancode.api.webank.service.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.api.webank.commons.ConfigConstants;
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
import com.hfepay.scancode.api.webank.httputil.HttpClientUtils;
import com.hfepay.scancode.api.webank.service.WeBankMerchantBusinessService;
import com.hfepay.scancode.api.webank.sign.Signature;
import com.hfepay.scancode.api.webank.xmlutil.XmlUtils;

import net.sf.json.JSONObject;

@Service
public class WeBankMerchantBusinessServiceImpl implements WeBankMerchantBusinessService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private ConfigConstants configConstants;
	
	/**
	 * 获取access_token,由于有效期只有两个小时，同时一些API的tocken依赖于该tocken，因此在此处将所有的需要借助于
	 * access_token的其他APItocken也一并获取，保持数据和tocken一致
	 * @return
	 */
	public String getWeBankAccessToken(){
		String access_token = "";
		String redisKeyStr = HfepayConfig.ALIPAY_ACCESS_TOKEN;  
		try{
			RedisKey redisKey = new RedisKey(redisKeyStr, redisKeyStr);
			access_token = redisSharedCache.get(redisKey);
			if(Strings.isNotBlank(access_token)){
				logger.info("从缓存中获取access_token成功,access_token:"+access_token);
				return access_token;
			}else{
				String url = configConstants.getAccess_token_url();
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("app_id", configConstants.getApp_id()));
				parameters.add(new BasicNameValuePair("secret", configConstants.getSecret()));
				parameters.add(new BasicNameValuePair("grant_type", configConstants.getGrant_type()));
				parameters.add(new BasicNameValuePair("version", configConstants.getVersion()));

				HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
				String response = httpClientUtils.doGet(url,parameters);
				Map<String, String> result = JSON.parseObject(response, Map.class);
				//将access_token保存至缓存中，有效时间7200秒（两小时）
				access_token = result.get("access_token");
				if(Strings.isEmpty(access_token)){
					throw new RuntimeException("access_token is null......");
				}
				//将access_token保存至缓存中
				redisSharedCache.set(redisKey, access_token,6600);
			}
		}catch(Exception e){
			logger.error(e.toString());
			throw new RuntimeException("获取 access_token失败");
		}
		logger.info("获取access_token成功:"+access_token);
		return access_token;
	}
	
	/**
	 * 获取ticket
	 * @return
	 */
	public String getWeBankTicket(){
		String access_token = "";
		try {
			access_token = getWeBankAccessToken();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//根据tocken获取ticket  需要放入redis
		String ticket = "";
		String redisKeyStr = HfepayConfig.ALIPAY_TICKET;  
		try {
			RedisKey redisKey = new RedisKey(redisKeyStr, redisKeyStr);
			ticket = redisSharedCache.get(redisKey);
			if(Strings.isNotBlank(ticket)){
				logger.info("从缓存中获取ticket成功,ticket:"+ticket);
				return ticket;
			}else{
				String ticketUrl = configConstants.getTicket_url();
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("app_id", configConstants.getApp_id()));
				parameters.add(new BasicNameValuePair("access_token", access_token));
				parameters.add(new BasicNameValuePair("type", "SIGN"));
				parameters.add(new BasicNameValuePair("version", configConstants.getVersion()));

				HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
				String ticketResponse = httpClientUtils.doGet(ticketUrl,parameters);
				JSONObject ticketJson = JSONObject.fromObject(ticketResponse);
				List<JSONObject> ticketList = (List<JSONObject>) ticketJson.get("tickets");
				JSONObject json = (JSONObject) ticketList.get(0);
				ticket = (String) json.get("value");
				if(Strings.isEmpty(ticket)){
					throw new RuntimeException("ticket is null......");
				}
				//将access_token保存至缓存中
				redisSharedCache.set(redisKey, ticket,3000);
				logger.info("获取ticket成功,ticket:"+ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			throw new RuntimeException("获取 ticket失败");
		}
		return ticket;
	}

	private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null || "".equals(v))
                return null;
            return v;
        }
    };
	/**
	 * 商户入网
	 */
	@Override
	public Map<String, String> merchantJoin(WeBankMerchantInfoBaseVo baseVo,WeBankMerchantInfoVo infoVo,WeBankMerchantAccountsVo accountsVo,List<WeBankMerchantRateVo> rateList) {
		JSONObject json = new JSONObject();
		//商户基本信息
		json.putAll(baseVo.toMap());
		logger.info("商户基本信息："+ JSON.toJSONString(baseVo,filter));
		// 商户详细信息
		json.put("merchantInfo", JSON.toJSONString(infoVo,filter));
		logger.info("商户详细信息："+JSON.toJSONString(infoVo,filter));
		// 商户账户信息
		json.put("merchantAccount", JSON.toJSONString(accountsVo,filter));
		logger.info("商户账户信息："+JSON.toJSONString(accountsVo,filter));
		// 商户费率信息
		json.put("merchantRateList", JSON.toJSONString(rateList,filter));
		logger.info("商户费率信息："+JSON.toJSONString(rateList,filter));
		String response = invokeWeBank(json,configConstants.getMerchant_join_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}
	
	/**
	 * 支付宝下单
	 * @return
	 */
	@Override
	public Map<String, String> preCreateTrade(WeBankPreCreateTradeVo tradeVo,List<WeBankGoodsDetailVo> goodsList){
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(tradeVo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(tradeVo,filter));
		// 商品详细信息
		json.put("goodsDetail", JSON.toJSONString(goodsList,filter));
		logger.info("商品详细信息："+JSON.toJSONString(goodsList,filter));
		
		String response = invokeWeBank(json,configConstants.getPrecreate_trade_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	/**
	 * 查询订单
	 * @return
	 */
	@Override
	public Map<String, String> queryTrade(WeBankRefundVo vo) {
		
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(vo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(vo,filter));
		String response = invokeWeBank(json,configConstants.getQuery_trade_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	/**
	 * 退款
	 * @return
	 */
	@Override
	public Map<String, String> refund(WeBankRefundVo vo) {
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(vo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(vo,filter));
		String response = invokeWeBank(json,configConstants.getRefund_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	/**
	 * 退款查询
	 * @return
	 */
	@Override
	public Map<String, String> queryRefund(WeBankRefundVo vo) {
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(vo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(vo,filter));
		
		String response = invokeWeBank(json,configConstants.getQuery_refund_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	/**
	 * 支付宝条码支付
	 * @return
	 */
	@Override
	public Map<String, String> scanPay(WeBankPreCreateTradeVo tradeVo,List<WeBankGoodsDetailVo> goodsList) {
		
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(tradeVo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(tradeVo,filter));
		// 商品详细信息
		json.put("goodsDetail", JSON.toJSONString(goodsList,filter));
		logger.info("商品详细信息："+JSON.toJSONString(goodsList,filter));
		String response = invokeWeBank(json,configConstants.getScan_pay_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	/**
	 * 冲正 撤销
	 * @return
	 */
	@Override
	public Map<String, String> cancel(WeBankRefundVo vo) {
		JSONObject json = new JSONObject();
		//商户订单信息
		json.putAll(vo.toMap());
		logger.info("商户订单信息："+ JSON.toJSONString(vo,filter));
		String response = invokeWeBank(json,configConstants.getCancel_url());
		Map<String, String> map = JSONObject.fromObject(response);
		return map;
	}

	private String invokeWeBank(JSONObject json,String urlStr) {
		// 获取ticket
		String ticket = getWeBankTicket();
		String nonce = Strings.getUUID();
		ArrayList<String> values = new ArrayList<String>();
		values.add(configConstants.getApp_id());// app_id
		values.add(nonce);// 32位随机串
		values.add(configConstants.getVersion());// 版本号
		values.add(ticket);// ticket
		values.add(json.toString());// 接口参数 json类型
		String signstr = Signature.sign(values);// 签名

		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("app_id", configConstants.getApp_id()));
		parameters.add(new BasicNameValuePair("nonce", nonce));
		parameters.add(new BasicNameValuePair("version", configConstants.getVersion()));
		parameters.add(new BasicNameValuePair("sign", signstr.toUpperCase()));
		
		// httpPost请求
		HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
		String response = httpClientUtils.doPost(urlStr, json.toString(),parameters);
		return response;
	}

	/**
	 * 微信扫码 商户入网
	 * @return
	 */
	@Override
	public String weChatMerchantJoin(WeBankWeChatMerchantInfoVo infoVo) {
		String reqXml = null;
		try {
			//定义Document接口
			Document doc = XmlUtils.createNewXmlObj();
			//创建报文根
			Element root = doc.createElement("xml");
			doc.appendChild(root);
			
			//流水号 必填
			Element serialNo = doc.createElement("serialNo");
			serialNo.appendChild(doc.createTextNode(infoVo.getSerialNo()));
			root.appendChild(serialNo);
			//商户名称 必填
			Element merchantName = doc.createElement("merchantName");
			merchantName.appendChild(doc.createTextNode(infoVo.getMerchantName()));
			root.appendChild(merchantName);
			//商户简称 必填
			Element merchantAlis = doc.createElement("merchantAlis");
			merchantAlis.appendChild(doc.createTextNode(infoVo.getMerchantAlis()));
			root.appendChild(merchantAlis);
			//地区 必填
			Element merchantArea = doc.createElement("merchantArea");
			merchantArea.appendChild(doc.createTextNode(infoVo.getMerchantArea()));
			root.appendChild(merchantArea);
			//开户行 必填
			Element bankName = doc.createElement("bankName");
			bankName.appendChild(doc.createTextNode(infoVo.getBankName()));
			root.appendChild(bankName);
			//开户行号 必填
			Element revactBankNo = doc.createElement("revactBankNo");
			revactBankNo.appendChild(doc.createTextNode(infoVo.getRevactBankNo()));
			root.appendChild(revactBankNo);
			
			//户名 必填
			Element bankAccountName = doc.createElement("bankAccoutName");
			bankAccountName.appendChild(doc.createTextNode(infoVo.getBankAccoutName()));
			root.appendChild(bankAccountName);
			//银行账户 必填
			Element bankAccout = doc.createElement("bankAccout");
			bankAccout.appendChild(doc.createTextNode(infoVo.getBankAccout()));
			root.appendChild(bankAccout);
			//代理机构 必填
			Element agency = doc.createElement("agency");
			agency.appendChild(doc.createTextNode(infoVo.getAgency()));
			root.appendChild(agency);
			//客服电话 必填
			Element servicePhone = doc.createElement("servicePhone");
			servicePhone.appendChild(doc.createTextNode(infoVo.getServicePhone()));
			root.appendChild(servicePhone);
			//经营类目 必填
			Element business = doc.createElement("business");
			business.appendChild(doc.createTextNode(infoVo.getBusiness()));
			root.appendChild(business);
			//商户性质 必填
			Element merchantNature = doc.createElement("merchantNature");
			merchantNature.appendChild(doc.createTextNode(infoVo.getMerchantNature()));
			root.appendChild(merchantNature);
			//商户扣率 必填
			Element wxCostRate = doc.createElement("wxCostRate");
			wxCostRate.appendChild(doc.createTextNode(infoVo.getWxCostRate()));
			root.appendChild(wxCostRate);
			//账号性质 必填
			Element companyFlag = doc.createElement("companyFlag");
			companyFlag.appendChild(doc.createTextNode(infoVo.getCompanyFlag()));
			root.appendChild(companyFlag);
			//根据参数生成xml报文
			reqXml = XmlUtils.xmlStringByObj(doc);
			//将生成的报文添加到stringbuffer中。
			StringBuffer hbreq = new StringBuffer();
			reqXml = reqXml.replace("</xml>", "").trim();
			hbreq.append(reqXml);
			hbreq.append("</xml>");
			
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = null;
			try {
				document = saxReader.read(new ByteArrayInputStream(hbreq.toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			org.dom4j.Element node = document.getRootElement();
			JSONObject reqjson = new JSONObject();
			reqjson = XmlUtils.parseXMLTOJSON(node, new JSONObject());
			System.out.println("json格式:"+reqjson);
			//签名
			String signstr = Signature.md5Sign(reqjson, configConstants.getJoin_key());
			String req = reqXml + "<sign>" + signstr
					+ "</sign>" + "</xml>";
			logger.info("##########XML请求报文！",req);
			// httpPost请求
			HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
			String response = httpClientUtils.doPost(configConstants.getWechat_merchant_join_url(), req,null);
			return response;
		} catch (Exception e) {
			logger.info("##########创建XML报文错误~！",e);
			try {
				throw new Exception("#####创建XML报文错误~！",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 微信扫码支付   商户信息查询
	 * @return
	 */
	@Override
	public String weChatSelectMerchantInfo(WeBankWeChatMerchantInfoVo infoVo) {
		JSONObject json = new JSONObject();
		//商户信息
		json.putAll(infoVo.toMap());
		logger.info("商户信息："+ JSON.toJSONString(infoVo,filter));

		// 签名
		String signstr = Signature.md5Sign(json, configConstants.getJoin_key());
		json.put("sign", signstr);
		// httpPost请求
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		String response = httpClientUtils.doPost(configConstants.getWechat_select_merchant_info_url(), json.toString(),null);
		return response;
	}
	/**
	 * 微信扫码支付  商户信息修改
	 * @return
	 */
	@Override
	public String weChatUpdateMerchantInfo(WeBankWeChatMerchantInfoVo infoVo) {
		JSONObject json = new JSONObject();
		//商户信息
		json.putAll(infoVo.toMap());
		logger.info("商户信息："+ JSON.toJSONString(infoVo,filter));
		// 签名
		String signstr = Signature.md5Sign(json, configConstants.getJoin_key());
		json.put("sign", signstr);
		// httpPost请求
		HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
		String response = httpClientUtils.doPost(configConstants.getWechat_update_merchant_info_url(),json.toString(),null);
		return response;
	}
	
	/**
	 * 微信扫码支付  下单
	 * @return
	 */
	@Override
	public String weChatNao(WeBankWeChatNaoInfoVo naoInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(naoInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(naoInfoVo,filter));
		
		String response = invokeWeBankWeChat(json,configConstants.getWechat_nao_url());
		return response;
	}
	
	/**
	 * 微信扫码支付  查询订单
	 * @return
	 */
	@Override
	public String weChatNgos(WeBankWeChatQueryInfoVo queryInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(queryInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(queryInfoVo,filter));
		
		String response = invokeWeBankWeChat(json,configConstants.getWechat_ngos_url());
		return response;
	}
	
	/**
	 * 微信扫码支付  刷卡支付
	 * @return
	 */
	@Override
	public String weChatMao(WeBankWeChatNaoInfoVo naoInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(naoInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(naoInfoVo,filter));
		
		String response = invokeWeBankWeChat(json,configConstants.getWechat_mao_url());
		return response;
	}
	/**
	 * 微信扫码支付  查询刷卡支付订单
	 * @return
	 */
	@Override
	public String weChatMgos(WeBankWeChatQueryInfoVo queryInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(queryInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(queryInfoVo,filter));
		
		String response = invokeWeBankWeChat(json,configConstants.getWechat_mgos_url());
		return response;
	}
	
	
	/**
	 * 微信扫码支付  冲正撤销
	 * @return
	 */
	@Override
	public String weChatRo(WeBankWeChatQueryInfoVo queryInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(queryInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(queryInfoVo,filter));
		
		String response = invokeWeBankWeChat(json,configConstants.getWechat_ro_url());
		return response;
	}
	
	/**
	 * 微信扫码支付  退款
	 * @return
	 */
	@Override
	public String weChatNro(WeBankWeChatQueryInfoVo queryInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(queryInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(queryInfoVo,filter));
		String response = invokeWeBankWeChat(json,configConstants.getWechat_nro_url());
		return response;
	}
	
	/**
	 * 微信扫码支付  退款查询
	 * @return
	 */
	@Override
	public String weChatNros(WeBankWeChatQueryInfoVo queryInfoVo) {
		JSONObject json = new JSONObject();
		//
		json.putAll(queryInfoVo.toMap());
		logger.info("订单信息："+ JSON.toJSONString(queryInfoVo,filter));
		String response = invokeWeBankWeChat(json,configConstants.getWechat_nros_url());
		return response;
	}

	private String invokeWeBankWeChat(JSONObject json,String url) {
		//签名
		String signstr = Signature.md5Sign(json, configConstants.getMerchant_key());
		json.put("sign", signstr);
		// httpPost请求
		HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
		String response = httpClientUtils.doPost(url, json.toString(),null);
		return response;
	}

	@Override
	public String weChatJsPayAddOrder(WeBankWeChatJsPayInfoVo payInfoVo) {
		
		String reqXml = null;
		try {
			org.w3c.dom.Document doc = XmlUtils.createNewXmlObj();
			//创建报文根
			Element root = doc.createElement("xml");
			doc.appendChild(root);
			
			//接口类型
			Element service = doc.createElement("Service");
			service.appendChild(doc.createTextNode("pay.wexin.jspay"));
			root.appendChild(service);
			//版本号
			Element version = doc.createElement("version");
			version.appendChild(doc.createTextNode(payInfoVo.getVersion()));
			root.appendChild(version);
			//字符集
			Element charset = doc.createElement("charset");
			charset.appendChild(doc.createTextNode(payInfoVo.getCharset()));
			root.appendChild(charset);
			//签名方式 
			Element signType = doc.createElement("sign_type");
			signType.appendChild(doc.createTextNode(payInfoVo.getSign_type()));
			root.appendChild(signType);
			//商户号
			Element mchId = doc.createElement("mch_id");
			mchId.appendChild(doc.createTextNode(payInfoVo.getMch_id()));
			root.appendChild(mchId);
			
			//是否原生态
			Element isRaw = doc.createElement("isRaw");
			isRaw.appendChild(doc.createTextNode(payInfoVo.getIsRaw()));
			root.appendChild(isRaw);
			//商户订单号
			Element out_trade_no = doc.createElement("out_trade_no");
			out_trade_no.appendChild(doc.createTextNode(payInfoVo.getOut_trade_no()));
			root.appendChild(out_trade_no);
			//设备号
			Element device_info = doc.createElement("device_info");
			device_info.appendChild(doc.createTextNode(payInfoVo.getDevice_info()));
			root.appendChild(device_info);
			//商品描述
			Element body = doc.createElement("body");
			body.appendChild(doc.createTextNode(payInfoVo.getBody()));
			root.appendChild(body);
			//用户openid
			Element sub_openid = doc.createElement("sub_openid");
			sub_openid.appendChild(doc.createTextNode(payInfoVo.getSub_openid()));
			root.appendChild(sub_openid);
			//商户appid
			Element sub_appid = doc.createElement("sub_appid");
			sub_appid.appendChild(doc.createTextNode(payInfoVo.getSub_appid()));
			root.appendChild(sub_appid);
			//附加信息
			Element attach = doc.createElement("attach");
			attach.appendChild(doc.createTextNode(payInfoVo.getAttach()));
			root.appendChild(attach);
			//总金额
			Element total_fee = doc.createElement("total_fee");
			total_fee.appendChild(doc.createTextNode(String.valueOf(payInfoVo.getTotal_fee())));
			root.appendChild(total_fee);
			//终端ip
			Element mch_create_ip = doc.createElement("mch_create_ip");
			mch_create_ip.appendChild(doc.createTextNode(payInfoVo.getMch_create_ip()));
			root.appendChild(mch_create_ip);
			//通知地址
			Element notify_url = doc.createElement("notify_url");
			notify_url.appendChild(doc.createTextNode(payInfoVo.getNotify_url()));
			root.appendChild(notify_url);
			//订单生成时间
			Element time_start = doc.createElement("time_start");
			time_start.appendChild(doc.createTextNode(payInfoVo.getTime_start()));
			root.appendChild(time_start);
			//订单超时时间
			Element time_expire = doc.createElement("time_expire");
			time_expire.appendChild(doc.createTextNode(payInfoVo.getTime_expire()));
			root.appendChild(time_expire);
			//商品标记
			Element goods_tag = doc.createElement("goods_tag");
			goods_tag.appendChild(doc.createTextNode(payInfoVo.getGoods_tag()));
			root.appendChild(goods_tag);
			//随机字符串
			Element nonce_str = doc.createElement("nonce_str");
			nonce_str.appendChild(doc.createTextNode(payInfoVo.getNonce_str()));
			root.appendChild(nonce_str);
			//是否限制信用卡
			Element limit_credit_pay = doc.createElement("limit_credit_pay");
			limit_credit_pay.appendChild(doc.createTextNode(payInfoVo.getLimit_credit_pay()));
			root.appendChild(limit_credit_pay);
			reqXml = XmlUtils.xmlStringByObj(doc);
			//将生成的报文添加到stringbuffer中。
			StringBuffer hbreq = new StringBuffer();
			reqXml = reqXml.replace("</xml>", "").trim();
			hbreq.append(reqXml);
			hbreq.append("</xml>");
			
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = null;
			try {
				document = saxReader.read(new ByteArrayInputStream(hbreq.toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			org.dom4j.Element node = document.getRootElement();
			JSONObject reqjson = new JSONObject();
			reqjson = XmlUtils.parseXMLTOJSON(node, new JSONObject());
			System.out.println("json格式:"+reqjson);
			//签名
			String signstr = Signature.md5Sign(reqjson, configConstants.getMerchant_key());
			String req = reqXml + "<sign>" + signstr
					+ "</sign>" + "</xml>";
			logger.info("##########XML请求报文！",req);
			// httpPost请求
			HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
			String response = httpClientUtils.doPost(configConstants.getWechat_jsapi_add_order_url(), req,null);
			return response;
		} catch (Exception e) {
			logger.info("##########创建XML报文错误~！",e);
			try {
				throw new Exception("#####创建XML报文错误~！",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String weChatJsPayGetOrderStatus(WeBankWeChatJsPayInfoVo payInfoVo) {

		String reqXml = null;
		try {
			org.w3c.dom.Document doc = XmlUtils.createNewXmlObj();
			//创建报文根
			Element root = doc.createElement("xml");
			doc.appendChild(root);
			
			//接口类型
			Element service = doc.createElement("Service");
			service.appendChild(doc.createTextNode("pay.wexin.jspay"));
			root.appendChild(service);
			//版本号
			Element version = doc.createElement("version");
			version.appendChild(doc.createTextNode(payInfoVo.getVersion()));
			root.appendChild(version);
			//字符集
			Element charset = doc.createElement("charset");
			charset.appendChild(doc.createTextNode(payInfoVo.getCharset()));
			root.appendChild(charset);
			//签名方式 
			Element signType = doc.createElement("sign_type");
			signType.appendChild(doc.createTextNode(payInfoVo.getSign_type()));
			root.appendChild(signType);
			//商户号
			Element mchId = doc.createElement("mch_id");
			mchId.appendChild(doc.createTextNode(payInfoVo.getMch_id()));
			root.appendChild(mchId);
			//商户订单号
			Element out_trade_no = doc.createElement("out_trade_no");
			out_trade_no.appendChild(doc.createTextNode(payInfoVo.getOut_trade_no()));
			root.appendChild(out_trade_no);
			//微众银行订单号
			Element orderid = doc.createElement("orderid");
			orderid.appendChild(doc.createTextNode(payInfoVo.getOrderid()));
			root.appendChild(orderid);
			//微信支付订单号
			Element transaction_id = doc.createElement("transaction_id");
			transaction_id.appendChild(doc.createTextNode(payInfoVo.getTransaction_id()));
			root.appendChild(transaction_id);
			//随机字符串
			Element nonce_str = doc.createElement("nonce_str");
			nonce_str.appendChild(doc.createTextNode(payInfoVo.getNonce_str()));
			root.appendChild(nonce_str);
			reqXml = XmlUtils.xmlStringByObj(doc);
			//将生成的报文添加到stringbuffer中。
			StringBuffer hbreq = new StringBuffer();
			reqXml = reqXml.replace("</xml>", "").trim();
			hbreq.append(reqXml);
			hbreq.append("</xml>");
			
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = null;
			try {
				document = saxReader.read(new ByteArrayInputStream(hbreq.toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			org.dom4j.Element node = document.getRootElement();
			JSONObject reqjson = new JSONObject();
			reqjson = XmlUtils.parseXMLTOJSON(node, new JSONObject());
			System.out.println("json格式:"+reqjson);
			//签名
			String signstr = Signature.md5Sign(reqjson, configConstants.getMerchant_key());
			String req = reqXml + "<sign>" + signstr
					+ "</sign>" + "</xml>";
			logger.info("##########XML请求报文！",req);
			// httpPost请求
			HttpClientUtils httpClientUtils = new HttpClientUtils(configConstants);
			String response = httpClientUtils.doPost(configConstants.getWechat_jsapi_order_status_url(), req,null);
			return response;
		} catch (Exception e) {
			logger.info("##########创建XML报文错误~！",e);
			try {
				throw new Exception("#####创建XML报文错误~！",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

}
