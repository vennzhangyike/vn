package com.test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		//获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);
        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        System.out.println("===============last:"+last+" 23:59:59");
        test();*/
		/*// 得到本地的缺省格式
		DecimalFormat df1 = new DecimalFormat("####.000");
		System.out.println(df1.format(1234.56));
		// 得到德国的格式
		//Locale.setDefault(Locale.GERMAN);
		DecimalFormat df2 = new DecimalFormat("####.000");
		System.out.println(df2.format(1234.56));
		
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher("5626262625");
		System.out.println(m.matches());*/
		Random r = new Random();
		System.out.println(new BigDecimal(r.nextDouble()*r.nextInt(1000)).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	static void test(){
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置Calendar月份数为下一个月  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);  
		// 设置Calendar日期为下一个月一号  
		calendar.set(Calendar.DATE, 1);  
		// 打印  
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		System.out.println("====="+format.format(calendar.getTime()));  
	}

}
