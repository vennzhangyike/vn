/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.hfepay.commons.entity;

import java.io.Serializable;

/**
 * ID实体接口。
 * 
 * @author Sam
 * 
 * @param <ID>
 *            实体的ID类型
 */
public interface IdEntity<ID extends Serializable> extends Serializable {

	public abstract void setId(ID id);

	public abstract ID getId();

}
