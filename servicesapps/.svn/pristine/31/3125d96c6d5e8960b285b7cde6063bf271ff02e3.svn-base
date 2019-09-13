/*package com.test;


import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Order;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.bo.MerchantNotifyMessage;
import com.hfepay.scancode.bo.OrderBo;
import com.hfepay.scancode.condition.ChannelExpandCondition;
import com.hfepay.scancode.condition.DataNodeCondition;
import com.hfepay.scancode.condition.MerchantBankcardCondition;
import com.hfepay.scancode.condition.OrderPayCondition;
import com.hfepay.scancode.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.entity.ChannelExpand;
import com.hfepay.scancode.entity.NodeRelation;
import com.hfepay.scancode.entity.OrderPay;
import com.hfepay.scancode.entity.PlatformQrcode;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.DataNodeService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.payway.CallBackService;
import com.hfepay.scancode.service.payway.ScanCodeService;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class DubboTest {
	@Autowired
	private DataNodeService dataNodeService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private ChannelAdminService channelAdminService;
	
	@Autowired
	private ScanCodeService scanCodeService;
	@Autowired
	private CallBackService callBackService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private NodeRelationService nodeRelationService;
	//@Autowired
	//private PropertyPlaceholderConfigurer propConfigurer = Springs.getBean("propConfigurer");
	@Test
	public void insert(){
		nodeRelationService.insertBatch();
	}
	@Test
	public void insertChannel(){
		//保存渠道，存放一条关联信息到节点表。只有一条
		DataNodeCondition dCondition = new DataNodeCondition();
		dCondition.setIdentityFlag("3");//1渠道2代理商3商户：必需参数
		dCondition.setId(Strings.getUUID());
		dCondition.setParentNode("agent211");
		dCondition.setCurrentNode("merchant2111");//当前节点id//必须参数
		//dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
		dCondition.setOperator("admin");//操作人
		
//		dataNodeService.doSaveNodeRelations(dCondition);
		//保存代理商，找到上级代理商或者渠道并保存，有多条
		
		//商户找到顶级并保存，有多条。
		//dataNodeService.deleteByCurrentNode(dCondition);
		//修改商户或者代理商的关系，删除节点表数据
	}
	
	@Test
	public void testMinus(){
		agentBaseService.updateUsedTimes("1001");
	}
	
	@Test
	public void testStep3(){
		//"bankName":bankName,"bankCard":bankCard,"mobile":phone,"validateCode":validateCode,"agree":agree,"bankCode":clearBankChannelNo
		MerchantBankcardCondition condition = new MerchantBankcardCondition();
		condition.setBankCard("6226097808098184");
		condition.setBankName("招商银行");
		condition.setMobile("18725984020");
		condition.setBankCode("200124587");
		condition.setMerchantNo("MC20161020135249");
		merchantBankcardService.applyStoreStep3(condition);
	}
	
	@Test
	public void testGet(){
		System.out.println(propConfigurer.getContextProperty("notify.url"));
	}
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	@Test 
	public void testSayHello() throws Exception {
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo("QDXX20161108000001");
		ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
		System.out.println(param.getWxParams());
		JSONObject js = JSONObject.fromObject(param.getWxParams());
		System.out.println(js.getString("appid"));
	}
	
	@Test 
	public void testSayHelloFrom() throws Exception {
		ApiMappingDicionCondition channelWxParamsCondition = new ApiMappingDicionCondition();
		channelWxParamsCondition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
		ApiMappingDicion param = apiMappingDicionService.getFromRedis(channelWxParamsCondition);
		System.out.println(param.getParaVal());
	}
	@Test
	public void testChannel(){
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		channelExpandCondition.setDomainName("wx.hfepay.cn");
		ChannelExpand ex = channelExpandService.findByCondition(channelExpandCondition);
		System.out.println(ex.getChannelCode());
	}
	
	@Test
	public void testPay(){
		//创建商户
		PlatformQrcodeCondition condition = new PlatformQrcodeCondition();
		condition.setChannelNo("QDXX20161128000004");//'QDXX20161128000003','QDXX20161128000002','QDXX20161128000004','QDXX20161128000001'
		List<PlatformQrcode> list = platformQrcodeService.findAll(condition);
		for (PlatformQrcode platformQrcode : list) {
			long resultCreate = channelAdminService.createMerchantByQrCode(platformQrcode.getQrCode(),platformQrcode.getQrCode());//创建商户
		}
		
		
		//支付订单
		PlatformQrcodeCondition condition = new PlatformQrcodeCondition();
		String channelNo = "QDXX20161128000004";//'QDXX20161128000003','QDXX20161128000002','QDXX20161128000004','QDXX20161128000001'
		String payCode="WXGZHZF";//WXGZHZF
		condition.setChannelNo(channelNo);
		List<PlatformQrcode> list = platformQrcodeService.findAll(condition);
		Random r = new Random();
		for (PlatformQrcode platformQrcode : list) {
			for (int i = 0; i < 20; i++) {
				OrderBo orderBo = new OrderBo();
				String qrCode = platformQrcode.getQrCode();
				orderBo.setChannelNo(platformQrcode.getChannelNo());
				orderBo.setAgentNo(platformQrcode.getAgentNo());
				orderBo.setMerchantNo(platformQrcode.getMerchantNo());
				orderBo.setQrCode(qrCode);
				orderBo.setPayCode(payCode);
				orderBo.setPrice(new BigDecimal(r.nextDouble()*r.nextInt(1000)).setScale(2, BigDecimal.ROUND_HALF_UP));
				scanCodeService.pay(orderBo);//支付
			}
		}
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setChannelNo(channelNo);
		orderPayCondition.setPayCode(payCode);
		 List<OrderPay> listp = orderPayService.findAll(orderPayCondition);
		 for (OrderPay orderPay : listp) {
			MerchantNotifyMessage bo = new MerchantNotifyMessage();
			bo.setUserOrderNo(orderPay.getTradeNo());
			bo.setStatus("02");
			bo.setOrderNo("101");//返回的值
			//bo.setErrorMsg("");
			try {
				callBackService.payCallBack(bo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//支付订单
		OrderBo orderBo = new OrderBo();
		String qrCode = "QRXX20161128000002";
		String payCode="ZFBSMZF";//WXGZHZF
		PlatformQrcode platformQrcode = scanCodeService.findByQrCode(qrCode);//主码支付
		orderBo.setChannelNo(platformQrcode.getChannelNo());
		orderBo.setAgentNo(platformQrcode.getAgentNo());
		orderBo.setMerchantNo(platformQrcode.getMerchantNo());
		orderBo.setQrCode(qrCode);
		orderBo.setPayCode(payCode);
		orderBo.setPrice(new BigDecimal(10));
		scanCodeService.pay(orderBo);//支付
		
		//回调
		MerchantNotifyMessage bo = new MerchantNotifyMessage();
		bo.setUserOrderNo("HF2016112800000053");
		bo.setStatus("02");
		bo.setOrderNo("101");//返回的值
		//bo.setErrorMsg("");
		try {
			callBackService.payCallBack(bo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//回调
	}
	
	@Test
	public void getRidom(){
		Random r = new Random();
		System.out.println(r.nextDouble()*r.nextInt(100));
	}
	@Test
	public void test(){
		merchantBankcardService.findByPhone("18725984020");
	}
}
*/