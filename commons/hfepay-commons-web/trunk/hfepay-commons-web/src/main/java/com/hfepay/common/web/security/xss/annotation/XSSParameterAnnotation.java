package com.hfepay.common.web.security.xss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface XSSParameterAnnotation {
	public static String JS = "js";
	public static String HTML = "html";
	public static String SQL = "sql";

	/**
	 * 
	 * types:防范类型，可取["js","html","sql"]. <br/>
	 * 
	 * @return
	 */
	String[] types() default {JS,HTML};

	/**
	 * 
	 * value:防范的目标输入参数名. <br/>
	 * 
	 * @return
	 */
	String value() default "";

}
