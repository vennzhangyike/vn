package com.hfepay.commons.base.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 编码工具类
 * @author Sam
 *
 */
public class Encoding {

	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String UTF8 = "UTF-8";
	
 
    
    public static String defaultEncoding(){
        return Charset.defaultCharset().name();
    }
    
    public static String encode(CharSequence str,String encoding) {
    	Assert.notNull(str,"the encode string can not be null!");
    	if (Strings.isNotEmpty(str)) {
            try {
                return new String(str.toString().getBytes(), encoding);
            } catch (UnsupportedEncodingException e) {
                throw Throwables.wrapThrow(e);
            }
        }
        return str.toString();
    }
    
    public static String utf8(CharSequence str) {
    	return encode(str,UTF8);
    }
    
    public static String iso8859(CharSequence str) {
    	return encode(str,ISO_8859_1);
    }
    
    public static String gbk(CharSequence str) {
    	return encode(str,GBK);
    }
    
    public static String gb2312(CharSequence str) {
    	return encode(str,GB2312);
    }
    
    public String get(String str) {
    	if (Strings.isNotEmpty(str)) {
            try {
                return new String(str.getBytes(), this.toString());
            } catch (UnsupportedEncodingException e) {
                throw Throwables.wrapThrow(e);
            }
        }
        return str;
    }
    
      
    
    public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public static void main(String[] args) {
    	System.out.println(Encoding.utf8("你好"));
    }

}
