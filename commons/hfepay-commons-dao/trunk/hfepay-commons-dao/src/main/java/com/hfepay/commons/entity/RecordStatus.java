package com.hfepay.commons.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录状态接口信息，用于描述更新记录时有些默认还要增加一些记录的状态，如：记录创建者、
 * 记录更新者、创建时间、更新时间等。
 * 在DAO接口持久时应根据此状态去更新DB中记录相应的状态。
 * @author Sam
 *
 */
public interface RecordStatus<ID extends Serializable>{
  public static final String ACTIVE = "A";
  public static final String INACTIVE = "I";
  public static final String LOCKED = "L";
  
  /**
   * 记录的状态
   * @return
   */
  public String getRecordStatus();
  public void setRecordStatus(String recordStatus);
	/**
	 * 记录的创建者
	 * @return
	 */
  	ID getCreatorId();
	
	void setCreatorId(ID id);
	
	/**
	 * 记录的最后一次修改的更新者
	 * @return
	 */
	ID getUpdaterId();
	
	void setUpdaterId(ID id);
	
	/**
	 * 记录的最后一次更新时间
	 * @return
	 */
	Date getUpdateDate();
	
	void setUpdateDate(Date date);
	
	/**
	 * 记录的创建时间
	 * @return
	 */
	Date getCreateDate();
	
	void setCreateDate(Date date);
	/**
	 * 记录修改的次数
	 * @return
	 */
	Integer getUpdateCount();
	void setUpdateCount(Integer updateCount);
	
}
