package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.scancode.service.order.ProfitService;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class DubboTest2 {
	
	@Autowired
	private ProfitService profitService;
		
	
	@Test
	public void getRidom(){
		profitService.doSaveProfit();
	}
	
	@Test
	public void showRedisRateDiff(){
		profitService.showRedisRateDiff();
	}
	
	@Test
	public void saveBakupPayways(){
		profitService.saveBakupPayways();
	}
}
