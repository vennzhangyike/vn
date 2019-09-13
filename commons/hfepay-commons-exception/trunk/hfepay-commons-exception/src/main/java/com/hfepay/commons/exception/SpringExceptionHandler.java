
package com.hfepay.commons.exception;

import java.util.List;
import java.util.Locale;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Objects;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Throwables;

/**
 * 异常处理类，主要功能：异常转换、错误处理、错误消息获取及定义。
 *  
 * @author Sam 
 */
public class SpringExceptionHandler extends ExceptionHandler {
     
	/**
     * 具体项目要添加进来的错误消息转换器
     */
    protected List<ErrorMessageTranslator> appendErrorTranslators ;   
    
    
    /**
     * 默认初始化构造函数
     */
    public SpringExceptionHandler() {
    	appendErrorTranslators = Lists.newList();    	
    }
    
    
    /**
     * 设置错误消息的本地化信息
     * 
     * @param locale
     */
    public void setLocale(String locale) {

        if (Strings.isNotEmpty(locale) && Strings.containsAny(locale, "_")) {
            String[] strs = locale.split("_");
            if (strs.length > 1) {
                this.locale = new Locale(strs[0], strs[1]);
            }
        }
    }
    
    public Locale getLocale() {
        return locale;
    }       
      

	@Override
	public ExceptionForward handle(Throwable ex,ExceptionForwardCreator creator) {	
		ErrorMessage error = null;
		for (Throwable th = ex;th != null; th = th.getCause()) {
	    	if (th instanceof ErrorMessage) {
	    		error = (ErrorMessage) th;
	    		break;
	    	}
	    	
	    	if (th instanceof ErrorMessageSupport) {
	    		ErrorMessageSupport ems = (ErrorMessageSupport)th;
	    		if (ems.hasErrorMessage()) {
	    			error = ems.getErrorMessage();
	    			break;
	    		}
	    	}
		}
		error = Objects.defaultIfNull(error, getErrorMessage(ex)); 
		ExceptionForwardCreator _creator = Objects.defaultIfNull(creator, ExceptionForwardCreator.DEFAULA_CREATOR); 
	    return _creator.create(error, ex);
	}
	 
	
	protected ErrorMessage getErrorMessage(Throwable th) {
		//从核心错误里面查找对应的错误信息
		ErrorMessage msg = innerErrorTranslator.translating(th);
		
		//从静态注册集里面找
		if (msg == null) {
			for (ErrorMessageTranslator et : jvmTranslators) {
				if (msg != null) break;
				msg = et.translating(th);
			}
		}
		
		//从spring注入的信息里面查找
		if (msg == null) {
			for (ErrorMessageTranslator et : appendErrorTranslators) {
				if (msg != null) break;
				msg = et.translating(th);
			}
		}
		
		////这时还为空的话，要么就是这个异常未在核心异常信息里面定义，要么就是自己抛出的一些Checked业务异常没有进行配置，我实在是无能为力了，你们看着办吧
		if (msg == null) {
			return new SimpleErrorMessage("00000017","网络异常","网络异常");//new SimpleErrorMessage("00000017",th.getMessage(),Throwables.getStackTrace(th));
		}
		
		return msg;		
		
	}
	
	  
}
