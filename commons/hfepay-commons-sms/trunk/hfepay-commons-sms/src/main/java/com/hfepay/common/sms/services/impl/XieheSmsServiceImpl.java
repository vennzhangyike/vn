package com.hfepay.common.sms.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hfepay.common.sms.services.SmsService;
import com.hfepay.commons.base.collection.Lists;

public class XieheSmsServiceImpl implements SmsService{

//	@Override
//	public boolean sendSms(String content, String mobile) throws Exception {
//		if(StringUtils.isBlank(mobile)){
//			return false;
//		}
//		XieheClient client = new DefaultXieheClient();
//		SendMessageRequest req = new SendMessageRequest();
//		List<String> mobiles = new ArrayList<String>();
//		mobiles.add(mobile);
//		req.setContent(content);
//		req.setExpandno("1");
//		req.setMobiles(mobiles);
//		SendMessageResponse res =  client.execute(req);
//		System.out.println("发送短信，是否成功："+res.isSuccess());
//		return res.isSuccess();
//	}
//	
//	@Override
//	public boolean sendSmsByCustom(String content, String mobile,String user,String password,String sendUrl) throws Exception {
//		if(StringUtils.isBlank(mobile)){
//			return false;
//		}
//		
//		Config config = new Config();
//		config.setAccount(user);
//		config.setPassword(password);  
//		config.setSendUrl(sendUrl);
//		
//		XieheClient client = new DefaultXieheClient(config);
//		SendMessageRequest req = new SendMessageRequest();
//		List<String> mobiles = new ArrayList<String>();
//		mobiles.add(mobile);
//		req.setContent(content);
//		req.setExpandno("1");
//		req.setMobiles(mobiles);
//		
//		SendMessageResponse res =  client.execute(req);
//		System.out.println("发送短信，是否成功："+res.isSuccess());
//		return res.isSuccess();
//	}
//
//	@Override
//	public boolean sendBatchSms(List<Map<String, String>> sms) throws Exception {
//		if(Lists.isEmpty(sms)){
//			return false;
//		}
//		for (Map<String, String> map : sms) {
//			Set<String> mobiles = map.keySet();
//			if(mobiles != null){
//				Iterator<String> iterator = mobiles.iterator();
//				while(iterator.hasNext()) {
//					String mobile = iterator.next();
//					String content = map.get(mobile);
//					sendSms(content, mobile);
//				}
//			}
//		}
//		return true;
//	}

}
