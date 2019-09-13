package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.utils.PagingCondition;

public class SysRoleCondition  extends PagingCondition {

	private static final long serialVersionUID = -5861907863416024606L;
	
    private String id;
    private String roleName;
    private Date createTime;
	private String description;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	


}
