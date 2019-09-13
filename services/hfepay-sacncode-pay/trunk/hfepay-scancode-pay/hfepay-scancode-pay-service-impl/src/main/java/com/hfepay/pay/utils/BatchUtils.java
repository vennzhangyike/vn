package com.hfepay.pay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class BatchUtils {

	public static String createBatchNo(){
		StringBuffer buf = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
		String nowDate = df.format(new Date());
		buf.append(nowDate);
		
		Random ra =new Random();
		buf.append(ra.nextInt(10000));
		return buf.toString();
	}
}
