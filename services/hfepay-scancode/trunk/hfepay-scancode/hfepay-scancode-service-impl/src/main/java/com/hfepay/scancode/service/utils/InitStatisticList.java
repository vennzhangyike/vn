package com.hfepay.scancode.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitStatisticList {
	public final static List<String> yearList = new ArrayList<>(12);//年
	public final static List<String> dayList = new ArrayList<>(24);//天
	public final static List<String> weekList = new ArrayList<>(Arrays.asList(new String[]{"周日","周一","周二","周三","周四","周五","周六"}));//周
	public final static List<String> monthList = new ArrayList<>(31);//月
	
	public static List<String> initDataList = new ArrayList<>(31);//纵坐标初始化容量初始值为0
	
	//初始化横坐标，年=12月，周=7天 ，日=24时 ，月=31天
	static {
		for (int i = 1; i <= 12; i++) {
			yearList.add(i+"月");
		}
		
		for (int i = 0; i < 24; i++) {
			dayList.add(i+"时");
		}
		
		/*for (int i = 1; i <= 7; i++) {
			weekList.add("周"+i);
		}*/
		for (int i = 1; i <= 31; i++) {
			monthList.add(""+i);
			initDataList.add("0");
		}
		
	}
}
