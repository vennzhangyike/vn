package com.hfepay.commons.criteria.impl;

import java.util.Map;

import com.hfepay.commons.criteria.Criterion;
import com.hfepay.commons.criteria.Logic;
import com.hfepay.commons.criteria.Operator;
/**
 * 
 * @author Sam
 *
 */
public class DefaultCriterion extends AbstractCondition implements Criterion {
 
	protected String name;
	protected Operator operator;
	protected Object value;
	protected Class<?> valueType;
	protected Column column;
	
	public DefaultCriterion(){}
	
	public DefaultCriterion(Logic logic, String name, Operator operator, Object value,Class<?> valueType) { 
		this.logic = logic;
		this.name = name;
		this.operator = operator;
		this.value = value;
		this.valueType = valueType;
	}
	
	public DefaultCriterion(Logic logic, String name, Operator operator, Object value,Class<?> valueType,Map<String, Object> attributes) { 
		this.logic = logic;
		this.name = name;
		this.operator = operator;
		this.value = value;
		this.valueType = valueType;
		this.attributes = attributes;
	}
	
	public Logic getLogic() {
		return logic;
	}
	public void setLogic(Logic logic) {
		this.logic = logic;
	}
	public String getName() {
		if (column != null) {
			return column.getConditionName();
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public boolean isGroup() { 
		return false;
	}

	public Class<?> getValueType() {
		return valueType;
	}

	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}

	public boolean isBetween() {
		return operator.isBetween();
	}

	public boolean isList() {
		return operator.isIn();
	}

	public boolean isSingle() {
		return operator.isSingle();
	}
	
	public boolean isNone() {
		return operator.isNone();
	}

	public void setColumn(Column column) {
		this.column = column;
	}
	
	public Column getColumn() {
		return this.column;
	}

	public String getExpression() {
		String selectColumn = name;
		if (column != null) {
			selectColumn = column.getConditionName();
		}
		return String.format(" %s %s %s ",logic,selectColumn,operator);
	}
	
	public String getExpressionNoalias(){
		String selectColumn = name;
		if (column != null) {
			selectColumn = column.getName();
		}
		return String.format(" %s %s %s ",logic,selectColumn,operator);
	}

	public boolean isBitOperation() {
		return operator.isBitOr() || operator.isBitAnd();
	}

	 

	 

	

	

}
