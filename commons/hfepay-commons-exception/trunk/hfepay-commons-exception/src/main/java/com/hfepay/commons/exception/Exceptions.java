
package com.hfepay.commons.exception;

import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.utils.Springs;

/**
 * 异常处理工具类。
 * 
 * @author Sam
 * 
 */
public abstract class Exceptions {
	
    public static <T extends Exception> T makeCheckedException(Class<T> exceptionClass,ErrorMessage errorMessage) {
    	T t = Reflector.of(exceptionClass).newOne();
    	if (t instanceof ErrorMessageSupport)
    		((ErrorMessageSupport)t).setErrorMessage(errorMessage);
    	return t;
    }
    
    public static <T extends Exception> T makeCheckedException(Class<T> exceptionClass,String errorKey,String...args) {
    	String error = Springs.getMessage(errorKey, args);
    	return makeCheckedException(exceptionClass , new SimpleErrorMessage(errorKey,error,null));
    }
     
    public static <T extends RuntimeException> T makeUnCheckedException(Class<T> exceptionClass,ErrorMessage errorMessage) {
    	T t = Reflector.of(exceptionClass).newOne();
    	if (t instanceof ErrorMessageSupport)
    		((ErrorMessageSupport)t).setErrorMessage(errorMessage);
    	return t;
    }
    
    public static <T extends RuntimeException> T makeUnCheckedException(Class<T> exceptionClass,String errorKey,String...args) {
    	String error = Springs.getMessage(errorKey, args);
    	return makeUnCheckedException(exceptionClass , new SimpleErrorMessage(errorKey,error,null));
    }
    
    public static ApplicationException makeApplicationException(ErrorMessage errorMessage,Throwable th,String message) {
    	ApplicationException applicationException = new ApplicationException(message,th);
    	applicationException.setErrorMessage(errorMessage);
    	return applicationException;
    }
    
    public static ServicesException makeServiceException(ErrorMessage errorMessage,Throwable th,String message) {
    	ServicesException servicesException = new ServicesException(message,th);
    	servicesException.setErrorMessage(errorMessage);
    	return servicesException; 
    }
    
    public static ServicesException makeServiceException(Throwable th,String message,String errorKey,String...args) {
    	String error = Springs.getMessage(errorKey, args);
    	ServicesException servicesException = new ServicesException(message,th);
    	servicesException.setErrorMessage(new SimpleErrorMessage(errorKey,error,null));
    	return servicesException; 
    }
    
    public static ServicesException makeServiceException(String errorKey,String...args) {
    	String error = Springs.getMessage(errorKey, args);
    	ServicesException servicesException = new ServicesException();
    	servicesException.setErrorMessage(new SimpleErrorMessage(errorKey,error,null));
    	return servicesException; 
    }
    
    public static ApplicationException makeApplicationException(String errorKey,String...args) {
    	String error = Springs.getMessage(errorKey, args);
    	ApplicationException servicesException = new ApplicationException();
    	servicesException.setErrorMessage(new SimpleErrorMessage(errorKey,error,null));
    	return servicesException; 
    }
     
}
