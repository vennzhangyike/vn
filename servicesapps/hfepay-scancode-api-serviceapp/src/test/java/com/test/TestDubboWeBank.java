package com.test;

import java.math.BigDecimal;
import java.security.KeyFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.condition.WebankOrderCondition;
import com.hfepay.scancode.api.entity.WebankOrder;
import com.hfepay.scancode.api.service.WebankOrderService;
import com.hfepay.scancode.api.webank.entity.vo.WeBankGoodsDetailVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantAccountsVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantInfoBaseVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantInfoVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankMerchantRateVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankPreCreateTradeVo;
import com.hfepay.scancode.api.webank.entity.vo.WeBankRefundVo;
import com.hfepay.scancode.api.webank.service.WeBankMerchantBusinessService;
import com.hfepay.scancode.api.webank.sign.Signature;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class TestDubboWeBank {
	
	@Autowired
	private WeBankMerchantBusinessService weBankMerchantBusinessService;
	
	@Autowired
	private WebankOrderService webankOrderService;
	
	public void test(){
		String str = "PLEASE";
		String str1 = "PLEASEHUOQUTIECKET";
		if(str1.substring(0,6).equals(str)){
			System.out.println(str1.substring(0,6));
			System.out.println("1");
		}
	}
	@Test
	public void testMerchantJoin() {
		
		try {
			WeBankMerchantInfoBaseVo baseVo = new WeBankMerchantInfoBaseVo();
			baseVo.setAliasName("华付通互联网");
			baseVo.setServicePhone("");
			baseVo.setContactPhone("");
			baseVo.setContactEmail("");
			baseVo.setMemo("");
			baseVo.setExternalInfo("");
			baseVo.setDistrict("0755");
			WeBankMerchantInfoVo infoVo = new WeBankMerchantInfoVo();
			infoVo.setAppId("W0000032");
			infoVo.setAgencyId("1070755003");
			//测试 1070755003
			//生产 1035840003
			infoVo.setIdType("01");
			infoVo.setIdNo("410183199011025814");
			infoVo.setMerchantName("zy");
			infoVo.setLegalRepresent("张宇");
			infoVo.setLicenceNo("abc123");
			infoVo.setLicenceBeginTime("");
			infoVo.setLicenceEndTime("");
			infoVo.setTaxRegisterNo("abc123");
			infoVo.setContactName("张宇");
			infoVo.setContactPhoneNo("13311102905");
			infoVo.setMainBusiness("互联网技术开发");
			infoVo.setBusinessRange("互联网技术开发；计算机软硬件及电子产品的技术开发、技术服务、技术转让与销售");
			infoVo.setRegisterAddr("深圳市");
			infoVo.setMerchantTypeCode("4812");
			infoVo.setMerchantNature("私营企业");
			infoVo.setContractNo("");
			infoVo.setOpenYear("");
			infoVo.setCategoryId("2015050700000010");
			WeBankMerchantAccountsVo accountsVo = new WeBankMerchantAccountsVo();
			accountsVo.setAccountNo("6214830100756005");
			accountsVo.setAccountOpbankNo("305100000013");
			accountsVo.setAccountName("张宇");
			accountsVo.setAccountOpbank("中国民生银行");
			accountsVo.setAccountSubbranchOpbank("深圳华联支行");
			accountsVo.setAccountOpbankAddr("深圳");
			accountsVo.setAcctType("01");
			List<WeBankMerchantRateVo> rateList = new ArrayList<WeBankMerchantRateVo>();
			WeBankMerchantRateVo rateVo = new WeBankMerchantRateVo();
			rateVo.setPaymentType("20");
			rateVo.setChargeType("02");
			rateVo.setCommissionRate("6");
			rateVo.setCommissionAmount("");
			rateVo.setMaxAmount("");
			rateList.add(rateVo);
			WeBankMerchantRateVo rateVo1 = new WeBankMerchantRateVo();
			rateVo1.setPaymentType("21");
			rateVo1.setChargeType("02");
			rateVo1.setCommissionRate("6");
			rateVo1.setCommissionAmount("");
			rateVo1.setMaxAmount("");
			rateList.add(rateVo1);
			Map<String, String> map = weBankMerchantBusinessService.merchantJoin(baseVo,infoVo,accountsVo,rateList);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703012LD01132100000000000000186","transactionTime":"20170301182951","wbapMerchantCode":"1078000000","wbapMerchantDesc":"操作成功","merchantId":"103037100103873","success":true}
	}
	//预订单
	
	public void testPrecreateTrade() {
		WeBankPreCreateTradeVo tradeVo = new WeBankPreCreateTradeVo();
		tradeVo.setWbMerchantId("103075548120012");
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");//小写的mm表示的是分钟  
		String orderId = sdf.format(date);
		tradeVo.setOrderId(orderId);
		tradeVo.setSellerId("");
		tradeVo.setTotalAmount("0.01");
		tradeVo.setDiscountableAmount("");
		tradeVo.setUndiscountableAmount("");
		tradeVo.setBuyerLogonId("");
		tradeVo.setSubject("小二买单测试");
		//tradeVo.setBody("小二买单测试");
		tradeVo.setOperatorId("");
		tradeVo.setStoreId("");
		tradeVo.setTerminalId("");
		tradeVo.setAlipayStoreId("");
		tradeVo.setTimeoutExpress("90m");
		tradeVo.setRoyaltyInfo("");
		//tradeVo.setExternalInfo("小二买单测试");
		
		List<WeBankGoodsDetailVo> goodsList = new ArrayList<WeBankGoodsDetailVo>();
//		WeBankGoodsDetailVo goodsVo = new WeBankGoodsDetailVo();
//		goodsVo.setGoodsId("A00000001");
//		goodsVo.setAlipayGoodsId("20010001");
//		goodsVo.setGoodsName("小二买单测试");
//		goodsVo.setQuantity("1");
//		goodsVo.setPrice("0.01");
//		goodsVo.setGoodsCategory("34540000");
//		goodsVo.setBody("小二买单测试");
//		goodsList.add(goodsVo);
//		WeBankGoodsDetailVo goodsVo1 = new WeBankGoodsDetailVo();
//		goodsVo1.setGoodsId("A00000002");
//		goodsVo1.setGoodsName("小二买单测试");
//		goodsVo1.setQuantity("1");
//		goodsVo1.setPrice("0.01");
//		goodsVo1.setBody("小二买单测试");
//		goodsList.add(goodsVo1);

		try {
			Map<String, String> map = weBankMerchantBusinessService.preCreateTrade(tradeVo, goodsList);
			WebankOrderCondition webankOrderCondition = new WebankOrderCondition();
			webankOrderCondition.setId(Strings.getUUID());
			webankOrderCondition.setMerchantId("103075548120012");
			webankOrderCondition.setTradeNo(orderId);
			webankOrderCondition.setTradeAmt(new BigDecimal(0.01));
			webankOrderCondition.setCreateTime(new Date());
			webankOrderCondition.setOperator("zy");
			webankOrderCondition.setTradeStatus("00");
			webankOrderService.insert(webankOrderCondition);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703132LD01132100000000000001236","transactionTime":"20170313181440","retCode":"10000","retMsg":"Success","qrCode":"https://qr.alipay.com/bax08325wmnqjm8hxtcm40dd","success":true}
	}
	//查询支付订单
	
	public void testQueryTrade() {
//		WebankOrderCondition webankOrderCondition = new WebankOrderCondition();
//		List<WebankOrder> list = webankOrderService.findAll(webankOrderCondition);
//		if(list != null && list.size()>0){
//			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//				WebankOrder webankOrder = (WebankOrder) iterator.next();
//				WeBankRefundVo vo = new WeBankRefundVo();
//				vo.setWbMerchantId(webankOrder.getMerchantId());
//				vo.setOrderId(webankOrder.getTradeNo());
//				vo.setTradeNo("");
//				try {
//					Map<String, String> map = weBankMerchantBusinessService.queryTrade(vo);
//					if("0".equals(map.get("code").toString())){
//						WebankOrderCondition orderCondition = new WebankOrderCondition();
//						BeanUtils.copyProperties(webankOrder, orderCondition);
//						orderCondition.setTradeStatus(map.get("tradeStatus"));
//						webankOrderService.update(orderCondition);
//					}
//					System.out.println(map.toString());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		WeBankRefundVo vo = new WeBankRefundVo();
		vo.setWbMerchantId("103584070120000");
		vo.setOrderId("20170322105142");
		vo.setTradeNo("");
		try {
			Map<String, String> map = weBankMerchantBusinessService.queryTrade(vo);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703012LD01132100000000000000224","transactionTime":"20170301195502","tradeNo":"2017030121001004230283044825","outTradeNo":"17030119412000001002","orderId":"201703011836","buyerLogonId":"309***@qq.com","tradeStatus":"01","totalAmount":"0.02","receiptAmount":"0.02","buyerPayAmount":"0.02","pointAmount":"0.00","invoiceAmount":"0.02","alipayStoreId":"a004","storeId":"a002","terminalId":"a003","fundBillList":[{"fundChannel":"ALIPAYACCOUNT","amount":"0.02"},{"fundChannel":"ALIPAYACCOUNT","amount":"0.02"}],"buyerUserId":"2088012489891235","discountGoodsDetail":"[]","externalInfo":"zy代理商购物","success":true}
	}

	// 退款
	
	public void testRefund() {
		WeBankRefundVo vo = new WeBankRefundVo();
		vo.setWbMerchantId("103584070120000");
		vo.setOrderId("20170323110212");
		vo.setTradeNo("");
		vo.setRefundAmount("30");
		vo.setRefundReason("退款");
		vo.setOutRequestNo("201703231056");
		//201703221054
		//201703221055
		//201703221056
		//201703221057
		try {
			Map<String, String> map = weBankMerchantBusinessService.refund(vo);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703162LD01132100000000000000515","transactionTime":"20170316150827",
		//"retCode":"10000","retMsg":"Success","tradeNo":"2017031621001004230204919105","outTradeNo":"17031615020400000096",
		//"openId":"20881083987806282451527542312023","buyerLogonId":"309***@qq.com","fundChange":"Y","refundFee":"0.01",
		//"gmtRefundPay":"2017-03-16 15:08:26","refundDetailItemList":[{"fundChannel":"ALIPAYACCOUNT","amount":"0.01"}],
//		"buyerUserId":"2088012489891235","sendBackFee":"0.01","success":true}
	}

	// 查询退款
	
		public void testQueryRefund() {
			WeBankRefundVo vo = new WeBankRefundVo();
			vo.setWbMerchantId("103584070120000");
			vo.setOrderId("20170322105142");
			vo.setTradeNo("");
			vo.setOutRequestNo("201703241538");
			try {
				Map<String, String> map = weBankMerchantBusinessService.queryRefund(vo);
				System.out.println(map.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// 取消
		
	public void testCancel() {
		WeBankRefundVo vo = new WeBankRefundVo();
		vo.setWbMerchantId("103075548120006");
		vo.setOrderId("201703161535");
		vo.setTradeNo("2017031621001004230204953128");
		try {
			Map<String, String> map = weBankMerchantBusinessService.cancel(vo);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703012LD01132100000000000000228","transactionTime":"20170301195637","retCode":"10000","retMsg":"Success","tradeNo":"2017030121001004230283044825","outTradeNo":"17030119412000001002","retryFlag":"N","action":"refund","success":true}
	}	

	// 条码支付
	
	public void testScanPay() {
		WeBankPreCreateTradeVo tradeVo = new WeBankPreCreateTradeVo();
		tradeVo.setWbMerchantId("103075548120006");
		tradeVo.setOrderId("201703161535");
		tradeVo.setAuthCode("281984373313900425");
		//tradeVo.setSellerId("309033108@qq.com");
		tradeVo.setTotalAmount("0.02");
		tradeVo.setDiscountableAmount("");
		tradeVo.setUndiscountableAmount("");
		tradeVo.setSubject("zy代理商测试");
		tradeVo.setBody("购买手机");
		tradeVo.setOperatorId("a001");
		tradeVo.setTerminalId("a003");
		tradeVo.setAlipayStoreId("");
		tradeVo.setTimeoutExpress("90m");
		tradeVo.setRoyaltyInfo("");
		tradeVo.setExternalInfo("zy代理商购物");

		List<WeBankGoodsDetailVo> goodsList = new ArrayList<WeBankGoodsDetailVo>();
		WeBankGoodsDetailVo goodsVo = new WeBankGoodsDetailVo();
		goodsVo.setGoodsId("A00000001");
		goodsVo.setAlipayGoodsId("20010001");
		goodsVo.setGoodsName("zy代理商商品");
		goodsVo.setQuantity("1");
		goodsVo.setPrice("0.01");
		goodsVo.setGoodsCategory("34540000");
		goodsVo.setBody("zy代理商商品");
		goodsList.add(goodsVo);
		WeBankGoodsDetailVo goodsVo1 = new WeBankGoodsDetailVo();
		goodsVo1.setGoodsId("A00000002");
		goodsVo1.setGoodsName("zy代理商商品1");
		goodsVo1.setQuantity("1");
		goodsVo1.setPrice("0.01");
		goodsVo1.setBody("zy代理商商品1");
		goodsList.add(goodsVo1);

		try {
			Map<String, String> map = weBankMerchantBusinessService.scanPay(tradeVo, goodsList);
			System.out.println(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":"0","msg":"请求成功","bizSeqNo":"1703162LD01132100000000000000527","transactionTime":"20170316151857",
		//"retCode":"10003","retMsg":" order success pay inprocess","tradeNo":"2017031621001004230204924759",
		//"outTradeNo":"17031615185500000106","buyerLogonId":"309***@qq.com","totalAmount":"0.02",
		//"receiptAmount":"0.00","buyerPayAmount":"0.00","pointAmount":"0.00","invoiceAmount":"0.00",
		//"buyerUserId":"2088012489891235","success":true}
	}
	
	public void testSign() {
		//JSONObject jsonObject = new JSONObject();
		Map<String,String> params = new HashMap();
		params.put("pointAmount", "0.00");
		params.put("tradeNo", "2017032521001004230218040197");
		params.put("orderId", "20170325015249");
		params.put("notifyTime", "2017-03-25 13:52:50");
		params.put("subject", "小二买单测试");
		params.put("invoiceAmount", "0.01");
		params.put("buyerId", "2088012489891235");
		params.put("gmtCreate", "2017-03-25 13:52:45");
		params.put("buyerLogonId", "309***@qq.com");
		params.put("sellerEmail", "zfbtest25@service.aliyun.com");
		params.put("totalAmount", "0.01");
		params.put("receiptAmount", "0.01");
		params.put("notifyType", "trade_status_sync");
		params.put("sellerId", "2088911212416201");
		params.put("gmtPayment", "2017-03-25 13:52:50");
		params.put("appId", "2015051100069170");
		params.put("outTradeNo", "17032513521700000017");
		params.put("tradeStatus", "01");
		params.put("buyerPayAmount", "0.01");
		params.put("notifyId", "a3e02d156c3435cc1be993ab92ce87dhry");
		List<Object> billList = new ArrayList<Object>();
		JSONObject object = new JSONObject();
		object.put("amount", "0.01");
		object.put("fundChannel", "ALIPAYACCOUNT");
		billList.add(object);
		params.put("fundBillList",JSON.toJSONString(billList)+"" );
		
		
		//String ticket = weBankMerchantBusinessService.getWeBankTicket();
		ArrayList<String> values = new ArrayList<String>();
		values.add("1490421171982");
		values.add("tcKahVsCrkkenYeWvqXUzKWtmvXDtLvw");// 32位随机串
		values.add("ZvTXrMIvs90TtSIdENsrGSW4x75aRaPoE559jbvBcJ9FZsY2Vg6wLjeEE6lijHmV");// ticket
		values.add(params.toString());// 接口参数 json类型
		System.out.println(JSON.toJSONString(params));
		System.out.println(values.toString());
		String signstr = Signature.sign(values);// 签名
		System.out.println(signstr);
	}
}
