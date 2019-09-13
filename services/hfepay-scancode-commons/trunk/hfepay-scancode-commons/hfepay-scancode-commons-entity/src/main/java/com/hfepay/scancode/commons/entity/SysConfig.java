package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "ID"), 
  @SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "NAME"), 
  @SelectColumnMapping(property = "keyNo", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "KEY_NO"), 
  @SelectColumnMapping(property = "paraName", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "PARA_NAME"), 
  @SelectColumnMapping(property = "paraVal", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "PARA_VAL"), 
  @SelectColumnMapping(property = "channelCode", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "channel_code"),
  @SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "RECORD_STATUS"), 
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_sys_config", tableAlias = "A", column = "CREATE_TIME"), 
  @SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_sys_config", tableAlias = "A", column = "UPDATE_TIME"), 
  @SelectColumnMapping(property = "operatorId", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "OPERATOR_ID"), 
  @SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_sys_config", tableAlias = "A", column = "REMARK")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
public class SysConfig implements  RecordStatus<Serializable>, IdEntity<String>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2971848954564728258L;

	/**
     * ID<br/>
     * 对应数据库字段 t_sys_config.ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String id;

    /**
     * 名称<br/>
     * 对应数据库字段 t_sys_config.NAME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String name;

    /**
     * KEY<br/>
     * 对应数据库字段 t_sys_config.KEY_NO
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String keyNo;

    /**
     * 参数名称<br/>
     * 对应数据库字段 t_sys_config.PARA_NAME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String paraName;

    /**
     * 参数值<br/>
     * 对应数据库字段 t_sys_config.PARA_VAL
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String paraVal;

    /**
     * 记录状态<br/>
     * 对应数据库字段 t_sys_config.RECORD_STATUS
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String recordStatus;

    /**
     * 创建时间<br/>
     * 对应数据库字段 t_sys_config.CREATE_TIME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private Date createTime;

    /**
     * 修改时间<br/>
     * 对应数据库字段 t_sys_config.UPDATE_TIME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private Date updateTime;

    /**
     * 操作人ID<br/>
     * 对应数据库字段 t_sys_config.OPERATOR_ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String operatorId;

    /**
     * 备注<br/>
     * 对应数据库字段 t_sys_config.REMARK
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    private String remark;

	private String channelCode;
	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

    /**
     * 返回: ID<br>
     * 对应数据库字段: t_sys_config.ID
     *
     * @返回  t_sys_config.ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getId() {
        return id;
    }

    /**
     *  设置: ID<br>
     * 对应数据库字段: t_sys_config.ID
     *
     * @param id ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 返回: 名称<br>
     * 对应数据库字段: t_sys_config.NAME
     *
     * @返回  t_sys_config.NAME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getName() {
        return name;
    }

    /**
     *  设置: 名称<br>
     * 对应数据库字段: t_sys_config.NAME
     *
     * @param name 名称
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 返回: KEY<br>
     * 对应数据库字段: t_sys_config.KEY_NO
     *
     * @返回  t_sys_config.KEY_NO
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getKeyNo() {
        return keyNo;
    }

    /**
     *  设置: KEY<br>
     * 对应数据库字段: t_sys_config.KEY_NO
     *
     * @param keyNo KEY
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo == null ? null : keyNo.trim();
    }

    /**
     * 返回: 参数名称<br>
     * 对应数据库字段: t_sys_config.PARA_NAME
     *
     * @返回  t_sys_config.PARA_NAME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getParaName() {
        return paraName;
    }

    /**
     *  设置: 参数名称<br>
     * 对应数据库字段: t_sys_config.PARA_NAME
     *
     * @param paraName 参数名称
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setParaName(String paraName) {
        this.paraName = paraName == null ? null : paraName.trim();
    }

    /**
     * 返回: 参数值<br>
     * 对应数据库字段: t_sys_config.PARA_VAL
     *
     * @返回  t_sys_config.PARA_VAL
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getParaVal() {
        return paraVal;
    }

    /**
     *  设置: 参数值<br>
     * 对应数据库字段: t_sys_config.PARA_VAL
     *
     * @param paraVal 参数值
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setParaVal(String paraVal) {
        this.paraVal = paraVal == null ? null : paraVal.trim();
    }

    /**
     * 返回: 记录状态<br>
     * 对应数据库字段: t_sys_config.RECORD_STATUS
     *
     * @返回  t_sys_config.RECORD_STATUS
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     *  设置: 记录状态<br>
     * 对应数据库字段: t_sys_config.RECORD_STATUS
     *
     * @param recordStatus 记录状态
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus == null ? null : recordStatus.trim();
    }

    /**
     * 返回: 创建时间<br>
     * 对应数据库字段: t_sys_config.CREATE_TIME
     *
     * @返回  t_sys_config.CREATE_TIME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建时间<br>
     * 对应数据库字段: t_sys_config.CREATE_TIME
     *
     * @param createTime 创建时间
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回: 修改时间<br>
     * 对应数据库字段: t_sys_config.UPDATE_TIME
     *
     * @返回  t_sys_config.UPDATE_TIME
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *  设置: 修改时间<br>
     * 对应数据库字段: t_sys_config.UPDATE_TIME
     *
     * @param updateTime 修改时间
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回: 操作人ID<br>
     * 对应数据库字段: t_sys_config.OPERATOR_ID
     *
     * @返回  t_sys_config.OPERATOR_ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getOperatorId() {
        return operatorId;
    }

    /**
     *  设置: 操作人ID<br>
     * 对应数据库字段: t_sys_config.OPERATOR_ID
     *
     * @param operatorId 操作人ID
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    /**
     * 返回: 备注<br>
     * 对应数据库字段: t_sys_config.REMARK
     *
     * @返回  t_sys_config.REMARK
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public String getRemark() {
        return remark;
    }

    /**
     *  设置: 备注<br>
     * 对应数据库字段: t_sys_config.REMARK
     *
     * @param remark 备注
     *
     * @mbggenerated 2016-04-21 18:07
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-21 18:32")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public Serializable getCreatorId() {
		return null;
	}

	@Override
	public void setCreatorId(Serializable id) {
	}

	@Override
	public Serializable getUpdaterId() {
		return null;
	}

	@Override
	public void setUpdaterId(Serializable id) {
	}

	@Override
	public Date getUpdateDate() {
		return null;
	}

	@Override
	public void setUpdateDate(Date date) {
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public void setCreateDate(Date date) {
	}

	@Override
	public Integer getUpdateCount() {
		return null;
	}

	@Override
	public void setUpdateCount(Integer updateCount) {
	}
}