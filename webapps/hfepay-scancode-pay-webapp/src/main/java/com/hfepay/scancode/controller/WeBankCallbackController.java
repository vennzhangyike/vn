package com.hfepay.scancode.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hfepay.scancode.api.condition.WebankOrderCondition;
import com.hfepay.scancode.api.entity.WebankOrder;
import com.hfepay.scancode.api.service.WebankOrderService;
import com.hfepay.scancode.api.webank.entity.vo.WeBankAlipayNotifyVo;
import com.hfepay.scancode.api.webank.service.WeBankMerchantBusinessService;
import com.hfepay.scancode.api.webank.sign.Signature;

import net.sf.json.JSONObject;

/**
 * 回调
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/webank/callback")
public class WeBankCallbackController {
	
	public static final Logger log = LoggerFactory.getLogger(WeBankCallbackController.class);
	
	@Autowired
	private WebankOrderService webankOrderService;
	
	@Autowired
	private WeBankMerchantBusinessService weBankMerchantBusinessService;
	/**
	 * 支付回调
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/zfb")
	public void zfbPayCallBack(HttpServletRequest request,HttpServletResponse response){
		//回调实体类
		WeBankAlipayNotifyVo notifyVO = new WeBankAlipayNotifyVo();
		BufferedReader in = null;
		JSONObject dataJson = new JSONObject();
		try {
			// 获取请求流
			ServletInputStream sis = request.getInputStream();
			in = new BufferedReader(new InputStreamReader(sis,"UTF-8"));
			String line;
			if ((line = in.readLine()) != null) {
				JSONObject jsonObject = JSONObject.fromObject(line);
				dataJson = JSONObject.fromObject(jsonObject.get("data"));
				notifyVO = (WeBankAlipayNotifyVo) JSONObject.toBean(dataJson, WeBankAlipayNotifyVo.class);
				log.info("########回调参数：####"+notifyVO.toMap().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("######获取回调参数失败######");
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 获取微众请求参数
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		log.info("#######微众支付宝回调请求参数：#######" + params.toString());
		//验证签名
		log.info("######开始验证签名：#####");
		String ticket = "";
		if(params.get("sign").substring(0, 6).equals("PLEASE")){
			log.info("######ticket已过期，请重新获取：#####");
			ticket = weBankMerchantBusinessService.getWeBankTicket();
			return;
		}
		log.info("######从缓存中获取ticket#####");
		ticket = weBankMerchantBusinessService.getWeBankTicket();
		ArrayList<String> values = new ArrayList<String>();
		values.add(params.get("timestamp"));
		values.add(params.get("nonce_str"));// 32位随机串
		values.add(ticket);// ticket
		values.add(dataJson.toString());// 接口参数 json类型
		String signstr = Signature.sign(values);// 签名
		log.info("######签名结果：#####"+signstr);
		boolean isSign = false;
		if(params.get("sign").equals(signstr)) {
			isSign = true;
			log.info("######签名验证成功：#####");
    	}
		if(isSign){
			if(notifyVO != null){
				WebankOrder webankOrder = webankOrderService.findByTradeNo(notifyVO.getOrderId());
				if(webankOrder != null){
					String tradeStatus = webankOrder.getTradeStatus();
					String notifyTradeStatus = notifyVO.getTradeStatus();
					if(!tradeStatus.equals(notifyTradeStatus)){
						WebankOrderCondition webankOrderCondition = new WebankOrderCondition();
						BeanUtils.copyProperties(webankOrder, webankOrderCondition);
						webankOrderCondition.setTradeStatus(notifyTradeStatus);
						webankOrderService.update(webankOrderCondition);
						log.info("######订单状态修改成功#####");
					}
				}else{
					throw new RuntimeException("订单信息不存在！");
				}
			}
		}else{
			throw new RuntimeException("签名验证失败！");
		}
	}
}
