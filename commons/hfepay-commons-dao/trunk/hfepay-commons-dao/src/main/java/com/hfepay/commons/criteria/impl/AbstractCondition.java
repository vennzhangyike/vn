package com.hfepay.commons.criteria.impl;

import java.util.Map;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.criteria.Condition;
import com.hfepay.commons.criteria.Logic;


public abstract class AbstractCondition implements Condition{
	protected Logic logic;
	
	protected Map<String, Object> attributes = Maps.newMap();

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(String attrName,Object attrValue) {
		attributes.put(attrName, attrValue);
	}
	
	public Object getAttribute(String attrName) {
		return attributes.get(attrName);
	}

	public Logic getLogic() {
		return logic;
	}

	public void setLogic(Logic logic) {
		this.logic = logic;
	}
}
