/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "qrCode", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "qr_code"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "goodsName", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "goods_name"),
	@SelectColumnMapping(property = "goodsDesc", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "goods_desc"),
	@SelectColumnMapping(property = "isDefault", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "is_default"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_qrcode_goods", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_qrcode_goods", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_qrcode_goods", tableAlias = "A", column = "remark")
})

@Generated("2016-10-25 17:49:38")
public class QrcodeGoods implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键
	private String qrCode;//二维码编号
	private String merchantNo;//商户id
	private String goodsName;//商品名称
	private String goodsDesc;//商品描述
	private String isDefault;//是否默认
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setQrCode(String value) {
		this.qrCode = value;
	}
	
	public String getQrCode() {
		return this.qrCode;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setGoodsName(String value) {
		this.goodsName = value;
	}
	
	public String getGoodsName() {
		return this.goodsName;
	}
	public void setGoodsDesc(String value) {
		this.goodsDesc = value;
	}
	
	public String getGoodsDesc() {
		return this.goodsDesc;
	}
	public void setIsDefault(String value) {
		this.isDefault = value;
	}
	
	public String getIsDefault() {
		return this.isDefault;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

}

