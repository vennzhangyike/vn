
package com.hfepay.commons.base.message;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Strings;

/**
 * 基于多个物理消息源的消息资源器
 * @author Sam
 *
 */
public class ComboMessageResource implements MessageResource {
    
    private List<MessageResource> resources = Lists.newList();
    
    
    public String getMessage(String code) {

        for (MessageResource r : resources) {
            String msg = r.getMessage(code);
            if (Strings.isNotEmpty(msg))
                return msg;
        }
        return null;
    }
    
    
    public String getMessage(String code, Object[] args) {

        for (MessageResource r : resources) {
            String msg = r.getMessage(code, args);
            if (Strings.isNotEmpty(msg))
                return msg;
        }
        return null;
    }
    
    
    public String getMessage(String code, Locale locale) {

        for (MessageResource r : resources) {
            String msg = r.getMessage(code, locale);
            if (StringUtils.isNotEmpty(msg))
                return msg;
        }
        return null;
    }
    
    
    public String getMessage(String code, Locale locale, Object[] args) {

        for (MessageResource r : resources) {
            String msg = r.getMessage(code, locale, args);
            if (Strings.isNotEmpty(msg))
                return msg;
        }
        return null;
    }
    
    public void setResources(List<MessageResource> resources) {

        this.resources = resources;
    }
    
    public List<MessageResource> getResources() {

        return resources;
    }
    
}
