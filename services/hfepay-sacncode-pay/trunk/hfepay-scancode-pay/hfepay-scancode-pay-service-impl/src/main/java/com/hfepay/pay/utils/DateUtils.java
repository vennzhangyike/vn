package com.hfepay.pay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hfepay.commons.base.lang.Strings;

public abstract class DateUtils {
	
	/**
	 * 传入格式字符串，获取该月有多少天
	 * @param timeStr
	 * @return
	 */
	public static int getMonthDays(String timeStr,String fmt){
		int dayCount;
		Calendar cl=Calendar.getInstance();//实例化一个日历对象
		cl.setTime(getDateBystr(timeStr, fmt));
		dayCount=cl.getActualMaximum(Calendar.DAY_OF_MONTH);
		return dayCount;
	}
	
	/**
	 * 根据时间字符串获取时间
	 * @param timestr
	 * @param fmt
	 * @return
	 */
	public static Date getDateBystr(String timestr,String fmt){
		SimpleDateFormat format = new SimpleDateFormat(fmt);  
	    Date time = null;  
	    try {  
	        time = format.parse(timestr);  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }  
	    return time;
	}
	
	/**
	 * 时间转字符串
	 * @param timestr
	 * @param fmt
	 * @return
	 */
	public static  String formatDateFmt(Date time,String fmt){
		SimpleDateFormat format = new SimpleDateFormat(fmt);  
	    String timestr = null;  
	    timestr = format.format(time);  
	    return timestr;
	}
	
	/**
	 * 起始时间对应的周日到周六的时间段
	 * @return
	 */
	public static  List<String> getWeekDate(String date,String fmt){
		List<String> list = new ArrayList<>();
		Calendar cl=Calendar.getInstance();//实例化一个日历对象
		cl.setTime(getDateBystr(date, fmt));
		cl.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周日的日期
		String timeStrSunday = formatDateFmt(cl.getTime(), fmt);
		cl.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); //获取本周六的日期
		String timeStrSaturday = formatDateFmt(cl.getTime(), fmt);
		list.add(timeStrSunday);
		list.add(timeStrSaturday);
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
		String datestr = "2016-06-21";
		//SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd");
		System.out.println(getWeekDate(datestr, "yyyy-MM-dd"));
		List<String>list = new ArrayList<>(20);
		System.out.println(list.size());
	}
	
    /** 
     * 使用参数Format将字符串转为Date 
     */  
    public static Date parse(String strDate, String pattern)  
            throws ParseException  
    {  
        return Strings.isBlank(strDate) ? null : new SimpleDateFormat(  
                pattern).parse(strDate);  
    }

}
