package com.hfepay.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.entity.annotations.EntityTableName;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@EntityTableName("test")
@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.Long.class, table = "test", tableAlias = "A", column = "ID"), 
  @SelectColumnMapping(property = "name", type = java.lang.String.class, table = "test", tableAlias = "A", column = "NAME")
  })
public class DemoEntity implements IdEntity<Long>, RecordStatus<Long>, Serializable {
	private static final long serialVersionUID = 7082496288545269370L;
	
	private Long id;
    private String name;
	private String recordStatus;
    private Integer updateCount;
    private Long creatorId;
    private Date createDate;
    private Long updaterId;
    private Date updateDate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
    	this.id = id;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
