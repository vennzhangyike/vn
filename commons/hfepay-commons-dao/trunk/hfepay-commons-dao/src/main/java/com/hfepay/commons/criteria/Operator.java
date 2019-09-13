package com.hfepay.commons.criteria;

import com.hfepay.commons.utils.Comparator;

/**
 * 条件比较运算符
 * 
 * @author Sam
 *
 */
public enum Operator  {
	
	NOT_BETWEEN(" NOT BETWEEN "),
	BETWEEN(" BETWEEN "),
	IN(" IN "),
	NOT_IN(" NOT IN "),
	NOT(" NOT "),
	NOT_EQ(" <> "),
	EQ(" = "),
	GT(" > "),	
	LT(" < "),
	LE(" <= "),
	GE(" >= "),
	LIKE(" LIKE "),
	IS_NOT_NULL(" IS NOT NULL "),
	IS_NULL(" IS NULL "),
	BIT_EQ(" & "), 
	BIT_OR(" | ");
	
	
	private Operator(String value) {
		this.operator = value;
	}
	
	private String operator;
	
	public String toString() {
		return operator;
	}
	
	public boolean isBetween() {
		return this == NOT_BETWEEN || this == BETWEEN;
	}
	
	public boolean isIn() {
		return this == IN || this == NOT_IN;
	}
	
	public boolean isNone() {
		return this == IS_NOT_NULL || this == IS_NULL;
	}
	
	public boolean isBitOr() {
		return this == BIT_OR;
	}
	
	public boolean isBitAnd() {
		return this == BIT_EQ;
	}
	
	public boolean isSingle() {
		return !isIn() && !isBetween() && !isNone();
	}
	
	public static Operator valueOf(Comparator c) {
		if (c == Comparator.EQ)
			return Operator.EQ;
		else if (c == Comparator.GE)
			return Operator.GE;
		else if (c == Comparator.GT)
			return Operator.GT;
		else if (c == Comparator.LE)
			return Operator.LE;
		else if (c == Comparator.LT)
			return Operator.LT;
		else
			return Operator.LIKE;
	}
	
	
}
