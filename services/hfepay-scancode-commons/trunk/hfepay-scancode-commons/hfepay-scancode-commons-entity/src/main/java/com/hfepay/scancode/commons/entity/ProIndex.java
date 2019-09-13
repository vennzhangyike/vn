package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

import java.io.Serializable;
import java.util.Date;

@SelectColumnMappings({
  @SelectColumnMapping(property = "indexname", type = java.lang.String.class, table = "t_pro_index", tableAlias = "A", column = "IndexName"), 
  @SelectColumnMapping(property = "index32", type = java.lang.Integer.class, table = "t_pro_index", tableAlias = "A", column = "Index32"), 
  @SelectColumnMapping(property = "index64", type = java.lang.Long.class, table = "t_pro_index", tableAlias = "A", column = "Index64"), 
  @SelectColumnMapping(property = "comments", type = java.lang.String.class, table = "t_pro_index", tableAlias = "A", column = "Comments"), 
  @SelectColumnMapping(property = "createddtm", type = java.util.Date.class, table = "t_pro_index", tableAlias = "A", column = "CreatedDtm"), 
  @SelectColumnMapping(property = "lastuserid", type = java.lang.Long.class, table = "t_pro_index", tableAlias = "A", column = "LastUserID")
})

    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
public class ProIndex implements IdEntity<String>, Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5865781942025542256L;

	/**
     * 产生索引对应的表名<br/>
     * 对应数据库字段 pro_index.IndexName
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private String indexname;

    /**
     * 对应的Integer类型的唯一编号<br/>
     * 对应数据库字段 pro_index.Index32
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private Integer index32;

    /**
     * 对应的BigInt类型的唯一编号<br/>
     * 对应数据库字段 pro_index.Index64
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private Long index64;

    /**
     * 备注说明<br/>
     * 对应数据库字段 pro_index.Comments
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private String comments;

    /**
     * 记录生成的时间<br/>
     * 对应数据库字段 pro_index.CreatedDtm
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private Date createddtm;

    /**
     * 最后访问修改该记录的用户<br/>
     * 对应数据库字段 pro_index.LastUserID
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    private Long lastuserid;


    /**
     * 返回: 产生索引对应的表名<br>
     * 对应数据库字段: pro_index.IndexName
     *
     * @返回  pro_index.IndexName
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public String getIndexname() {
        return indexname;
    }

    /**
     *  设置: 产生索引对应的表名<br>
     * 对应数据库字段: pro_index.IndexName
     *
     * @param indexname 产生索引对应的表名
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setIndexname(String indexname) {
        this.indexname = indexname == null ? null : indexname.trim();
    }

    /**
     * 返回: 对应的Integer类型的唯一编号<br>
     * 对应数据库字段: pro_index.Index32
     *
     * @返回  pro_index.Index32
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public Integer getIndex32() {
        return index32;
    }

    /**
     *  设置: 对应的Integer类型的唯一编号<br>
     * 对应数据库字段: pro_index.Index32
     *
     * @param index32 对应的Integer类型的唯一编号
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setIndex32(Integer index32) {
        this.index32 = index32;
    }

    /**
     * 返回: 对应的BigInt类型的唯一编号<br>
     * 对应数据库字段: pro_index.Index64
     *
     * @返回  pro_index.Index64
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public Long getIndex64() {
        return index64;
    }

    /**
     *  设置: 对应的BigInt类型的唯一编号<br>
     * 对应数据库字段: pro_index.Index64
     *
     * @param index64 对应的BigInt类型的唯一编号
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setIndex64(Long index64) {
        this.index64 = index64;
    }

    /**
     * 返回: 备注说明<br>
     * 对应数据库字段: pro_index.Comments
     *
     * @返回  pro_index.Comments
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public String getComments() {
        return comments;
    }

    /**
     *  设置: 备注说明<br>
     * 对应数据库字段: pro_index.Comments
     *
     * @param comments 备注说明
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    /**
     * 返回: 记录生成的时间<br>
     * 对应数据库字段: pro_index.CreatedDtm
     *
     * @返回  pro_index.CreatedDtm
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public Date getCreateddtm() {
        return createddtm;
    }

    /**
     *  设置: 记录生成的时间<br>
     * 对应数据库字段: pro_index.CreatedDtm
     *
     * @param createddtm 记录生成的时间
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setCreateddtm(Date createddtm) {
        this.createddtm = createddtm;
    }

    /**
     * 返回: 最后访问修改该记录的用户<br>
     * 对应数据库字段: pro_index.LastUserID
     *
     * @返回  pro_index.LastUserID
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public Long getLastuserid() {
        return lastuserid;
    }

    /**
     *  设置: 最后访问修改该记录的用户<br>
     * 对应数据库字段: pro_index.LastUserID
     *
     * @param lastuserid 最后访问修改该记录的用户
     *
     * @mbggenerated 2016-04-22 14:39
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-04-22 14:09")
    public void setLastuserid(Long lastuserid) {
        this.lastuserid = lastuserid;
    }

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}