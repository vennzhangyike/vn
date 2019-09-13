package com.hfepay.scancode.bean;

import java.io.Serializable;

/**
 * Created by Kevin on 2016/9/13 0013.
 */
public abstract class ResponseMessage<T> implements Serializable {
	private static final long serialVersionUID = 238032038699187311L;
	private ResponseHeader head;
	
	
    public ResponseHeader getHead() {
        return head;
    }

    public void setHead(ResponseHeader head) {
        this.head = head;
    }

	public abstract T getBody();

	public abstract void setBody(T t) ;
    
}
