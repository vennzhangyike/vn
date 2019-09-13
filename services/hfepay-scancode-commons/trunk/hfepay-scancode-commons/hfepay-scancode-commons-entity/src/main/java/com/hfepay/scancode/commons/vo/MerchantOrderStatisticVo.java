/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import java.io.Serializable;
import java.util.List;

import com.hfepay.commons.base.annotation.Generated;

@Generated("2016-06-15 17:52:56")
public class MerchantOrderStatisticVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> orderPaySuccessAliData;//支付成功数据支付宝
	private List<String> orderPaySuccessWechatData;//支付成功数据微信
	private List<String> orderPaySuccessQQData;//支付成功数据QQ
	private List<String> orderPayFailAliData;//支付失败数据支付宝
	private List<String> orderPayFailWechatData;//支付失败数据微信
	private List<String> orderPayFailQQData;//支付失败数据QQ
	private List<String> hCoordinateList;//横坐标
	private String title;//图标名称
	
	public List<String> getOrderPaySuccessAliData() {
		return orderPaySuccessAliData;
	}
	public void setOrderPaySuccessAliData(List<String> orderPaySuccessAliData) {
		this.orderPaySuccessAliData = orderPaySuccessAliData;
	}
	public List<String> getOrderPaySuccessWechatData() {
		return orderPaySuccessWechatData;
	}
	public void setOrderPaySuccessWechatData(List<String> orderPaySuccessWechatData) {
		this.orderPaySuccessWechatData = orderPaySuccessWechatData;
	}
	public List<String> getOrderPayFailAliData() {
		return orderPayFailAliData;
	}
	public void setOrderPayFailAliData(List<String> orderPayFailAliData) {
		this.orderPayFailAliData = orderPayFailAliData;
	}
	public List<String> getOrderPayFailWechatData() {
		return orderPayFailWechatData;
	}
	public void setOrderPayFailWechatData(List<String> orderPayFailWechatData) {
		this.orderPayFailWechatData = orderPayFailWechatData;
	}
	public List<String> gethCoordinateList() {
		return hCoordinateList;
	}
	public void sethCoordinateList(List<String> hCoordinateList) {
		this.hCoordinateList = hCoordinateList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getOrderPaySuccessQQData() {
		return orderPaySuccessQQData;
	}
	public void setOrderPaySuccessQQData(List<String> orderPaySuccessQQData) {
		this.orderPaySuccessQQData = orderPaySuccessQQData;
	}
	public List<String> getOrderPayFailQQData() {
		return orderPayFailQQData;
	}
	public void setOrderPayFailQQData(List<String> orderPayFailQQData) {
		this.orderPayFailQQData = orderPayFailQQData;
	}
	
}

