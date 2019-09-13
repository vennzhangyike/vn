package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.MessagePushRuleService;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class ZJLTest {
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private MessagePushRuleService messagePushRuleService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	
	@Test
	public void messagePushRuleService(){
		messagePushRuleService.messagePush();
	}
	
	@Test
	public void channelExpandService(){
		ChannelExpand entity;//就算有两条数据也会取一条而不报错
		entity = channelExpandService.findByChannelNo(null);
		entity = channelExpandService.findByChannelNo("");
		entity = channelExpandService.findByChannelNo("QDXX20161108000001");
		
		entity = channelExpandService.findByChannelCode(null);
		entity = channelExpandService.findByChannelCode("");
		entity = channelExpandService.findByChannelCode("zhangsan");
		
		System.out.println("OK");
	}
	
	@Test
	public void channelAdminService(){
		ChannelAdmin entity;//就算有两条数据也会取一条而不报错
		entity = channelAdminService.findByChannelNo(null);
		entity = channelAdminService.findByChannelNo("");
		entity = channelAdminService.findByChannelNo("QDXX20161108000001");
		
		entity = channelAdminService.findByMerchantNo(null);
		entity = channelAdminService.findByMerchantNo("");
		entity = channelAdminService.findByMerchantNo("HFZFMER20170117000005");
		
		System.out.println("OK");
	}
	
	@Test
	public void channelBaseService(){
		ChannelBase entity;//就算有两条数据也会取一条而不报错
		entity = channelBaseService.findByChannelNo(null);
		entity = channelBaseService.findByChannelNo("");
		entity = channelBaseService.findByChannelNo("QDXX20161108000001");
		
		System.out.println("OK");
	}
	@Test
	public void channelWxParamsService(){
		ChannelWxParams entity;//就算有两条数据也会取一条而不报错
		entity = channelWxParamsService.findByChannelNo(null);
		entity = channelWxParamsService.findByChannelNo("");
		entity = channelWxParamsService.findByChannelNo("QDXX20161108000001");
		
		System.out.println("OK");
	}
	
	@Test
	public void merchantStoreService(){
		MerchantStore entity;//就算有两条数据也会取一条而不报错
		
		try {
//			entity = merchantStoreService.findByMerchantNo(null);
//			entity = merchantStoreService.findByMerchantNo("");
			entity = merchantStoreService.findByMerchantNo("HFZFMER20170117000005");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("OK");
	}
}
