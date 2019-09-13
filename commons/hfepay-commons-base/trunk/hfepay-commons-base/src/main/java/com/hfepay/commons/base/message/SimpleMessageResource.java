
package com.hfepay.commons.base.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Assert;

 
 
/**
 * 使用ResourceBundle实现的消息资源器
 * @author Sam
 * @see #java.util.ResourceBundle
 */
public class SimpleMessageResource implements MessageResource { 
	
    private String path;
    
    private Map<Locale,ResourceBundle> rbs = Maps.newMap();
    
    private Locale defaultLocale = Locale.CHINA;
    
    public SimpleMessageResource(String path) {

        this(path, Locale.CHINA);
    }
    
    public SimpleMessageResource(String path, Locale l) {

        this.path = path;
        this.defaultLocale = l;
        rbs.put(l,ResourceBundle.getBundle(path, l)); 
    }

    
    public String getMessage(String code) { 
        return getString(code);
    }
    
    public String getMessage(String code, Object[] args) {
    	return format(getString(code), args);
    }
    
    public String getMessage(String code, Locale locale) {
    	return getString(locale,code);
    }
    
    public String getMessage(String code, Locale locale, Object[] args) {
    	String msg = getString(locale,code);
    	return format(msg,args);
    }
    
    protected String format(String msg, Object[] args) {
        MessageFormat mf = new MessageFormat(msg);
        return mf.format(args);
    }    
    
    protected void addBundle(Locale l,String path) {
    	 rbs.put(l,ResourceBundle.getBundle(path, l));
    }
    
    protected String getString(Locale l,String key) {
    	ResourceBundle rb = rbs.get(l); 
    	if (rb == null) {
    		addBundle(l,path);    		
    	}
    	Assert.notNull(rb, "指定的消息资源路径不存在或者文件不存在");
    	return rb.getString(key);    	
    }
    
    protected String getString(String key) {
    	ResourceBundle rb = rbs.get(defaultLocale);
    	Assert.notNull(rb, "指定的消息资源路径不存在或者文件不存在");
    	try {
    		return rb.getString(key);    	
    	} catch (Exception ex) {
    		return "";
    	}
    } 
    
    public static void main(String[] s) {

        
    }
    
}
