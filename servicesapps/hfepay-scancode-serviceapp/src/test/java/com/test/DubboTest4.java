package com.test;


import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.scancode.service.operator.MerchantActivityService;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class DubboTest4 {
	@Autowired
	private MerchantActivityService merchantActivityService;
	
	@Test
	public void calculateCheapCash(){
		String merchantNo = "FASMER20161026000023";
		BigDecimal payCash = new BigDecimal(10);
		System.out.println(merchantActivityService.calculateCheapCash(merchantNo, payCash,payCash));
	}
}
