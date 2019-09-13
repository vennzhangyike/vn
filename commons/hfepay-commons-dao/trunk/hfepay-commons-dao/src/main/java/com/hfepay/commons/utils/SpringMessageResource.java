
package com.hfepay.commons.utils;

import java.util.Locale;

import com.hfepay.commons.base.message.MessageResource;

/**
 * 基于Spring消息源的消息资源器
 * @author Sam
 *
 */
public class SpringMessageResource implements MessageResource {
    
    
    public String getMessage(String code) {

        return Springs.getMessage(code);
    }
    
    
    public String getMessage(String code, Object[] args) {

        return Springs.getMessage(code, args);
    }
    
    
    public String getMessage(String code, Locale locale) {

        return Springs.getMessage(code, locale);
    }
    
    
    public String getMessage(String code, Locale locale, Object[] args) {

        return Springs.getMessage(code, locale, args);
    }
    
}
