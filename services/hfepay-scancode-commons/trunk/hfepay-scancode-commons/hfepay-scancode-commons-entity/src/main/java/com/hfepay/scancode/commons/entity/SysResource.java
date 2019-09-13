package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_sys_resource", tableAlias = "A", column = "id"), 
  @SelectColumnMapping(property = "menuName", type = java.lang.String.class, table = "t_sys_resource", tableAlias = "A", column = "menu_name"), 
  @SelectColumnMapping(property = "parentId", type = java.lang.String.class, table = "t_sys_resource", tableAlias = "A", column = "parent_id"), 
  @SelectColumnMapping(property = "sort", type = java.lang.Byte.class, table = "t_sys_resource", tableAlias = "A", column = "sort"), 
  @SelectColumnMapping(property = "icon", type = java.lang.String.class, table = "t_sys_resource", tableAlias = "A", column = "icon"), 
  @SelectColumnMapping(property = "url", type = java.lang.String.class, table = "t_sys_resource", tableAlias = "A", column = "url"), 
  @SelectColumnMapping(property = "level", type = java.lang.Byte.class, table = "t_sys_resource", tableAlias = "A", column = "level"), 
  @SelectColumnMapping(property = "status", type = java.lang.Boolean.class, table = "t_sys_resource", tableAlias = "A", column = "status"), 
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_sys_resource", tableAlias = "A", column = "create_time")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class SysResource implements Serializable, IdEntity<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6079733742686282319L;

	/**
     * 主键id<br/>
     * 对应数据库字段 sys_resource.id
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String id;

    /**
     * 资源名称<br/>
     * 对应数据库字段 sys_resource.menu_name
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String menuName;

    /**
     * 资源父节点ID<br/>
     * 对应数据库字段 sys_resource.parent_id
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String parentId;

    /**
     * 排序<br/>
     * 对应数据库字段 sys_resource.sort
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Byte sort;

    /**
     * 资源小图标<br/>
     * 对应数据库字段 sys_resource.icon
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String icon;

    /**
     * 资源url<br/>
     * 对应数据库字段 sys_resource.url
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private String url;

    /**
     * 资源等级(一级菜单，二级菜单)<br/>
     * 对应数据库字段 sys_resource.level
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Byte level;

    /**
     * 资源是否删除(0删除，1可用)<br/>
     * 对应数据库字段 sys_resource.status
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Boolean status;

    /**
     * 创建时间<br/>
     * 对应数据库字段 sys_resource.create_time
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    private Date createTime;


    private String hasPerssion;
    /**
     * 返回: 主键id<br>
     * 对应数据库字段: sys_resource.id
     *
     * @返回  sys_resource.id
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getId() {
        return id;
    }

    /**
     *  设置: 主键id<br>
     * 对应数据库字段: sys_resource.id
     *
     * @param id 主键id
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 返回: 资源名称<br>
     * 对应数据库字段: sys_resource.menu_name
     *
     * @返回  sys_resource.menu_name
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getMenuName() {
        return menuName;
    }

    /**
     *  设置: 资源名称<br>
     * 对应数据库字段: sys_resource.menu_name
     *
     * @param menuName 资源名称
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 返回: 资源父节点ID<br>
     * 对应数据库字段: sys_resource.parent_id
     *
     * @返回  sys_resource.parent_id
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getParentId() {
        return parentId;
    }

    /**
     *  设置: 资源父节点ID<br>
     * 对应数据库字段: sys_resource.parent_id
     *
     * @param parentId 资源父节点ID
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * 返回: 排序<br>
     * 对应数据库字段: sys_resource.sort
     *
     * @返回  sys_resource.sort
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Byte getSort() {
        return sort;
    }

    /**
     *  设置: 排序<br>
     * 对应数据库字段: sys_resource.sort
     *
     * @param sort 排序
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setSort(Byte sort) {
        this.sort = sort;
    }

    /**
     * 返回: 资源小图标<br>
     * 对应数据库字段: sys_resource.icon
     *
     * @返回  sys_resource.icon
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getIcon() {
        return icon;
    }

    /**
     *  设置: 资源小图标<br>
     * 对应数据库字段: sys_resource.icon
     *
     * @param icon 资源小图标
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 返回: 资源url<br>
     * 对应数据库字段: sys_resource.url
     *
     * @返回  sys_resource.url
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public String getUrl() {
        return url;
    }

    /**
     *  设置: 资源url<br>
     * 对应数据库字段: sys_resource.url
     *
     * @param url 资源url
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 返回: 资源等级(一级菜单，二级菜单)<br>
     * 对应数据库字段: sys_resource.level
     *
     * @返回  sys_resource.level
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Byte getLevel() {
        return level;
    }

    /**
     *  设置: 资源等级(一级菜单，二级菜单)<br>
     * 对应数据库字段: sys_resource.level
     *
     * @param level 资源等级(一级菜单，二级菜单)
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * 返回: 资源是否删除(0删除，1可用)<br>
     * 对应数据库字段: sys_resource.status
     *
     * @返回  sys_resource.status
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Boolean getStatus() {
        return status;
    }

    /**
     *  设置: 资源是否删除(0删除，1可用)<br>
     * 对应数据库字段: sys_resource.status
     *
     * @param status 资源是否删除(0删除，1可用)
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 返回: 创建时间<br>
     * 对应数据库字段: sys_resource.create_time
     *
     * @返回  sys_resource.create_time
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建时间<br>
     * 对应数据库字段: sys_resource.create_time
     *
     * @param createTime 创建时间
     *
     * @mbggenerated 2016-04-14 09:25
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getHasPerssion() {
		return hasPerssion;
	}

	public void setHasPerssion(String hasPerssion) {
		this.hasPerssion = hasPerssion;
	}
    
    
}