package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext*.xml") // 加载配置
public class TestDubbo {
	
	@Test 
	public void testSayHello() {
		String bankcard="6226";
		int length = bankcard.length()>=10?10:bankcard.length();
		bankcard = bankcard.substring(0, length);
		System.out.println(bankcard);
		char[] arr = bankcard.toCharArray();
		List<String> list = new ArrayList<>();
		for(int i=2;i<=bankcard.length();i++){
			list.add(bankcard.substring(0, i));
		}
		System.out.println(list);
	}
	
}
