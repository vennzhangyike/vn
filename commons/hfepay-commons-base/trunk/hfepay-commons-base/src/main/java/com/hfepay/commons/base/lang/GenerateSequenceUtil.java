package com.hfepay.commons.base.lang;

import java.util.Date;

public class GenerateSequenceUtil {
 
	private static long tmpID = 0;
	 
	 private static boolean tmpIDlocked = false;
	 
	 public synchronized static String getBatchNo() {
		  long ltime = 0;
		  while (true) {
			   if (tmpIDlocked == false) {
				   tmpIDlocked = true;
				   ltime = Long.valueOf(Dates.format("yyMMddHHmmssSSS", new Date()).toString()) * 10000;
				   if (tmpID < ltime) {
				       tmpID = ltime;
				   } else {
					   tmpID = tmpID + 1;
					   ltime = tmpID;
				   }
				   tmpIDlocked = false;
				   return String.valueOf(ltime);
			   }
		  }
	 }
	 
	 public synchronized static String getBatchNo15() {
		  long ltime = Long.valueOf(Dates.format("yyMMddHHmmssSSS", new Date()).toString());
		  return String.valueOf(ltime);
	 }
	 
	 public static void main(String[] args) {
		 for (int i = 0; i < 100; i++) {
			 String num = GenerateSequenceUtil.getBatchNo();
			 System.out.println(num);
		}
	}
}
