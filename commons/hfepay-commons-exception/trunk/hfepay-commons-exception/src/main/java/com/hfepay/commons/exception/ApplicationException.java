
package com.hfepay.commons.exception;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.hfepay.commons.base.lang.Objects;

/**
 * 定义一个基于UncheckedException的基类异常，对于大多数据的应用程序都可以使用此类作为异常信息来处理。
 *  
 * @author Sam
 */
public class ApplicationException extends UncheckedException {
    
    private static final long serialVersionUID = -2394147719359810950L;
 
    public ApplicationException(){}
 
    public ApplicationException(Throwable e) {
        super(e);
    }    
 
    public ApplicationException(String msg, Throwable e) {
        super(msg, e);
    }
    
    public ApplicationException(String msg) {
        super(msg);
    }    
     
    public String toString() {
        return Objects.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }    
      
}
