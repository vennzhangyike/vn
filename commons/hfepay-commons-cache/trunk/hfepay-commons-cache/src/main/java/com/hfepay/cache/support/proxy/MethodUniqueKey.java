package com.hfepay.cache.support.proxy;

import java.io.Serializable;

/**   
* @Title: MethodUniqueKey.java 
* @Package cn.hfepay.cache.support.proxy 
* @Description: TODO
* @author maozk
* @date 2016年3月21日 上午11:04:16 
*/
@SuppressWarnings("all")
public class MethodUniqueKey implements Serializable{

	private static final long serialVersionUID = -1531193336077139344L;

	String className;
	
	String methodName;
	
	Class[] paramTypes;
	
	Object[] params;

	public MethodUniqueKey(String className, String methodName, Class[] paramTypes, Object[] params) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
		this.params = params;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MethodUniqueKey)){
			return false;
		}
		MethodUniqueKey compare = (MethodUniqueKey)obj;
		if(!this.getClassName().equals(compare.getClassName())){
			return false;
		}
		if(!this.getMethodName().equals(compare.getMethodName())){
			return false;
		}
		
		return true;
	}
	
	
}
