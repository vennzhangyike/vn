package com.hfepay.scancode.bean;

import java.io.Serializable;

/**
 * Created by Kevin on 2016/9/12 0012.
 */
public abstract class Message<T> implements Serializable {

	private static final long serialVersionUID = -8890697819315014352L;
	private HeaderMessage head;

	public HeaderMessage getHead() {
		return head;
	}

	public void setHead(HeaderMessage head) {
		this.head = head;
	}

//	public abstract T getBody();
//
//	public abstract  void setBody(T T);
}
