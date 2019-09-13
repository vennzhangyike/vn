package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.scancode.api.webank.entity.vo.WeBankGoodsDetailVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankPreCreateTradeVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankRefundVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatMerchantInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatNaoInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankWeChatQueryInfoVo;
import com.hfepay.scancode.api.webank.service.WeBankMerchantBusinessService;


@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class TestDubboWeBankWeChat {
	
	@Autowired
	private WeBankMerchantBusinessService weBankMerchantBusinessService;
	
	@Test
	public void testMerchantJoin() {
		WeBankWeChatMerchantInfoVo infovo = new WeBankWeChatMerchantInfoVo();
		infovo.setSerialNo("201703271105");
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
	public void testWeChatNao() {
		WeBankWeChatNaoInfoVo infovo = new WeBankWeChatNaoInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081626");
		infovo.setAmount("0.01");
		infovo.setProduct("微众微信扫码测试");
		infovo.setSub_openid("");
		infovo.setSub_appid("");
		infovo.setGoods_tag("");
		infovo.setTime_expire("");
		infovo.setNotify_url("");
		infovo.setAttach("");
		try {
			String response = weBankMerchantBusinessService.weChatNao(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//服务器返回:{"orderid":"201703081631481488961908095319","url":"weixin://wxpay/bizpayurl?pr=kwBapaC","terminal_serialno":"201703081626","result":{"errmsg":"SUCCESS,,","errno":"0"},"sign":"028aad00ea3e5b82885623a4fd571c87"}
	}

	// 订单查询
	public void testNgos() {
		WeBankWeChatQueryInfoVo infovo = new WeBankWeChatQueryInfoVo();
		infovo.setMerchant_code("107491000020002");
		infovo.setTerminal_code("web");
		infovo.setTerminal_serialno("201703081626");
		infovo.setOrderid("");
		infovo.setTransaction_id("");
		try {
			String response = weBankMerchantBusinessService.weChatNgos(infovo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//服务器返回:{"trade_type":"NATIVE","transaction_id":"4005522001201703082669891382","time_end":"20170308163244","is_subscribe":"N","sub_is_subscribe":"N","bank_type":"CFT","terminal_serialno":"201703081626","orderid":"201703081631481488961908095319","payment":"1","openid":"oApmQt88m1_r6Fo0NmN2tDB4aGn8","total_fee":"0.01","coupon_fee":"0.00","fee_type":"CHN","result":{"errmsg":"SUCCESS","errno":"0"},"sign":"c217f44323c4feb64ab43179f40ad79b"}
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
	@Test
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
