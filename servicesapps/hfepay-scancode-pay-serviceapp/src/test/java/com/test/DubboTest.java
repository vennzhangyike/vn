package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.scancode.commons.entity.MerchantInfo;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class DubboTest {
	@Autowired
	private PayCallBackOperatorService callBackOperatorService;
	
	@Test
	public void testPage(){
		MerchantInfo info = callBackOperatorService.findByMerchantNo("QWERMER20170210000005");
	}
	
	
}
