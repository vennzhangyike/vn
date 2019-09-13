package com.hfepay.common.sms.services.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hfepay.common.sms.contants.ManDaoContants;
import com.hfepay.common.sms.mandao.Client;
import com.hfepay.common.sms.services.SmsService;
import com.hfepay.commons.base.collection.Lists;

public class ManDaoSmsServiceImpl implements SmsService {

	
//	public boolean sendSms(String content, String mobile) throws Exception {
//		if(StringUtils.isBlank(mobile)){
//			return false;
//		}
//		//输入软件序列号和密码
//		Client client=new Client(ManDaoContants.MANDAO_SN,ManDaoContants.MANDAO_PWD);
//		//我们的Demo最后是拼成xml了，所以要按照xml的语法来转义
//		if(content.indexOf("&")>=0) {
//			content = content.replace("&","&amp;");
//		}
//		if(content.indexOf("<")>=0) {
//			content = content.replace("<","&lt;");
//		}
//		if(content.indexOf(">")>=0) {
//			content = content.replace(">","&gt;");
//		}
//		//短信发送
//		//发送内容需要加上注册签名
//		StringBuffer message = new StringBuffer(content);
//		message.append(ManDaoContants.SIGN);
//		String result_mt = client.mt(mobile, message.toString(), "", "", "");
//		if(result_mt.startsWith("-")||result_mt.equals(""))//以负号判断是否发送成功
//		{
//			System.out.print("发送失败！返回值为："+result_mt+"。请查看webservice返回值对照表");
//			return false;
//		}
//		//输出返回标识，为小于19位的正数，String类型的，记录您发送的批次
//		else{
//			System.out.print("发送成功，返回值为："+result_mt);
//			return true;
//		}
//	}
//
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
//
//	@Override
//	public boolean sendSmsByCustom(String content, String mobile, String user, String password, String sendUrl)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
