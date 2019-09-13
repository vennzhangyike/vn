package com.hfepay.commons.base.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期处理工具类，提供对日期的计算、格式、解析等工具方法
 * 
 * @author Sam
 *
 */
public class Dates extends DateUtils {
	/**
     * 判断一年是否为闰年，如果给定年份小于1全部为 false
     * 
     * @param year
     *            年份，比如 2012 就是二零一二年
     * @return 给定年份是否是闰年
     */
    public static boolean isLeapYear(int year) {
        if (year < 4)
            return false;
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 判断某年（不包括自己）之前有多少个闰年
     * 
     * @param year
     *            年份，比如 2012 就是二零一二年
     * @return 闰年的个数
     */
    public static int countLeapYear(int year) {
        // 因为要计算年份到公元元年（0001年）的年份跨度，所以减去1
        int span = year - 1;
        return (span / 4) - (span / 100) + (span / 400);
    }

    /**
     * 将一个秒数（天中），转换成一个如下格式的数组:
     * 
     * <pre>
     * [0-23][0-59[-059]
     * </pre>
     * 
     * @param sec
     *            秒数
     * @return 时分秒的数组
     */
    public static int[] secondToInts(int sec) {
        int[] re = new int[3];
        re[0] = Math.min(23, sec / 3600);
        re[1] = Math.min(59, (sec - (re[0] * 3600)) / 60);
        re[2] = Math.min(59, sec - (re[0] * 3600) - (re[1] * 60));
        return re;
    }

    /**
     * 将一个时间字符串，转换成一个一天中的绝对秒数
     * 
     * @param ts
     *            时间字符串，符合格式 "HH:mm:ss"
     * @return 一天中的绝对秒数
     */
    public static int toSecond(String ts) {
        String[] tss = Strings.splitIgnoreBlank(ts, ":");
        if (null != tss && tss.length == 3) {
            int hh = Integer.parseInt(tss[0]);
            int mm = Integer.parseInt(tss[1]);
            int ss = Integer.parseInt(tss[2]);
            return hh * 3600 + mm * 60 + ss;
        }
        throw Throwables.makeThrow("Wrong format of time string '%s'", ts);
    }

    /**
     * 返回服务器当前时间
     * 
     * @return 服务器当前时间
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    private static Pattern _P_TIME = Pattern.compile("^((\\d{2,4})([/\\\\-])(\\d{1,2})([/\\\\-])(\\d{1,2}))?"
                                                     + "(([ T])?"
                                                     + "(\\d{1,2})(:)(\\d{1,2})(:)(\\d{1,2})"
                                                     + "(([.])"
                                                     + "(\\d{1,}))?)?"
                                                     + "(([+-])(\\d{1,2})(:\\d{1,2})?)?"
                                                     + "$");

    /**
     * 根据默认时区计算时间字符串的绝对毫秒数
     * 
     * @param ds
     *            时间字符串
     * @return 绝对毫秒数
     * 
     * @see #ams(String, TimeZone)
     */
    public static long ams(String ds) {
        return ams(ds, null);
    }

    /**
     * 根据字符串得到相对于 "UTC 1970-01-01 00:00:00" 的绝对毫秒数。
     * 本函数假想给定的时间字符串是本地时间。所以计算出来结果后，还需要减去时差
     * 
     * 支持的时间格式字符串为:
     * 
     * <pre>
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * yy-MM-dd HH:mm:ss;
     * yy-MM-dd HH:mm:ss.SSS;
     * yyyy-MM-dd;
     * yy-MM-dd;
     * HH:mm:ss;
     * HH:mm:ss.SSS;
     * </pre>
     * 
     * @param ds
     *            时间字符串
     * @param tz
     *            你给定的时间字符串是属于哪个时区的
     * @return 时间
     */
    public static long ams(String ds, TimeZone tz) {
        Matcher m = _P_TIME.matcher(ds);
        if (m.find()) {
            int yy = _int(m, 2, 1970);
            int MM = _int(m, 4, 1);
            int dd = _int(m, 6, 1);

            int HH = _int(m, 9, 0);
            int mm = _int(m, 11, 0);
            int ss = _int(m, 13, 0);

            int ms = _int(m, 16, 0);

            long day = (long) D1970(yy, MM, dd);
            long MS = day * 86400000L;
            MS += (((long) HH) * 3600L + ((long) mm) * 60L + ss) * 1000L;
            MS += (long) ms;

            // 如果没有指定时区，那么用字符串中带有的时区信息，如果依然木有，则用系统默认时区
            long tzOffset;
            if (null == tz) {
                if (!Strings.isBlank(m.group(17))) {
                    tzOffset = Long.parseLong(m.group(19))
                               * 3600000L
                               * (m.group(18).charAt(0) == '-' ? -1 : 1);

                } else {
                    tzOffset = TimeZone.getDefault().getRawOffset();
                }
            }
            // 采用指定的时区
            else {
                tzOffset = tz.getRawOffset();
            }
            // 计算
            return MS - tzOffset;
        }
        throw Throwables.makeThrow("Unexpect date format '%s'", ds);
    }

    /**
     * 返回时间对象在一天中的毫秒数
     * 
     * @param d
     *            时间对象
     * 
     * @return 时间对象在一天中的毫秒数
     */
    public static long ms(Date d) {
        return ms(calendar(d));
    }

    /**
     * 返回时间对象在一天中的毫秒数
     * 
     * @param c
     *            时间对象
     * 
     * @return 时间对象在一天中的毫秒数
     */
    public static int ms(Calendar c) {
        int ms = c.get(Calendar.HOUR_OF_DAY) * 3600000;
        ms += c.get(Calendar.MINUTE) * 60000;
        ms += c.get(Calendar.SECOND) * 1000;
        ms += c.get(Calendar.MILLISECOND);
        return ms;
    }

    /**
     * 返回当前时间在一天中的毫秒数
     * 
     * @return 当前时间在一天中的毫秒数
     */
    public static int ms() {
        return ms(Calendar.getInstance());
    }

    /**
     * 根据一个当天的绝对毫秒数，得到一个时间字符串，格式为 "HH:mm:ss.EEE"
     * 
     * @param ms
     *            当天的绝对毫秒数
     * @return 时间字符串
     */
    public static String mss(int ms) {
        int sec = ms / 1000;
        ms = ms - sec * 1000;
        return secs((int) sec) + "." + Strings.alignRight(ms, 3, '0');
    }

    /**
     * 根据一个当天的绝对秒数，得到一个时间字符串，格式为 "HH:mm:ss"
     * 
     * @param ms
     *            当天的绝对秒数
     * @return 时间字符串
     */
    public static String secs(int sec) {
        int hh = sec / 3600;
        sec -= hh * 3600;
        int mm = sec / 60;
        sec -= mm * 60;
        return Strings.alignRight(hh, 2, '0')
               + ":"
               + Strings.alignRight(mm, 2, '0')
               + ":"
               + Strings.alignRight(sec, 2, '0');

    }

    /**
     * 返回时间对象在一天中的秒数
     * 
     * @param d
     *            时间对象
     * 
     * @return 时间对象在一天中的秒数
     */
    public static int sec(Date d) {
        Calendar c = calendar(d);
        int sec = c.get(Calendar.HOUR_OF_DAY) * 3600;
        sec += c.get(Calendar.MINUTE) * 60;
        sec += c.get(Calendar.SECOND);
        return sec;
    }

    /**
     * 返回当前时间在一天中的秒数
     * 
     * @return 当前时间在一天中的秒数
     */
    public static int sec() {
        return sec(now());
    }

    /**
     * 根据字符串得到时间对象
     * 
     * @param ds
     *            时间字符串
     * @return 时间
     * 
     * @see #ams(String)
     */
    public static Date D(String ds) {
        return date(ams(ds));
    }

    private static int _int(Matcher m, int index, int dft) {
        String s = m.group(index);
        if (Strings.isBlank(s))
            return dft;
        return Integer.parseInt(s);
    }

    // 常量数组，一年每个月多少天
    private static final int[] _MDs = new int[]{31,
                                                28,
                                                31,
                                                30,
                                                31,
                                                30,
                                                31,
                                                31,
                                                30,
                                                31,
                                                30,
                                                31};

    /**
     * 计算一个给定日期，距离 1970 年 1 月 1 日有多少天
     * 
     * @param yy
     *            年，比如 1999，或者 43
     * @param MM
     *            月，一月为 1，十二月为 12
     * @param dd
     *            日，每月一号为 1
     * @return 距离 1970 年 1 月 1 日的天数
     */
    public static int D1970(int yy, int MM, int dd) {
        // 转换成相对公元元年的年份
        // 如果给的年份小于 100，那么就认为是从 1970 开始算的年份
        int year = (yy < 100 ? yy + 1970 : yy);
        // 得到今年之前的基本天数
        int day = (year - 1970) * 365;
        // 补上闰年天数
        day += countLeapYear(year) - countLeapYear(1970);
        // 计算今年本月之前的月份
        int mi = Math.min(MM - 1, 11);
        boolean isLeapYear = isLeapYear(yy);
        for (int i = 0; i < mi; i++) {
            day += _MDs[i];
        }
        // 考虑今年是闰年的情况
        if (isLeapYear && MM > 2) {
            day++;
        }
        // 最后加上天数
        day += Math.min(dd, _MDs[mi]) - 1;

        // 如果是闰年且本月是 2 月
        if (isLeapYear && dd == 29) {
            day++;
        }

        // 如果是闰年并且过了二月
        return day;
    }

    /**
     * 根据毫秒数得到时间
     * 
     * @param ms
     *            时间的毫秒数
     * @return 时间
     */
    public static Date date(long ms) {
        return new Date(ms);
    }

    /**
     * 根据字符串得到时间
     * 
     * <pre>
     * 如果你输入了格式为 "yyyy-MM-dd HH:mm:ss"
     *    那么会匹配到秒
     *    
     * 如果你输入格式为 "yyyy-MM-dd"
     *    相当于你输入了 "yyyy-MM-dd 00:00:00"
     * </pre>
     * 
     * @param ds
     *            时间字符串
     * @return 时间
     */
    public static Calendar calendar(String ds) {
        return calendar(D(ds));
    }

    /**
     * 根据日期对象得到时间
     * 
     * @param d
     *            时间对象
     * @return 时间
     */
    public static Calendar calendar(Date d) {
        return caleadar(d.getTime());
    }

    /**
     * 根据毫秒数得到时间
     * 
     * @param ms
     *            时间的毫秒数
     * @return 时间
     */
    public static Calendar caleadar(long ms) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        return c;
    }

    /**
     * 把时间转换成格式为 y-M-d H:m:s.S 的字符串
     * 
     * @param d
     *            时间对象
     * @return 该时间的字符串形式 , 格式为 y-M-d H:m:s.S
     */
    public static String yMdHmsS(Date d) {
        return format(DF_DATE_TIME_MS, d);
    }

    /**
     * 把时间转换成格式为 yyyy-MM-dd HH:mm:ss 的字符串
     * 
     * @param d
     *            时间对象
     * @return 该时间的字符串形式 , 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String yyyyMMddHHmmss(Date d) {
        return format(DF_DATE_TIME, d);
    }

    /**
     * 把时间转换成格式为 yyyy-MM-dd HH:mm:ss 的字符串
     * 
     * @param d
     *            时间对象
     * @return 该时间的字符串形式 , 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String getYyyyMMddHHmmss(Date d) {
        return format(DF_DATE_TIMES, d);
    }
    
    /**
     * 把时间转换成格式为 yyyy-MM-dd 的字符串
     * 
     * @param d
     *            时间对象
     * @return 该时间的字符串形式 , 格式为 yyyy-MM-dd
     */
    public static String yyyyMMdd(Date d) {
        return format(DF_DATE, d);
    }

    /**
     * 将一个秒数（天中），转换成一个格式为 HH:mm:ss 的字符串
     * 
     * @param sec
     *            秒数
     * @return 格式为 HH:mm:ss 的字符串
     */
    public static String secondToHHmmss(int sec) {
        int[] ss = secondToInts(sec);
        return Strings.alignRight(ss[0], 2, '0')
               + ":"
               + Strings.alignRight(ss[1], 2, '0')
               + ":"
               + Strings.alignRight(ss[2], 2, '0');
    }

    /**
     * 以本周为基础获得某一周的时间范围
     * 
     * @param off
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * 
     * @return 时间范围(毫秒级别)
     * 
     * @see org.nutz.ztask.util.ZTasks#getDatesOfWeeks(long, int, int)
     */
    public static Date[] getDatesOfWeek(int off) {
        return getDatesOfWeek(System.currentTimeMillis(), off);
    }

    /**
     * 以某周为基础获得某一周的时间范围
     * 
     * @param base
     *            基础时间，毫秒
     * @param off
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * 
     * @return 时间范围(毫秒级别)
     * 
     * @see org.nutz.ztask.util.ZTasks#getDatesOfWeeks(long, int, int)
     */
    public static Date[] getDatesOfWeek(long base, int off) {
        return getDatesOfWeeks(base, off, off);
    }

    /**
     * 以本周为基础获得时间范围
     * 
     * @param offL
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * @param offR
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * 
     * @return 时间范围(毫秒级别)
     * 
     * @see org.nutz.ztask.util.ZTasks#getDatesOfWeeks(long, int, int)
     */
    public static Date[] getDatesOfWeeks(int offL, int offR) {
        return getDatesOfWeeks(System.currentTimeMillis(), offL, offR);
    }

    /**
     * 按周获得某几周周一 00:00:00 到周六 的时间范围
     * <p>
     * 它会根据给定的 offL 和 offR 得到一个时间范围
     * <p>
     * 对本函数来说 week(-3,-5) 和 week(-5,-3) 是一个意思
     * 
     * @param base
     *            基础时间，毫秒
     * @param offL
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * @param offR
     *            从本周偏移几周， 0 表示本周，-1 表示上一周，1 表示下一周
     * 
     * @return 时间范围(毫秒级别)
     */
    public static Date[] getDatesOfWeeks(long base, int offL, int offR) {
        int from = Math.min(offL, offR);
        int len = Math.abs(offL - offR);
        // 现在
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(base);

        Date[] re = new Date[2];

        // 计算开始
        c.setTimeInMillis(c.getTimeInMillis() + MS_WEEK * from);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        re[0] = c.getTime();

        // 计算结束
        c.setTimeInMillis(c.getTimeInMillis() + MS_WEEK * (len + 1) - 1000);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        re[1] = c.getTime();

        // 返回
        return re;
    }

    /**
     * 以给定的时间格式来安全的对时间进行格式化，并返回格式化后所对应的字符串
     * 
     * @param fmt
     *            时间格式
     * @param d
     *            时间对象
     * @return 格式化后的字符串
     */
    public static String format(DateFormat fmt, Date d) {
        return ((DateFormat) fmt.clone()).format(d);
    }

    /**
     * 以给定的时间格式来安全的对时间进行格式化，并返回格式化后所对应的字符串
     * 
     * @param fmt
     *            时间格式
     * @param d
     *            时间对象
     * @return 格式化后的字符串
     */
    public static String format(String fmt, Date d) {
        return new SimpleDateFormat(fmt).format(d);
    }
    
    public static String format(DF fmt,Date d) {
    	return fmt.format(d);
    }

    /**
     * 以给定的时间格式来安全的解析时间字符串，并返回解析后所对应的时间对象（包裹RuntimeException）
     * 
     * @param fmt
     *            时间格式
     * @param s
     *            时间字符串
     * @return 该时间字符串对应的时间对象
     */
    public static Date parseq(DateFormat fmt, String s) {
        try {
            return parse(fmt, s);
        }
        catch (ParseException e) {
            throw Throwables.wrapThrow(e);
        }
    }

    /**
     * 以给定的时间格式来安全的解析时间字符串，并返回解析后所对应的时间对象（包裹RuntimeException）
     * 
     * @param fmt
     *            时间格式
     * @param s
     *            时间字符串
     * @return 该时间字符串对应的时间对象
     */
    public static Date parseq(String fmt, String s) {
        try {
            return parse(fmt, s);
        }
        catch (ParseException e) {
            throw Throwables.wrapThrow(e);
        }
    }

    /**
     * 以给定的时间格式来安全的解析时间字符串，并返回解析后所对应的时间对象
     * 
     * @param fmt
     *            时间格式
     * @param s
     *            日期时间字符串
     * @return 该时间字符串对应的时间对象
     */
    public static Date parse(DateFormat fmt, String s) throws ParseException {
        return ((DateFormat) fmt.clone()).parse(s);
    }

    /**
     * 以给定的时间格式来安全的解析时间字符串，并返回解析后所对应的时间对象
     * 
     * @param fmt
     *            时间格式
     * @param s
     *            日期时间字符串
     * @return 该时间字符串对应的时间对象
     */
    public static Date parse(String fmt, String s) throws ParseException {
        return new SimpleDateFormat(fmt).parse(s);
    }

    /**
     * 为某个Date设置时分秒，常用于查询条件时的开始时间要设置时分秒为0：0：0，结束时间为23:59:59
     * @param date
     * @param hour
     * @param minute
     * @param second
     */
    public static void setHMS(Date date,int hour,int minute,int second) {
    	Calendar sc = Calendar.getInstance();
    	sc.setTime(date);
		sc.set(Calendar.HOUR, hour);
		sc.set(Calendar.MINUTE, minute);
		sc.set(Calendar.SECOND, second);		
    }
    
   
    public static void setStartHMS(Date date) {
    	setHMS(date,0,0,0);
    }
    
    public static void setEndHMS(Date date) {
    	setHMS(date,23,59,59);
    }
    
    /**
     * 为某个Date设置时分秒，常用于查询条件时的开始时间要设置时分秒为0：0：0，结束时间为23:59:59
     * @param date
     * @param hour
     * @param minute
     * @param second
     */
    public static String setHHMMSS(Date date,int hour,int minute,int second) {
    	Calendar sc = Calendar.getInstance();
    	sc.setTime(date);
		sc.set(Calendar.HOUR, hour);
		sc.set(Calendar.MINUTE, minute);
		sc.set(Calendar.SECOND, second);
		return yyyyMMddHHmmss(sc.getTime());
    }
    
   
    public static String getStartTime(Date date) {
    	return setHHMMSS(date,0,0,0);
    }
    
    public static String getEndTime(Date date) {
    	return setHHMMSS(date,23,59,59);
    }
    
    private static final DateFormat DF_DATE_TIME_MS = new SimpleDateFormat("y-M-d H:m:s.S");
    private static final DateFormat DF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DF_DATE_TIMES = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final long MS_DAY = 3600L * 24 * 1000;
    private static final long MS_WEEK = MS_DAY * 7;
    
    public static enum DF {
    	Y_M_D_H_m_s_S("y-M-d H:m:s.S"),
    	yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    	yyyy_MM_dd("yyyy-MM-dd"),
    	yyyy("yyyy"),
    	yyyyMMdd("yyyyMMdd"),
    	yyyy年MM月dd日("yyyy年MM月dd日"),
    	yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    	yyyy年MM月dd日_HH_mm_ss("yyyy年MM月dd日  HH:mm:ss"),
    	;
    	String fmt;
    	DF(String fmt) {
    		this.fmt = fmt;
    	}
    	
    	public String toString() {
    		return this.fmt;
    	}
    	
    	public String format(Date date) { 
    		return new SimpleDateFormat(toString()).format(date);
    	}
    	
    	public Date parse(String date) throws ParseException {
    		return new SimpleDateFormat(fmt).parse(date);
    	}
    }
    public static void main(String[] args) {
		//String date = Dates.format("yyMMddHHmmssSSS", new Date());
    	Date[] date = getDatesOfWeek(0);
		System.out.println(date[0]+":"+date[1]);
		Date start = new Date();
		System.out.println(getEndTime(start));
	}
}
