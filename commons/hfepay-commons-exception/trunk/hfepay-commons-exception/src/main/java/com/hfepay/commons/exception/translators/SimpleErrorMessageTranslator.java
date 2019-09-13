package com.hfepay.commons.exception.translators;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.io.Files;
import com.hfepay.commons.base.lang.Assert;
import com.hfepay.commons.base.lang.Objects;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Throwables;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.base.message.ComboMessageResource;
import com.hfepay.commons.base.message.MessageResource;
import com.hfepay.commons.base.message.SimpleMessageResource;
import com.hfepay.commons.exception.ErrorMessage;
import com.hfepay.commons.exception.ErrorMessageTranslator;
import com.hfepay.commons.exception.SimpleErrorMessage;
/**
 * 内部错误异常信息(jdk,spring等有关的异常)转换器
 * @author Sam
 *
 */
public class SimpleErrorMessageTranslator implements ErrorMessageTranslator  {
	
	protected ComboMessageResource errorMessageResource = new ComboMessageResource();
	
	protected Map<Class<?>,String> exceptionMappings ;
	
	protected Locale locale ;
	
	public SimpleErrorMessageTranslator(List<MessageResource> mrs, 
										Map<Class<? >,String> exceptionMappings,
										Locale locale) {
		this.errorMessageResource.setResources(mrs);
		this.exceptionMappings = exceptionMappings;
		this.locale = Objects.defaultIfNull(locale, Locale.CHINA);
	}
	
	void addMapping(String className,String code) {
		exceptionMappings.put(Reflector.forName(className).getWrapClass(), code);
	}
	
	public ErrorMessage translating(Throwable throwable) {
		Assert.notNull(throwable);
		Class<?  extends Throwable> throwableClass = throwable.getClass();
		String errorCode 	= exceptionMappings.get(throwableClass);
		if (Strings.isBlank(errorCode))
			return null;
		String errorMessage = errorMessageResource.getMessage(errorCode,locale);
		return new SimpleErrorMessage(errorCode,errorMessage,errorMessage,Throwables.getStackTrace(throwable));
	}
	
	public static SimpleErrorMessageTranslator create(String mappingPath,String...messagePaths) {
		return create(Locale.CHINA,mappingPath,messagePaths);
	}
	
	@SuppressWarnings("rawtypes")
	public static SimpleErrorMessageTranslator create(Locale locale,String mappingPath,String...messagePaths) {
		Assert.noneNull(mappingPath);
		Assert.noNullElements(messagePaths);
		Properties properties = Files.loadPropertiesFile(mappingPath);		
		
		Map<Class<?>,String> exceptionMappings = Maps.newMap();
		Set keys = properties.keySet();
		for ( Iterator iter = keys.iterator(); iter.hasNext();) {
			String exceptionClassName = iter.next().toString();
			String errorCode = properties.getProperty(exceptionClassName);
			exceptionMappings.put(Reflector.forName(exceptionClassName).getWrapClass(), errorCode);
		}
		
		Locale loca = Objects.defaultIfNull(locale, Locale.CHINA);
		List<MessageResource> resources = Lists.newList();
		for (String messagePath : messagePaths) {
			resources.add(new SimpleMessageResource(messagePath, loca));
		}
		
		return new SimpleErrorMessageTranslator(resources,exceptionMappings,loca);
		
	}

}
