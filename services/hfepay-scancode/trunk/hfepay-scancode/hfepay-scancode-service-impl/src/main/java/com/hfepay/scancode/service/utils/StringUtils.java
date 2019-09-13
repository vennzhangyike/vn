package com.hfepay.scancode.service.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hfepay.commons.base.lang.Dates;

public class StringUtils {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

	public static String getRandom(){
		StringBuffer authCode = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int j = (int) (Math.random()*10);
			authCode.append(j);
		}
		return authCode.toString();
	}
	
	public static String getMerchantNo(){
		String dateStr = Dates.getYyyyMMddHHmmss(new Date());
		return "MC"+dateStr;
	}
	
	public static String getStoreNo(){
		String dateStr = Dates.getYyyyMMddHHmmss(new Date());
		return "ST"+dateStr;
	}
	
	public static String getMonthFirstDay(){
		//获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);
        return first;
	}
	
	public static String getMonthLastDay(){
		 //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        System.out.println("===============last:"+last);
        return last;
	}
	
	public static String getNextMonthFirstDay(){
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置Calendar月份数为下一个月  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);  
		// 设置Calendar日期为下一个月一号  
		calendar.set(Calendar.DATE, 1);  
		return format.format(calendar.getTime());
		//System.out.println("====="+); 
	}
	
	public static void main(String[] args) {
		System.out.println(getMerchantNo());
	}
}
