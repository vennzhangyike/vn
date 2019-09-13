package com.hfepay.commons.exception;

/**
 * 如果是web层抛出的的异常，还可以定义一个转发的页面
 * @author Sam
 *
 */
public interface WebFlow {
	public String getForward();
	public boolean isRedirect();
}
