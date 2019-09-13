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
public class LongIdEntity implements IdEntity<Long> {
	static final long serialVersionUID = 6680228309523656252L;
	protected Long id;
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
 
}
