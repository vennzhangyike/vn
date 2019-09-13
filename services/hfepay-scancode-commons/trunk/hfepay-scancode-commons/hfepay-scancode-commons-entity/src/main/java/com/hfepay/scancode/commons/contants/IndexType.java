package com.hfepay.scancode.commons.contants;

/**
 * 交易订单类型
 * @author tanbiao
 *
 */
public enum IndexType {
	Index32("0"),//返回值为int
	Index64("1");//返回值为bigint
	private String type;

	IndexType(String type){
		this.type = type;
	}
	
	public String value(){
		return type;
	}
}
