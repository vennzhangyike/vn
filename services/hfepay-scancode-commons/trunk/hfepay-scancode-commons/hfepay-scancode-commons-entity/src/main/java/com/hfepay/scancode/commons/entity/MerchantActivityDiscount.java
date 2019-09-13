/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_activity_discount", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "activityId", type = java.lang.String.class, table = "t_merchant_activity_discount", tableAlias = "A", column = "ACTIVITY_ID"),
	@SelectColumnMapping(property = "activityDiscount", type = java.lang.String.class, table = "t_merchant_activity_discount", tableAlias = "A", column = "ACTIVITY_DISCOUNT"),
	@SelectColumnMapping(property = "activityCondition", type = java.lang.String.class, table = "t_merchant_activity_discount", tableAlias = "A", column = "ACTIVITY_CONDITION"),
	@SelectColumnMapping(property = "chance", type = java.lang.String.class, table = "t_merchant_activity_discount", tableAlias = "A", column = "CHANCE")
})

@Generated("2017-02-14 10:46:19")
public class MerchantActivityDiscount implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String activityId;//活动ID
	private String activityDiscount;//活动优惠
	private String activityCondition;//活动条件
	private String chance;//中奖概率

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setActivityId(String value) {
		this.activityId = value;
	}
	
	public String getActivityId() {
		return this.activityId;
	}
	public void setActivityDiscount(String value) {
		this.activityDiscount = value;
	}
	
	public String getActivityDiscount() {
		return this.activityDiscount;
	}
	public void setActivityCondition(String value) {
		this.activityCondition = value;
	}
	
	public String getActivityCondition() {
		return this.activityCondition;
	}
	public void setChance(String value) {
		this.chance = value;
	}
	
	public String getChance() {
		return this.chance;
	}

}

