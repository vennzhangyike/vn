/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.hfepay.commons.entity;


/**
 * 以Long为ID类型的实体基类。
 * 
 * @author Sam
 * 
 */
public class StringIdEntity implements IdEntity<String> {
	static final long serialVersionUID = 6680228309523656252L;
	protected String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
 
}
