package com.hfepay.commons.exception;

/**
 * 错误信息翻译器，将抛出的任何异常转化成理错误信息对象
 * @author Sam
 *
 */
public interface ErrorMessageTranslator  {

	ErrorMessage translating(Throwable throwable); 
}
