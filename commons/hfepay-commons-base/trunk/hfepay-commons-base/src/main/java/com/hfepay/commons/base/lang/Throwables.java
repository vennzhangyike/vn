package com.hfepay.commons.base.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * 异常工具类
 * @author Sam
 *
 */
public class Throwables {
	
 
	/**
	 * 将异常堆栈信息转成字符串
	 * @param throwable 异常信息
	 * @return
	 */
	public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
	
	/**
	 * 组合异常
	 * @param es
	 * @return
	 */
	public static ComboException comboThrow(Throwable... es) {
        ComboException ce = new ComboException();
        for (Throwable e : es)
            ce.add(e);
        return ce;
    }

    /**
     * 生成一个未实现的运行时异常
     * 
     * @return 一个未实现的运行时异常
     */
    public static RuntimeException noImplement() {
        return new RuntimeException("Not implement yet!");
    }

    /**
     * 生成一个不可能的运行时异常
     * 
     * @return 一个不可能的运行时异常
     */
    public static RuntimeException impossible() {
        return new RuntimeException("r u kidding me?! It is impossible!");
    }

    /**
     * 根据格式化字符串，生成运行时异常
     * 
     * @param format
     *            格式
     * @param args
     *            参数
     * @return 运行时异常
     */
    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }


    /**
     * 将抛出对象包裹成运行时异常，并增加自己的描述
     * 
     * @param e
     *            抛出对象
     * @param fmt
     *            格式
     * @param args
     *            参数
     * @return 运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e,
                                             String fmt,
                                             Object... args) {
        return new RuntimeException(String.format(fmt, args), e);
    }

    /**
     * 用运行时异常包裹抛出对象，如果抛出对象本身就是运行时异常，则直接返回。
     * <p>
     * 如果是 InvocationTargetException，那么将其剥离，只包裹其 TargetException
     * 
     * @param e
     *            抛出对象
     * @return 运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e) {
        if (e instanceof RuntimeException)
            return (RuntimeException) e;
        if (e instanceof InvocationTargetException)
            return wrapThrow(((InvocationTargetException) e).getTargetException());
        return new RuntimeException(e);
    }



    public static Throwable unwrapThrow(Throwable e) {
        if (e == null)
            return null;
        if (e instanceof InvocationTargetException) {
            InvocationTargetException itE = (InvocationTargetException) e;
            if (itE.getTargetException() != null)
                return unwrapThrow(itE.getTargetException());
        }
        if (e instanceof RuntimeException && e.getCause() != null)
            return unwrapThrow(e.getCause());
        return e;
    }
}
