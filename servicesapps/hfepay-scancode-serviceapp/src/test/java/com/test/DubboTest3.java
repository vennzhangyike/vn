package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class DubboTest3 {
	@Autowired
	private HierarchicalSettlementTotalService hierarchicalSettlementTotalService;
	@Test
	public void test(){
		hierarchicalSettlementTotalService.saveSummaryProfit("2016-12-06");
	}
}
