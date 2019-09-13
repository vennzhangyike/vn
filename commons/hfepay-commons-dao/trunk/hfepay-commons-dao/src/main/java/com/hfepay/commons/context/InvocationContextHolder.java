package com.hfepay.commons.context;
/**
 * 基于ThreadLocal实现的InvocationContext持有者
 * 
 * @author Sam
 *
 */
public class InvocationContextHolder {
	private static ThreadLocal<InvocationContext> cachedContext = new ThreadLocal<InvocationContext>();
	
	public static InvocationContext getInvocationContext() {
		return cachedContext.get();
	}
	
	static void setInvocationContext(InvocationContext invocationContext) {
		cachedContext.set(invocationContext);
	}
	
	/**
	 * remove context.
	 * 
	 * @see com.alibaba.dubbo.rpc.filter.ContextFilter
	 */
	public static void removeContext() {
		cachedContext.remove();
	}
}
