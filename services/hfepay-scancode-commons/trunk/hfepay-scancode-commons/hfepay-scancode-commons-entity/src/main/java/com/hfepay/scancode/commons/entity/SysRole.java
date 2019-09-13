package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_sys_role", tableAlias = "A", column = "id"), 
  @SelectColumnMapping(property = "roleName", type = java.lang.String.class, table = "t_sys_role", tableAlias = "A", column = "role_name"), 
  @SelectColumnMapping(property = "description", type = java.lang.String.class, table = "t_sys_role", tableAlias = "A", column = "description"),
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_sys_role", tableAlias = "A", column = "create_time")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
public class SysRole implements Serializable, IdEntity<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7294640357885156227L;

	/**
     * 主键id<br/>
     * 对应数据库字段 sys_role.id
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    private String id;

    /**
     * 角色名称<br/>
     * 对应数据库字段 sys_role.role_name
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    private String roleName;

    /**
     * 角色描述<br/>
     * 对应数据库字段 sys_role.description
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    private String description;

    /**
     * 创建时间<br/>
     * 对应数据库字段 sys_role.create_time
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    private Date createTime;

    /**
     * 返回: 主键id<br>
     * 对应数据库字段: sys_role.id
     *
     * @返回  sys_role.id
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public String getId() {
        return id;
    }

    /**
     *  设置: 主键id<br>
     * 对应数据库字段: sys_role.id
     *
     * @param id 主键id
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 返回: 角色名称<br>
     * 对应数据库字段: sys_role.role_name
     *
     * @返回  sys_role.role_name
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public String getRoleName() {
        return roleName;
    }

    /**
     *  设置: 角色名称<br>
     * 对应数据库字段: sys_role.role_name
     *
     * @param roleName 角色名称
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 返回: 角色描述<br>
     * 对应数据库字段: sys_role.description
     *
     * @返回  sys_role.description
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public String getDescription() {
        return description;
    }

    /**
     *  设置: 角色描述<br>
     * 对应数据库字段: sys_role.description
     *
     * @param description 角色描述
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 返回: 创建时间<br>
     * 对应数据库字段: sys_role.create_time
     *
     * @返回  sys_role.create_time
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建时间<br>
     * 对应数据库字段: sys_role.create_time
     *
     * @param createTime 创建时间
     *
     * @mbggenerated 2016-04-14 09:27
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:52")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}