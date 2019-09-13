package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "id"), 
  @SelectColumnMapping(property = "userName", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "user_name"), 
  @SelectColumnMapping(property = "password", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "password"), 
  @SelectColumnMapping(property = "salt", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "salt"), 
  @SelectColumnMapping(property = "email", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "email"), 
  @SelectColumnMapping(property = "phone", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "phone"), 
  @SelectColumnMapping(property = "shortName", type = java.lang.String.class, table = "t_admin", tableAlias = "A", column = "short_Name"), 
  @SelectColumnMapping(property = "status", type = java.lang.Integer.class, table = "t_admin", tableAlias = "A", column = "status"), 
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_admin", tableAlias = "A", column = "create_time")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class Admin  implements Serializable, IdEntity<String>{

	private static final long serialVersionUID = 5327000106265000684L;

	/**
     * 主键id<br/>
     * 对应数据库字段 t_admin.id
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String id;

    /**
     * 用户名<br/>
     * 对应数据库字段 t_admin.user_name
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String userName;

    /**
     * 密码<br/>
     * 对应数据库字段 t_admin.password
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String password;

    /**
     * 加密盐<br/>
     * 对应数据库字段 t_admin.salt
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String salt;

    /**
     * 状态(0禁用,1可用)<br/>
     * 对应数据库字段 t_admin.status
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Integer status;

    /**
     * 创建时间<br/>
     * 对应数据库字段 t_admin.create_time
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Date createTime;


    private Set<String> roles;
    
    private String email;
    private String phone;
    private String shortName;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
     * 返回: 主键id<br>
     * 对应数据库字段: t_admin.id
     *
     * @返回  t_admin.id
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getId() {
        return id;
    }

    /**
     *  设置: 主键id<br>
     * 对应数据库字段: t_admin.id
     *
     * @param id 主键id
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 返回: 用户名<br>
     * 对应数据库字段: t_admin.user_name
     *
     * @返回  t_admin.user_name
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getUserName() {
        return userName;
    }

    /**
     *  设置: 用户名<br>
     * 对应数据库字段: t_admin.user_name
     *
     * @param userName 用户名
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 返回: 密码<br>
     * 对应数据库字段: t_admin.password
     *
     * @返回  t_admin.password
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getPassword() {
        return password;
    }

    /**
     *  设置: 密码<br>
     * 对应数据库字段: t_admin.password
     *
     * @param password 密码
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 返回: 加密盐<br>
     * 对应数据库字段: t_admin.salt
     *
     * @返回  t_admin.salt
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getSalt() {
        return salt;
    }

    /**
     *  设置: 加密盐<br>
     * 对应数据库字段: t_admin.salt
     *
     * @param salt 加密盐
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 返回: 状态(0禁用,1可用)<br>
     * 对应数据库字段: t_admin.status
     *
     * @返回  t_admin.status
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Integer getStatus() {
        return status;
    }

    /**
     *  设置: 状态(0禁用,1可用)<br>
     * 对应数据库字段: t_admin.status
     *
     * @param status 状态(0禁用,1可用)
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 返回: 创建时间<br>
     * 对应数据库字段: t_admin.create_time
     *
     * @返回  t_admin.create_time
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建时间<br>
     * 对应数据库字段: t_admin.create_time
     *
     * @param createTime 创建时间
     *
     * @mbggenerated 2016-04-14 09:02
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getCredentialsSalt(){
    	return userName+salt;
    }
    
    public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}