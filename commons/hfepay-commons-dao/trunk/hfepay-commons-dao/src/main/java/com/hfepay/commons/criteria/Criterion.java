package com.hfepay.commons.criteria;


/**
 * 子条件，一个子条件表示一个不可分割的条件，如下列条件都表示为不可分割的子条件：
 * <pre>
 * name = value;
 * name in (vlaue1,value2,value3);
 * name between start and end;
 * </pre>
 * @author Sam
 *
 */
public interface Criterion extends Condition {
	
	
	/**
	 * 获取此过滤条件的过滤字段名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获取此条件的操作运算符
	 */
	Operator getOperator();
	
	/**
	 * 获取此条件的过滤值
	 */
	Object getValue();
	
	/**
	 * 获取此条件的值的类型
	 * @return
	 */
	Class<?> getValueType();
	
	/**
	 * 将这个条件拼成一个表达式
	 * @return
	 */
	String getExpression();
	
	/**
	 * 将这个条件拼成一个表达式，但是不加表别名
	 * @return
	 */
	String getExpressionNoalias();
	
	boolean isBetween();
	
	boolean isList();
	
	boolean isSingle();
	
	boolean isNone(); 
	
	boolean isBitOperation();
	
	
}
