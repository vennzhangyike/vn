package com.hfepay.scancode.service.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.scancode.commons.dao.ProIndexDAO;

/**
 * 生成产品的id
 * @author lenovo
 *
 */
@Component("scancodeOrderIDUtils")
public class OrderIDUtils {
	@Autowired
	private ProIndexDAO proIndexDAO;
	
	/**
	 * 
	 * @param indexName 索引名称
	 * @param type 索引类型：32位，64位
	 * @return
	 */
	public String getOrderID(String indexName,String type,String flag){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("indexType", type);
		map.put("indexName", indexName);
		proIndexDAO.getOrderId(map);
		String orderId = type.equals("0")?map.get("outIndex32").toString():map.get("outIndex64").toString();
		return findCode(orderId, flag);
	}
	
	/**
	 * 
	 * @param indexName 索引名称
	 * @param type 索引类型：32位，64位
	 * @return
	 */
	public String getRegisterID(String indexName,String type,String flag){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("indexType", type);
		map.put("indexName", indexName);
		proIndexDAO.getOrderId(map);
		String orderId = type.equals("0")?map.get("outIndex32").toString():map.get("outIndex64").toString();
		return findCodeEx(orderId, flag);
	}
	
	
	/**
	 * 获取OrderCode 
	 * 订单号 20130806000001 14位组成 前8位日期 后6位流水
	 * nowid 流水号
	 * flag 编号的开头标示，如支付订单flag=“ZF”
	 */
	private String findCode(String nowid,String flag){
		//获取今天的日期
		String now = Dates.format(Dates.DF.yyyyMMdd,new Date());
		//流水少于6位补0
		int idlength = nowid.length();
		if(idlength<6){
			for(int i=0;i<6-idlength;i++){
				nowid="0"+nowid;
			}
		}
		return flag + now + nowid;
	}
	
	/**
	 * 获取OrderCode 
	 * 订单号 20130806000001 14位组成 前8位日期 后6位流水
	 * nowid 流水号
	 * flag 编号的开头标示，如支付订单flag=“ZF”
	 */
	private String findCodeEx(String nowid,String flag){
		//获取今天的日期
		String now = Dates.format(Dates.DF.yyyyMMdd,new Date());
		//流水少于6位补0
		int idlength = nowid.length();
		if(idlength<4){
			for(int i=0;i<4-idlength;i++){
				nowid="0"+nowid;
			}
		}
		return flag + now + nowid;
	}
}
