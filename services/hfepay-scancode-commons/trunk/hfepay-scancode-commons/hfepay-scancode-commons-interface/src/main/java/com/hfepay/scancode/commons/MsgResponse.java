package com.hfepay.scancode.commons;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.hfepay.scancode.bean.HeaderMessage;
import com.hfepay.scancode.bean.ResponseHeader;
import com.hfepay.commons.base.lang.Strings;

/**
 * Created by Kevin on 2016/9/9 0009.
 */
public class MsgResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5802751432865911745L;
	
	private ResponseHeader head;

	public ResponseHeader getHead() {
		return head;
	}

	public void setHead(ResponseHeader head) {
		this.head = head;
	}
	
	public MsgResponse(){}
	
	public MsgResponse(HeaderMessage message, String respCode, String respDesc){
		if(head == null)
			head = new ResponseHeader();
		BeanUtils.copyProperties(message, head);
		head.setEncryptKey("");
		head.setSign("");
		if(Strings.isNotBlank(respCode)){
			head.setRespCode(respCode);
			head.setRespDesc(respDesc);
//			head.setRespTime(Dates.getYyyyMMddHHmmss(new Date()));
//			head.setRespNo(respNo);
		}
	}
}
