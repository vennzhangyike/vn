package com.hfepay.commons.exception;

import java.util.List;
import java.util.Locale;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.exception.translators.SimpleErrorMessageTranslator;

/**
 * 异常处理器，对抛出的任何异常进行的一个处理，处理后会返回一个ExceptionForward，应用程序
 * 可以通过对象进行再一步的处理，此类通常会置于webapp的ioc环境中
 * @author Sam
 *
 */
public abstract class ExceptionHandler {

	/**
	 * 处理异常
	 * 
	 * @param e 程序及框架抛出的任何异常
	 * @param creator 异常转发器的创建器，因为不知道具体的应用怎么处理，所以这个留给他们去实现吧，当然我会给一些常用业务场景的转发器的：）
	 * @return
	 */
	public abstract ExceptionForward handle(Throwable e,ExceptionForwardCreator creator); 
	
	/**
     * 消息类型
     */
    protected Locale locale = Locale.CHINA;
    
    /**
     * 内部错误消息转化器对象
     */
    protected ErrorMessageTranslator innerErrorTranslator;
        
     
    /**
     * 默认构造函数
     */
    public ExceptionHandler() {
    	innerErrorTranslator = SimpleErrorMessageTranslator.create(Locale.CHINA, "/com/hfepay/common/exception/exception-mapping.properties", "com.hfepay.common.exception.msg.error");
    }
	
	/**
	 * 注册扩展的异常信息翻译器，用于具体的项目的一些公用模块可以定义自己的异常信息翻译
	 * @param errTranslator
	 */
	public static void registerTranslator(ErrorMessageTranslator errTranslator) {
		jvmTranslators.add(errTranslator);
    }		
	
	//基于jvm级别存储的各个模块加载进来的业务异常映射信息
	static final List<ErrorMessageTranslator> jvmTranslators = Lists.newList();

}