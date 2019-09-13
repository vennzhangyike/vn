package com.hfepay.commons.base.lang;

import org.joda.time.DateTime;

/**
 * 基于JodaTime的日期时间处理封闭类
 * @author Sam
 *
 */
public class JodaTime {
	
	DateTime dateTime ;
	
	JodaTime(DateTime dt) {
		dateTime = dt;
	}
	
	//TODO 还有若干方法要想好才能加进来，或者会直接干掉此类
	
	public static JodaTime now() {
		return new JodaTime(DateTime.now());
	}
	
	public static JodaTime of (java.util.Date d) {
		DateTime dateTime = new DateTime(d);
		return new JodaTime(dateTime);
	}
	
	public DateTime getDateTime() {
		return dateTime;
	}
}
