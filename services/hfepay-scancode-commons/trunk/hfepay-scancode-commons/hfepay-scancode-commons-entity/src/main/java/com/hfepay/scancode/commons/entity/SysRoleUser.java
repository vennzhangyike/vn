package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_sys_role_user", tableAlias = "A", column = "id"), 
  @SelectColumnMapping(property = "roleId", type = java.lang.String.class, table = "t_sys_role_user", tableAlias = "A", column = "role_id"), 
  @SelectColumnMapping(property = "accountId", type = java.lang.String.class, table = "t_sys_role_user", tableAlias = "A", column = "account_id"), 
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_sys_role_user", tableAlias = "A", column = "create_time")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
public class SysRoleUser implements Serializable, IdEntity<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5004665800851469203L;

	/**
     * 主键id<br/>
     * 对应数据库字段 sys_role_user.id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    private String id;

    /**
     * 角色ID<br/>
     * 对应数据库字段 sys_role_user.role_id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    private String roleId;

    /**
     * 用户ID<br/>
     * 对应数据库字段 sys_role_user.account_id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    private String accountId;

    /**
     * 创建时间<br/>
     * 对应数据库字段 sys_role_user.create_time
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    private Date createTime;


    /**
     * 返回: 主键id<br>
     * 对应数据库字段: sys_role_user.id
     *
     * @返回  sys_role_user.id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public String getId() {
        return id;
    }

    /**
     *  设置: 主键id<br>
     * 对应数据库字段: sys_role_user.id
     *
     * @param id 主键id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 返回: 角色ID<br>
     * 对应数据库字段: sys_role_user.role_id
     *
     * @返回  sys_role_user.role_id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public String getRoleId() {
        return roleId;
    }

    /**
     *  设置: 角色ID<br>
     * 对应数据库字段: sys_role_user.role_id
     *
     * @param roleId 角色ID
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * 返回: 用户ID<br>
     * 对应数据库字段: sys_role_user.account_id
     *
     * @返回  sys_role_user.account_id
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public String getAccountId() {
        return accountId;
    }

    /**
     *  设置: 用户ID<br>
     * 对应数据库字段: sys_role_user.account_id
     *
     * @param accountId 用户ID
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 返回: 创建时间<br>
     * 对应数据库字段: sys_role_user.create_time
     *
     * @返回  sys_role_user.create_time
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建时间<br>
     * 对应数据库字段: sys_role_user.create_time
     *
     * @param createTime 创建时间
     *
     * @mbggenerated 2016-04-14 09:58
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}