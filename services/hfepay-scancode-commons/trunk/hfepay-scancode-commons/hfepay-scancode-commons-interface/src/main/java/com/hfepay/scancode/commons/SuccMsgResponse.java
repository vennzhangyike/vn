package com.hfepay.scancode.commons;

import com.hfepay.scancode.bean.ResponseHeader;

/**
 * Created by Kevin on 2016/9/9 0009.
 */
public class SuccMsgResponse extends MsgResponse {
	
	private static final long serialVersionUID = 8620812823439101414L;
	
	/**
	 * body
	 */
    private String body;
    
//    public SuccMsgResponse(HeaderMessage message, String respCode, String respDesc, String respNo) {
//		super(message, respCode, respDesc, respNo);
//	}
    
    public SuccMsgResponse(ResponseHeader header, String body){
    	this.setHead(header);
    	this.body = body;
    }
    
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
