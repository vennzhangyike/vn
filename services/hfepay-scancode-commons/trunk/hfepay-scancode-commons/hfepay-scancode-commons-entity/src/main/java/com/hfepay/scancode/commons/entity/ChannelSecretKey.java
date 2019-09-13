package com.hfepay.scancode.commons.entity;

import java.io.Serializable;
import java.util.Date;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
  @SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "ID"), 
  @SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "CHANNEL_NO"), 
  @SelectColumnMapping(property = "companyName", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "COMPANY_NAME"), 
  @SelectColumnMapping(property = "channelType", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "CHANNEL_TYPE"), 
  @SelectColumnMapping(property = "firstPayobj", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "FIRST_PAYOBJ"), 
  @SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "PAY_CODE"), 
  @SelectColumnMapping(property = "payName", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "PAY_NAME"), 
  @SelectColumnMapping(property = "boundIp", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "BOUND_IP"), 
  @SelectColumnMapping(property = "joinUserPublicKey", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "JOIN_USER_PUBLIC_KEY"), 
  @SelectColumnMapping(property = "joinPublicKey", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "JOIN_PUBLIC_KEY"), 
  @SelectColumnMapping(property = "joinPrivateKey", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "JOIN_PRIVATE_KEY"), 
  @SelectColumnMapping(property = "joinKey", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "JOIN_KEY"), 
  @SelectColumnMapping(property = "joinType", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "JOIN_TYPE"), 
  @SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "STATUS"), 
  @SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "RECORD_STATUS"), 
  @SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_secret_key", tableAlias = "A", column = "CREATE_TIME"), 
  @SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_secret_key", tableAlias = "A", column = "UPDATE_TIME"), 
  @SelectColumnMapping(property = "operatorId", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "OPERATOR_ID"), 
  @SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "REMARK"), 
  @SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "TEMP_1"), 
  @SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_channel_secret_key", tableAlias = "A", column = "TEMP_2")
})

    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
public class ChannelSecretKey implements IdEntity<String>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8736998149087967798L;

	/**
     * ID<br/>
     * 对应数据库字段 t_channel_secret_key.ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String id;

    /**
     * 渠道编号<br/>
     * 对应数据库字段 t_channel_secret_key.CHANNEL_NO
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String channelNo;

    /**
     * 公司名称<br/>
     * 对应数据库字段 t_channel_secret_key.COMPANY_NAME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String companyName;

    /**
     * 渠道类型<br/>
     * 对应数据库字段 t_channel_secret_key.CHANNEL_TYPE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String channelType;

    /**
     * 优选通道<br/>
     * 对应数据库字段 t_channel_secret_key.FIRST_PAYOBJ
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String firstPayobj;

    /**
     * 华付通道编号<br/>
     * 对应数据库字段 t_channel_secret_key.PAY_CODE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String payCode;

    /**
     * 华付通道名称<br/>
     * 对应数据库字段 t_channel_secret_key.PAY_NAME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String payName;

    /**
     * 绑定IP<br/>
     * 对应数据库字段 t_channel_secret_key.BOUND_IP
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String boundIp;

    /**
     * 用户上传公钥<br/>
     * 对应数据库字段 t_channel_secret_key.JOIN_USER_PUBLIC_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String joinUserPublicKey;

    /**
     * 平台公钥<br/>
     * 对应数据库字段 t_channel_secret_key.JOIN_PUBLIC_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String joinPublicKey;

    /**
     * 平台私钥<br/>
     * 对应数据库字段 t_channel_secret_key.JOIN_PRIVATE_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String joinPrivateKey;

    /**
     * 接入加解密KEY<br/>
     * 对应数据库字段 t_channel_secret_key.JOIN_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String joinKey;

    /**
     * 接入方式1：密文接入，2：明文接入<br/>
     * 对应数据库字段 t_channel_secret_key.JOIN_TYPE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String joinType;

    /**
     * 秘钥状态，1：有效，2：无效或禁止<br/>
     * 对应数据库字段 t_channel_secret_key.STATUS
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String status;

    /**
     * 记录状态<br/>
     * 对应数据库字段 t_channel_secret_key.RECORD_STATUS
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String recordStatus;

    /**
     * 创建日期<br/>
     * 对应数据库字段 t_channel_secret_key.CREATE_TIME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private Date createTime;

    /**
     * 修改日期<br/>
     * 对应数据库字段 t_channel_secret_key.UPDATE_TIME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private Date updateTime;

    /**
     * 操作人ID<br/>
     * 对应数据库字段 t_channel_secret_key.OPERATOR_ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String operatorId;

    /**
     * 备注<br/>
     * 对应数据库字段 t_channel_secret_key.REMARK
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String remark;

    /**
     * <br/>
     * 对应数据库字段 t_channel_secret_key.TEMP_1
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String temp1;

    /**
     * <br/>
     * 对应数据库字段 t_channel_secret_key.TEMP_2
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    private String temp2;


    /**
     * 返回: ID<br>
     * 对应数据库字段: t_channel_secret_key.ID
     *
     * @返回  t_channel_secret_key.ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getId() {
        return id;
    }

    /**
     *  设置: ID<br>
     * 对应数据库字段: t_channel_secret_key.ID
     *
     * @param id ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 返回: 渠道编号<br>
     * 对应数据库字段: t_channel_secret_key.CHANNEL_NO
     *
     * @返回  t_channel_secret_key.CHANNEL_NO
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getChannelNo() {
        return channelNo;
    }

    /**
     *  设置: 渠道编号<br>
     * 对应数据库字段: t_channel_secret_key.CHANNEL_NO
     *
     * @param channelNo 渠道编号
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

    /**
     * 返回: 公司名称<br>
     * 对应数据库字段: t_channel_secret_key.COMPANY_NAME
     *
     * @返回  t_channel_secret_key.COMPANY_NAME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getCompanyName() {
        return companyName;
    }

    /**
     *  设置: 公司名称<br>
     * 对应数据库字段: t_channel_secret_key.COMPANY_NAME
     *
     * @param companyName 公司名称
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 返回: 渠道类型<br>
     * 对应数据库字段: t_channel_secret_key.CHANNEL_TYPE
     *
     * @返回  t_channel_secret_key.CHANNEL_TYPE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getChannelType() {
        return channelType;
    }

    /**
     *  设置: 渠道类型<br>
     * 对应数据库字段: t_channel_secret_key.CHANNEL_TYPE
     *
     * @param channelType 渠道类型
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    /**
     * 返回: 优选通道<br>
     * 对应数据库字段: t_channel_secret_key.FIRST_PAYOBJ
     *
     * @返回  t_channel_secret_key.FIRST_PAYOBJ
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getFirstPayobj() {
        return firstPayobj;
    }

    /**
     *  设置: 优选通道<br>
     * 对应数据库字段: t_channel_secret_key.FIRST_PAYOBJ
     *
     * @param firstPayobj 优选通道
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setFirstPayobj(String firstPayobj) {
        this.firstPayobj = firstPayobj == null ? null : firstPayobj.trim();
    }

    /**
     * 返回: 华付通道编号<br>
     * 对应数据库字段: t_channel_secret_key.PAY_CODE
     *
     * @返回  t_channel_secret_key.PAY_CODE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getPayCode() {
        return payCode;
    }

    /**
     *  设置: 华付通道编号<br>
     * 对应数据库字段: t_channel_secret_key.PAY_CODE
     *
     * @param payCode 华付通道编号
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    /**
     * 返回: 华付通道名称<br>
     * 对应数据库字段: t_channel_secret_key.PAY_NAME
     *
     * @返回  t_channel_secret_key.PAY_NAME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getPayName() {
        return payName;
    }

    /**
     *  设置: 华付通道名称<br>
     * 对应数据库字段: t_channel_secret_key.PAY_NAME
     *
     * @param payName 华付通道名称
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    /**
     * 返回: 绑定IP<br>
     * 对应数据库字段: t_channel_secret_key.BOUND_IP
     *
     * @返回  t_channel_secret_key.BOUND_IP
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getBoundIp() {
        return boundIp;
    }

    /**
     *  设置: 绑定IP<br>
     * 对应数据库字段: t_channel_secret_key.BOUND_IP
     *
     * @param boundIp 绑定IP
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setBoundIp(String boundIp) {
        this.boundIp = boundIp == null ? null : boundIp.trim();
    }

    /**
     * 返回: 用户上传公钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_USER_PUBLIC_KEY
     *
     * @返回  t_channel_secret_key.JOIN_USER_PUBLIC_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getJoinUserPublicKey() {
        return joinUserPublicKey;
    }

    /**
     *  设置: 用户上传公钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_USER_PUBLIC_KEY
     *
     * @param joinUserPublicKey 用户上传公钥
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setJoinUserPublicKey(String joinUserPublicKey) {
        this.joinUserPublicKey = joinUserPublicKey == null ? null : joinUserPublicKey.trim();
    }

    /**
     * 返回: 平台公钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_PUBLIC_KEY
     *
     * @返回  t_channel_secret_key.JOIN_PUBLIC_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getJoinPublicKey() {
        return joinPublicKey;
    }

    /**
     *  设置: 平台公钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_PUBLIC_KEY
     *
     * @param joinPublicKey 平台公钥
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setJoinPublicKey(String joinPublicKey) {
        this.joinPublicKey = joinPublicKey == null ? null : joinPublicKey.trim();
    }

    /**
     * 返回: 平台私钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_PRIVATE_KEY
     *
     * @返回  t_channel_secret_key.JOIN_PRIVATE_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getJoinPrivateKey() {
        return joinPrivateKey;
    }

    /**
     *  设置: 平台私钥<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_PRIVATE_KEY
     *
     * @param joinPrivateKey 平台私钥
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setJoinPrivateKey(String joinPrivateKey) {
        this.joinPrivateKey = joinPrivateKey == null ? null : joinPrivateKey.trim();
    }

    /**
     * 返回: 接入加解密KEY<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_KEY
     *
     * @返回  t_channel_secret_key.JOIN_KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getJoinKey() {
        return joinKey;
    }

    /**
     *  设置: 接入加解密KEY<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_KEY
     *
     * @param joinKey 接入加解密KEY
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey == null ? null : joinKey.trim();
    }

    /**
     * 返回: 接入方式1：密文接入，2：明文接入<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_TYPE
     *
     * @返回  t_channel_secret_key.JOIN_TYPE
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getJoinType() {
        return joinType;
    }

    /**
     *  设置: 接入方式1：密文接入，2：明文接入<br>
     * 对应数据库字段: t_channel_secret_key.JOIN_TYPE
     *
     * @param joinType 接入方式1：密文接入，2：明文接入
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    /**
     * 返回: 秘钥状态，1：有效，2：无效或禁止<br>
     * 对应数据库字段: t_channel_secret_key.STATUS
     *
     * @返回  t_channel_secret_key.STATUS
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getStatus() {
        return status;
    }

    /**
     *  设置: 秘钥状态，1：有效，2：无效或禁止<br>
     * 对应数据库字段: t_channel_secret_key.STATUS
     *
     * @param status 秘钥状态，1：有效，2：无效或禁止
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 返回: 记录状态<br>
     * 对应数据库字段: t_channel_secret_key.RECORD_STATUS
     *
     * @返回  t_channel_secret_key.RECORD_STATUS
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     *  设置: 记录状态<br>
     * 对应数据库字段: t_channel_secret_key.RECORD_STATUS
     *
     * @param recordStatus 记录状态
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus == null ? null : recordStatus.trim();
    }

    /**
     * 返回: 创建日期<br>
     * 对应数据库字段: t_channel_secret_key.CREATE_TIME
     *
     * @返回  t_channel_secret_key.CREATE_TIME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *  设置: 创建日期<br>
     * 对应数据库字段: t_channel_secret_key.CREATE_TIME
     *
     * @param createTime 创建日期
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回: 修改日期<br>
     * 对应数据库字段: t_channel_secret_key.UPDATE_TIME
     *
     * @返回  t_channel_secret_key.UPDATE_TIME
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *  设置: 修改日期<br>
     * 对应数据库字段: t_channel_secret_key.UPDATE_TIME
     *
     * @param updateTime 修改日期
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回: 操作人ID<br>
     * 对应数据库字段: t_channel_secret_key.OPERATOR_ID
     *
     * @返回  t_channel_secret_key.OPERATOR_ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getOperatorId() {
        return operatorId;
    }

    /**
     *  设置: 操作人ID<br>
     * 对应数据库字段: t_channel_secret_key.OPERATOR_ID
     *
     * @param operatorId 操作人ID
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    /**
     * 返回: 备注<br>
     * 对应数据库字段: t_channel_secret_key.REMARK
     *
     * @返回  t_channel_secret_key.REMARK
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getRemark() {
        return remark;
    }

    /**
     *  设置: 备注<br>
     * 对应数据库字段: t_channel_secret_key.REMARK
     *
     * @param remark 备注
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 返回: <br>
     * 对应数据库字段: t_channel_secret_key.TEMP_1
     *
     * @返回  t_channel_secret_key.TEMP_1
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getTemp1() {
        return temp1;
    }

    /**
     *  设置: <br>
     * 对应数据库字段: t_channel_secret_key.TEMP_1
     *
     * @param temp1 
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setTemp1(String temp1) {
        this.temp1 = temp1 == null ? null : temp1.trim();
    }

    /**
     * 返回: <br>
     * 对应数据库字段: t_channel_secret_key.TEMP_2
     *
     * @返回  t_channel_secret_key.TEMP_2
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public String getTemp2() {
        return temp2;
    }

    /**
     *  设置: <br>
     * 对应数据库字段: t_channel_secret_key.TEMP_2
     *
     * @param temp2 
     *
     * @mbggenerated 2016-09-18 19:38
     */
    
    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
    public void setTemp2(String temp2) {
        this.temp2 = temp2 == null ? null : temp2.trim();
    }
}