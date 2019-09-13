package com.test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.webank.entity.vo.WeBankGoodsDetailVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankPreCreateTradeVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankRefundVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatJsPackageInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatJsPayInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatMerchantInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatNaoInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatQueryInfoVo;
import com.hfepay.scancode.api.webank.service.WeBankMerchantBusinessService;
import com.hfepay.scancode.api.webank.xmlutil.XmlUtils;

import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class TestDubboWeBankWeChatJSAPI {
	
	@Autowired
	private WeBankMerchantBusinessService weBankMerchantBusinessService;
	
	
	public void testMerchantJoin() {
		WeBankWeChatMerchantInfoVo infovo = new WeBankWeChatMerchantInfoVo();
		infovo.setSerialNo("201703081543");
		infovo.setMerchantName("zy商户");
		infovo.setMerchantAlis("zy");
		infovo.setMerchantArea("4910");
		infovo.setBankName("招商银行股份有限公司");
		infovo.setRevactBankNo("308584000013");
		infovo.setBankAccoutName("张宇");
		infovo.setBankAccout("6214830100756005");
		infovo.setAgency("1075888890");
		infovo.setServicePhone("0755-88888888");
		infovo.setBusiness("0002");
		infovo.setMerchantNature("私营企业");
		infovo.setWxCostRate("0.6");
		infovo.setCompanyFlag("00");
		try {
			String response = weBankMerchantBusinessService.weChatMerchantJoin(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//服务器返回:<xml><mch_id>107491000020002</mch_id><sign_type>MD5</sign_type><status>0</status><sign>E1C74CDE397D823827B0821A0DC7CC1A</sign></xml>
	}
	
	
	public void testSelectMerchantInfo() {
		WeBankWeChatMerchantInfoVo infovo = new WeBankWeChatMerchantInfoVo();
		infovo.setSerialNo("201703081543");
		infovo.setAgency("1075888890");
		infovo.setMch_id("107491000020002");
		try {
			String response = weBankMerchantBusinessService.weChatSelectMerchantInfo(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//服务器返回:{"mch_id":"107491000020002","checkStatus":"1","merchantName":"zy商户","merchantAlis":"zy","merchantArea":"4910","bankName":"招商银行股份有限公司","revactBankNo":"308584000013","bankAccoutName":"张宇","bankAccout":"6214830100756005","agency":"1075888890","servicePhone":"0755-88888888","contact":"","contactPhone":"","contactEmail":"","business":"2","merchantNature":"4","openYear":"","contractNo":"","businessStartDate":"","businessEndDate":"","wxCostRate":"0.6","companyFlag":"00","sign_type":"MD5","status":"0","sign":"D738CA476697BA835DD9CFC1F832DCDD"}
	
	//修改商户信息
	public void testUpdateMerchantInfo() {
		WeBankWeChatMerchantInfoVo infovo = new WeBankWeChatMerchantInfoVo();
		infovo.setSerialNo("201703081621");
		infovo.setAgency("1075888890");
		infovo.setMch_id("107491000020002");
		infovo.setMerchantAlis("zy1");
		infovo.setWxCostRate("0.6");
		infovo.setCompanyFlag("00");
		try {
			String response = weBankMerchantBusinessService.weChatUpdateMerchantInfo(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 下单
	@Test
	public void testWeChatJSAPIPay() {
		WeBankWeChatJsPayInfoVo payInfovo = new WeBankWeChatJsPayInfoVo();
		payInfovo.setVersion("2.0");
		payInfovo.setCharset("UTF-8");
		payInfovo.setSign_type("MD5");
		payInfovo.setMch_id("107491000020002");
		payInfovo.setIsRaw("0");
		payInfovo.setOut_trade_no("201703101510");
		payInfovo.setDevice_info("web");
		payInfovo.setBody("商品描述");
		payInfovo.setSub_openid("oCUkNwhW5WfXTq7beZ77KGuONoh0");
		payInfovo.setSub_appid("wxcd078cbcdc3c7734");
		payInfovo.setAttach("附加信息");
		payInfovo.setTotal_fee(1);
		payInfovo.setMch_create_ip("172.16.2.7");
		payInfovo.setNotify_url("www.baidu.com");
		payInfovo.setTime_start("20170310145711");
		payInfovo.setTime_expire("20170310155711");
		payInfovo.setGoods_tag("abc");
		payInfovo.setNonce_str(Strings.getUUID());
		payInfovo.setLimit_credit_pay("1");
		try {
			String response = weBankMerchantBusinessService.weChatJsPayAddOrder(payInfovo);
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = null;
			try {
				document = saxReader.read(new ByteArrayInputStream(response.toString().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			org.dom4j.Element node = document.getRootElement();
			JSONObject reqjson = new JSONObject();
			reqjson = XmlUtils.parseXMLTOJSON(node, new JSONObject());
			System.out.println(reqjson.get("pay_info"));
			JSONObject chat = JSONObject.fromObject(reqjson.get("pay_info"));
			WeBankWeChatJsPackageInfoVo infovo = new WeBankWeChatJsPackageInfoVo();
			infovo.setAppId(chat.getString("appId"));
			infovo.setNonceStr(chat.getString("nonceStr"));
			infovo.setPackageContent(chat.getString("package"));
			infovo.setPaySign(chat.getString("paySign"));
			infovo.setSignType(chat.getString("signType"));
			infovo.setTimeStamp(chat.getString("timeStamp"));
			//WeBankWeChatJsPackageInfoVo infovo = (WeBankWeChatJsPackageInfoVo) JSONObject.toBean(JSONObject.fromObject(reqjson.get("pay_info")),WeBankWeChatJsPackageInfoVo.class);
			System.out.println(infovo.toMap());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//<xml><appid>wx90bfe8ac7aa1338a</appid><version>2.0</version><charset>UTF-8</charset><sign_type>MD5</sign_type><status>0</status><message>
		//<![CDATA[OK]]></message><mch_id>107491000020002</mch_id><nonce_str>LAk51OYlFi9m9HvU</nonce_str><result_code>0</result_code>
		//<token_id>wx20170308184547b1de1213ea0683628011</token_id><pay_info>
		//<![CDATA[{"appId":"wx90bfe8ac7aa1338a","timeStamp":"1488969947308","nonceStr":"201703081845471488969947308974","package":"prepay_id=wx20170308184547b1de1213ea0683628011",
		//"signType":"MD5","paySign":"487625B32317027FC5898E1046D37B68"}]]></pay_info>
		//<time_expire><![CDATA[20170308200011]]></time_expire><goods_tag><![CDATA[abc]]>
		//</goods_tag><orderid>201703081845461488969946983793</orderid><out_trade_no>201703081846</out_trade_no><sign>DA70053530C1A33AE67EB474288D48FB</sign></xml>
	}

	// 订单查询
	
	public void testGetOrderStatus() {
		WeBankWeChatJsPayInfoVo payInfovo = new WeBankWeChatJsPayInfoVo();
		payInfovo.setVersion("2.0");
		payInfovo.setCharset("UTF-8");
		payInfovo.setSign_type("MD5");
		payInfovo.setMch_id("107491000020002");
		payInfovo.setOut_trade_no("201703081846");
		payInfovo.setOrderid("");
		payInfovo.setTransaction_id("");
		payInfovo.setNonce_str(Strings.getUUID());
		try {
			String response = weBankMerchantBusinessService.weChatJsPayGetOrderStatus(payInfovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//<xml><version>2.0</version><charset>UTF-8</charset><sign_type>MD5</sign_type><status>0</status><message><![CDATA[OK]]></message><mch_id>107491000020002</mch_id><nonce_str>fKCAByk6NosQCXNl</nonce_str><result_code>0</result_code><trade_state>NOTPAY</trade_state><sign>0C255C5D1498173B4550ED088ED2623A</sign></xml>
	}	

	// 刷卡支付
	
	public void testMao() {
		WeBankWeChatNaoInfoVo infovo = new WeBankWeChatNaoInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081648");
		infovo.setAmount("0.01");
		infovo.setProduct("微众微信扫码测试");
		infovo.setSub_openid("");
		infovo.setSub_appid("");
		infovo.setGoods_tag("");
		infovo.setAttach("");
		infovo.setAuth_code("130139061607538622");
		try {
			String response = weBankMerchantBusinessService.weChatMao(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 服务器返回:{"terminal_serialno":"201703081648","orderid":"201703081647451488962865950985","payment":"1","openid":"oCUkNwhW5WfXTq7beZ77KGuONoh0","sub_openid":"oCUkNwhW5WfXTq7beZ77KGuONoh0","is_subscribe":"N","sub_is_subscribe":"Y","trade_type":"MICROPAY","bank_type":"CFT","total_fee":"0.01","coupon_fee":"0","fee_type":"CNY","transaction_id":"4005522001201703082672782767","time_end":"20170308164746","attach":"weixin","result":{"errmsg":"SUCCESS","errno":"0"},"sign":"03d2a281d6ec1a6b7562290a036a6d9f"}
	}

	// 查询刷卡支付
	public void testMgos() {
		WeBankWeChatQueryInfoVo infovo = new WeBankWeChatQueryInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081648");
		infovo.setOrderid("");
		infovo.setTransaction_id("");
		try {
			String response = weBankMerchantBusinessService.weChatMgos(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//服务器返回:{"trade_type":"MICROPAY","transaction_id":"4005522001201703082672782767","time_end":"","is_subscribe":"","bank_type":"","attach":"","terminal_serialno":"201703081648","orderid":"201703081647451488962865950985","payment":"1","openid":"oApmQt88m1_r6Fo0NmN2tDB4aGn8","total_fee":"0.01","coupon_fee":"0.00","fee_type":"CHN","result":{"errmsg":"SUCCESS","errno":"0"},"sign":"8fed9b7cba82e61d44e1c7559b1650f8"}
	}
	
	// 冲正
	public void testRo() {
		WeBankWeChatQueryInfoVo infovo = new WeBankWeChatQueryInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081650");
		infovo.setO_terminal_serialno("201703081648");
		infovo.setAmount("0.01");
		try {
			String response = weBankMerchantBusinessService.weChatRo(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 服务器返回:{"recall":"N","result":{"errmsg":"冲正成功","errno":"0"},"sign":"902dcc3ba08fc73eb8254bfee5a62943"}
	}
	
	// 退款
	public void testNro() {
		WeBankWeChatQueryInfoVo infovo = new WeBankWeChatQueryInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081626");
		infovo.setRefund_amount("0.01");
		infovo.setOrderid("");
		
		try {
			String response = weBankMerchantBusinessService.weChatNro(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 退款查询
	public void testNros() {
		WeBankWeChatQueryInfoVo infovo = new WeBankWeChatQueryInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081626");
		infovo.setOrderid("");
		try {
			String response = weBankMerchantBusinessService.weChatNros(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 服务器返回:{"refundment":"1","orderid":"201703081631481488961908095319","refundid":"201703081702131488963733250493","total_fee":"0.01","refund_fee":"0.01","refund_channel":"","result":{"errmsg":"OK","errno":"0"},"sign":"5622850ce7eab50d37a68a2f199422a0"}
	}
}
